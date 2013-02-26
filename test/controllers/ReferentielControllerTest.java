package controllers;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class ReferentielControllerTest {

    @Test
    public void test_sponsorlevel() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(fakeRequest(GET, "/ref/sponsorlevel/list"));
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo("application/json");
                assertThat(contentAsString(result)).isEqualTo("[ { \"label\" : \"Titanium\"} , { \"label\" : \"Platine\"} , { \"label\" : \"Or\"} , { \"label\" : \"Argent\"} , { \"label\" : \"Community\"} , { \"label\" : \"Education\"} , { \"label\" : \"Média\"}]");
            }
        });
    }

    @Test
    public void test_categories() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(fakeRequest(GET, "/ref/category/list"));
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo("application/json");
                assertThat(contentAsString(result)).isEqualTo("[ { \"label\" : \"Android\"} , { \"label\" : \"HTML5\"} , { \"label\" : \"Java\"} , { \"label\" : \"Entreprise\"} , { \"label\" : \"Web Design\"}]");
            }
        });
    }

}