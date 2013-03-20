package controllers;

import org.junit.Assert;
import org.junit.Test;
import util.TestUtils;

import models.database.MongoDatabase;
import models.objects.access.SessionDB;
import models.util.Constantes;

import play.Logger;
import play.mvc.Result;
import play.libs.Json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class SessionControllerTest {

    @Test
    public void test_listSession() throws java.io.IOException {
        running(fakeApplication(), new Runnable() {
            public void run() {
                try {
                    TestUtils.updateDatabase("test/data/session.js");
                    Result result = route(fakeRequest(GET, "/session/list"));
                    assertThat(status(result)).isEqualTo(OK);
                    assertThat(contentType(result)).isEqualTo("application/json");
                    assertThat(contentAsString(result)).isEqualTo("[ { \"id\" : \"101\" , \"title\" : \"title 1\" , \"summary\" : \"summary 1\" , \"description\" : \"description 1\" , \"status\" : \"status 1\" , \"keyword\" : \"keyword 1\" , \"category\" : [ \"HTML 5\" , \"Android\"] , \"start\" : \"12/02/2013 10:22\" , \"end\" : \"16/02/2013 10:23\" , \"speakers\" : [ \"11\" , \"12\"]} , { \"id\" : \"102\" , \"title\" : \"title 2\" , \"summary\" : \"summary 2\" , \"description\" : \"description 2\" , \"status\" : \"status 2\" , \"keyword\" : \"keyword 2\" , \"category\" : [ \"HTML 5\" , \"Android\"] , \"start\" : \"12/02/2013 10:22\" , \"end\" : \"16/02/2013 10:23\" , \"speakers\" : [ \"21\" , \"22\"]}]");
                } catch (IOException e) {
                	Assert.fail(e.getMessage());
                }
            }
        });
    }

    @Test
    public void test_unAuthUser_session_deletion_forbidden() {
    	Logger.info("*** DEBUT -> test_session_deletion_forbidden ***");
    	Logger.info("Seul le user avec le role admin doit pouvoir supprimer une session");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Map<String, String> params = new HashMap<String, String>();
                params.put("id", "101");
            	Result result = callAction(routes.ref.SessionController.removeSession(), fakeRequest().withSession("user", "normal").withJsonBody(Json.toJson(params), POST));
                assertThat(status(result)).isEqualTo(FORBIDDEN);
            }
        });
        Logger.info("*** FIN -> test_session_deletion_forbidden ***");
    }
    
    @Test
    public void test_invalid_inner_json() {
    	Logger.info("*** DEBUT -> test_invalid_inner_json ***");
    	Logger.info("Seul le user avec le role admin doit pouvoir supprimer une session");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Map<String, String> params = new HashMap<String, String>();
                params.put("", "101");
            	Result result = callAction(routes.ref.SessionController.removeSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
            	Logger.info("tracked result is : " + status(result));
            	assertThat(status(result)).isEqualTo(BAD_REQUEST);
            }
        });
        Logger.info("*** FIN -> test_invalid_inner_json ***");
    }
    
    @Test
    public void test_session_deletion_inner_json_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_deletion_inner_json_null ***");
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", "");
                    Logger.info("Le format json d'entrée doit être valide (id pas empty ni null)");
                    Result result = callAction(routes.ref.SessionController.removeSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                    assertThat(contentType(result)).isEqualTo("application/json");
                    assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                    Logger.info("*** FIN -> test_session_deletion_inner_json_null ***");
            }
        });
    }
    
    @Test
    public void test_session_deletion_invalid_id() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_deletion_invalid_id ***");
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", "abc1_");
                    Logger.info("Le format json d'entrée doit être valide (id doit etre un number)");
                    Result result = callAction(routes.ref.SessionController.removeSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                    assertThat(contentType(result)).isEqualTo("application/json");
                    assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                    Logger.info("*** FIN -> test_session_deletion_invalid_id ***");
            }
        });
    }
    
    @Test
    public void test_session_deletion_unregistred_session_id() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_deletion_unregistred_session_id ***");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session.js");
                		Map<String, String> params = new HashMap<String, String>();
                        params.put("id", "10000000");                        
                        Logger.info("La demande de suppession ne doit concernée que des sessions existantes.");
                        Result result = callAction(routes.ref.SessionController.removeSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_session_deletion_unregistred_session_id ***");
					} catch (Exception e) {
						// TODO: handle exception
						Logger.error("Une erreur est survenue lors du test de l'existence de la session relative au inner ID", e);
					}
            }
        });
    }

    @Test
    public void test_session_all_OK() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_all_OK ***");
                	try {
                		Logger.info("Tous les inner params sont valides.");
                		MongoDatabase.getInstance().loadDbWithData("test/data/session.js");
                		assertThat(SessionDB.getInstance().get("101") != null );
                		Map<String, String> params = new HashMap<String, String>();
                        params.put("id", "101");
                        Result result = callAction(routes.ref.SessionController.removeSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(status(result)).isEqualTo(OK);
                        assertThat(SessionDB.getInstance().get("101") == null );
                        Logger.info("*** FIN -> test_session_all_OK ***");
					} catch (Exception e) {
						// TODO: handle exception
						Logger.error("Une erreur est survenue lors du test de l'existence de la session relative au inner ID", e);
					}
            }
        });
    }
    
}