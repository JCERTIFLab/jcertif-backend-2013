package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.FORBIDDEN;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.POST;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;
import static play.test.Helpers.running;
import static play.test.Helpers.status;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.objects.access.SessionDB;
import models.util.Constantes;

import org.junit.Assert;
import org.junit.Test;

import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import util.TestUtils;

import com.mongodb.BasicDBObject;

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
//                    assertThat(contentAsString(result)).isEqualTo("[ { \"id\" : \"101\" , \"title\" : \"title 1\" , \"summary\" : \"summary 1\" , \"description\" : \"description 1\" , \"status\" : \"status 1\" , \"keyword\" : \"keyword 1\" , \"category\" : [ \"HTML 5\" , \"Android\"] , \"start\" : \"12/02/2013 10:22\" , \"end\" : \"16/02/2013 10:23\" , \"speakers\" : [ \"11\" , \"12\"]} ,{ \"id\" : \"102\" , \"title\" : \"title 2\" , \"summary\" : \"summary 2\" , \"description\" : \"description 2\" , \"status\" : \"Status2\" , \"keyword\" : \"keyword 2\" , \"category\" : [ \"HTML 5\" , \"Android\"] , \"start\" : \"12/02/2013 10:22\" , \"end\" : \"16/02/2013 10:23\" , \"speakers\" : [ \"21\" , \"22\"]}]");
                    
                } catch (IOException e) {
                	Assert.fail(e.getMessage());
                }
            }
        });
    }

    /**
     * Tests fonctionnels du service deleteSession  
    **/
    
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
    public void test_delete_session_invalid_inner_json() {
    	Logger.info("*** DEBUT -> test_delete_session_invalid_inner_json ***");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Map<String, String> params = new HashMap<String, String>();
                params.put("", "101");
            	Result result = callAction(routes.ref.SessionController.removeSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
            	Logger.info("tracked result is : " + status(result));
            	assertThat(status(result)).isEqualTo(BAD_REQUEST);
            }
        });
        Logger.info("*** FIN -> test_delete_session_invalid_inner_json ***");
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
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
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
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
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
                		TestUtils.updateDatabase("test/data/session.js");
                		Map<String, String> params = new HashMap<String, String>();
                        params.put("id", "10000000");                        
                        Logger.info("La demande de suppession ne doit concernée que des sessions existantes.");
                        Result result = callAction(routes.ref.SessionController.removeSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(status(result)).isEqualTo(NOT_FOUND);
                        Logger.info("*** FIN -> test_session_deletion_unregistred_session_id ***");
					} catch (Exception e) {
						// TODO: handle exception
						Logger.error("Une erreur est survenue lors du test de l'existence de la session relative au inner ID", e);
					}
            }
        });
    }

    @Test
    public void test_session_delletion_OK() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_all_OK ***");
                	try {
                		Logger.info("Tous les inner params sont valides.");
                		TestUtils.updateDatabase("test/data/session.js");
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

    /**
     * Tests fonctionnels du service updateSession  
    **/
    
    @Test
    public void test_unAuthUser_session_update_forbidden() {
    	Logger.info("*** DEBUT -> test_unAuthUser_session_update_forbidden ***");
    	Logger.info("Seul le user avec le role admin doit pouvoir mettre a jour une session");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Map<String, String> params = new HashMap<String, String>();
                params.put("id", "101");
            	Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("user", "normal").withJsonBody(Json.toJson(params), POST));
                assertThat(status(result)).isEqualTo(FORBIDDEN);
            }
        });
        Logger.info("*** FIN -> test_unAuthUser_session_update_forbidden ***");
    }
    
    @Test
    public void test_update_session_invalid_inner_json() {
    	Logger.info("*** DEBUT -> test_update_session_invalid_inner_json ***");
    	Logger.info("Les inner params doivent former un objet json valide");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Map<String, String> params = new HashMap<String, String>();
                params.put("", "101");
            	Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
            	Logger.info("tracked result is : " + status(result));
            	assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
            }
        });
        Logger.info("*** FIN -> test_update_session_invalid_inner_json ***");
    }
    
    @Test
    public void test_update_session_invalid_id_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_id_null ***");
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", "");
                    Logger.info("Le format json d'entrée doit être valide (id doit etre un number)");
                    Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                    assertThat(contentType(result)).isEqualTo("application/json");
                    assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                    Logger.info("*** FIN -> test_update_session_invalid_id_null ***");
            }
        });
    }
    
    @Test
    public void test_update_session_invalid_id() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_id ***");
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", "abc1_");
                    Logger.info("Le format json d'entrée doit être valide (id doit etre un number)");
                    Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                    assertThat(contentType(result)).isEqualTo("application/json");
                    assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                    Logger.info("*** FIN -> test_update_session_invalid_id ***");
            }
        });
    }
    
    @Test
    public void test_update_session_invalid_title_empty() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_title_empty ***");
                	Logger.info("Le title d'une session ne peut etre vide");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "[HTML 5]");
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", "[13]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_title_empty ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }

    @Test
    public void test_update_session_invalid_title_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_title_null ***");
                	Logger.info("Le title d'une session ne peut etre ansent du inner json ");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "[HTML]");
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", "[13]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_title_null ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_update_session_invalid_summary_empty() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_summary_empty ***");
                	Logger.info("Le summary d'une session ne peut etre vide");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "[HTML]");
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", "[13]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_summary_empty ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_update_session_invalid_summary_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_summary_null ***");
                	Logger.info("Le summary d'une session ne peut etre ansent du inner json ");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "[HTML]");
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", "[13]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_summary_null ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }

    @Test
    public void test_update_session_invalid_description_empty() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_description_empty ***");
                	Logger.info("La description d'une session ne peut etre vide");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "[HTML]");
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", "[13]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_description_empty ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_update_session_invalid_description_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_description_null ***");
                	Logger.info("Le summary d'une session ne peut etre ansent du inner json ");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "[HTML]");
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", "[13]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_description_null ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_update_session_invalid_status_empty() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_status_empty ***");
                	Logger.info("Le status d'une session ne peut etre vide");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "");
                        params.put("keyword", "keyword 2");
                        params.put("category", "[HTML]");
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", "[13]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_status_empty ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_update_session_invalid_status_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_status_null ***");
                	Logger.info("Le status d'une session ne peut etre absent du inner json ");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "[HTML]");
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", "[13]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_status_null ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
	
    @Test
    public void test_update_session_invalid_keyword_empty() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_keyword_empty ***");
                	Logger.info("Le keyword d'une session ne peut etre vide");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "");
                        params.put("category", "[HTML]");
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", "[13]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_keyword_empty ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_update_session_invalid_keyword_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_keyword_null() ***");
                	Logger.info("Le keyword d'une session ne peut etre absent du inner json ");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("category", "[HTML]");
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", "[13]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_keyword_null() ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }

    @Test
    public void test_update_session_invalid_category_empty() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_category_empty ***");
                	Logger.info("La category d'une session ne peut etre vide");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "");
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", "[13]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_category_empty ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_update_session_invalid_category_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_category_null() ***");
                	Logger.info("Le keyword d'une session ne peut etre absent du inner json ");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", "[13]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_category_null() ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }

    @Test
    public void test_update_session_invalid_start_empty() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_start_empty ***");
                	Logger.info("La start date d'une session ne peut etre vide");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "[category 2]");
                        params.put("start", "");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", "[13]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_start_empty ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_update_session_invalid_satrt_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_satrt_null() ***");
                	Logger.info("La start date d'une session ne peut etre absent du inner json ");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "[category 2]");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", "[13]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_satrt_null() ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }

    @Test
    public void test_update_session_invalid_end_empty() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_end_empty ***");
                	Logger.info("La end date d'une session ne peut etre vide");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "[category 2]");
                        params.put("start", "12/12/2013 10:23");
                        params.put("end", "");
                        params.put("speakers", "[13]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_end_empty ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_update_session_invalid_send_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_send_null() ***");
                	Logger.info("La start date d'une session ne peut etre absent du inner json ");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "[category 2]");
                        params.put("start", "16/12/2013 10:23");
                        params.put("speakers", "[13]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_send_null() ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_update_session_invalid_date_start_format() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_date_start_format ***");
                	Logger.info("Le format de la start date d'une session doit etre conforme à : JJ/MM/AAAA hh:mm");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "['category', 'HTML 5']");
                        params.put("start", "5/6/2013 7:23");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", "[13, 34]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_date_start_format ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_update_session_invalid_date_end_format() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_date_end_format() ***");
                	Logger.info("La start date d'une session ne peut etre absent du inner json ");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "['category', 'HTML 5']" );
                        params.put("start", "16/12/2013 10:23");
                        params.put("end", "7/4/2013 3:43");
                        params.put("speakers", "[13, 34]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_invalid_date_end_format() ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_update_session_inconsitent_start_date() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_inconsitent_start_date() ***");
                	Logger.info("La start date d'une session ne peut etre supérieur ou égal a sa end date ");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "['category', 'HTML 5']" );
                        params.put("start", "16/12/2013 10:23");
                        params.put("end", "07/04/2013 3:43");
                        params.put("speakers", "[13, 34]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_inconsitent_start_date() ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_update_session_unregistred_session() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_unregistred_session() ***");
                	Logger.info("Ne peut être mise a jour qu'une session déjà enrégistrée ");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "100002");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "['category', 'HTML 5']" );
                        params.put("start", "16/12/2013 10:23");
                        params.put("end", "07/04/2013 3:43");
                        params.put("speakers", "[13, 34]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_unregistred_session() ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_update_session_unregistred_session_status() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_unregistred_session_status() ***");
                	Logger.info("Le status d'une session doit faire partie de la liste des status session ");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "status 89");
                        params.put("keyword", "keyword 2");
                        params.put("category", "['category', 'HTML 5']" );
                        params.put("start", "16/12/2013 10:23");
                        params.put("end", "07/04/2013 3:43");
                        params.put("speakers", "[13, 34]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(INTERNAL_SERVER_ERROR);
                        Logger.info("*** FIN -> test_update_session_unregistred_session_status() ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }

    @Test
    public void test_update_session_all_OK() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_all_OK() ***");
                	Logger.info("Tous les inner params sont valides ");
                	try {
                		MongoDatabase.getInstance().loadDbWithData("test/data/session_update.js");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", "['category', 'HTML 5']" );
                        params.put("start", "16/10/2013 10:23");
                        params.put("end", "27/12/2013 03:43");
                        params.put("speakers", "[13, 34]");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(contentType(result)).isEqualTo("application/json");
                        assertThat(status(result)).isEqualTo(OK);
                        assertThat(contentAsString(result)).contains("OK");
                        Logger.info("*** FIN -> test_update_session_all_OK() ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_session_update_OK() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_update_OK ***");
                	try {
                		Logger.info("Tous les inner params sont valides.");
                		TestUtils.updateDatabase("test/data/session.js");
                		Map<String, String> params = new HashMap<String, String>();
                        params.put("id", "101");
                        params.put("title", "title 3");
                        params.put("summary", "summary 3");
                        params.put("description", "description 3");
                        params.put("status", "status 3");
                        params.put("keyword", "keyword 3");
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(status(result)).isEqualTo(OK);
                        
                        Logger.info("Vérification que la session a bien été modifiée en base de données");
    	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(Constantes.COLLECTION_SESSION, new BasicDBObject().append("id", "101"));
    	                Assert.assertTrue(null != dbObjects);
    	                Assert.assertEquals(1,dbObjects.size());
    	                BasicDBObject dbObject = dbObjects.get(0);
    	                Assert.assertEquals("101",dbObject.getString("id"));
    	                Assert.assertEquals("title 3",dbObject.getString("title"));
    	                Assert.assertEquals("summary 3",dbObject.getString("summary"));
    	                Assert.assertEquals("description 3",dbObject.getString("description"));
    	                Assert.assertEquals("status 3",dbObject.getString("status"));
    	                Assert.assertEquals("keyword 3",dbObject.getString("keyword"));
    	                Assert.assertEquals("12/02/2013 10:22",dbObject.getString("start"));
    	                Assert.assertEquals("16/02/2013 10:23",dbObject.getString("end"));
                        Logger.info("*** FIN -> test_session_update_OK ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise à jour d'une session", e);
						Assert.fail("Une erreur est survenue lors du test de mise à jour d'une session");
					}
            }
        });
    }
    
    @Test
    public void test_unAuthUser_session_update_forbidden() {
    	Logger.info("*** DEBUT -> test_unAuthUser_session_update_forbidden ***");
    	Logger.info("Seul le user avec le role admin doit pouvoir modifier une session");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Map<String, String> params = new HashMap<String, String>();
                params.put("id", "101");
            	Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("user", "normal").withJsonBody(Json.toJson(params), POST));
                assertThat(status(result)).isEqualTo(FORBIDDEN);
            }
        });
        Logger.info("*** FIN -> test_unAuthUser_session_update_forbidden ***");
    }
    
    @Test
    public void test_session_update_unregistred_session_id() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_update_unregistred_session_id ***");
                	try {
                		TestUtils.updateDatabase("test/data/session.js");
                		Map<String, String> params = new HashMap<String, String>();
                        params.put("id", "10000000");                        
                        Logger.info("La demande de mise à jour ne doit concernée que des sessions existantes.");
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(status(result)).isEqualTo(NOT_FOUND);
                        Logger.info("*** FIN -> test_session_update_unregistred_session_id ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise à jour d'une session", e);
						Assert.fail("Une erreur est survenue lors du test de mise à jour d'une session");
					}
            }
        });
    }
    
}