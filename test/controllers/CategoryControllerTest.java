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

import models.util.TestConstantes;

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
                    TestUtils.updateDatabase("test/data/purge.js");
                } catch (IOException e) {
                    Assert.fail(e.getMessage());
                }
            }
        });
    }
    
    @Test
    public void test_category_list_diff() throws IOException {

        running(fakeApplication(), new Runnable() {
            public void run() {
                try {
                	TestUtils.updateDatabase("test/data/category.js");
                    Result result = route(fakeRequest(GET, "/ref/category/list/01"));
                    assertThat(status(result)).isEqualTo(OK);
                    assertThat(contentType(result)).isEqualTo("application/json");
                    JsonNode jsonNode = Json.parse(contentAsString(result));
                    Assert.assertEquals(2, jsonNode.size());                   
                    Assert.assertEquals("Category2",jsonNode.get(0).findPath("label").getTextValue());
	                Assert.assertEquals("02",jsonNode.get(0).findPath("version").getTextValue());
	                Assert.assertEquals("false",jsonNode.get(0).findPath("deleted").getTextValue());
	                
	                Assert.assertEquals("Category3",jsonNode.get(1).findPath("label").getTextValue());
	                Assert.assertEquals("03",jsonNode.get(1).findPath("version").getTextValue());
	                Assert.assertEquals("false",jsonNode.get(1).findPath("deleted").getTextValue());
	                
                    TestUtils.updateDatabase("test/data/purge.js");
                } catch (IOException e) {
                    Assert.fail(e.getMessage());
                }
            }
        });
    }
    
    @Test
    public void test_category_list_jsonp() throws IOException {

        running(fakeApplication(), new Runnable() {
            public void run() {
                try {
                	TestUtils.updateDatabase("test/data/category.js");
                    Result result = route(fakeRequest(GET, "/ref/category/list?jsonp=myFonction"));
                    assertThat(status(result)).isEqualTo(OK);
                    assertThat(contentType(result)).isEqualTo("text/javascript");
                    assertThat(contentAsString(result).contains("myFonction"));
                    Logger.info("Test : " + contentAsString(result));
                    TestUtils.updateDatabase("test/data/purge.js");
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
				try {
					TestUtils.updateDatabase("test/data/oauth_grant_admin.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "e096fdd2-448b-4df4-9fca-11f80d8a5f86");
	                params.put("label", "HTTT");
	                Result result = callAction(routes.ref.CategoryController.newCategory(), fakeRequest().withJsonBody(Json.toJson(params), POST).withHeader("authorization", "Basic YmFja29mZmljZTpyODc2Q0lOVzNwWnV1N25MN2g2QVA="));
	                assertThat(status(result)).isEqualTo(OK);

	                Logger.info("Vérification que la nouvelle catégorie est bien présente en base de données");
	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_CATEGORY, new BasicDBObject().append("label", "HTTT"));
	                Assert.assertTrue(null != dbObjects);
	                Assert.assertEquals(1,dbObjects.size());
	                Assert.assertEquals("01",dbObjects.get(0).get("version"));
	                Assert.assertEquals("false",dbObjects.get(0).get("deleted"));
	                Assert.assertEquals("HTTT",dbObjects.get(0).get("label"));
	                TestUtils.updateDatabase("test/data/purge.js");
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
				
            }
        });
    }
    
    @Test
    public void test_category_remove_ok() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/category.js");				
					TestUtils.updateDatabase("test/data/oauth_grant_admin.js");
					Logger.info("Suppression d'une catégorie");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "e096fdd2-448b-4df4-9fca-11f80d8a5f86");
	                params.put("label", "Category3");
	                params.put("version", "03");
	                Result result = callAction(routes.ref.CategoryController.removeCategory(), fakeRequest().withJsonBody(Json.toJson(params), POST).withHeader("authorization", "Basic YmFja29mZmljZTpyODc2Q0lOVzNwWnV1N25MN2g2QVA="));
	                assertThat(status(result)).isEqualTo(OK);

	                Logger.info("Vérification que la catégorie a bien été supprimmé en base de données");
	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_CATEGORY, new BasicDBObject().append("label", "Category3"));
	                Assert.assertTrue(null != dbObjects);
	                Assert.assertEquals(1,dbObjects.size());
	                Assert.assertEquals("true",dbObjects.get(0).getString("deleted"));
	                Assert.assertEquals("04",dbObjects.get(0).getString("version"));
	                TestUtils.updateDatabase("test/data/purge.js");
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
            }
        });
    }
    
}
