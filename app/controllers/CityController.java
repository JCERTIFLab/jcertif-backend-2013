package controllers;

import models.City;
import models.exception.JCertifInvalidRequestException;
import models.exception.JCertifObjectNotFoundException;
import models.util.Constantes;
import models.util.Json;
import models.util.Tools;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;
import controllers.Security.Admin;

/**
 * @author Martial SOMDA
 *
 */
public class CityController extends Controller {

	public static Result list() {

        return ok(Json.serialize(City.findAll()));
    }
    
    public static Result listVersion(String version) {

        return ok(Json.serialize(City.findAll(version)));
    }

    @Admin
    public static Result newCity() {
    	JsonNode jsonNode = request().body().asJson();
		
    	City country = Json.parse(City.class, jsonNode.toString());
		
    	country.create();
		return ok(Json.serialize("Ok"));
    }

    @Admin
    public static Result removeCity() {
    	JsonNode jsonNode = request().body().asJson();
		
		String name = jsonNode.findPath(Constantes.NAME_ATTRIBUTE_NAME).getTextValue();
		
		if(Tools.isBlankOrNull(name)){
			throw new JCertifInvalidRequestException("Name cannot be null or empty");
		}
		
		City city = City.find(name);
		 
		if(null == city){
			throw new JCertifObjectNotFoundException(City.class, name);
		}

		city.delete();
		return ok(Json.serialize("Ok"));
    }
}
