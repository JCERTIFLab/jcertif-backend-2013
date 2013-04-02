package controllers;

import models.objects.SponsorLevel;
import models.objects.access.SponsorLevelDB;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;

import com.mongodb.util.JSON;

/**
 * <p>Controleur des niveaux de partenariats.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class SponsorLevelController extends AbstractController{

	@JCertifContext
	public static Result addSponsorLevel() {
        
		JsonNode jsonNode = request().body().asJson();
		
		SponsorLevel sponsorLevel = new SponsorLevel(jsonNode.findPath("label").getTextValue());
		
		sponsorLevel.add();
		return ok(JSON.serialize("Ok"));
    }
	
	@JCertifContext(admin=false,bodyParse=false)
	public static Result listSponsorLevel() {
        
		return ok(JSON.serialize(SponsorLevelDB.getInstance().list()));
    }
	
	@JCertifContext
	public static Result removeSponsorLevel() {
        
		JsonNode jsonNode = request().body().asJson();
		
		SponsorLevel sponsorLevel = new SponsorLevel(jsonNode.findPath("label").getTextValue());
		
		sponsorLevel.remove();
		return ok(JSON.serialize("Ok"));
    }
}
