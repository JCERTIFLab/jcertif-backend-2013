package controllers;

import static org.fest.assertions.Assertions.assertThat;
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

public class TitleControllerTest extends ReferentielControllerTest {

	@Override
	public HandlerRef<?> getCreateURL() {
		return routes.ref.TitleController.addTitle();
	}
	
	@Override
	public HandlerRef<?> getRemoveURL() {
		return routes.ref.TitleController.removeTitle();
	}
	
    @Test
    public void test_title_list() throws IOException {

        running(fakeApplication(), new Runnable() {
            public void run() {
                try {
                	TestUtils.updateDatabase("test/data/title.js");
                    Result result = route(fakeRequest(GET, "/ref/title/list"));
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
    public void test_title_new_ok() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                Logger.info("Création d'une nouvealle civilité");
                try {
					TestUtils.updateDatabase("test/data/title.js");
					TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@title.com");
	                params.put("label", "M.");
	                Result result = callAction(routes.ref.TitleController.addTitle(), fakeRequest().withJsonBody(Json.toJson(params)));
	                assertThat(status(result)).isEqualTo(OK);

	                Logger.info("Vérification que la nouvelle civilité est bien présente en base de données");
	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_TITLE, new BasicDBObject().append("label", "M."));
	                Assert.assertTrue(null != dbObjects);
	                Assert.assertEquals(1,dbObjects.size());	
	                Assert.assertEquals("01",dbObjects.get(0).getString("version"));
	                Assert.assertEquals("false",dbObjects.get(0).getString("deleted"));
	                TestUtils.updateDatabase("test/data/purge.js");
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
                
            }
        });
    }
    
    @Test
    public void test_title_remove_ok() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/title.js");
					Logger.info("Suppression d'une civilité");
					TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@title.com");
	                params.put("label", "Title2");
	                params.put("version", "01");
	                
	                Result result = callAction(routes.ref.TitleController.removeTitle(), fakeRequest().withJsonBody(Json.toJson(params)));
	                assertThat(status(result)).isEqualTo(OK);

	                Logger.info("Vérification que la civilité a bien été supprimmé en base de données");
	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_TITLE, new BasicDBObject().append("label", "Title2"));
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
