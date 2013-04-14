package controllers;

import models.SponsorLevel;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;

import com.mongodb.util.JSON;

import controllers.Security.Admin;

/**
 * <p>Controleur des niveaux de partenariats.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class SponsorLevelController extends AbstractController{

	@Admin
	public static Result addSponsorLevel() {
        
		JsonNode jsonNode = request().body().asJson();
		
		SponsorLevel sponsorLevel = new SponsorLevel(jsonNode.findPath("label").getTextValue());
		
		sponsorLevel.create();
		return ok(JSON.serialize("Ok"));
    }
		
	public static Result listSponsorLevel() {
        
		return ok(JSON.serialize(SponsorLevel.findAll()));
    }
	
	@Admin
	public static Result removeSponsorLevel() {
        
		JsonNode jsonNode = request().body().asJson();
		
		SponsorLevel sponsorLevel = new SponsorLevel(jsonNode.findPath("label").getTextValue());
		
		sponsorLevel.remove();
		return ok(JSON.serialize("Ok"));
    }
}
