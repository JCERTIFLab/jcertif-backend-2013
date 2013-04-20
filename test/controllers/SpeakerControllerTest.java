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
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Liste des speakers");
	            	try {
						TestUtils.updateDatabase("test/data/speaker.js");
						Result result = route(fakeRequest(GET, "/speaker/list"));
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
}
