package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.FORBIDDEN;
import static play.test.Helpers.callAction;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.running;
import static play.test.Helpers.status;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import play.Logger;
import play.api.mvc.HandlerRef;
import play.libs.Json;
import play.mvc.Result;
import util.TestUtils;

public abstract class ReferentielControllerTest {

	public abstract HandlerRef<?> getCreateURL();
	public abstract HandlerRef<?> getRemoveURL();
	
	@Test
    public void test_referentiel_new_forbidden() {
        Logger.info("Une requête de création d'un objet du referentiel requiert les droits d'administration");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@referentiel.com");
	                Result result = callAction(getCreateURL(), fakeRequest().withJsonBody(Json.toJson(params)));	                
	                assertThat(status(result)).isEqualTo(FORBIDDEN);
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}finally{
					try {
						TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
				}
				
            }
        });
    }
		
	@Test
    public void test_referentiel_remove_forbidden() {
        Logger.info("Une requête de suppression d'un objet du referentiel requiert les droits d'administration");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("label", "HTTP");
					params.put("email", "test@referentiel.com");
	                Result result = callAction(getRemoveURL(), fakeRequest().withJsonBody(Json.toJson(params)));
	                assertThat(status(result)).isEqualTo(FORBIDDEN);	                
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}finally{
					try {
						TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
				}
				
            }
        });
    }
	
    @Test
    public void test_referentiel_new_badrequest() {
        Logger.info("Une requête de création d'un objet du referentiel sans paramètre JSON renvoie une réponse BAD REQUEST");
        Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@referentiel.com");
	                Result result = callAction(getCreateURL(), fakeRequest().withJsonBody(Json.toJson(params)));
	                assertThat(status(result)).isEqualTo(BAD_REQUEST);
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}finally{
					try {
						TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
				}
				
            }
        });
    }
}