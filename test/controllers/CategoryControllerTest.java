package controllers;


import models.database.MongoDatabase;
import models.util.Constantes;

import org.junit.Test;
import play.Logger;
import play.libs.Json;
import play.mvc.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class CategoryControllerTest {

    @Test
    public void test_category_list() throws IOException {

        running(fakeApplication(), new Runnable() {
            public void run() {
                try {
                    MongoDatabase.getInstance().loadDbWithData(Constantes.INIT_DATA_FILE);
                    Result result = route(fakeRequest(GET, "/ref/category/list"));
                    assertThat(status(result)).isEqualTo(OK);
                    assertThat(contentType(result)).isEqualTo("application/json");
                    assertThat(contentAsString(result)).contains("[ { \"label\" : \"Android\"} , { \"label\" : \"HTML5\"} , { \"label\" : \"Java\"} , { \"label\" : \"Entreprise\"} , { \"label\" : \"Web Design\"}]");

                } catch (IOException e) {
                    junit.framework.Assert.fail(e.getMessage());
                }
            }
        });
    }

    @Test
    public void test_category_new_forbidden() {
        Logger.info("Une requête de création d'une nouvelle catégorie requiert les droits d'administration");
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(fakeRequest(POST, "/ref/category/new"));
                assertThat(status(result)).isEqualTo(FORBIDDEN);
            }
        });
    }

    @Test
    public void test_category_new_badrequest() {
        Logger.info("Une requête de création d'une nouvelle catégorie sans paramètre JSON renvoie une réponse BAD REQUEST");
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(fakeRequest(POST, "/ref/category/new").withSession("admin", "admin"));
                assertThat(status(result)).isEqualTo(BAD_REQUEST);
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
                result = route(fakeRequest(GET, "/ref/category/list"));
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo("application/json");
                assertThat(contentAsString(result)).contains("HTTT");

            }
        });
    }
}
