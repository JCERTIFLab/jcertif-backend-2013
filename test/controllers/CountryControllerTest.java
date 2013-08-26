package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.FORBIDDEN;
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

import com.mongodb.BasicDBObject;

import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import util.TestUtils;

/**
 * @author Martial SOMDA
 *
 */
public class CountryControllerTest {

	@Test
    public void test_country_list() throws IOException {

        running(fakeApplication(), new Runnable() {
            public void run() {
                try {
                	TestUtils.updateDatabase("test/data/country.js");
                    Result result = route(fakeRequest(GET, "/ref/country/list"));
                    assertThat(status(result)).isEqualTo(OK);
                    assertThat(contentType(result)).isEqualTo("application/json");
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
    public void test_country_list_diff() throws IOException {

        running(fakeApplication(), new Runnable() {
            public void run() {
                try {
                	TestUtils.updateDatabase("test/data/country.js");
                    Result result = route(fakeRequest(GET, "/ref/country/list/01"));
                    assertThat(status(result)).isEqualTo(OK);
                    assertThat(contentType(result)).isEqualTo("application/json");
                    JsonNode jsonNode = Json.parse(contentAsString(result));
                    Assert.assertEquals(1, jsonNode.size());         
                    Assert.assertEquals("Country3",jsonNode.get(0).findPath("cid").getTextValue());
                    Assert.assertEquals("CountryName3",jsonNode.get(0).findPath("name").getTextValue());
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
    public void test_country_list_jsonp() throws IOException {

        running(fakeApplication(), new Runnable() {
            public void run() {
                try {
                	TestUtils.updateDatabase("test/data/country.js");
                    Result result = route(fakeRequest(GET, "/ref/country/list?jsonp=myFonction"));
                    assertThat(status(result)).isEqualTo(OK);
                    assertThat(contentType(result)).isEqualTo("text/javascript");
                    assertThat(contentAsString(result).contains("myFonction"));
                    TestUtils.updateDatabase("test/data/purge.js");
                } catch (IOException e) {
                    Assert.fail(e.getMessage());
                }
            }
        });
    }
    
    @Test
    public void test_country_new_forbidden() {
        Logger.info("Une requête de création d'un objet country requiert les droits d'administration");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("email", "test@referentiel.com");
	                Result result = callAction(routes.ref.CountryController.newCountry(), fakeRequest().withJsonBody(Json.toJson(params)));	                
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
    public void test_country_remove_forbidden() {
        Logger.info("Une requête de suppression d'un objet country requiert les droits d'administration");
        running(fakeApplication(), new Runnable() {
            public void run() {
            	try {
					TestUtils.updateDatabase("test/data/token.js");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
					params.put("provider", "google");
					params.put("label", "HTTP");
					params.put("email", "test@referentiel.com");
	                Result result = callAction(routes.ref.CountryController.removeCountry(), fakeRequest().withJsonBody(Json.toJson(params)));
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
    public void test_country_new_badrequest() {
        Logger.info("Une requête de création d'un objet country sans paramètre JSON renvoie une réponse BAD REQUEST");
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
	                Result result = callAction(routes.ref.CountryController.newCountry(), fakeRequest().withJsonBody(Json.toJson(params)));
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
	
	 @Test
	    public void test_country_new_ok() {
	    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
			additionalConfiguration.put("admin.mock", "true");
	        running(fakeApplication(additionalConfiguration), new Runnable() {
	            public void run() {
	                Logger.info("Création d'un nouveau pays");
	                try {
	                	TestUtils.updateDatabase("test/data/country.js");
						TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@category.com");
		                params.put("name", "MyCountry");
		                Result result = callAction(routes.ref.CountryController.newCountry(), fakeRequest().withJsonBody(Json.toJson(params)));
		                assertThat(status(result)).isEqualTo(OK);

		                Logger.info("Vérification que le nouvau pays est bien présent en base de données");
		                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_COUNTRY, new BasicDBObject().append("name", "MyCountry"));
		                Assert.assertTrue(null != dbObjects);
		                Assert.assertEquals(1,dbObjects.size());
		                Assert.assertEquals("01",dbObjects.get(0).get("version"));
		                Assert.assertEquals("false",dbObjects.get(0).get("deleted"));
		                Assert.assertEquals("MyCountry",dbObjects.get(0).get("name"));
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
					
	            }
	        });
	    }
	    
	    @Test
	    public void test_country_remove_ok() {
	    	Map<String, Object> additionalConfiguration = new HashMap<String, Object>();
			additionalConfiguration.put("admin.mock", "true");
	        running(fakeApplication(additionalConfiguration), new Runnable() {
	            public void run() {
	            	try {
	            		Logger.info("Suppression d'un pays");
						TestUtils.updateDatabase("test/data/city.js");
						TestUtils.updateDatabase("test/data/token.js");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("access_token", "ya29.AHES6ZSSZXzOghdA6emCl7LBgozLQkPfJ6exbEQBmTzBfRJ8");
						params.put("provider", "google");
						params.put("email", "test@category.com");
		                params.put("name", "CountryName1");
		                params.put("version", "01");
		                Result result = callAction(routes.ref.CountryController.removeCountry(), fakeRequest().withJsonBody(Json.toJson(params), POST));
		                assertThat(status(result)).isEqualTo(OK);

		                Logger.info("Vérification que le pays a bien été supprimmé en base de données");
		                List<BasicDBObject> dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_COUNTRY, new BasicDBObject().append("name", "CountryName1"));
		                Assert.assertTrue(null != dbObjects);
		                Assert.assertEquals(1,dbObjects.size());
		                Assert.assertEquals("true",dbObjects.get(0).getString("deleted"));
		                Assert.assertEquals("02",dbObjects.get(0).getString("version"));
		                
		                dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_CITY, new BasicDBObject().append("name", "City1OfCountry1"));
		                Assert.assertTrue(null != dbObjects);
		                Assert.assertEquals(1,dbObjects.size());
		                Assert.assertEquals("true",dbObjects.get(0).getString("deleted"));
		                Assert.assertEquals("02",dbObjects.get(0).getString("version"));
		                
		                dbObjects = TestUtils.loadFromDatabase(TestConstantes.COLLECTION_CITY, new BasicDBObject().append("name", "City2OfCountry1"));
		                Assert.assertTrue(null != dbObjects);
		                Assert.assertEquals(1,dbObjects.size());
		                Assert.assertEquals("true",dbObjects.get(0).getString("deleted"));
		                Assert.assertEquals("03",dbObjects.get(0).getString("version"));
		                TestUtils.updateDatabase("test/data/purge.js");
					} catch (IOException e) {
						Assert.fail(e.getMessage());
					}
	            }
	        });
	    }
}
