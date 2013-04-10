package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.FORBIDDEN;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.POST;
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
import play.libs.Json;
import play.mvc.Result;
import util.TestUtils;

/**
 * <p>Tests fonctionnels pour le controleur {@link LoginController}</p>
 * 
 * @author Martial SOMDA
 *
 */
public class LoginControllerTest {

	@Test
	public void test_speaker_login_ok(){
		running(fakeApplication(), new Runnable() {
            public void run() {
            	Logger.info("Connexion d'un membre JCertif Conference");
            	try {
					TestUtils.updateDatabase("test/data/login.js");
					Map<String, Object> params = new HashMap<String, Object>();
	                params.put("email", "test@participant.com");
	                params.put("password", "testjcertif");
	                Result result = callAction(routes.ref.LoginController.login(),fakeRequest().withJsonBody(Json.toJson(params),POST));
	                assertThat(status(result)).isEqualTo(OK);	  
	                TestUtils.updateDatabase("test/data/purge.js");
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
                
            }
        });
	}
	
	@Test
	public void test_speaker_login_failed(){
		running(fakeApplication(), new Runnable() {
            public void run() {
            	Logger.info("Connexion d'un membre JCertif Conference");
            	try {
					TestUtils.updateDatabase("test/data/login.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("email", "test@participant.com");
	                params.put("password", "wrongpassword");
	                Result result = callAction(routes.ref.LoginController.login(),fakeRequest().withJsonBody(Json.toJson(params),POST));
	                assertThat(status(result)).isEqualTo(FORBIDDEN);	
	                TestUtils.updateDatabase("test/data/purge.js");
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
                
            }
        });
	}
	
	@Test
	public void test_participant_login_ok(){
		running(fakeApplication(), new Runnable() {
            public void run() {
            	Logger.info("Connexion d'un membre JCertif Conference");
            	try {
					TestUtils.updateDatabase("test/data/login.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("email", "test@speaker.com");
	                params.put("password", "testjcertif");
	                Result result = callAction(routes.ref.LoginController.login(),fakeRequest().withJsonBody(Json.toJson(params),POST));
	                assertThat(status(result)).isEqualTo(OK);	
	                TestUtils.updateDatabase("test/data/purge.js");
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
                
            }
        });
	}
	
	@Test
	public void test_participant_login_failed(){
		running(fakeApplication(), new Runnable() {
            public void run() {
            	Logger.info("Connexion d'un membre JCertif Conference");
            	try {
					TestUtils.updateDatabase("test/data/login.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("email", "test@speaker.com");
	                params.put("password", "wrongpassword");
	                Result result = callAction(routes.ref.LoginController.login(),fakeRequest().withJsonBody(Json.toJson(params),POST));
	                assertThat(status(result)).isEqualTo(FORBIDDEN);	
	                TestUtils.updateDatabase("test/data/purge.js");
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
                
            }
        });
	}
}
