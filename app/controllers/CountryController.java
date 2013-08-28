package controllers;

import models.City;
import models.Country;
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
public class CountryController extends Controller{

	public static Result list() {

        return ok(Json.serialize(Country.findAll()));
    }
    
    public static Result listVersion(String version) {

        return ok(Json.serialize(Country.findAll(version)));
    }

    @Admin
    public static Result newCountry() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Country country = Json.parse(Country.class, jsonNode.toString());
		
    	country.create();
		return ok(Json.serialize("Ok"));
    }

    @Admin
    public static Result removeCountry() {
    	JsonNode jsonNode = request().body().asJson();
		
		String name = jsonNode.findPath(Constantes.NAME_ATTRIBUTE_NAME).getTextValue();
		
		if(Tools.isBlankOrNull(name)){
			throw new JCertifInvalidRequestException("Name cannot be null or empty");
		}
		
		Country country = Country.findByName(name);
		 
		if(null == country){
			throw new JCertifObjectNotFoundException(Country.class, name);
		}
		
		for(City city : City.findByCountryId(country.getCid())){
			city.delete();
		}
		country.delete();
		return ok(Json.serialize("Ok"));
    }
}
