package controllers;

import org.codehaus.jackson.JsonNode;

import com.mongodb.util.JSON;

import models.objects.SponsorLevel;
import models.objects.access.SponsorLevelDB;
import play.mvc.Result;

/**
 * <p>Controleur des niveaux de partenariats.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class SponsorLevelController extends AbstractController{

	@JCertifContext(admin=true)
	public static Result addSponsorLevel() {
        
		JsonNode jsonNode = request().body().asJson();
		
		SponsorLevel sponsorLevel = new SponsorLevel(jsonNode.findPath("label").getTextValue());
		
		SponsorLevelDB.getInstance().add(sponsorLevel);
		return ok(JSON.serialize("Ok"));
    }
	
	@JCertifContext
	public static Result listSponsorLevel() {
        
		return ok(JSON.serialize(SponsorLevelDB.getInstance().list()));
    }
	
	@JCertifContext(admin=true)
	public static Result removeSponsorLevel() {
        
		JsonNode jsonNode = request().body().asJson();
		
		SponsorLevel sponsorLevel = new SponsorLevel(jsonNode.findPath("label").getTextValue());
		
		SponsorLevelDB.getInstance().remove(sponsorLevel);
		return ok(JSON.serialize("Ok"));
    }
}
