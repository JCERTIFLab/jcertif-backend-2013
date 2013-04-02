package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.CONFLICT;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.POST;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;
import static play.test.Helpers.running;
import static play.test.Helpers.status;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.util.Constantes;

import org.codehaus.jackson.JsonNode;
import org.junit.Assert;
import org.junit.Test;

import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import util.TestUtils;

import com.mongodb.BasicDBObject;

public class ParticipantControllerTest {
	
	@Test
	public void test_list_participant(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Liste des participants");
	            	try {
						TestUtils.updateDatabase("test/data/participant.js");
						Result result = route(fakeRequest(GET, "/participant/list"));
		                assertThat(status(result)).isEqualTo(OK);
		                assertThat(contentType(result)).isEqualTo("application/json");
		                JsonNode jsonNode = Json.parse(contentAsString(result));
	                    Assert.assertEquals(3, jsonNode.size());
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
	
	@Test
	public void test_list_participant_sessions(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Liste des sessions d'un participant");
	            	try {
						TestUtils.updateDatabase("test/data/participant.js");
						Result result = callAction(routes.ref.ParticipantController.listParticipantSession("test@participant.com"), fakeRequest());
		                assertThat(status(result)).isEqualTo(OK);	                
		                assertThat(contentType(result)).isEqualTo("application/json");
		                assertThat(contentAsString(result)).isEqualTo("[ \"01\" , \"02\"]");
		                
	            	} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
	
	@Test
	public void test_get_participant_information(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Récuperer les informations d'un participant");
	            	try {
						TestUtils.updateDatabase("test/data/participant.js");
						Result result = callAction(routes.ref.ParticipantController.getParticipant("test@participant.com"), fakeRequest());
		                assertThat(status(result)).isEqualTo(OK);	                
		                assertThat(contentType(result)).isEqualTo("application/json");
		                
		                Logger.info("Vérification des informations du participant");
		                JsonNode jsonNode = Json.parse(contentAsString(result));
		                Assert.assertEquals("Developer",jsonNode.findPath("title").getTextValue());
		                Assert.assertEquals("-",jsonNode.findPath("password").getTextValue());
		                Assert.assertEquals("Lastname",jsonNode.findPath("lastname").getTextValue());
		                Assert.assertEquals("Firstname",jsonNode.findPath("firstname").getTextValue());
		                Assert.assertEquals("www.test.com",jsonNode.findPath("website").getTextValue());
		                Assert.assertEquals("Paris",jsonNode.findPath("city").getTextValue());
		                Assert.assertEquals("France",jsonNode.findPath("country").getTextValue());
		                Assert.assertEquals("Test SA",jsonNode.findPath("company").getTextValue());
		                Assert.assertEquals("0102030405",jsonNode.findPath("phone").getTextValue());
		                Assert.assertEquals("All about test",jsonNode.findPath("biography").getTextValue());
		                Assert.assertEquals("[\"01\",\"02\"]",jsonNode.findPath("sessions").toString().trim());
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
	
	@Test
	public void test_registration_ok(){
		 Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		 additionalConfiguration.put("smtp.mock", true);
	     running(fakeApplication(additionalConfiguration), new Runnable() {
	            public void run() {
	            	Logger.info("Création d'un nouveau participant");
	                Map<String, Object> params = new HashMap<String, Object>();
	                params.put("email", "jcertif@gmail.com");
	                params.put("password", "testjcertif");
	                params.put("title", "CTO");
	                params.put("lastname", "John");
	                params.put("firstname", "Hudson");
	                params.put("website", "www.jcertif.com");
	                params.put("city", "Paris");
	                params.put("country", "France");
	                params.put("company", "JCertif");
	                params.put("phone", "+33102030405");
	                params.put("photo", "http://jcertif.blog.com/pictures/photo.gif");
	                params.put("biography", "This is all about me");
	                params.put("sessions", new String[]{"01","02","03","04"});
	                Result result = callAction(routes.ref.ParticipantController.registerParticipant(), fakeRequest().withJsonBody(Json.toJson(params), POST));
	                assertThat(status(result)).isEqualTo(OK);	                

	                Logger.info("Vérification que le nouveau participant a bien été enregistré");
	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(Constantes.COLLECTION_PARTICIPANT, new BasicDBObject().append("email", "jcertif@gmail.com"));
	                Assert.assertTrue(null != dbObjects);
	                Assert.assertEquals(1,dbObjects.size());
	                Logger.info(dbObjects.get(0).toString());
	                Assert.assertEquals("CTO",dbObjects.get(0).get("title"));
	                Assert.assertEquals("+33102030405",dbObjects.get(0).get("phone"));
	                Assert.assertEquals("France",dbObjects.get(0).get("country"));
	                Assert.assertEquals("[ \"01\" , \"02\" , \"03\" , \"04\"]",dbObjects.get(0).get("sessions").toString());
	            }
	        });
	}
	
	@Test
	public void test_update_participant_ok(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Mise à jour des informations d'un participant");
	                Map<String, Object> params = new HashMap<String, Object>();
	                params.put("email", "jandiew@gmail.com");
	                params.put("title", "CTO");
	                params.put("website", "www.jandriewrebirth.com");
	                params.put("city", "Somewhere");
	                params.put("country", "Jungle");
	                params.put("company", "Lost");
	                params.put("photo", "http://jandriewrebirth.blog.com/pictures/myPic.gif");
	                params.put("biography", "The new me");
	                Result result = callAction(routes.ref.ParticipantController.updateParticipant(), fakeRequest().withJsonBody(Json.toJson(params), POST));
	                assertThat(status(result)).isEqualTo(OK);	                

	                Logger.info("Vérification que les informations du participant ont bien été mises à jour");
	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(Constantes.COLLECTION_PARTICIPANT, new BasicDBObject().append("email", "jandiew@gmail.com"));
	                Assert.assertTrue(null != dbObjects);
	                Assert.assertEquals(1,dbObjects.size());
	                Logger.info(dbObjects.get(0).toString());
	                Assert.assertEquals("CTO",dbObjects.get(0).get("title"));
	                Assert.assertEquals("Johnson",dbObjects.get(0).get("lastname"));
	                Assert.assertEquals("Andriew",dbObjects.get(0).get("firstname"));
	                Assert.assertEquals("www.jandriewrebirth.com",dbObjects.get(0).get("website"));
	                Assert.assertEquals("Somewhere",dbObjects.get(0).get("city"));
	                Assert.assertEquals("Jungle",dbObjects.get(0).get("country"));
	                Assert.assertEquals("Lost",dbObjects.get(0).get("company"));
	                Assert.assertEquals("0102030405",dbObjects.get(0).get("phone"));
	                Assert.assertEquals("http://jandriewrebirth.blog.com/pictures/myPic.gif",dbObjects.get(0).get("photo"));
	                Assert.assertEquals("The new me",dbObjects.get(0).get("biography"));
	                Assert.assertEquals("[ \"02\"]".trim(),dbObjects.get(0).get("sessions").toString());
	            }
	        });
	}
	
	@Test
	public void test_changepassword_ok(){
		Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		 additionalConfiguration.put("smtp.mock", true);
	     running(fakeApplication(additionalConfiguration), new Runnable() {
	            public void run() {
	            	Logger.info("Changement du mot de passe d'un participant");
	            	try {
						TestUtils.updateDatabase("test/data/participant.js");
						Map<String, Object> params = new HashMap<String, Object>();
		                params.put("oldpassword", "testjcertif");
		                params.put("newpassword", "testjcertifnew");
		                Result result = callAction(routes.ref.ParticipantController.changePasswordParticipant("test@participant.com"), fakeRequest().withJsonBody(Json.toJson(params), POST));
		                assertThat(status(result)).isEqualTo(OK);	                

		                Logger.info("Vérification que le nouveau mot de passe a bien été enregistré");
		                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(Constantes.COLLECTION_PARTICIPANT, new BasicDBObject().append("email", "test@participant.com"));
		                Assert.assertTrue(null != dbObjects);
		                Assert.assertEquals(1,dbObjects.size());
		                Assert.assertNotSame("mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg=",dbObjects.get(0).get("password"));
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
	
	@Test
	public void test_reinitpassword_ok(){
		Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		 additionalConfiguration.put("smtp.mock", true);
	     running(fakeApplication(additionalConfiguration), new Runnable() {
	            public void run() {
	            	Logger.info("Réinitialisation du mot de passe d'un participant");
	            	try {
						TestUtils.updateDatabase("test/data/participant.js");
		                Result result = callAction(routes.ref.ParticipantController.reinitPasswordParticipant("test-senior@participant.com"), fakeRequest());
		                assertThat(status(result)).isEqualTo(OK);	                

		                Logger.info("Vérification que le nouveau mot de passe a bien été enregistré");
		                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(Constantes.COLLECTION_PARTICIPANT, new BasicDBObject().append("email", "test-senior@participant.com"));
		                Assert.assertTrue(null != dbObjects);
		                Assert.assertEquals(1,dbObjects.size());
		                Assert.assertNotSame("mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg=",dbObjects.get(0).get("password"));
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
	
	@Test
	public void test_session_enrollment_ok(){
		 Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		 additionalConfiguration.put("smtp.mock", true);
	     running(fakeApplication(additionalConfiguration), new Runnable() {
	            public void run() {
	            	Logger.info("Inscription d'un participant à une session");
	            	try {
						TestUtils.updateDatabase("test/data/participant.js");
						Result result = callAction(routes.ref.ParticipantController.inscrireParticipantSession("test-senior@participant.com", "101"), fakeRequest());
		                assertThat(status(result)).isEqualTo(OK);	                

		                Logger.info("Vérification que le nouveau participant a bien été ajouté à la session");
		                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(Constantes.COLLECTION_PARTICIPANT, new BasicDBObject().append("email", "test-senior@participant.com"));
		                Assert.assertTrue(null != dbObjects);
		                Assert.assertEquals(1,dbObjects.size());
		                Assert.assertEquals("[ \"01\" , \"03\" , \"05\" , \"101\"]",dbObjects.get(0).get("sessions").toString());
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	                
	            }
	        });
	}
	
	@Test
	public void test_session_enrollment_email_does_not_exists(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("On ne peut inscrire à une session qu'un participant répertorié");
	            	try {
						TestUtils.updateDatabase("test/data/participant.js");
						Result result = callAction(routes.ref.ParticipantController.inscrireParticipantSession("toto@gmail.com", "101"), fakeRequest());
		                assertThat(status(result)).isEqualTo(NOT_FOUND);	                
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	                
	            }
	        });
	}
	
	@Test
	public void test_session_enrollment_session_does_not_exists(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("On ne peut inscrire un participant qu' une session existante");
	            	try {
						TestUtils.updateDatabase("test/data/participant.js");
						Result result = callAction(routes.ref.ParticipantController.inscrireParticipantSession("jandiew@gmail.com", "105"), fakeRequest());
		                assertThat(status(result)).isEqualTo(NOT_FOUND);	                
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	                
	            }
	        });
	}
	
	@Test
	public void test_session_enrollment_participant_already_registered(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Un participant doit être inscrit à une session pour pouvoir en être déinscrit");
	            	try {
						TestUtils.updateDatabase("test/data/participant.js");
						Result result = callAction(routes.ref.ParticipantController.inscrireParticipantSession("test-senior@participant.com", "03"), fakeRequest());
		                assertThat(status(result)).isEqualTo(CONFLICT);	                
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	                
	            }
	        });
	}
	
	@Test
	public void test_session_unenrollment_ok(){
		 Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		 additionalConfiguration.put("smtp.mock", true);
	     running(fakeApplication(additionalConfiguration), new Runnable() {
	            public void run() {
	            	Logger.info("Désinscription d'un participant à une session");
	            	try {
						TestUtils.updateDatabase("test/data/participant.js");
						Result result = callAction(routes.ref.ParticipantController.desinscrireParticipantSession("test-senior@participant.com", "03"), fakeRequest());
		                assertThat(status(result)).isEqualTo(OK);	                

		                Logger.info("Vérification que le nouveau participant a bien été supprimé de la session");
		                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(Constantes.COLLECTION_PARTICIPANT, new BasicDBObject().append("email", "test-senior@participant.com"));
		                Assert.assertTrue(null != dbObjects);
		                Assert.assertEquals(1,dbObjects.size());
		                Assert.assertEquals("[ \"01\" , \"05\"]",dbObjects.get(0).get("sessions").toString());
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	                
	            }
	        });
	}
	
	@Test
	public void test_session_unenrollment_email_does_not_exists(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Un participant doit exister pour pouvoir le désinscrire à une session");
	            	try {
						TestUtils.updateDatabase("test/data/participant.js");
						Result result = callAction(routes.ref.ParticipantController.desinscrireParticipantSession("toto@participant.com", "03"), fakeRequest());
		                assertThat(status(result)).isEqualTo(NOT_FOUND);	                
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	                
	            }
	        });
	}
	
	@Test
	public void test_session_unenrollment_session_does_not_exists(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("On ne peut désinscrire un participant d'une session que lorsque la session existe");
	            	try {
						TestUtils.updateDatabase("test/data/participant.js");
						Result result = callAction(routes.ref.ParticipantController.desinscrireParticipantSession("jandiew@gmail.com", "103"), fakeRequest());
		                assertThat(status(result)).isEqualTo(NOT_FOUND);	                
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	                
	            }
	        });
	}
	
	@Test
	public void test_session_unenrollment_participant_not_registered(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Un participant doit être inscrit à une session pour pouvoir en être déinscrit");
	            	try {
						TestUtils.updateDatabase("test/data/participant.js");
						Result result = callAction(routes.ref.ParticipantController.desinscrireParticipantSession("jandiew@gmail.com", "03"), fakeRequest());
		                assertThat(status(result)).isEqualTo(BAD_REQUEST);	                
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	                
	            }
	        });
	}
}
