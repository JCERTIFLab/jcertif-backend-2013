package controllers;


import org.codehaus.jackson.JsonNode;
import org.junit.*;

import play.libs.Json;
import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import java.util.HashMap;
import java.util.Map;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class CategoryControllerTest {

    @Test
    public void test_category_list() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(fakeRequest(GET, "/ref/category/list"));
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo("application/json");
                assertThat(contentAsString(result)).isEqualTo("[ { \"label\" : \"Android\"} , { \"label\" : \"HTML5\"} , { \"label\" : \"Java\"} , { \"label\" : \"Entreprise\"} , { \"label\" : \"Web Design\"}]");
            }
        });
    }

    @Test
    public void test_category_new_forbidden() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(fakeRequest(POST, "/ref/category/new"));
                assertThat(status(result)).isEqualTo(FORBIDDEN);
            }
        });
    }

    @Test
    public void test_category_new_badrequest() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(fakeRequest(POST, "/ref/category/new").withSession("admin","admin"));
                assertThat(status(result)).isEqualTo(BAD_REQUEST);
            }
        });
    }

    @Test
    public void test_category_new_ok() {
        // TODO
    }
}
