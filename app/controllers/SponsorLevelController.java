package controllers;

import models.SponsorLevel;
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
 * <p>Controleur des niveaux de partenariats.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class SponsorLevelController extends Controller {

	@Admin
	public static Result addSponsorLevel() {
        
		JsonNode jsonNode = request().body().asJson();
		
		SponsorLevel sponsorLevel = Json.parse(SponsorLevel.class, jsonNode.toString());
		
		sponsorLevel.create();
		return ok(Json.serialize("Ok"));
    }
		
	public static Result listSponsorLevel() {
        
		return ok(Json.serialize(SponsorLevel.findAll()));
    }
	
	@Admin
	public static Result removeSponsorLevel() {
        
		JsonNode jsonNode = request().body().asJson();
		
		String label = jsonNode.findPath(Constantes.LABEL_ATTRIBUTE_NAME).getTextValue();
		
		if(Tools.isBlankOrNull(label)){
			throw new JCertifInvalidRequestException("Label cannot be null or empty");
		}
		
		SponsorLevel sponsorLevel = SponsorLevel.find(label);
		
		if(null == sponsorLevel){
			throw new JCertifObjectNotFoundException(SponsorLevel.class, label);
		}

		sponsorLevel.delete();
		return ok(Json.serialize("Ok"));
    }
}
