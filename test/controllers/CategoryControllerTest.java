package controllers;

import models.util.Constantes;

import org.junit.Test;

import com.mongodb.BasicDBObject;

import play.Logger;
import play.libs.Json;
import play.mvc.*;
import util.TestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class CategoryControllerTest extends ReferentielControllerTest{

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
                    assertThat(contentAsString(result).toLowerCase().trim().equals("[ {\"label\" : \"Category1\"} , " +
                    		"{\"label\" : \"Category2\"} , " +
                    		"{\"label\" : \"Category3\"}]".toLowerCase().trim()));

                } catch (IOException e) {
                    junit.framework.Assert.fail(e.getMessage());
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
                assertThat(null != dbObjects && dbObjects.size() == 1);
                assertThat("HTTT".equals(dbObjects.get(0).get("label")));
            }
        });
    }
    
}
