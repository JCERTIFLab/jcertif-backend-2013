package controllers;

import static org.fest.assertions.Assertions.assertThat;
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

import org.junit.Test;

import com.mongodb.BasicDBObject;

import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import util.TestUtils;

/**
 * <p>Tests fonctionnels pour le controleur {@link SponsorLevelController}</p>
 * 
 * @author Martial SOMDA
 *
 */
public class SponsorLevelControllerTest extends ReferentielControllerTest{

	@Override
	public String getCreateURL() {
		return "/ref/sponsorlevel/new";
	}
	
	@Override
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
                    assertThat(contentAsString(result).toLowerCase().trim().equals("[ {\"code\" : \"SponsorLevel1\", \"label\" : \"First SponsorLevel\"} , " +
                    		"{\"code\" : \"SponsorLevel2\", \"label\" : \"Second SponsorLevel\"} , " +
                    		"{\"code\" : \"SponsorLevel3\", \"label\" : \"Third SponsorLevel\"}]".toLowerCase().trim()));

                } catch (IOException e) {
                    junit.framework.Assert.fail(e.getMessage());
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
                params.put("code", "HTTT");
                params.put("label", "HTTT");
                Result result = callAction(routes.ref.SponsorLevelController.addSponsorLevel(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
                assertThat(status(result)).isEqualTo(OK);

                Logger.info("Vérification que le nouveau niveau de patenariat est bien présente en base de données");
                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(Constantes.COLLECTION_SPONSOR_LEVEL, new BasicDBObject().append("code", "HTTT"));
                assertThat(null != dbObjects && dbObjects.size() == 1);
                assertThat("HTTT".equals(dbObjects.get(0).get("code")));
                assertThat("HTTT".equals(dbObjects.get(0).get("label")));

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
	                params.put("code", "SponsorLevel3");
	                params.put("label", "Third SponsorLevel");
	                Result result = callAction(routes.ref.SponsorLevelController.addSponsorLevel(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                assertThat(status(result)).isEqualTo(Http.Status.CONFLICT);
				} catch (IOException e) {
					junit.framework.Assert.fail(e.getMessage());
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
	                params.put("code", "HTTP");
	                Result result = callAction(routes.ref.SponsorLevelController.removeSponsorLevel(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                assertThat(status(result)).isEqualTo(Http.Status.NOT_FOUND);
				} catch (IOException e) {
					junit.framework.Assert.fail(e.getMessage());
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
	                params.put("code", "SponsorLevel2");
	                Result result = callAction(routes.ref.SponsorLevelController.removeSponsorLevel(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                assertThat(status(result)).isEqualTo(OK);

	                Logger.info("Vérification que le niveau de partenariat a bien été supprimmé en base de données");
	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(Constantes.COLLECTION_SPONSOR_LEVEL, new BasicDBObject().append("code", "SponsorLevel2"));
	                assertThat(null != dbObjects && dbObjects.size() == 0);
				} catch (IOException e) {
					junit.framework.Assert.fail(e.getMessage());
				}
            }
        });
    }
}
