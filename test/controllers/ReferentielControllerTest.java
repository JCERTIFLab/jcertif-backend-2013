package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.FORBIDDEN;
import static play.test.Helpers.POST;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;
import static play.test.Helpers.running;
import static play.test.Helpers.status;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import util.TestUtils;

public abstract class ReferentielControllerTest {

	public abstract String getCreateURL();
	public abstract String getRemoveURL();
	
	@Test
    public void test_referentiel_new_forbidden() {
        Logger.info("Une requête de création d'un objet du referentiel requiert les droits d'administration");
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(fakeRequest(POST, getCreateURL()));
                assertThat(status(result)).isEqualTo(FORBIDDEN);
            }
        });
    }
		
	@Test
    public void test_referentiel_remove_forbidden() {
        Logger.info("Une requête de suppression d'un objet du referentiel requiert les droits d'administration");
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(fakeRequest(POST, getRemoveURL()));
                assertThat(status(result)).isEqualTo(FORBIDDEN);
            }
        });
    }
	
    @Test
    public void test_referentiel_new_badrequest() {
        Logger.info("Une requête de création d'un objet du referentiel sans paramètre JSON renvoie une réponse BAD REQUEST");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/oauth_grant_admin.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "e096fdd2-448b-4df4-9fca-11f80d8a5f86");
	                Result result = route(fakeRequest(POST, getCreateURL()).withJsonBody(Json.toJson(params)).withHeader("authorization", "Basic YmFja29mZmljZTpyODc2Q0lOVzNwWnV1N25MN2g2QVA="));
	                assertThat(status(result)).isEqualTo(BAD_REQUEST);
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
				
            }
        });
    }
}