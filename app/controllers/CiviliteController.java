package controllers;

import models.Civilite;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;
import play.mvc.Security.Authenticated;

import com.mongodb.util.JSON;

/**
 * <p>Controleur des civilités.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class CiviliteController extends AbstractController{

	@Authenticated(Admin.class)
	public static Result addCivilite() {
        
		JsonNode jsonNode = request().body().asJson();
		
		Civilite civilite = new Civilite(jsonNode.findPath("label").getTextValue());
		
		civilite.create();
		return ok(JSON.serialize("Ok"));
    }
	
	public static Result listCivilite() {
        
		return ok(JSON.serialize(Civilite.findAll()));
    }
	
	@Authenticated(Admin.class)
	public static Result removeCivilite() {
        
		JsonNode jsonNode = request().body().asJson();
		
		Civilite civilite = new Civilite(jsonNode.findPath("label").getTextValue());
		
		civilite.remove();
		return ok(JSON.serialize("Ok"));
    }
}
