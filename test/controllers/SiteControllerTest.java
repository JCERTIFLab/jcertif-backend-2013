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

public class SiteControllerTest {

    @Test
    public void test_listSite() throws java.io.IOException {
        running(fakeApplication(), new Runnable() {
            public void run() {
                try {
                    TestUtils.updateDatabase("test/data/site.js");
                    Result result = route(fakeRequest(GET, "/site/list"));
                    assertThat(status(result)).isEqualTo(OK);
                    assertThat(contentType(result)).isEqualTo("application/json");
                    
                    JsonNode jsonNode = Json.parse(contentAsString(result));
                    Assert.assertEquals(2, jsonNode.size());
	                Assert.assertEquals("101",jsonNode.get(0).findPath("id").getTextValue());
	                Assert.assertEquals("name 1",jsonNode.get(0).findPath("name").getTextValue());
	                Assert.assertEquals("street 1",jsonNode.get(0).findPath("street").getTextValue());
	                Assert.assertEquals("city 1",jsonNode.get(0).findPath("city").getTextValue());
	                Assert.assertEquals("country 1",jsonNode.get(0).findPath("country").getTextValue());
	                Assert.assertEquals("contact@website1.com",jsonNode.get(0).findPath("contact").getTextValue());
	                Assert.assertEquals("www.website1.com",jsonNode.get(0).findPath("website").getTextValue());
	                Assert.assertEquals("description 1",jsonNode.get(0).findPath("description").getTextValue());
	                Assert.assertEquals("http://www.website1.com/pictures/website1.gif",jsonNode.get(0).findPath("photo").getTextValue());
	                
	                Assert.assertEquals("102",jsonNode.get(1).findPath("id").getTextValue());
	                Assert.assertEquals("name 2",jsonNode.get(1).findPath("name").getTextValue());
	                Assert.assertEquals("street 2",jsonNode.get(1).findPath("street").getTextValue());
	                Assert.assertEquals("city 2",jsonNode.get(1).findPath("city").getTextValue());
	                Assert.assertEquals("country 2",jsonNode.get(1).findPath("country").getTextValue());
	                Assert.assertEquals("contact@website2.com",jsonNode.get(1).findPath("contact").getTextValue());
	                Assert.assertEquals("www.website2.com",jsonNode.get(1).findPath("website").getTextValue());
	                Assert.assertEquals("description 2",jsonNode.get(1).findPath("description").getTextValue());
	                Assert.assertEquals("http://www.website2.com/pictures/website2.gif",jsonNode.get(1).findPath("photo").getTextValue());
	                TestUtils.updateDatabase("test/data/purge.js");
                } catch (IOException e) {
                	Assert.fail(e.getMessage());
                }
            }
        });
    }
    
    @Test
	public void test_get_site_not_found(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Récuperer les informations d'un site, le site doit exister");
	            	try {
						TestUtils.updateDatabase("test/data/site.js");
						Result result = callAction(routes.ref.SiteController.getSite("100001"), fakeRequest());
		                assertThat(status(result)).isEqualTo(NOT_FOUND);	                
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
	
	@Test
	public void test_get_site(){
	     running(fakeApplication(), new Runnable() {
	            public void run() {
	            	Logger.info("Récuperer les informations d'un site");
	            	try {
						TestUtils.updateDatabase("test/data/site.js");
						Result result = callAction(routes.ref.SiteController.getSite("101"), fakeRequest());
		                assertThat(status(result)).isEqualTo(OK);	                
		                
		                Logger.info("Vérification des informations du site");
		                JsonNode jsonNode = Json.parse(contentAsString(result));
		                Assert.assertEquals("101",jsonNode.findPath("id").getTextValue());
		                Assert.assertEquals("name 1",jsonNode.findPath("name").getTextValue());
		                Assert.assertEquals("street 1",jsonNode.findPath("street").getTextValue());
		                Assert.assertEquals("city 1",jsonNode.findPath("city").getTextValue());
		                Assert.assertEquals("country 1",jsonNode.findPath("country").getTextValue());
		                Assert.assertEquals("contact@website1.com",jsonNode.findPath("contact").getTextValue());
		                Assert.assertEquals("www.website1.com",jsonNode.findPath("website").getTextValue());
		                Assert.assertEquals("description 1",jsonNode.findPath("description").getTextValue());
		                Assert.assertEquals("http://www.website1.com/pictures/website1.gif",jsonNode.findPath("photo").getTextValue());
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	}
	
    @Test
    public void test_site_new_ok() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Logger.info("Enregistrement d'un nouveau site");
                try {
					TestUtils.updateDatabase("test/data/site.js");
					Map<String, Object> params = new HashMap<String, Object>();
	                params.put("id", "105");
	                params.put("name", "name 5");
	                params.put("street", "street 5");
	                params.put("city", "city 5");
	                params.put("country", "country 5");
	                params.put("contact", "contact@website5.com");
	                params.put("website", "www.website5.com");
	                params.put("description", "description 5");
	                params.put("photo", "http://www.website5.com/pictures/website5.gif");
	                Result result = callAction(routes.ref.SiteController.newSite(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                assertThat(status(result)).isEqualTo(OK);

	                Logger.info("Vérification que le nouveau site est en base de données");
	                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_SITE, new BasicDBObject().append("id", "105"));
	                Assert.assertTrue(null != dbObjects);
	                Assert.assertEquals(1,dbObjects.size());
	                Assert.assertEquals("name 5",dbObjects.get(0).get("name"));
	                Assert.assertEquals("street 5",dbObjects.get(0).get("street"));
	                Assert.assertEquals("city 5",dbObjects.get(0).get("city"));
	                Assert.assertEquals("country 5",dbObjects.get(0).get("country"));
	                Assert.assertEquals("contact@website5.com",dbObjects.get(0).get("contact"));
	                Assert.assertEquals("www.website5.com",dbObjects.get(0).get("website"));
	                Assert.assertEquals("description 5",dbObjects.get(0).get("description"));
	                Assert.assertEquals("http://www.website5.com/pictures/website5.gif",dbObjects.get(0).get("photo"));
	                TestUtils.updateDatabase("test/data/purge.js");
				} catch (IOException e) {
					Assert.fail(e.getMessage());
				}
                

            }
        });
    }

    @Test
    public void test_site_new_invalid_id_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_site_new_invalid_id_null ***");
                	Logger.info("Le champ id d'un site ne peut etre absent");
                	try {
                		TestUtils.updateDatabase("test/data/site.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    	                params.put("name", "name 5");
    	                Result result = callAction(routes.ref.SiteController.newSite(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
    	                
    	                assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        assertThat(contentAsString(result)).contains("Id cannot be empty or null");
                        TestUtils.updateDatabase("test/data/purge.js");
                        Logger.info("*** FIN -> test_site_new_invalid_id_null ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour du site", e);
					}
            }
        });
    }
    
    @Test
    public void test_site_new_invalid_id_empty() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_site_new_invalid_id_empty ***");
                	Logger.info("Le champ id d'un site ne doit être vide");
                	try {
                		TestUtils.updateDatabase("test/data/site.js");
    					Map<String, Object> params = new HashMap<String, Object>();
    					params.put("id", "");
    	                params.put("name", "name 5");
    	                Result result = callAction(routes.ref.SiteController.newSite(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
    	                
    	                assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        assertThat(contentAsString(result)).contains("Id cannot be empty or null");
                        TestUtils.updateDatabase("test/data/purge.js");
                        Logger.info("*** FIN -> test_site_new_invalid_id_empty ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour du site", e);
					}
            }
        });
    }    
    
    @Test
    public void test_site_new_invalid_id() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_site_new_invalid_id ***");
            	Logger.info("Le champ id d'un site ne doit être vide");
            	try {
            		TestUtils.updateDatabase("test/data/site.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", "__");
	                params.put("name", "name 5");
	                Result result = callAction(routes.ref.SiteController.newSite(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                
	                assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    assertThat(contentAsString(result)).contains("Id must be a valid number");
                    TestUtils.updateDatabase("test/data/purge.js");
                    Logger.info("*** FIN -> test_site_new_invalid_id ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour du site", e);
				}
            }
        });
    }
    
    @Test
    public void test_site_new_invalid_name_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_site_new_invalid_name_null ***");
                	Logger.info("Le name d'un site ne peut etre absent");
                	try {
                		TestUtils.updateDatabase("test/data/site.js");
                		Map<String, Object> params = new HashMap<String, Object>();
    					params.put("id", "105");
    	                Result result = callAction(routes.ref.SiteController.newSite(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
    	                
                        assertThat(status(result)).isEqualTo(BAD_REQUEST);
                        TestUtils.updateDatabase("test/data/purge.js");
                        assertThat(contentAsString(result)).contains("Name cannot be empty or null");
                        Logger.info("*** FIN -> test_site_new_invalid_name_null ***");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour du site", e);
					}
            }
        });
    }

    @Test
    public void test_site_new_invalid_name_empty() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_site_new_invalid_name_empty ***");
            	Logger.info("Le name d'un site ne peut etre vide");
            	try {
            		TestUtils.updateDatabase("test/data/site.js");
            		Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", "105");
					params.put("name", "");
	                Result result = callAction(routes.ref.SiteController.newSite(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("Name cannot be empty or null");
                    Logger.info("*** FIN -> test_site_new_invalid_name_empty ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour du site", e);
				}
            }
        });
    }
    
    @Test
    public void test_site_new_invalid_street_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_site_new_invalid_street_null ***");
            	Logger.info("Le champ street d'un site ne peut etre absent");
            	try {
            		TestUtils.updateDatabase("test/data/site.js");
            		Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", "105");
					params.put("name", "name 5");
	                Result result = callAction(routes.ref.SiteController.newSite(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("Street cannot be empty or null");
                    Logger.info("*** FIN -> test_site_new_invalid_street_null ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour du site", e);
				}
            }
        });
    }
    
    @Test
    public void test_site_new_invalid_street_empty() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_site_new_invalid_street_empty ***");
            	Logger.info("Le champ street d'un site ne peut etre vide");
            	try {
            		TestUtils.updateDatabase("test/data/site.js");
            		Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", "105");
					params.put("name", "name 5");
					params.put("street", "");
	                Result result = callAction(routes.ref.SiteController.newSite(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("Street cannot be empty or null");
                    Logger.info("*** FIN -> test_site_new_invalid_street_empty ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour du site", e);
				}
            }
        });
    }
    
    @Test
    public void test_site_new_invalid_city_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_site_new_invalid_city_null ***");
            	Logger.info("Le champ city d'un site ne peut etre absent");
            	try {
            		TestUtils.updateDatabase("test/data/site.js");
            		Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", "105");
					params.put("name", "name 5");
					params.put("street", "street 5");
	                Result result = callAction(routes.ref.SiteController.newSite(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("City cannot be empty or null");
                    Logger.info("*** FIN -> test_site_new_invalid_city_null ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour du site", e);
				}
            }
        });
    }
	
    @Test
    public void test_site_new_invalid_city_empty() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_site_new_invalid_city_empty ***");
            	Logger.info("Le champ city d'un site ne peut etre vide");
            	try {
            		TestUtils.updateDatabase("test/data/site.js");
            		Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", "105");
					params.put("name", "name 5");
					params.put("street", "street 5");
					params.put("city", "");
	                Result result = callAction(routes.ref.SiteController.newSite(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("City cannot be empty or null");
                    Logger.info("*** FIN -> test_site_new_invalid_city_empty ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour du site", e);
				}
            }
        });
    }
    
    @Test
    public void test_site_new_invalid_country_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_site_new_invalid_country_null ***");
            	Logger.info("Le champ country d'un site ne peut etre absent");
            	try {
            		TestUtils.updateDatabase("test/data/site.js");
            		Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", "105");
					params.put("name", "name 5");
					params.put("street", "street 5");
					params.put("city", "city 5");
	                Result result = callAction(routes.ref.SiteController.newSite(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("Country cannot be empty or null");
                    Logger.info("*** FIN -> test_site_new_invalid_country_null ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour du site", e);
				}
            }
        });
    }

    @Test
    public void test_site_new_invalid_country_empty() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_site_new_invalid_country_empty ***");
            	Logger.info("Le champ country d'un site ne peut etre vide");
            	try {
            		TestUtils.updateDatabase("test/data/site.js");
            		Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", "105");
					params.put("name", "name 5");
					params.put("street", "street 5");
					params.put("city", "city 5");
					params.put("country", "");
	                Result result = callAction(routes.ref.SiteController.newSite(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("Country cannot be empty or null");
                    Logger.info("*** FIN -> test_site_new_invalid_country_empty ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour du site", e);
				}
            }
        });
    }
    
    @Test
    public void test_site_new_invalid_contact_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_site_new_invalid_contact_null ***");
            	Logger.info("Le champ contact d'un site ne peut etre absent");
            	try {
            		TestUtils.updateDatabase("test/data/site.js");
            		Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", "105");
					params.put("name", "name 5");
					params.put("street", "street 5");
					params.put("city", "city 5");
					params.put("country", "country 5");
	                Result result = callAction(routes.ref.SiteController.newSite(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("Contact cannot be empty or null");
                    Logger.info("*** FIN -> test_site_new_invalid_contact_null ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour du site", e);
				}
            }
        });
    }

    @Test
    public void test_site_new_invalid_contact_empty() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_site_new_invalid_contact_empty ***");
            	Logger.info("Le champ contact d'un site ne peut etre vide");
            	try {
            		TestUtils.updateDatabase("test/data/site.js");
            		Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", "105");
					params.put("name", "name 5");
					params.put("street", "street 5");
					params.put("city", "city 5");
					params.put("country", "country 5");
					params.put("contact", "");
	                Result result = callAction(routes.ref.SiteController.newSite(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("Contact cannot be empty or null");
                    Logger.info("*** FIN -> test_site_new_invalid_contact_empty ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour du site", e);
				}
            }
        });
    }
    
    @Test
    public void test_site_new_invalid_description_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_site_new_invalid_description_null ***");
            	Logger.info("Le champ description d'un site ne peut etre absent");
            	try {
            		TestUtils.updateDatabase("test/data/site.js");
            		Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", "105");
					params.put("name", "name 5");
					params.put("street", "street 5");
					params.put("city", "city 5");
					params.put("country", "country 5");
					params.put("contact", "contact5@website5.com");
					params.put("website", "www.website5.com");
	                Result result = callAction(routes.ref.SiteController.newSite(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("Description cannot be empty or null");
                    Logger.info("*** FIN -> test_site_new_invalid_description_null ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour du site", e);
				}
            }
        });
    }

    @Test
    public void test_site_new_invalid_description_empty() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Logger.info("*** DEBUT -> test_site_new_invalid_description_empty ***");
            	Logger.info("Le champ description d'un site ne peut etre vide");
            	try {
            		TestUtils.updateDatabase("test/data/site.js");
            		Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", "105");
					params.put("name", "name 5");
					params.put("street", "street 5");
					params.put("city", "city 5");
					params.put("country", "country 5");
					params.put("contact", "contact5@website5.com");
					params.put("website", "www.website5.com");
					params.put("description", "");
	                Result result = callAction(routes.ref.SiteController.newSite(), fakeRequest().withJsonBody(Json.toJson(params), POST).withSession("admin", "admin"));
	                
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    TestUtils.updateDatabase("test/data/purge.js");
                    assertThat(contentAsString(result)).contains("Description cannot be empty or null");
                    Logger.info("*** FIN -> test_site_new_invalid_description_empty ***");
				} catch (Exception e) {
					Logger.error("Une erreur est survenue lors du test de mise a jour du site", e);
				}
            }
        });
    }
    
    @Test
    public void test_site_deletion_forbidden() {
    	Logger.info("*** DEBUT -> test_site_deletion_forbidden ***");
    	Logger.info("Seul le user avec le role admin doit pouvoir supprimer une site");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", "101");
            	Result result = callAction(routes.ref.SiteController.removeSite(), fakeRequest().withSession("user", "normal").withJsonBody(Json.toJson(params), POST));
                assertThat(status(result)).isEqualTo(FORBIDDEN);
            }
        });
        Logger.info("*** FIN -> test_site_deletion_forbidden ***");
    }
    
    @Test
    public void test_delete_site_invalid_inner_json() {
    	Logger.info("*** DEBUT -> test_delete_site_invalid_inner_json ***");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Map<String, Object> params = new HashMap<String, Object>();
                params.put("", "101");
            	Result result = callAction(routes.ref.SiteController.removeSite(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
            	assertThat(status(result)).isEqualTo(BAD_REQUEST);
            }
        });
        Logger.info("*** FIN -> test_delete_site_invalid_inner_json ***");
    }
    
    @Test
    public void test_site_deletion_invalid_id_null() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_site_deletion_invalid_id_null ***");
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("id", "");
                    Logger.info("Le format json d'entrée doit être valide (id ni empty ni null)");
                    Result result = callAction(routes.ref.SiteController.removeSite(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    Logger.info("*** FIN -> test_site_deletion_invalid_id_null ***");
            }
        });
    }
    
    @Test
    public void test_site_deletion_invalid_id() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_site_deletion_invalid_id ***");
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("id", "abc1_");
                    Logger.info("Le format json d'entrée doit être valide (id doit etre un number)");
                    Result result = callAction(routes.ref.SiteController.removeSite(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    Logger.info("*** FIN -> test_site_deletion_invalid_id ***");
            }
        });
    }
    
    @Test
    public void test_site_deletion_unregistred_site_id() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_site_deletion_unregistred_site_id ***");
                	try {
                		TestUtils.updateDatabase("test/data/site.js");
                		Map<String, Object> params = new HashMap<String, Object>();
                        params.put("id", "10000000");                        
                        Logger.info("La demande de suppession ne doit concerner que des sites existantes.");
                        Result result = callAction(routes.ref.SiteController.removeSite(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(status(result)).isEqualTo(NOT_FOUND);
                        Logger.info("*** FIN -> test_site_deletion_unregistred_site_id ***");
                        TestUtils.updateDatabase("test/data/purge.js");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de l'existence de la site relative au inner ID", e);
					}
            }
        });
    }

    @Test
    public void test_site_deletion_ok() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_site_deletion_ok ***");
                	try {
                		Logger.info("Tous les inner params sont valides.");
                		TestUtils.updateDatabase("test/data/site.js");
                		Map<String, Object> params = new HashMap<String, Object>();
                        params.put("id", "101");
                        Result result = callAction(routes.ref.SiteController.removeSite(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(status(result)).isEqualTo(OK);
                        List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_SITE, new BasicDBObject().append("id", "101"));
                        Assert.assertTrue(null != dbObjects);
    	                Assert.assertEquals(0,dbObjects.size());
                        Logger.info("*** FIN -> test_site_deletion_ok ***"); 
                        TestUtils.updateDatabase("test/data/purge.js");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de l'existence de la site relative au inner ID", e);
					}
            }
        });
    }
    
    @Test
    public void test_site_update_forbidden() {
    	Logger.info("*** DEBUT -> test_site_update_forbidden ***");
    	Logger.info("Seul le user avec le role admin doit pouvoir mettre a jour un site");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", "101");
            	Result result = callAction(routes.ref.SiteController.updateSite(), fakeRequest().withSession("user", "normal").withJsonBody(Json.toJson(params), POST));
                assertThat(status(result)).isEqualTo(FORBIDDEN);
            }
        });
        Logger.info("*** FIN -> test_site_update_forbidden ***");
    }
    
    @Test
    public void test_update_site_invalid_inner_json() {
    	Logger.info("*** DEBUT -> test_update_site_invalid_inner_json ***");
    	Logger.info("Les inner params doivent former un objet json valide");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	Map<String, Object> params = new HashMap<String, Object>();
                params.put("", "101");
            	Result result = callAction(routes.ref.SiteController.updateSite(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
            	assertThat(status(result)).isEqualTo(BAD_REQUEST);
            }
        });
        Logger.info("*** FIN -> test_update_site_invalid_inner_json ***");
    }
    
    @Test
    public void test_update_site_invalid_id_empty() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_site_invalid_id_empty ***");
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("id", "");
                    Logger.info("Le format json d'entrée doit être valide (id doit etre un number)");
                    Result result = callAction(routes.ref.SiteController.updateSite(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    Logger.info("*** FIN -> test_update_site_invalid_id_empty ***");
            }
        });
    }
    
    @Test
    public void test_update_site_invalid_id() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_site_invalid_id ***");
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("id", "abc1_");
                    Logger.info("Le format json d'entrée doit être valide (id doit etre un number)");
                    Result result = callAction(routes.ref.SiteController.updateSite(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                    assertThat(status(result)).isEqualTo(BAD_REQUEST);
                    Logger.info("*** FIN -> test_update_site_invalid_id ***");
            }
        });
    }

    @Test
    public void test_update_site_ok() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_update_site_ok() ***");
                	Logger.info("Tous les inner params sont valides ");
                	try {
                		TestUtils.updateDatabase("test/data/site.js");
                        Map<String, Object> params = new HashMap<String, Object>();

                        params.put("id", "101");
                        params.put("name", "changedName");
                        params.put("street", "changedStreet");
                        params.put("description", "changedDescription");
                        
                        Result result = callAction(routes.ref.SiteController.updateSite(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(status(result)).isEqualTo(OK);
                        Logger.info("Vérification que les informations du site ont bien été mises à jour");
		                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_SITE, new BasicDBObject().append("id", "101"));
		                Assert.assertTrue(null != dbObjects);
		                Assert.assertEquals(1,dbObjects.size());
		                Assert.assertEquals("changedName",dbObjects.get(0).get("name").toString());
		                Assert.assertEquals("changedStreet",dbObjects.get(0).get("street").toString());
		                Assert.assertEquals("city 1",dbObjects.get(0).get("city").toString());
		                Assert.assertEquals("country 1",dbObjects.get(0).get("country").toString());
		                Assert.assertEquals("contact@website1.com",dbObjects.get(0).get("contact").toString());
		                Assert.assertEquals("www.website1.com",dbObjects.get(0).get("website").toString());
		                Assert.assertEquals("changedDescription",dbObjects.get(0).get("description").toString());
		                Assert.assertEquals("http://www.website1.com/pictures/website1.gif",dbObjects.get(0).get("photo").toString());
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise a jour du site", e);
					}
            }
        });
    }
    
    @Test
    public void test_site_update_unregistred_site_id() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                	Logger.info("*** DEBUT -> test_site_update_unregistred_site_id ***");
                	try {
                		TestUtils.updateDatabase("test/data/site.js");
                		Map<String, Object> params = new HashMap<String, Object>();
                        params.put("id", "10000000");                        
                        Logger.info("La demande de mise à jour ne doit concernée que des sites existantes.");
                        Result result = callAction(routes.ref.SiteController.updateSite(), fakeRequest().withSession("admin", "admin").withJsonBody(Json.toJson(params), POST));
                        assertThat(status(result)).isEqualTo(NOT_FOUND);
                        Logger.info("*** FIN -> test_site_update_unregistred_site_id ***");
                        TestUtils.updateDatabase("test/data/purge.js");
					} catch (Exception e) {
						Logger.error("Une erreur est survenue lors du test de mise à jour d'un site", e);
						Assert.fail("Une erreur est survenue lors du test de mise à jour d'un site");
					}
            }
        });
    }
    
}