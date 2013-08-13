package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;
import static play.test.Helpers.running;
import static play.test.Helpers.status;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import models.util.TestConstantes;

import org.codehaus.jackson.JsonNode;
import org.junit.Assert;
import org.junit.Test;

import play.Logger;
import play.api.mvc.HandlerRef;
import play.libs.Json;
import play.mvc.Result;
import util.TestUtils;

public class SpeakerControllerTest extends MemberControllerTest{
	
	@Override
	public HandlerRef<?> getChangePasswordURL(String email) {
		return routes.ref.SpeakerController.changePasswordSpeaker(email);
	}

	@Override
	public HandlerRef<?> getReinitPasswordURL(String email) {
		return routes.ref.SpeakerController.reinitPasswordSpeaker(email);
	}

	@Override
	public HandlerRef<?> getRegistrationURL() {
		return routes.ref.SpeakerController.registerSpeaker();
	}
	
	@Override
	public HandlerRef<?> getDeletionURL() {
		return routes.ref.SpeakerController.removeSpeaker();
	}

	@Override
	public HandlerRef<?> getUpdateURL() {
		return routes.ref.SpeakerController.updateSpeaker();
	}

	@Override
	public String getCollection() {
		return TestConstantes.COLLECTION_SPEAKER;
	}
	
	@Test
	public void test_list_speakers(){
		Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
	            public void run() {
	            	Logger.info("Liste des speakers");
	            	try {
						TestUtils.updateDatabase("test/data/speaker.js");
						TestUtils.updateDatabase("test/data/token.js");
						Result result = route(fakeRequest(GET, "/admin/speaker/list?access_token=ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8&provider=google"));
		                assertThat(status(result)).isEqualTo(OK);
		                JsonNode jsonNode = Json.parse(contentAsString(result));
	                    Assert.assertEquals(2, jsonNode.size());
	                    TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
	
	@Test
	public void test_list_speakers_filtered(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Liste des speakers");
	            	try {
						TestUtils.updateDatabase("test/data/speaker.js");
						Result result = route(fakeRequest(GET, "/speaker/list"));
		                assertThat(status(result)).isEqualTo(OK);
		                JsonNode jsonNode = Json.parse(contentAsString(result));
	                    Assert.assertEquals(1, jsonNode.size());
	                    TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
	
	@Test
	public void test_list_speakers_diff(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Liste des speakers");
	            	try {
						TestUtils.updateDatabase("test/data/speaker.js");
						Result result = route(fakeRequest(GET, "/speaker/list/02"));
		                assertThat(status(result)).isEqualTo(OK);
		                JsonNode jsonNode = Json.parse(contentAsString(result));
	                    Assert.assertEquals(1, jsonNode.size());
		                Assert.assertEquals("Fowler",jsonNode.get(0).findPath("lastname").getTextValue());
		                Assert.assertEquals("Martin",jsonNode.get(0).findPath("firstname").getTextValue());		                
	                    TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
}
