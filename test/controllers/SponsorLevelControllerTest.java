package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.CONFLICT;
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

import models.util.Constantes;

import org.codehaus.jackson.JsonNode;
import org.junit.Assert;
import org.junit.Test;

import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import util.TestUtils;

import com.mongodb.BasicDBObject;

/**
 * <p>Tests fonctionnels pour le controleur {@link SponsorLevelController}</p>
 * 
 * @author Martial SOMDA
 *
 */
public class SponsorLevelControllerTest extends ReferentielControllerTest {


	public String getCreateURL() {
		return "/ref/sponsorlevel/new";
	}
	

	public String getRemoveURL() {
		return "/ref/sponsorlevel/remove";
	}
	
	@Test
    public void test_sponsorlevel_list() throws IOException {

        running(fakeApplication(), new Runnable() {
            public void run() {
                try {
                	TestUtils.updateDatabase("test/data/sponsor_level.js");
                    Result result = route(fakeRequest(GET, "/ref/sponsorlevel/list"));
                    assertThat(status(result)).isEqualTo(OK);
                    assertThat(contentType(result)).isEqualTo("application/json");
                    JsonNode jsonNode = Json.parse(contentAsString(result));
                    Assert.assertEquals(3, jsonNode.size());

                } catch (IOException e) {
                    Assert.fail(e.getMessage());
                }
            }
        });
    }
	
	@Test
    public void test_sponsorlevel_new_ok() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Logger.info("Création d'un nouveau niveau de partenariat");
                Map<String, String> params = new HashMap<String, String>();
                params.put("label", "HTTT");
                Result result = callAction(routes.ref.SponsorLevelController.addSponsorLevel(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
                assertThat(status(result)).isEqualTo(OK);

                Logger.info("Vérification que le nouveau niveau de patenariat est bien présente en base de données");
                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(Constantes.COLLECTION_SPONSOR_LEVEL, new BasicDBObject().append("label", "HTTT"));
                Assert.assertTrue(null != dbObjects);
                Assert.assertEquals(1,dbObjects.size());
                Assert.assertEquals("HTTT",dbObjects.get(0).get("label"));

            }
        });
    }
	
	@Test
    public void test_sponsorlevel_new_conflict() {
		running(fakeApplication(), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/sponsor_level.js");
					Logger.info("Création d'un nouveau niveau de partenariat");
	                Map<String, String> params = new HashMap<String, String>();
	                params.put("label", "SponsorLevel3");
	                Result result = callAction(routes.ref.SponsorLevelController.addSponsorLevel(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                Logger.info(Integer.toString(status(result)));
	                assertThat(status(result)).isEqualTo(CONFLICT);
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
            }
        });
    }
	
	@Test
    public void test_sponsorlevel_remove_notfound() {
		running(fakeApplication(), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/sponsor_level.js");
					Logger.info("Suppression d'un niveau de partenariat");
	                Map<String, String> params = new HashMap<String, String>();
	                params.put("label", "HTTP");
	                Result result = callAction(routes.ref.SponsorLevelController.removeSponsorLevel(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                Logger.info(Integer.toString(status(result)));
	                assertThat(status(result)).isEqualTo(NOT_FOUND);
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
            }
        });
    }
	
	@Test
    public void test_sponsorlevel_remove_ok() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/sponsor_level.js");
					Logger.info("Suppression d'un niveau de partenariat");
	                Map<String, String> params = new HashMap<String, String>();
	                params.put("label", "SponsorLevel2");
	                Result result = callAction(routes.ref.SponsorLevelController.removeSponsorLevel(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                assertThat(status(result)).isEqualTo(OK);

	                Logger.info("Vérification que le niveau de partenariat a bien été supprimmé en base de données");
	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(Constantes.COLLECTION_SPONSOR_LEVEL, new BasicDBObject().append("label", "SponsorLevel2"));
	                Assert.assertTrue(null != dbObjects);
	                Assert.assertEquals(0,dbObjects.size());
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
            }
        });
    }
}
