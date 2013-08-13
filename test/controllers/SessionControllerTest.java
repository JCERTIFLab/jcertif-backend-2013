package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.FORBIDDEN;
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

import models.util.TestConstantes;

import org.codehaus.jackson.JsonNode;
import org.junit.Assert;
import org.junit.Test;

import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import util.TestUtils;

import com.mongodb.BasicDBObject;

public class SessionControllerTest {

    @Test
    public void test_listSession() throws java.io.IOException {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                try {
                    TestUtils.updateDatabase("test/data/session.js");
                    TestUtils.updateDatabase("test/data/token.js");
					
                    Result result = route(fakeRequest(GET, "/admin/session/list?access_token=ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8&provider=google"));
                    assertThat(status(result)).isEqualTo(OK);
                    assertThat(contentType(result)).isEqualTo("application/json");
                    
                    JsonNode jsonNode = Json.parse(contentAsString(result));
                    Assert.assertEquals(3, jsonNode.size());
	                Assert.assertEquals("101",jsonNode.get(0).findPath("id").getTextValue());
	                Assert.assertEquals("title 1",jsonNode.get(0).findPath("title").getTextValue());
	                Assert.assertEquals("summary 1",jsonNode.get(0).findPath("summary").getTextValue());
	                Assert.assertEquals("description 1",jsonNode.get(0).findPath("description").getTextValue());
	                Assert.assertEquals("Status1",jsonNode.get(0).findPath("status").getTextValue());
	                Assert.assertEquals("keyword 1",jsonNode.get(0).findPath("keyword").getTextValue());
	                Assert.assertEquals("12/02/2013 10:22",jsonNode.get(0).findPath("start").getTextValue());
	                Assert.assertEquals("16/02/2013 10:23",jsonNode.get(0).findPath("end").getTextValue());
	                Assert.assertEquals("[\"11\",\"12\"]",jsonNode.get(0).findPath("speakers").toString().trim());
	                Assert.assertEquals("[\"HTML 5\",\"Android\"]",jsonNode.get(0).findPath("category").toString().trim());
	                Assert.assertEquals("01",jsonNode.get(0).findPath("version").getTextValue());
	                Assert.assertEquals("false",jsonNode.get(0).findPath("deleted").getTextValue());
	                
	                Assert.assertEquals("102",jsonNode.get(1).findPath("id").getTextValue());
	                Assert.assertEquals("title 2",jsonNode.get(1).findPath("title").getTextValue());
	                Assert.assertEquals("summary 2",jsonNode.get(1).findPath("summary").getTextValue());
	                Assert.assertEquals("description 2",jsonNode.get(1).findPath("description").getTextValue());
	                Assert.assertEquals("Status2",jsonNode.get(1).findPath("status").getTextValue());
	                Assert.assertEquals("keyword 2",jsonNode.get(1).findPath("keyword").getTextValue());
	                Assert.assertEquals("12/02/2013 10:22",jsonNode.get(1).findPath("start").getTextValue());
	                Assert.assertEquals("16/02/2013 10:23",jsonNode.get(1).findPath("end").getTextValue());
	                Assert.assertEquals("[\"21\",\"22\"]",jsonNode.get(1).findPath("speakers").toString().trim());
	                Assert.assertEquals("[\"HTML 5\",\"Android\"]",jsonNode.get(1).findPath("category").toString().trim());
	                Assert.assertEquals("03",jsonNode.get(1).findPath("version").getTextValue());
	                Assert.assertEquals("false",jsonNode.get(1).findPath("deleted").getTextValue());
	                
	                Assert.assertEquals("104",jsonNode.get(2).findPath("id").getTextValue());
	                Assert.assertEquals("title 2",jsonNode.get(2).findPath("title").getTextValue());
	                Assert.assertEquals("summary 2",jsonNode.get(2).findPath("summary").getTextValue());
	                Assert.assertEquals("description 2",jsonNode.get(2).findPath("description").getTextValue());
	                Assert.assertEquals("Approuvé",jsonNode.get(2).findPath("status").getTextValue());
	                Assert.assertEquals("keyword 2",jsonNode.get(2).findPath("keyword").getTextValue());
	                Assert.assertEquals("12/02/2013 10:22",jsonNode.get(2).findPath("start").getTextValue());
	                Assert.assertEquals("16/02/2013 10:23",jsonNode.get(2).findPath("end").getTextValue());
	                Assert.assertEquals("[\"21\",\"22\"]",jsonNode.get(2).findPath("speakers").toString().trim());
	                Assert.assertEquals("[\"HTML 5\",\"Android\"]",jsonNode.get(2).findPath("category").toString().trim());
	                Assert.assertEquals("03",jsonNode.get(2).findPath("version").getTextValue());
	                Assert.assertEquals("false",jsonNode.get(2).findPath("deleted").getTextValue());
	                TestUtils.updateDatabase("test/data/purge.js");
                } catch (IOException e) {
                	Assert.fail(e.getMessage());
                }
            }
        });
    }
    
    @Test
    public void test_listSession_filtered() throws java.io.IOException {
        running(fakeApplication(), new Runnable() {
            public void run() {
                try {
                    TestUtils.updateDatabase("test/data/session.js");
                    Result result = route(fakeRequest(GET, "/session/list"));
                    assertThat(status(result)).isEqualTo(OK);
                    assertThat(contentType(result)).isEqualTo("application/json");
                    
                    JsonNode jsonNode = Json.parse(contentAsString(result));
                    Assert.assertEquals(1, jsonNode.size());
	                
	                Assert.assertEquals("104",jsonNode.get(0).findPath("id").getTextValue());
	                Assert.assertEquals("title 2",jsonNode.get(0).findPath("title").getTextValue());
	                Assert.assertEquals("summary 2",jsonNode.get(0).findPath("summary").getTextValue());
	                Assert.assertEquals("description 2",jsonNode.get(0).findPath("description").getTextValue());
	                Assert.assertEquals("Approuvé",jsonNode.get(0).findPath("status").getTextValue());
	                Assert.assertEquals("keyword 2",jsonNode.get(0).findPath("keyword").getTextValue());
	                Assert.assertEquals("12/02/2013 10:22",jsonNode.get(0).findPath("start").getTextValue());
	                Assert.assertEquals("16/02/2013 10:23",jsonNode.get(0).findPath("end").getTextValue());
	                Assert.assertEquals("[\"21\",\"22\"]",jsonNode.get(0).findPath("speakers").toString().trim());
	                Assert.assertEquals("[\"HTML 5\",\"Android\"]",jsonNode.get(0).findPath("category").toString().trim());
	                Assert.assertEquals("03",jsonNode.get(0).findPath("version").getTextValue());
	                Assert.assertEquals("false",jsonNode.get(0).findPath("deleted").getTextValue());
	                TestUtils.updateDatabase("test/data/purge.js");
                } catch (IOException e) {
                	Assert.fail(e.getMessage());
                }
            }
        });
    }
    
    @Test
    public void test_listSession_diff() throws java.io.IOException {
        running(fakeApplication(), new Runnable() {
            public void run() {
                try {
                    TestUtils.updateDatabase("test/data/session.js");
                    Result result = route(fakeRequest(GET, "/session/list/02"));
                    assertThat(status(result)).isEqualTo(OK);
                    assertThat(contentType(result)).isEqualTo("application/json");
                    
                    JsonNode jsonNode = Json.parse(contentAsString(result));
                    Assert.assertEquals(1, jsonNode.size());
	                Assert.assertEquals("104",jsonNode.get(0).findPath("id").getTextValue());
	                Assert.assertEquals("title 2",jsonNode.get(0).findPath("title").getTextValue());
	                Assert.assertEquals("summary 2",jsonNode.get(0).findPath("summary").getTextValue());
	                Assert.assertEquals("description 2",jsonNode.get(0).findPath("description").getTextValue());
	                Assert.assertEquals("Approuvé",jsonNode.get(0).findPath("status").getTextValue());
	                Assert.assertEquals("keyword 2",jsonNode.get(0).findPath("keyword").getTextValue());
	                Assert.assertEquals("12/02/2013 10:22",jsonNode.get(0).findPath("start").getTextValue());
	                Assert.assertEquals("16/02/2013 10:23",jsonNode.get(0).findPath("end").getTextValue());
	                Assert.assertEquals("[\"21\",\"22\"]",jsonNode.get(0).findPath("speakers").toString().trim());
	                Assert.assertEquals("[\"HTML 5\",\"Android\"]",jsonNode.get(0).findPath("category").toString().trim());
	                Assert.assertEquals("03",jsonNode.get(0).findPath("version").getTextValue());
	                Assert.assertEquals("false",jsonNode.get(0).findPath("deleted").getTextValue());
	                
	                TestUtils.updateDatabase("test/data/purge.js");
                } catch (IOException e) {
                	Assert.fail(e.getMessage());
                }
            }
        });
    }
    
    @Test
    public void test_session_new_ok() {   	
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
		additionalConfiguration.put("smtp.mock", true);
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                Logger.info("Proposition d'une nouvelle session");
                try {
					TestUtils.updateDatabase("test/data/session.js");
					TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@session.com");
	                params.put("title", "Lost in the jungle");
	                params.put("summary", "Learn how to suvive in the jungle");
	                params.put("description", "A small desc of lost in the jungle");
	                params.put("status", "Approuvé");
	                params.put("keyword", "Lost-Jungle");
	                params.put("category", new String[]{"Java"});
	                params.put("start", "12/02/2013 10:22");
	                params.put("end", "16/02/2013 10:23");
	                params.put("speakers", new String[]{"01","02"});
	                Result result = callAction(routes.ref.SessionController.newSession(), fakeRequest().withJsonBody(Json.toJson(params)));
	                assertThat(status(result)).isEqualTo(OK);

	                Logger.info("Vérification que la nouvelle session est bien présente en base de données");
	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_SESSION, new BasicDBObject().append("id", "01"));
	                Assert.assertTrue(null != dbObjects);
	                Assert.assertEquals(1,dbObjects.size());
	                Assert.assertEquals("Lost in the jungle",dbObjects.get(0).get("title"));
	                Assert.assertEquals("Learn how to suvive in the jungle",dbObjects.get(0).get("summary"));
	                Assert.assertEquals("A small desc of lost in the jungle",dbObjects.get(0).get("description"));
	                Assert.assertEquals("Brouillon",dbObjects.get(0).get("status"));
	                Assert.assertEquals("Lost-Jungle",dbObjects.get(0).get("keyword"));
	                Assert.assertEquals("[ \"Java\"]",dbObjects.get(0).get("category").toString());
	                Assert.assertEquals("12/02/2013 10:22",dbObjects.get(0).get("start"));
	                Assert.assertEquals("16/02/2013 10:23",dbObjects.get(0).get("end"));
	                Assert.assertEquals("[ \"01\" , \"02\"]",dbObjects.get(0).get("speakers").toString());
	                Assert.assertEquals("01",dbObjects.get(0).get("version"));
	                Assert.assertEquals("false",dbObjects.get(0).get("deleted").toString());
	                TestUtils.updateDatabase("test/data/purge.js");
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
                

            }
        });
    }

    @Test
    public void test_session_new_invalid_title_null() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_new_invalid_title_null ***");
                	Logger.info("Le title d'une session ne peut etre ansent du inner json ");
                	try {
                		TestUtils.updateDatabase("test/data/session_update.js");
                		TestUtils.updateDatabase("test/data/token.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
    					params.put("provider", "google");
    					params.put("email", "test@session.com");
                        params.put("id", "101");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", new String[]{"HTML 5"});
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", new String[]{"13"});
                        
                        Result result = callAction(routes.ref.SessionController.newSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        assertThat(contentAsString(result)).contains("Title cannot be empty or null");
                        TestUtils.updateDatabase("test/data/purge.js");
                        Logger.info("*** FIN -> test_session_new_invalid_title_null ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_session_new_invalid_title_empty() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_new_invalid_title_empty ***");
                	Logger.info("Le title d'une session ne peut etre vide");
                	try {
                		TestUtils.updateDatabase("test/data/session_update.js");
                		TestUtils.updateDatabase("test/data/token.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
    					params.put("provider", "google");
    					params.put("email", "test@session.com");
                        params.put("id", "111");
                        params.put("title", "");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", new String[]{"HTML 5"});
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", new String[]{"13"});
                        
                        Result result = callAction(routes.ref.SessionController.newSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        assertThat(contentAsString(result)).contains("Title cannot be empty or null");
                        TestUtils.updateDatabase("test/data/purge.js");
                        Logger.info("*** FIN -> test_session_new_invalid_title_empty ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }    
    
    @Test
    public void test_session_new_invalid_summary_empty() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_new_invalid_summary_empty ***");
                	Logger.info("Le summary d'une session ne peut etre vide");
                	try {
                		TestUtils.updateDatabase("test/data/session_update.js");
                		TestUtils.updateDatabase("test/data/token.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
    					params.put("provider", "google");
    					params.put("email", "test@session.com");
                        params.put("id", "111");
                        params.put("title", "title 2");
                        params.put("summary", "");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", new String[]{"HTML 5"});
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", new String[]{"13"});
                        
                        Result result = callAction(routes.ref.SessionController.newSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        TestUtils.updateDatabase("test/data/purge.js");
                        assertThat(contentAsString(result)).contains("Summary cannot be empty or null");
                        Logger.info("*** FIN -> test_session_new_invalid_summary_empty ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_session_new_invalid_summary_null() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_new_invalid_summary_null ***");
                	Logger.info("Le summary d'une session ne peut etre ansent du inner json ");
                	try {
                		TestUtils.updateDatabase("test/data/session_update.js");
                		TestUtils.updateDatabase("test/data/token.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
    					params.put("provider", "google");
    					params.put("email", "test@session.com");
                        params.put("id", "111");
                        params.put("title", "title 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", new String[]{"HTML 5"});
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", new String[]{"13"});
                        
                        Result result = callAction(routes.ref.SessionController.newSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        TestUtils.updateDatabase("test/data/purge.js");
                        assertThat(contentAsString(result)).contains("Summary cannot be empty or null");
                        Logger.info("*** FIN -> test_session_new_invalid_summary_null ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }

    @Test
    public void test_session_new_invalid_description_empty() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_new_invalid_description_empty ***");
                	Logger.info("La description d'une session ne peut etre vide");
                	try {
                		TestUtils.updateDatabase("test/data/session_update.js");
                		TestUtils.updateDatabase("test/data/token.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
    					params.put("provider", "google");
    					params.put("email", "test@session.com");
                        params.put("id", "111");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", new String[]{"HTML 5"});
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", new String[]{"13"});
                        
                        Result result = callAction(routes.ref.SessionController.newSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        TestUtils.updateDatabase("test/data/purge.js");
                        assertThat(contentAsString(result)).contains("Description cannot be empty or null");
                        Logger.info("*** FIN -> test_session_new_invalid_description_empty ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_session_new_invalid_description_null() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_new_invalid_description_null ***");
                	Logger.info("Le summary d'une session ne peut etre ansent du inner json ");
                	try {
                		TestUtils.updateDatabase("test/data/session_update.js");
                		TestUtils.updateDatabase("test/data/token.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
    					params.put("provider", "google");
    					params.put("email", "test@session.com");
                        params.put("id", "111");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", new String[]{"HTML 5"});
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", new String[]{"13"});
                        
                        Result result = callAction(routes.ref.SessionController.newSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        TestUtils.updateDatabase("test/data/purge.js");
                        assertThat(contentAsString(result)).contains("Description cannot be empty or null");
                        Logger.info("*** FIN -> test_session_new_invalid_description_null ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
	
    @Test
    public void test_session_new_invalid_keyword_empty() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_new_invalid_keyword_empty ***");
                	Logger.info("Le keyword d'une session ne peut etre vide");
                	try {
                		TestUtils.updateDatabase("test/data/session_update.js");
                		TestUtils.updateDatabase("test/data/token.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
    					params.put("provider", "google");
    					params.put("email", "test@session.com");
                        params.put("id", "111");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "");
                        params.put("category", new String[]{"HTML 5"});
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", new String[]{"13"});
                        
                        Result result = callAction(routes.ref.SessionController.newSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        TestUtils.updateDatabase("test/data/purge.js");
                        assertThat(contentAsString(result)).contains("Keyword cannot be empty or null");
                        Logger.info("*** FIN -> test_session_new_invalid_keyword_empty ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_session_new_invalid_keyword_null() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_new_invalid_keyword_null() ***");
                	Logger.info("Le keyword d'une session ne peut etre absent du inner json ");
                	try {
                		TestUtils.updateDatabase("test/data/session_update.js");
                		TestUtils.updateDatabase("test/data/token.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
    					params.put("provider", "google");
    					params.put("email", "test@session.com");
                        params.put("id", "111");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("category", new String[]{"HTML 5"});
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", new String[]{"13"});
                        
                        Result result = callAction(routes.ref.SessionController.newSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        TestUtils.updateDatabase("test/data/purge.js");
                        assertThat(contentAsString(result)).contains("Keyword cannot be empty or null");
                        Logger.info("*** FIN -> test_session_new_invalid_keyword_null() ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }

    @Test
    public void test_session_new_invalid_category_empty() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_new_invalid_category_empty ***");
                	Logger.info("La category d'une session ne peut etre vide");
                	try {
                		TestUtils.updateDatabase("test/data/session_update.js");
                		TestUtils.updateDatabase("test/data/token.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
    					params.put("provider", "google");
    					params.put("email", "test@session.com");
                        params.put("id", "111");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", new String[]{});
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", new String[]{"13"});
                        
                        Result result = callAction(routes.ref.SessionController.newSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        assertThat(contentAsString(result)).contains("Category cannot be empty or null");
                        TestUtils.updateDatabase("test/data/purge.js");
                        Logger.info("*** FIN -> test_session_new_invalid_category_empty ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_session_new_invalid_category_null() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_new_invalid_category_null() ***");
                	Logger.info("Le keyword d'une session ne peut etre absent du inner json ");
                	try {
                		TestUtils.updateDatabase("test/data/session_update.js");
                		TestUtils.updateDatabase("test/data/token.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
    					params.put("provider", "google");
    					params.put("email", "test@session.com");
                        params.put("id", "111");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("start", "12/12/2013 10:22");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", new String[]{"13"});
                        
                        Result result = callAction(routes.ref.SessionController.newSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        assertThat(contentAsString(result)).contains("Category cannot be empty or null");
                        TestUtils.updateDatabase("test/data/purge.js");
                        Logger.info("*** FIN -> test_session_new_invalid_category_null() ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_session_new_invalid_date_start_format() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_new_invalid_date_start_format ***");
                	Logger.info("Le format de la start date d'une session doit etre conforme à : JJ/MM/AAAA hh:mm");
                	try {
                		TestUtils.updateDatabase("test/data/session_update.js");
                		TestUtils.updateDatabase("test/data/token.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
    					params.put("provider", "google");
    					params.put("email", "test@session.com");
                        params.put("id", "111");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", new String[]{"Android","HTML 5"});
                        params.put("start", "2013/6/5 7:23");
                        params.put("end", "16/12/2013 10:23");
                        params.put("speakers", new String[]{"13","34"});
                        
                        Result result = callAction(routes.ref.SessionController.newSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        assertThat(contentAsString(result)).contains("Start Date is not valid");
                        TestUtils.updateDatabase("test/data/purge.js");
                        Logger.info("*** FIN -> test_session_new_invalid_date_start_format ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_session_new_invalid_date_end_format() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_new_invalid_date_end_format() ***");
                	Logger.info("La start date d'une session ne peut etre absent du inner json ");
                	try {
                		TestUtils.updateDatabase("test/data/session_update.js");
                		TestUtils.updateDatabase("test/data/token.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
    					params.put("provider", "google");
    					params.put("email", "test@session.com");
                        params.put("id", "111");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", new String[]{"Android","HTML 5"} );
                        params.put("start", "16/12/2013 10:23");
                        params.put("end", "2013/12/23 3:43");
                        params.put("speakers", new String[]{"13","34"});
                        
                        Result result = callAction(routes.ref.SessionController.newSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        assertThat(contentAsString(result)).contains("End Date is not valid");
                        TestUtils.updateDatabase("test/data/purge.js");
                        Logger.info("*** FIN -> test_session_new_invalid_date_end_format() ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_session_new_inconsitent_start_date() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_new_inconsitent_start_date() ***");
                	Logger.info("La start date d'une session ne peut etre supérieur ou égal a sa end date ");
                	try {
                		TestUtils.updateDatabase("test/data/session_update.js");
                		TestUtils.updateDatabase("test/data/token.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
    					params.put("provider", "google");
    					params.put("email", "test@session.com");
                        params.put("id", "111");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("category", new String[]{"Android","HTML 5"} );
                        params.put("start", "16/12/2013 10:23");
                        params.put("end", "07/04/2013 3:43");
                        params.put("speakers", new String[]{"13","34"});
                        
                        Result result = callAction(routes.ref.SessionController.newSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        assertThat(contentAsString(result)).contains("Start Date must not be equals or greater than End Date");
                        TestUtils.updateDatabase("test/data/purge.js");
                        Logger.info("*** FIN -> test_session_new_inconsitent_start_date() ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_session_new_unregistred_category() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_new_unregistred_category() ***");
                	Logger.info("Les catégories d'une session doivent faire partie de la liste des caégories");
                	try {
                		TestUtils.updateDatabase("test/data/session_update.js");
                		TestUtils.updateDatabase("test/data/token.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
    					params.put("provider", "google");
    					params.put("email", "test@session.com");
                        params.put("id", "111");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status1");
                        params.put("keyword", "keyword 2");
                        params.put("category", new String[]{"category","NotAValidCategory"} );
                        params.put("start", "16/12/2013 10:23");
                        params.put("end", "20/12/2013 3:43");
                        params.put("speakers", new String[]{"13","34"});
                        
                        Result result = callAction(routes.ref.SessionController.newSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        assertThat(contentAsString(result)).contains("does not exist. Check Category List");
                        TestUtils.updateDatabase("test/data/purge.js");
                        Logger.info("*** FIN -> test_session_new_unregistred_category() ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    /**
     * Tests fonctionnels du service deleteSession  
    **/
    
    @Test
    public void test_unAuthUser_session_deletion_forbidden() {
    	Logger.info("*** DEBUT -> test_session_deletion_forbidden ***");
    	Logger.info("Seul le user avec le role admin doit pouvoir supprimer une session");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", "101");
            	Result result = callAction(routes.ref.SessionController.removeSession(), fakeRequest().withSession("user", "normal").withJsonBody(Json.toJson(params), POST));
                assertThat(status(result)).isEqualTo(FORBIDDEN);
            }
        });
        Logger.info("*** FIN -> test_session_deletion_forbidden ***");
    }
    
    @Test
    public void test_delete_session_invalid_inner_json() {
    	Logger.info("*** DEBUT -> test_delete_session_invalid_inner_json ***");
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@session.com");
	                params.put("", "101");
	            	Result result = callAction(routes.ref.SessionController.removeSession(), fakeRequest().withJsonBody(Json.toJson(params)));
	            	Logger.info("tracked result is : " + status(result));
	            	assertThat(status(result)).isEqualTo(BAD_REQUEST);
				} catch (IOException e) {
					Logger.error("Une erreur est survenue lors du test de suppression de la session", e);
				}finally{
					try {
						TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Logger.error("Une erreur est survenue lors du test de suppression de la session", e);
					}
				}
				
            }
        });
        Logger.info("*** FIN -> test_delete_session_invalid_inner_json ***");
    }
    
    @Test
    public void test_session_deletion_inner_json_null() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_deletion_inner_json_null ***");
                	try {
						TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@session.com");
	                    params.put("id", "");
	                    Logger.info("Le format json d'entrée doit être valide (id pas empty ni null)");
	                    Result result = callAction(routes.ref.SessionController.removeSession(), fakeRequest().withJsonBody(Json.toJson(params)));
	                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
	                    Logger.info("*** FIN -> test_session_deletion_inner_json_null ***");
					} catch (IOException e) {
						Logger.error("Une erreur est survenue lors du test de suppression de la session", e);
					}finally{
						try {
							TestUtils.updateDatabase("test/data/purge.js");
						} catch (IOException e) {
							Logger.error("Une erreur est survenue lors du test de suppression de la session", e);
						}
					}
					
            }
        });
    }
    
    @Test
    public void test_session_deletion_unregistred_session_id() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_deletion_unregistred_session_id ***");
                	try {
                		TestUtils.updateDatabase("test/data/session.js");
                		TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@session.com");
                        params.put("id", "10000000");                        
                        Logger.info("La demande de suppession ne doit concerner que des sessions existantes.");
                        Result result = callAction(routes.ref.SessionController.removeSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(NOT_FOUND);
                        Logger.info("*** FIN -> test_session_deletion_unregistred_session_id ***");
                        TestUtils.updateDatabase("test/data/purge.js");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de l'existence de la session relative au inner ID", e);
					}
            }
        });
    }

    @Test
    public void test_session_delletion_OK() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_all_OK ***");
                	try {
                		Logger.info("Tous les inner params sont valides.");
                		TestUtils.updateDatabase("test/data/session.js");
                		TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@session.com");
                        params.put("id", "101");
                        params.put("version", "01");
                        Result result = callAction(routes.ref.SessionController.removeSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(OK);
                        List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_SESSION, new BasicDBObject().append("id", "101"));
                        Assert.assertTrue(null != dbObjects);
                        Assert.assertEquals(1,dbObjects.size());
                        Assert.assertEquals("02",dbObjects.get(0).getString("version"));
		                Assert.assertEquals("true",dbObjects.get(0).getString("deleted"));
                        Logger.info("*** FIN -> test_session_all_OK ***"); 
                        TestUtils.updateDatabase("test/data/purge.js");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de l'existence de la session relative au inner ID", e);
					}
            }
        });
    }

    /**
     * Tests fonctionnels du service updateSession  
    **/
    
    @Test
    public void test_unAuthUser_session_update_forbidden() {
    	Logger.info("*** DEBUT -> test_unAuthUser_session_update_forbidden ***");
    	Logger.info("Seul le user avec le role admin doit pouvoir mettre a jour une session");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", "101");
            	Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withSession("user", "normal").withJsonBody(Json.toJson(params), POST));
                assertThat(status(result)).isEqualTo(FORBIDDEN);
            }
        });
        Logger.info("*** FIN -> test_unAuthUser_session_update_forbidden ***");
    }
    
    @Test
    public void test_update_session_invalid_inner_json() {
    	Logger.info("*** DEBUT -> test_update_session_invalid_inner_json ***");
    	Logger.info("Les inner params doivent former un objet json valide");
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@session.com");
	                params.put("", "101");
	            	Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withJsonBody(Json.toJson(params)));
	            	assertThat(status(result)).isEqualTo(BAD_REQUEST);
				} catch (IOException e) {
					Logger.error("Une erreur est survenue lors du test de mise à jour de la session", e);
				}finally{
					try {
						TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Logger.error("Une erreur est survenue lors du test de mise à jour de la session", e);
					}
				}
            	
            }
        });
        Logger.info("*** FIN -> test_update_session_invalid_inner_json ***");
    }
    
    @Test
    public void test_update_session_invalid_id_null() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_id_null ***");
                	try {
						TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@session.com");
	                    params.put("id", "");
	                    Logger.info("Le format json d'entrée doit être valide (id doit etre un number)");
	                    Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withJsonBody(Json.toJson(params)));
	                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
	                    Logger.info("*** FIN -> test_update_session_invalid_id_null ***");
					} catch (IOException e) {
						Logger.error("Une erreur est survenue lors du test de mise à jour de la session", e);
					}finally{
						try {
							TestUtils.updateDatabase("test/data/purge.js");
						} catch (IOException e) {
							Logger.error("Une erreur est survenue lors du test de mise à jour de la session", e);
						}
					}
					
            }
        });
    }
    
    @Test
    public void test_update_session_invalid_id() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_invalid_id ***");
                	try {
						TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@session.com");
	                    params.put("id", "abc1_");
	                    Logger.info("Le format json d'entrée doit être valide (id doit etre un number)");
	                    Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withJsonBody(Json.toJson(params)));
	                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
	                    Logger.info("*** FIN -> test_update_session_invalid_id ***");
					} catch (IOException e) {
						Logger.error("Une erreur est survenue lors du test de mise à jour de la session", e);
					}finally{
						try {
							TestUtils.updateDatabase("test/data/purge.js");
						} catch (IOException e) {
							Logger.error("Une erreur est survenue lors du test de mise à jour de la session", e);
						}
					}
					
            }
        });
    }

    @Test
    public void test_update_session_unregistered_room() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_session_unreistered_room() ***");
                	Logger.info("Tous les inner params sont valides ");
                	try {
                		TestUtils.updateDatabase("test/data/session.js");
                		TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@session.com");
                        params.put("id", "101");
                        params.put("title", "title 2");
                        params.put("summary", "summary 2");
                        params.put("description", "description 2");
                        params.put("status", "Status2");
                        params.put("keyword", "keyword 2");
                        params.put("room", "105");
                        params.put("category", new String[]{"Android","HTML 5"});
                        params.put("start", "16/10/2013 10:23");
                        params.put("end", "27/12/2013 03:43");
                        params.put("speakers", new String[]{"13","34"});
                        params.put("version", "01");
                        
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        assertThat(contentAsString(result)).contains("does not exist. Check Room List");
                        Logger.info("*** FIN -> test_update_session_unreistered_room() ***");
                        TestUtils.updateDatabase("test/data/purge.js");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la session", e);
					}
            }
        });
    }
    
    @Test
    public void test_session_update_OK() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_update_OK ***");
                	try {
                		Logger.info("Tous les inner params sont valides.");
                		TestUtils.updateDatabase("test/data/session.js");
                		TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@session.com");
                        params.put("id", "101");
                        params.put("title", "title 3");
                        params.put("summary", "summary 3");
                        params.put("description", "description 3");
                        params.put("status", "Status3");
                        params.put("keyword", "keyword 3");
                        params.put("room", "01");
                        params.put("version", "01");
		                params.put("deleted", "false");
		                
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(OK);
                        
                        Logger.info("Vérification que la session a bien été modifiée en base de données");
    	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_SESSION, new BasicDBObject().append("id", "101"));
    	                Assert.assertTrue(null != dbObjects);
    	                Assert.assertEquals(1,dbObjects.size());
    	                BasicDBObject dbObject = dbObjects.get(0);
    	                Assert.assertEquals("101",dbObject.getString("id"));
    	                Assert.assertEquals("title 3",dbObject.getString("title"));
    	                Assert.assertEquals("summary 3",dbObject.getString("summary"));
    	                Assert.assertEquals("description 3",dbObject.getString("description"));
    	                Assert.assertEquals("Status3",dbObject.getString("status"));
    	                Assert.assertEquals("keyword 3",dbObject.getString("keyword"));
    	                Assert.assertEquals("01",dbObject.getString("room"));
    	                Assert.assertEquals("12/02/2013 10:22",dbObject.getString("start"));
    	                Assert.assertEquals("16/02/2013 10:23",dbObject.getString("end"));
    	                Assert.assertEquals("02",dbObject.getString("version"));
    	                Assert.assertEquals("false",dbObject.getString("deleted"));
                        Logger.info("*** FIN -> test_session_update_OK ***");
                        TestUtils.updateDatabase("test/data/purge.js");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise à jour d'une session", e);
						Assert.fail("Une erreur est survenue lors du test de mise à jour d'une session");
					}
            }
        });
    }
    
    @Test
    public void test_session_update_unregistred_session_id() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_session_update_unregistred_session_id ***");
                	try {
                		TestUtils.updateDatabase("test/data/session.js");
                		TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@session.com");
                        params.put("id", "10000000");                        
                        Logger.info("La demande de mise à jour ne doit concernée que des sessions existantes.");
                        Result result = callAction(routes.ref.SessionController.updateSession(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(NOT_FOUND);
                        Logger.info("*** FIN -> test_session_update_unregistred_session_id ***");
                        TestUtils.updateDatabase("test/data/purge.js");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise à jour d'une session", e);
						Assert.fail("Une erreur est survenue lors du test de mise à jour d'une session");
					}
            }
        });
    }
    
}