package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.POST;
import static play.test.Helpers.callAction;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.running;
import static play.test.Helpers.status;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import play.Logger;
import play.api.mvc.HandlerRef;
import play.libs.Json;
import play.mvc.Result;
import util.TestUtils;

import com.mongodb.BasicDBObject;

public abstract class MemberControllerTest {
	
	public abstract HandlerRef<?> getChangePasswordURL(String email);
	public abstract HandlerRef<?> getReinitPasswordURL(String email);
	public abstract HandlerRef<?> getRegistrationURL();
	public abstract HandlerRef<?> getDeletionURL();
	public abstract HandlerRef<?> getUpdateURL();
	public abstract String getCollection();

	@Test
	public void test_member_registration_ok(){
		 Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		 additionalConfiguration.put("smtp.mock", true);
	     running(fakeApplication(additionalConfiguration), new Runnable() {
	            public void run() {
	            	Logger.info("Création d'un nouveau membre JCertif Conference");
	            	try {
						TestUtils.updateDatabase("test/data/member.js");
						Map<String, Object> params = new HashMap<String, Object>();
		                params.put("email", "jcertif@gmail.com");
		                params.put("password", "testjcertif");
		                params.put("title", "M.");
		                params.put("lastname", "John");
		                params.put("firstname", "Hudson");
		                params.put("website", "www.jcertif.com");
		                params.put("city", "Paris");
		                params.put("country", "France");
		                params.put("company", "JCertif");
		                params.put("phone", "+33102030405");
		                params.put("photo", "http://jcertif.blog.com/pictures/photo.gif");
		                params.put("biography", "This is all about me");
		                Result result = callAction(getRegistrationURL(),fakeRequest().withJsonBody(Json.toJson(params),POST));
		                assertThat(status(result)).isEqualTo(OK);	                

		                Logger.info("Vérification que le nouveau participant a bien été enregistré");
		                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(getCollection(), new BasicDBObject().append("email", "jcertif@gmail.com"));
		                Assert.assertTrue(null != dbObjects);
		                Assert.assertEquals(1,dbObjects.size());
		                Logger.info(dbObjects.get(0).toString());
		                Assert.assertEquals("M.",dbObjects.get(0).get("title"));
		                Assert.assertEquals("+33102030405",dbObjects.get(0).get("phone"));
		                Assert.assertEquals("France",dbObjects.get(0).get("country"));
		                Assert.assertEquals("01",dbObjects.get(0).get("version"));
		                Assert.assertEquals("false",dbObjects.get(0).get("deleted"));
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	                
	            }
	        });
	}
	
	@Test
	public void test_member_registration_title_not_valid(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Création d'un nouveau membre JCertif Conference");
	            	try {
						TestUtils.updateDatabase("test/data/member.js");
						Map<String, Object> params = new HashMap<String, Object>();
		                params.put("email", "jcertif@gmail.com");
		                params.put("password", "testjcertif");
		                params.put("title", "toto");
		                params.put("lastname", "John");
		                params.put("firstname", "Hudson");
		                params.put("website", "www.jcertif.com");
		                params.put("city", "Paris");
		                params.put("country", "France");
		                params.put("company", "JCertif");
		                params.put("phone", "+33102030405");
		                params.put("photo", "http://jcertif.blog.com/pictures/photo.gif");
		                params.put("biography", "This is all about me");
		                Result result = callAction(getRegistrationURL(),fakeRequest().withJsonBody(Json.toJson(params),POST));
		                assertThat(status(result)).isEqualTo(BAD_REQUEST);	   
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	                
	            }
	        });
	}
	
	@Test
	public void test_update_member_ok(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Mise à jour des informations d'un membre");
	            	try {
						TestUtils.updateDatabase("test/data/member.js");
						TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
		                params.put("email", "jandiew@gmail.com");
		                params.put("title", "M.");
		                params.put("website", "www.jandriewrebirth.com");
		                params.put("city", "Somewhere");
		                params.put("country", "Jungle");
		                params.put("company", "Lost");
		                params.put("photo", "http://jandriewrebirth.blog.com/pictures/myPic.gif");
		                params.put("biography", "The new me");
		                params.put("version", "01");
		                params.put("deleted", "false");
		                Result result = callAction(getUpdateURL(), fakeRequest().withJsonBody(Json.toJson(params), POST));
		                assertThat(status(result)).isEqualTo(OK);	                

		                Logger.info("Vérification que les informations du participant ont bien été mises à jour");
		                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(getCollection(), new BasicDBObject().append("email", "jandiew@gmail.com"));
		                Assert.assertTrue(null != dbObjects);
		                Assert.assertEquals(1,dbObjects.size());
		                Assert.assertEquals("M.",dbObjects.get(0).get("title"));
		                Assert.assertEquals("Johnson",dbObjects.get(0).get("lastname"));
		                Assert.assertEquals("Andriew",dbObjects.get(0).get("firstname"));
		                Assert.assertEquals("www.jandriewrebirth.com",dbObjects.get(0).get("website"));
		                Assert.assertEquals("Somewhere",dbObjects.get(0).get("city"));
		                Assert.assertEquals("Jungle",dbObjects.get(0).get("country"));
		                Assert.assertEquals("Lost",dbObjects.get(0).get("company"));
		                Assert.assertEquals("0102030405",dbObjects.get(0).get("phone"));
		                Assert.assertEquals("http://jandriewrebirth.blog.com/pictures/myPic.gif",dbObjects.get(0).get("photo"));
		                Assert.assertEquals("The new me",dbObjects.get(0).get("biography"));
		                Assert.assertEquals("02",dbObjects.get(0).get("version"));
		                Assert.assertEquals("false",dbObjects.get(0).get("deleted"));
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}	                
	            }
	        });
	}
	
	@Test
	public void test_update_member_not_found(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Mise à jour des informations d'un membre");
	            	try {
						TestUtils.updateDatabase("test/data/member.js");
						TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
		                params.put("email", "toto@toto.com");
		                Result result = callAction(getUpdateURL(), fakeRequest().withJsonBody(Json.toJson(params), POST));
		                assertThat(status(result)).isEqualTo(NOT_FOUND);	                
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}	                
	            }
	        });
	}
	
	@Test
	public void test_update_member_title_not_valid(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Mise à jour des informations d'un membre, la civilité doit être valide");
	            	try {
						TestUtils.updateDatabase("test/data/member.js");
						TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
		                params.put("email", "jandiew@gmail.com");
		                params.put("title", "toto");
		                params.put("website", "www.jandriewrebirth.com");
		                params.put("city", "Somewhere");
		                params.put("country", "Jungle");
		                params.put("company", "Lost");
		                params.put("photo", "http://jandriewrebirth.blog.com/pictures/myPic.gif");
		                params.put("biography", "The new me");
		                params.put("version", "01");
		                Result result = callAction(getUpdateURL(), fakeRequest().withJsonBody(Json.toJson(params), POST));
		                assertThat(status(result)).isEqualTo(BAD_REQUEST);	 
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}	                
	            }
	        });
	}
	
	@Test
	public void test_member_changepassword_not_found(){
		Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		 additionalConfiguration.put("smtp.mock", true);
	     running(fakeApplication(additionalConfiguration), new Runnable() {
	            public void run() {
	            	Logger.info("Changement du mot de passe d'un membre");
	            	try {
						TestUtils.updateDatabase("test/data/member.js");
						Map<String, Object> params = new HashMap<String, Object>();
		                params.put("oldpassword", "testjcertif");
		                params.put("newpassword", "testjcertifnew");
		                Result result = callAction(getChangePasswordURL("toto@toto.com"), fakeRequest().withJsonBody(Json.toJson(params), POST));
		                assertThat(status(result)).isEqualTo(NOT_FOUND);	                
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
	
	@Test
	public void test_member_changepassword_ok(){
		Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		 additionalConfiguration.put("smtp.mock", true);
	     running(fakeApplication(additionalConfiguration), new Runnable() {
	            public void run() {
	            	Logger.info("Changement du mot de passe d'un membre");
	            	try {
						TestUtils.updateDatabase("test/data/member.js");
						Map<String, Object> params = new HashMap<String, Object>();
		                params.put("oldpassword", "testjcertif");
		                params.put("newpassword", "testjcertifnew");
		                params.put("version", "01");
		                params.put("deleted", "false");
		                Result result = callAction(getChangePasswordURL("test@member.com"), fakeRequest().withJsonBody(Json.toJson(params), POST));
		                assertThat(status(result)).isEqualTo(OK);	                

		                Logger.info("Vérification que le nouveau mot de passe a bien été enregistré");
		                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(getCollection(), new BasicDBObject().append("email", "test@member.com"));
		                Assert.assertTrue(null != dbObjects);
		                Assert.assertEquals(1,dbObjects.size());
		                Assert.assertNotSame("mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg=",dbObjects.get(0).get("password"));
		                Assert.assertEquals("02",dbObjects.get(0).get("version"));
		                Assert.assertEquals("false",dbObjects.get(0).get("deleted"));
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
	
	@Test
	public void test_member_reinitpassword_not_found(){
		Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		 additionalConfiguration.put("smtp.mock", true);
	     running(fakeApplication(additionalConfiguration), new Runnable() {
	            public void run() {
	            	Logger.info("Réinitialisation du mot de passe d'un membre");
	            	try {
						TestUtils.updateDatabase("test/data/member.js");
						Result result = callAction(getReinitPasswordURL("toto@toto.com"), fakeRequest());
		                assertThat(status(result)).isEqualTo(NOT_FOUND);	                
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
	
	@Test
	public void test_member_reinitpassword_ok(){
		Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		 additionalConfiguration.put("smtp.mock", true);
	     running(fakeApplication(additionalConfiguration), new Runnable() {
	            public void run() {
	            	Logger.info("Réinitialisation du mot de passe d'un membre");
	            	try {
						TestUtils.updateDatabase("test/data/member.js");
						Result result = callAction(getReinitPasswordURL("test-senior@member.com"), fakeRequest());
		                assertThat(status(result)).isEqualTo(OK);	                

		                Logger.info("Vérification que le nouveau mot de passe a bien été enregistré");
		                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(getCollection(), new BasicDBObject().append("email", "test-senior@member.com"));
		                Assert.assertTrue(null != dbObjects);
		                Assert.assertEquals(1,dbObjects.size());
		                Assert.assertNotSame("mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg=",dbObjects.get(0).get("password"));
		                Assert.assertEquals("02",dbObjects.get(0).get("version"));
		                Assert.assertEquals("false",dbObjects.get(0).get("deleted"));
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
	
	@Test
	public void test_member_remove_ok(){
		Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		 additionalConfiguration.put("admin.mock", "true");
	     running(fakeApplication(additionalConfiguration), new Runnable() {
	            public void run() {
	            	Logger.info("Suppression d'un membre");
	            	try {
						TestUtils.updateDatabase("test/data/member.js");
						TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
		                params.put("email", "test@member.com");
		                params.put("version", "01");
		                params.put("deleted", "false");
						Result result = callAction(getDeletionURL(), fakeRequest().withJsonBody(Json.toJson(params)));
		                assertThat(status(result)).isEqualTo(OK);	                

		                Logger.info("Vérification que le membre a bien été supprimé");
		                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(getCollection(), new BasicDBObject().append("email", "test@member.com"));
		                Assert.assertTrue(null != dbObjects);
		                Assert.assertEquals(1,dbObjects.size());
		                Assert.assertEquals("02",dbObjects.get(0).get("version"));
		                Assert.assertEquals("true",dbObjects.get(0).get("deleted"));
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
	
}
