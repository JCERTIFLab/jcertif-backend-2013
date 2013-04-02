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

public class CategoryControllerTest extends ReferentielControllerTest {

	@Override
	public String getCreateURL() {
		return "/ref/category/new";
	}
	
	@Override
	public String getRemoveURL() {
		return "/ref/category/remove";
	}
	
    @Test
    public void test_category_list() throws IOException {

        running(fakeApplication(), new Runnable() {
            public void run() {
                try {
                	TestUtils.updateDatabase("test/data/category.js");
                    Result result = route(fakeRequest(GET, "/ref/category/list"));
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
    public void test_category_new_ok() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Logger.info("Création d'une nouvealle catégorie");
                Map<String, String> params = new HashMap<String, String>();
                params.put("label", "HTTT");
                Result result = callAction(routes.ref.CategoryController.newCategory(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
                assertThat(status(result)).isEqualTo(OK);

                Logger.info("Vérification que la nouvelle catégorie est bien présente en base de données");
                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(Constantes.COLLECTION_CATEGORY, new BasicDBObject().append("label", "HTTT"));
                Assert.assertTrue(null != dbObjects);
                Assert.assertEquals(1,dbObjects.size());
                Assert.assertEquals("HTTT",dbObjects.get(0).get("label"));
            }
        });
    }
    
    @Test
    public void test_category_remove_ok() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/category.js");
					Logger.info("Suppression d'une catégorie");
	                Map<String, String> params = new HashMap<String, String>();
	                params.put("label", "Category3");
	                Result result = callAction(routes.ref.CategoryController.removeCategory(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                assertThat(status(result)).isEqualTo(OK);

	                Logger.info("Vérification que la catégorie a bien été supprimmé en base de données");
	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(Constantes.COLLECTION_CATEGORY, new BasicDBObject().append("label", "Category3"));
	                Assert.assertTrue(null != dbObjects);
	                Assert.assertEquals(0,dbObjects.size());
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
            }
        });
    }
    
}
