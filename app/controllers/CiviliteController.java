package controllers;

import models.objects.Civilite;
import models.objects.access.CiviliteDB;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;

import com.mongodb.util.JSON;

/**
 * <p>Controleur des civilités.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class CiviliteController extends AbstractController{

	public static Result addCivilite() {
        
		JsonNode jsonNode = request().body().asJson();
		
		Civilite civilite = new Civilite(jsonNode.findPath("label").getTextValue());
		
		civilite.add();
		return ok(JSON.serialize("Ok"));
    }
	
	public static Result listCivilite() {
        
		return ok(JSON.serialize(CiviliteDB.getInstance().list()));
    }
	
	public static Result removeCivilite() {
        
		JsonNode jsonNode = request().body().asJson();
		
		Civilite civilite = new Civilite(jsonNode.findPath("label").getTextValue());
		
		civilite.remove();
		return ok(JSON.serialize("Ok"));
    }
}
