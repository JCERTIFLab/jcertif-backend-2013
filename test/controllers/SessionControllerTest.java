package controllers;

import org.junit.Assert;
import org.junit.Test;
import play.mvc.Result;
import util.TestUtils;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class SessionControllerTest {


    @Test
    public void test_listSession() throws java.io.IOException {
        running(fakeApplication(), new Runnable() {
            public void run() {
                try {
                    TestUtils.updateDatabase("test/data/session.js");
                    Result result = route(fakeRequest(GET, "/session/list"));
                    assertThat(status(result)).isEqualTo(OK);
                    assertThat(contentType(result)).isEqualTo("application/json");
                    assertThat(contentAsString(result)).isEqualTo("[ { \"id\" : \"101\" , \"title\" : \"title 1\" , \"summary\" : \"summary 1\" , \"description\" : \"description 1\" , \"status\" : \"status 1\" , \"keyword\" : \"keyword 1\" , \"category\" : [ \"HTML 5\" , \"Android\"] , \"start\" : \"12/02/2013 10:22\" , \"end\" : \"16/02/2013 10:23\" , \"speakers\" : [ \"11\" , \"12\"]} , { \"id\" : \"102\" , \"title\" : \"title 2\" , \"summary\" : \"summary 2\" , \"description\" : \"description 2\" , \"status\" : \"status 2\" , \"keyword\" : \"keyword 2\" , \"category\" : [ \"HTML 5\" , \"Android\"] , \"start\" : \"12/02/2013 10:22\" , \"end\" : \"16/02/2013 10:23\" , \"speakers\" : [ \"21\" , \"22\"]}]");
                } catch (IOException e) {
                	Assert.fail(e.getMessage());
                }
            }
        });
    }

}