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

public class RoomControllerTest {

    @Test
    public void test_listRoom() throws java.io.IOException {
        running(fakeApplication(), new Runnable() {
            public void run() {
                try {
                    TestUtils.updateDatabase("test/data/room.js");
                    Result result = route(fakeRequest(GET, "/room/list"));
                    assertThat(status(result)).isEqualTo(OK);
                    assertThat(contentType(result)).isEqualTo("application/json");
                    
                    JsonNode jsonNode = Json.parse(contentAsString(result));
                    Assert.assertEquals(3, jsonNode.size());
	                Assert.assertEquals("101",jsonNode.get(0).findPath("id").getTextValue());
	                Assert.assertEquals("name 1",jsonNode.get(0).findPath("name").getTextValue());
	                Assert.assertEquals("101",jsonNode.get(0).findPath("site").getTextValue());
	                Assert.assertEquals("500",jsonNode.get(0).findPath("seats").getTextValue());
	                Assert.assertEquals("This is the bigest room",jsonNode.get(0).findPath("description").getTextValue());
	                Assert.assertEquals("http://www.website1.com/pictures/rooms/room1.gif",jsonNode.get(0).findPath("photo").getTextValue());
	                Assert.assertEquals("01",jsonNode.get(0).findPath("version").getTextValue());
	                Assert.assertEquals("false",jsonNode.get(0).findPath("deleted").getTextValue());
	                
	                Assert.assertEquals("102",jsonNode.get(1).findPath("id").getTextValue());
	                Assert.assertEquals("name 2",jsonNode.get(1).findPath("name").getTextValue());
	                Assert.assertEquals("101",jsonNode.get(1).findPath("site").getTextValue());
	                Assert.assertEquals("200",jsonNode.get(1).findPath("seats").getTextValue());
	                Assert.assertEquals("A medium size room",jsonNode.get(1).findPath("description").getTextValue());
	                Assert.assertEquals("http://www.website1.com/pictures/rooms/room2.gif",jsonNode.get(1).findPath("photo").getTextValue());
	                Assert.assertEquals("01",jsonNode.get(1).findPath("version").getTextValue());
	                Assert.assertEquals("false",jsonNode.get(1).findPath("deleted").getTextValue());
	                
	                Assert.assertEquals("103",jsonNode.get(2).findPath("id").getTextValue());
	                Assert.assertEquals("name 3",jsonNode.get(2).findPath("name").getTextValue());
	                Assert.assertEquals("102",jsonNode.get(2).findPath("site").getTextValue());
	                Assert.assertEquals("100",jsonNode.get(2).findPath("seats").getTextValue());
	                Assert.assertEquals("A small size room",jsonNode.get(2).findPath("description").getTextValue());
	                Assert.assertEquals("http://www.website2.com/pictures/rooms/room3.gif",jsonNode.get(2).findPath("photo").getTextValue());
	                Assert.assertEquals("01",jsonNode.get(2).findPath("version").getTextValue());
	                Assert.assertEquals("false",jsonNode.get(2).findPath("deleted").getTextValue());
	                TestUtils.updateDatabase("test/data/purge.js");
                } catch (IOException e) {
                	Assert.fail(e.getMessage());
                }
            }
        });
    }
    
    @Test
	public void test_get_room_not_found(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Récuperer les informations d'une salle, la salle doit exister");
	            	try {
						TestUtils.updateDatabase("test/data/room.js");
						Result result = callAction(routes.ref.RoomController.getRoom("100001"), fakeRequest());
		                assertThat(status(result)).isEqualTo(NOT_FOUND);	                
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
	
	@Test
	public void test_get_room(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Récuperer les informations d'une salle");
	            	try {
						TestUtils.updateDatabase("test/data/room.js");
						Result result = callAction(routes.ref.RoomController.getRoom("101"), fakeRequest());
		                assertThat(status(result)).isEqualTo(OK);	                
		                
		                Logger.info("Vérification des informations de la salle");
		                JsonNode jsonNode = Json.parse(contentAsString(result));
		                Assert.assertEquals("name 1",jsonNode.findPath("name").getTextValue());
		                Assert.assertEquals("101",jsonNode.findPath("site").getTextValue());
		                Assert.assertEquals("500",jsonNode.findPath("seats").getTextValue());
		                Assert.assertEquals("This is the bigest room",jsonNode.findPath("description").getTextValue());
		                Assert.assertEquals("http://www.website1.com/pictures/rooms/room1.gif",jsonNode.findPath("photo").getTextValue());
		                Assert.assertEquals("01",jsonNode.findPath("version").getTextValue());
		                Assert.assertEquals("false",jsonNode.findPath("deleted").getTextValue());
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
	
    @Test
    public void test_room_new_ok() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                Logger.info("Enregistrement d'une nouvelle salle");
                try {
					TestUtils.updateDatabase("test/data/room.js");
					TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@room.com");
	                params.put("name", "name 5");
	                params.put("site", "102");
	                params.put("seats", "600");
	                params.put("description", "The bigest room");
	                params.put("photo", "http://www.website2.com/pictures/rooms/room5.gif");
	                Result result = callAction(routes.ref.RoomController.newRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
	                assertThat(status(result)).isEqualTo(OK);

	                Logger.info("Vérification que la nouvelle salle est en base de données");
	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_ROOM, new BasicDBObject().append("id", "01"));
	                Assert.assertTrue(null != dbObjects);
	                Assert.assertEquals(1,dbObjects.size());
	                Assert.assertEquals("name 5",dbObjects.get(0).get("name"));
	                Assert.assertEquals("102",dbObjects.get(0).get("site"));
	                Assert.assertEquals("600",dbObjects.get(0).get("seats"));
	                Assert.assertEquals("The bigest room",dbObjects.get(0).get("description"));
	                Assert.assertEquals("http://www.website2.com/pictures/rooms/room5.gif",dbObjects.get(0).get("photo"));
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
    public void test_room_new_invalid_name_null() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_room_new_invalid_name_null ***");
                	Logger.info("Le name d'une salle ne peut etre absent");
                	try {
                		TestUtils.updateDatabase("test/data/room.js");
                		TestUtils.updateDatabase("test/data/token.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
    					params.put("provider", "google");
    					params.put("email", "test@room.com");
    	                params.put("site", "102");
    	                params.put("seats", "600");
    	                params.put("description", "The bigest room");
    	                params.put("photo", "http://www.website2.com/pictures/rooms/room5.gif");
    	                Result result = callAction(routes.ref.RoomController.newRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
    	                
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        TestUtils.updateDatabase("test/data/purge.js");
                        assertThat(contentAsString(result)).contains("Name cannot be empty or null");
                        Logger.info("*** FIN -> test_room_new_invalid_name_null ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la salle", e);
					}
            }
        });
    }

    @Test
    public void test_room_new_invalid_name_empty() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_room_new_invalid_name_empty ***");
            	Logger.info("Le name d'une salle ne peut etre vide");
            	try {
            		TestUtils.updateDatabase("test/data/room.js");
            		TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@room.com");
					params.put("name", "");
	                params.put("site", "102");
	                params.put("seats", "600");
	                params.put("description", "The bigest room");
	                params.put("photo", "http://www.website2.com/pictures/rooms/room5.gif");
	                Result result = callAction(routes.ref.RoomController.newRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("Name cannot be empty or null");
                    Logger.info("*** FIN -> test_room_new_invalid_name_empty ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour de la salle", e);
				}
            }
        });
    }
    
    @Test
    public void test_room_new_invalid_site_null() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_room_new_invalid_site_null ***");
            	Logger.info("Le champ site d'une salle ne peut etre absent");
            	try {
            		TestUtils.updateDatabase("test/data/room.js");
            		TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@room.com");
					params.put("name", "name 5");
					params.put("seats", "600");
	                params.put("description", "The bigest room");
	                params.put("photo", "http://www.website2.com/pictures/rooms/room5.gif");
	                Result result = callAction(routes.ref.RoomController.newRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("Site cannot be empty or null");
                    Logger.info("*** FIN -> test_room_new_invalid_site_null ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour de la salle", e);
				}
            }
        });
    }
    
    @Test
    public void test_room_new_invalid_site_empty() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_room_new_invalid_site_empty ***");
            	Logger.info("Le champ site d'une salle ne peut etre vide");
            	try {
            		TestUtils.updateDatabase("test/data/room.js");
            		TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@room.com");
					params.put("name", "name 5");
					params.put("site", "");
					params.put("seats", "600");
	                params.put("description", "The bigest room");
	                params.put("photo", "http://www.website2.com/pictures/rooms/room5.gif");
	                Result result = callAction(routes.ref.RoomController.newRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("Site cannot be empty or null");
                    Logger.info("*** FIN -> test_room_new_invalid_site_empty ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour de la salle", e);
				}
            }
        });
    }
    
    @Test
    public void test_room_new_invalid_seats_null() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_room_new_invalid_seats_null ***");
            	Logger.info("Le champ seats d'une salle ne peut etre absent");
            	try {
            		TestUtils.updateDatabase("test/data/room.js");
            		TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@room.com");
					params.put("name", "name 5");
					params.put("site", "101");
					params.put("description", "The bigest room");
	                params.put("photo", "http://www.website2.com/pictures/rooms/room5.gif");
	                Result result = callAction(routes.ref.RoomController.newRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("Seats cannot be empty or null");
                    Logger.info("*** FIN -> test_room_new_invalid_seats_null ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour de la salle", e);
				}
            }
        });
    }
	
    @Test
    public void test_room_new_invalid_seats_empty() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_room_new_invalid_seats_empty ***");
            	Logger.info("Le champ seats d'une salle ne peut etre vide");
            	try {
            		TestUtils.updateDatabase("test/data/room.js");
            		TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@room.com");
					params.put("name", "name 5");
					params.put("site", "101");
					params.put("seats", "");
					params.put("description", "The bigest room");
	                params.put("photo", "http://www.website2.com/pictures/rooms/room5.gif");
	                Result result = callAction(routes.ref.RoomController.newRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("Seats cannot be empty or null");
                    Logger.info("*** FIN -> test_room_new_invalid_seats_empty ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour de la salle", e);
				}
            }
        });
    }
    
    @Test
    public void test_room_new_invalid_description_null() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_room_new_invalid_description_null ***");
            	Logger.info("Le champ description d'une salle ne peut etre absent");
            	try {
            		TestUtils.updateDatabase("test/data/room.js");
            		TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@room.com");
					params.put("name", "name 5");
					params.put("site", "101");
					params.put("seats", "600");
	                params.put("photo", "http://www.website2.com/pictures/rooms/room5.gif");
	                Result result = callAction(routes.ref.RoomController.newRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("Description cannot be empty or null");
                    Logger.info("*** FIN -> test_room_new_invalid_description_null ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour de la salle", e);
				}
            }
        });
    }

    @Test
    public void test_room_new_invalid_description_empty() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_room_new_invalid_description_empty ***");
            	Logger.info("Le champ description d'une salle ne peut etre vide");
            	try {
            		TestUtils.updateDatabase("test/data/room.js");
            		TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@room.com");
            		params.put("id", "05");
					params.put("name", "name 5");
					params.put("site", "101");
					params.put("seats", "600");
					params.put("description", "");
					params.put("photo", "http://www.website2.com/pictures/rooms/room5.gif");
	                Result result = callAction(routes.ref.RoomController.newRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("Description cannot be empty or null");
                    Logger.info("*** FIN -> test_room_new_invalid_description_empty ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour de la salle", e);
				}
            }
        });
    }
    
    @Test
    public void test_room_new_unregistred_site() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_room_new_unregistred_site ***");
            	Logger.info("Pour enregistrer une salle, le site doit exister");
            	try {
            		TestUtils.updateDatabase("test/data/room.js");
            		TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@room.com");
					params.put("name", "name 5");
					params.put("site", "1000001");
					params.put("seats", "600");
					params.put("description", "A description");
	                Result result = callAction(routes.ref.RoomController.newRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("does not exist. Check Site List");
                    Logger.info("*** FIN -> test_room_new_unregistred_site ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour de la salle", e);
				}
            }
        });
    }
    
    @Test
    public void test_room_deletion_forbidden() {
    	Logger.info("*** DEBUT -> test_room_deletion_forbidden ***");
    	Logger.info("Seul le user avec le role admin doit pouvoir supprimer une salle");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", "101");
            	Result result = callAction(routes.ref.RoomController.removeRoom(), fakeRequest().withSession("user", "normal").withJsonBody(Json.toJson(params), POST));
                assertThat(status(result)).isEqualTo(FORBIDDEN);
            }
        });
        Logger.info("*** FIN -> test_room_deletion_forbidden ***");
    }
    
    @Test
    public void test_room_deletion_invalid_inner_json() {
    	Logger.info("*** DEBUT -> test_room_deletion_invalid_inner_json ***");
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@room.com");
	                params.put("", "101");
	            	Result result = callAction(routes.ref.RoomController.removeRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
	            	assertThat(status(result)).isEqualTo(BAD_REQUEST);
				} catch (IOException e) {
					Logger.error("Une erreur est survenue lors du test de suppression de la salle", e);
				}finally{
					try {
						TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Logger.error("Une erreur est survenue lors du test de suppression de la salle", e);
					}
				}
				
            }
        });
        Logger.info("*** FIN -> test_room_deletion_invalid_inner_json ***");
    }
    
    @Test
    public void test_room_deletion_invalid_id_null() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_room_deletion_invalid_id_null ***");
                	try {
						TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@room.com");
	                    params.put("id", "");
	                    Logger.info("Le format json d'entrée doit être valide (id ni empty ni null)");
	                    Result result = callAction(routes.ref.RoomController.removeRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
	                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
	                    Logger.info("*** FIN -> test_room_deletion_invalid_id_null ***");
					} catch (IOException e) {
						Logger.error("Une erreur est survenue lors du test de suppression de la salle", e);
					}finally{
						try {
							TestUtils.updateDatabase("test/data/purge.js");
						} catch (IOException e) {
							Logger.error("Une erreur est survenue lors du test de suppression de la salle", e);
						}
					}
					
            }
        });
    }
    
    @Test
    public void test_room_deletion_invalid_id() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_room_deletion_invalid_id ***");
                	try {
						TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@room.com");
	                    params.put("id", "abc1_");
	                    Logger.info("Le format json d'entrée doit être valide (id doit etre un number)");
	                    Result result = callAction(routes.ref.RoomController.removeRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
	                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
	                    Logger.info("*** FIN -> test_room_deletion_invalid_id ***");
					} catch (IOException e) {
						Logger.error("Une erreur est survenue lors du test de suppression de la salle", e);
					}finally{
						try {
							TestUtils.updateDatabase("test/data/purge.js");
						} catch (IOException e) {
							Logger.error("Une erreur est survenue lors du test de suppression de la salle", e);
						}
					}
					
            }
        });
    }
    
    @Test
    public void test_room_deletion_unregistred_room_id() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_room_deletion_unregistred_site_id ***");
                	try {
                		TestUtils.updateDatabase("test/data/room.js");
                		TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@room.com");
                        params.put("id", "10000000");                        
                        Logger.info("La demande de suppession ne doit concerner que des salles existantes.");
                        Result result = callAction(routes.ref.RoomController.removeRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(NOT_FOUND);
                        Logger.info("*** FIN -> test_room_deletion_unregistred_site_id ***");
                        TestUtils.updateDatabase("test/data/purge.js");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de l'existence de la site relative au inner ID", e);
					}
            }
        });
    }

    @Test
    public void test_room_deletion_ok() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_room_deletion_ok ***");
                	try {
                		Logger.info("Tous les inner params sont valides.");
                		TestUtils.updateDatabase("test/data/room.js");
                		TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@room.com");
                        params.put("id", "101");
                        params.put("version", "01");
		                params.put("deleted", "false");
                        Result result = callAction(routes.ref.RoomController.removeRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(OK);
                        List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_ROOM, new BasicDBObject().append("id", "101"));
                        Assert.assertTrue(null != dbObjects);
                        Assert.assertEquals(1,dbObjects.size());
		                Assert.assertEquals("true",dbObjects.get(0).getString("deleted"));
                        Logger.info("*** FIN -> test_room_deletion_ok ***"); 
                        TestUtils.updateDatabase("test/data/purge.js");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de l'existence de la site relative au inner ID", e);
					}
            }
        });
    }
    
    @Test
    public void test_room_update_forbidden() {
    	Logger.info("*** DEBUT -> test_room_update_forbidden ***");
    	Logger.info("Seul le user avec le role admin doit pouvoir mettre a jour une salle");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", "101");
            	Result result = callAction(routes.ref.RoomController.updateRoom(), fakeRequest().withSession("user", "normal").withJsonBody(Json.toJson(params), POST));
                assertThat(status(result)).isEqualTo(FORBIDDEN);
            }
        });
        Logger.info("*** FIN -> test_room_update_forbidden ***");
    }
    
    @Test
    public void test_update_room_invalid_inner_json() {
    	Logger.info("*** DEBUT -> test_update_room_invalid_inner_json ***");
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
					params.put("email", "test@room.com");
	                params.put("", "101");
	            	Result result = callAction(routes.ref.RoomController.updateRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
	            	assertThat(status(result)).isEqualTo(BAD_REQUEST);
				} catch (IOException e) {
					Logger.error("Une erreur est survenue lors du test de mise à jour de la salle", e);
				}finally{
					try {
						TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Logger.error("Une erreur est survenue lors du test de mise à jour de la salle", e);
					}
				}
				
            }
        });
        Logger.info("*** FIN -> test_update_room_invalid_inner_json ***");
    }
    
    @Test
    public void test_update_room_invalid_id_empty() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_room_invalid_id_empty ***");
                	try {
						TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@room.com");
	                    params.put("id", "");
	                    Logger.info("Le format json d'entrée doit être valide (id doit etre un number)");
	                    Result result = callAction(routes.ref.RoomController.updateRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
	                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
	                    Logger.info("*** FIN -> test_update_room_invalid_id_empty ***");
					} catch (IOException e) {
						Logger.error("Une erreur est survenue lors du test de mise à jour de la salle", e);
					}finally{
						try {
							TestUtils.updateDatabase("test/data/purge.js");
						} catch (IOException e) {
							Logger.error("Une erreur est survenue lors du test de mise à jour de la salle", e);
						}
					}
					
            }
        });
    }
    
    @Test
    public void test_update_room_invalid_id() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_room_invalid_id ***");
                	try {
						TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@room.com");
	                    params.put("id", "abc1_");
	                    Logger.info("Le format json d'entrée doit être valide (id doit etre un number)");
	                    Result result = callAction(routes.ref.RoomController.updateRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
	                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
	                    Logger.info("*** FIN -> test_update_room_invalid_id ***");
					} catch (IOException e) {
						Logger.error("Une erreur est survenue lors du test de mise à jour de la salle", e);
					}finally{
						try {
							TestUtils.updateDatabase("test/data/purge.js");
						} catch (IOException e) {
							Logger.error("Une erreur est survenue lors du test de mise à jour de la salle", e);
						}
					}
					
            }
        });
    }

    @Test
    public void test_update_room_ok() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_room_ok() ***");
                	Logger.info("Tous les inner params sont valides ");
                	try {
                		TestUtils.updateDatabase("test/data/room.js");
                		TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@room.com");
                        params.put("id", "101");
                        params.put("name", "changedName");
                        params.put("description", "changedDescription");
                        params.put("version", "01");
		                params.put("deleted", "false");
		                
                        Result result = callAction(routes.ref.RoomController.updateRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(OK);
                        Logger.info("Vérification que les informations de la salle ont bien été mises à jour");
		                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_ROOM, new BasicDBObject().append("id", "101"));
		                Assert.assertTrue(null != dbObjects);
		                Assert.assertEquals(1,dbObjects.size());
		                Assert.assertEquals("changedName",dbObjects.get(0).get("name").toString());
		                Assert.assertEquals("101",dbObjects.get(0).get("site").toString());
		                Assert.assertEquals("500",dbObjects.get(0).get("seats").toString());
		                Assert.assertEquals("changedDescription",dbObjects.get(0).get("description").toString());
		                Assert.assertEquals("http://www.website1.com/pictures/rooms/room1.gif",dbObjects.get(0).get("photo").toString());
		                Assert.assertEquals("02",dbObjects.get(0).get("version").toString());
		                Assert.assertEquals("false",dbObjects.get(0).get("deleted").toString());
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour de la salle", e);
					}
            }
        });
    }
    
    @Test
    public void test_room_update_unregistred_site_id() {
    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
		additionalConfiguration.put("admin.mock", "true");
        running(fakeApplication(additionalConfiguration), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_room_update_unregistred_site_id ***");
                	try {
                		TestUtils.updateDatabase("test/data/room.js");
                		TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@room.com");
                        params.put("id", "10000000");                        
                        Logger.info("La demande de mise à jour ne doit concernée que des sites existantes.");
                        Result result = callAction(routes.ref.RoomController.updateRoom(), fakeRequest().withJsonBody(Json.toJson(params)));
                        assertThat(status(result)).isEqualTo(NOT_FOUND);
                        Logger.info("*** FIN -> test_room_update_unregistred_site_id ***");
                        TestUtils.updateDatabase("test/data/purge.js");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise à jour d'une salle", e);
						Assert.fail("Une erreur est survenue lors du test de mise à jour d'une salle");
					}
            }
        });
    }
    
}