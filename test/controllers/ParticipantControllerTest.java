package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;
import static play.test.Helpers.running;
import static play.test.Helpers.status;

import org.junit.Test;

import play.mvc.Result;

public class ParticipantControllerTest {
	
	@Test
	void test_registration(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	                Result result = route(fakeRequest(GET, "/participant/register"));
	                assertThat(status(result)).isEqualTo(OK);
	                assertThat(contentType(result)).isEqualTo("application/json");
	                assertThat(contentAsString(result)).isEqualTo("ok");
	            }
	        });
	}
	
	@Test
	void test_changepassword(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	                Result result = route(fakeRequest(GET, "/participant/{email}/changepassword"));
	                assertThat(status(result)).isEqualTo(OK);
	                assertThat(contentAsString(result)).isEqualTo("ok");
	            }
	        });
	}
}
