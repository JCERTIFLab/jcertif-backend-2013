package controllers;

import models.SponsorLevel;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

import controllers.Security.Admin;

/**
 * <p>Controleur des niveaux de partenariats.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class SponsorLevelController extends Controller {

	@Admin
	public static Result addSponsorLevel() {
        
		JsonNode jsonNode = request().body().asJson();
		
		SponsorLevel sponsorLevel = new SponsorLevel((BasicDBObject)JSON.parse(jsonNode.toString()));
		
		sponsorLevel.create();
		return ok(JSON.serialize("Ok"));
    }
		
	public static Result listSponsorLevel() {
        
		return ok(JSON.serialize(SponsorLevel.findAll()));
    }
	
	@Admin
	public static Result removeSponsorLevel() {
        
		JsonNode jsonNode = request().body().asJson();
		
		SponsorLevel sponsorLevel = new SponsorLevel((BasicDBObject)JSON.parse(jsonNode.toString()));
		
		sponsorLevel.remove();
		return ok(JSON.serialize("Ok"));
    }
}
