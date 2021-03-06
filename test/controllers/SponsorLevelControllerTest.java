package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.CONFLICT;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
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

import models.util.TestConstantes;

import org.codehaus.jackson.JsonNode;
import org.junit.Assert;
import org.junit.Test;

import play.Logger;
import play.api.mvc.HandlerRef;
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


	@Override
	public HandlerRef<?> getCreateURL() {
		return routes.ref.SponsorLevelController.addSponsorLevel();
	}
	
	@Override
	public HandlerRef<?> getRemoveURL() {
		return routes.ref.SponsorLevelController.removeSponsorLevel();
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
                    TestUtils.updateDatabase("test/data/purge.js");
                } catch (IOException e) {
                    Assert.fail(e.getMessage());
                }
            }
        });
    }
	
	@Test
    public void test_sponsorlevel_new_ok() {
		Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                Logger.info("Création d'un nouveau niveau de partenariat");
                try {
					TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@sponsor.com");
	                params.put("label", "HTTT");
	                Result result = callAction(routes.ref.SponsorLevelController.addSponsorLevel(), fakeRequest().withJsonBody(Json.toJson(params)));
	                assertThat(status(result)).isEqualTo(OK);

	                Logger.info("Vérification que le nouveau niveau de patenariat est bien présente en base de données");
	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_SPONSOR_LEVEL, new BasicDBObject().append("label", "HTTT"));
	                Assert.assertTrue(null != dbObjects);
	                Assert.assertEquals(1,dbObjects.size());
	                Assert.assertEquals("HTTT",dbObjects.get(0).get("label"));
	                Assert.assertEquals("01",dbObjects.get(0).get("version"));
	                Assert.assertEquals("false",dbObjects.get(0).get("deleted"));
	                TestUtils.updateDatabase("test/data/purge.js");
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
				
            }
        });
    }
	
	@Test
    public void test_sponsorlevel_new_conflict() {
		Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
		running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/sponsor_level.js");
					Logger.info("Création d'un nouveau niveau de partenariat");
					TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@sponsor.com");
	                params.put("label", "SponsorLevel3");
	                Result result = callAction(routes.ref.SponsorLevelController.addSponsorLevel(), fakeRequest().withJsonBody(Json.toJson(params)));
	                assertThat(status(result)).isEqualTo(CONFLICT);
	                TestUtils.updateDatabase("test/data/purge.js");
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
            }
        });
    }
	
	@Test
    public void test_sponsorlevel_remove_notfound() {
		Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
		running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/sponsor_level.js");
					Logger.info("Suppression d'un niveau de partenariat");
					TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@sponsor.com");
	                params.put("label", "HTTP");
	                Result result = callAction(routes.ref.SponsorLevelController.removeSponsorLevel(), fakeRequest().withJsonBody(Json.toJson(params)));
	                assertThat(status(result)).isEqualTo(NOT_FOUND);
	                TestUtils.updateDatabase("test/data/purge.js");
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
            }
        });
    }
	
	@Test
    public void test_sponsorlevel_remove_ok() {
		Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/sponsor_level.js");
					Logger.info("Suppression d'un niveau de partenariat");
					TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@sponsor.com");
	                params.put("label", "SponsorLevel2");
	                params.put("version", "01");
	                params.put("deleted", "false");
	                
	                Result result = callAction(routes.ref.SponsorLevelController.removeSponsorLevel(), fakeRequest().withJsonBody(Json.toJson(params)));
	                assertThat(status(result)).isEqualTo(OK);

	                Logger.info("Vérification que le niveau de partenariat a bien été supprimmé en base de données");
	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_SPONSOR_LEVEL, new BasicDBObject().append("label", "SponsorLevel2"));
	                Assert.assertTrue(null != dbObjects);
	                Assert.assertEquals(1,dbObjects.size());
	                Assert.assertEquals("02",dbObjects.get(0).getString("version"));
	                Assert.assertEquals("true",dbObjects.get(0).getString("deleted"));
	                TestUtils.updateDatabase("test/data/purge.js");
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
            }
        });
    }
}
