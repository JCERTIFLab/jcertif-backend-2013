package controllers;

import org.junit.Test;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class ReferentielControllerTest {

    @Test
    public void test_sponsorlevel() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(fakeRequest(GET, "/ref/sponsorlevel/list"));
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo("application/json");
                assertThat(contentAsString(result)).isEqualTo("\"[ { \\\"label\\\" : \\\"Titanium\\\"} , { \\\"label\\\" : \\\"Platine\\\"} , { \\\"label\\\" : \\\"Or\\\"} , { \\\"label\\\" : \\\"Argent\\\"} , { \\\"label\\\" : \\\"Community\\\"} , { \\\"label\\\" : \\\"Education\\\"} , { \\\"label\\\" : \\\"Média\\\"}]\"");
            }
        });
    }

}