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

import org.codehaus.jackson.JsonNode;
import org.junit.Assert;
import org.junit.Test;

import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import util.TestUtils;

import com.mongodb.BasicDBObject;

public class SessionStatusControllerTest extends ReferentielControllerTest {

	@Override
	public String getCreateURL() {
		return "/ref/sessionstatus/new";
	}

	@Override
	public String getRemoveURL() {
		return "/ref/sessionstatus/remove";
	}
	
	@Test
    public void test_sessionstatus_list() throws IOException {

        running(fakeApplication(), new Runnable() {
            public void run() {
                try {
                	TestUtils.updateDatabase("test/data/session_status.js");
                    Result result = route(fakeRequest(GET, "/ref/sessionstatus/list"));
                    assertThat(status(result)).isEqualTo(OK);
                    assertThat(contentType(result)).isEqualTo("application/json");
                    JsonNode jsonNode = Json.parse(contentAsString(result));
                    Assert.assertEquals(2, jsonNode.size());

                } catch (IOException e) {
                    Assert.fail(e.getMessage());
                }
            }
        });
    }
	
	@Test
    public void test_sessionstatus_new_ok() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Logger.info("Création d'un nouveau statut de session");
                try {
					TestUtils.updateDatabase("test/data/session_status.js");
					Map<String, String> params = new HashMap<String, String>();
	                params.put("label", "HTTT");
	                Result result = callAction(routes.ref.SessionStatusController.addSessionStatus(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                assertThat(status(result)).isEqualTo(OK);

	                Logger.info("Vérification que le nouveau statut est bien présent en base de données");
	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(Constantes.COLLECTION_SESSION_STATUS, new BasicDBObject().append("label", "HTTT"));
	                Assert.assertTrue(null != dbObjects);
	                Assert.assertEquals(1,dbObjects.size());
	                Assert.assertEquals("HTTT",dbObjects.get(0).get("label"));
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
                

            }
        });
    }
	
	@Test
    public void test_sessionstatus_remove_ok() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	try {
            		TestUtils.updateDatabase("test/data/session_status.js");
					Logger.info("Suppression d'un statut de session");
	                Map<String, String> params = new HashMap<String, String>();
	                params.put("label", "Status1");
	                Result result = callAction(routes.ref.SessionStatusController.removeSessionStatus(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                assertThat(status(result)).isEqualTo(OK);

	                Logger.info("Vérification que le statut a bien été supprimmé en base de données");
	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(Constantes.COLLECTION_SESSION_STATUS, new BasicDBObject().append("label", "Status1"));
	                Assert.assertTrue(null != dbObjects);
	                Assert.assertEquals(0,dbObjects.size());
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
            }
        });
    }

}
