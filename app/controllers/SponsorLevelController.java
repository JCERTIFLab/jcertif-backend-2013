package controllers;

import org.codehaus.jackson.JsonNode;

import com.mongodb.util.JSON;

import models.exception.JCertifException;
import models.objects.SponsorLevel;
import models.objects.access.SponsorLevelDB;
import play.mvc.Result;

import controllers.ActionTemplate.ActionCallback;

/**
 * <p>Controleur des niveaux de partenariats.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class SponsorLevelController extends AbstractController{

	
	public static Result addSponsorLevel() {
        
		ActionCallback callBack = new ActionCallback("ReferentielController.addSponsorLevel") {
			
			@Override
			public Result execute() throws JCertifException {
				JsonNode jsonNode = request().body().asJson();
				
				SponsorLevel sponsorLevel = new SponsorLevel(jsonNode.findPath("label").getTextValue());
				
				SponsorLevelDB.getInstance().add(sponsorLevel);
				return ok(JSON.serialize("Ok"));
			}
		};
		JSONPostActionTemplate template = new JSONPostActionTemplate();
		return template.doAction(callBack, true);
    }
	
	public static Result listSponsorLevel() {
        
		ActionCallback callBack = new ActionCallback("ReferentielController.listSponsorLevel") {			
			@Override
			public Result execute() throws JCertifException {
				
				return ok(JSON.serialize(SponsorLevelDB.getInstance().list()));
			}
		};
		JSONGetActionTemplate template = new JSONGetActionTemplate();
		return template.doAction(callBack, false);
    }
	
	public static Result removeSponsorLevel() {
        
		ActionCallback callBack = new ActionCallback("ReferentielController.removeSponsorLevel") {
			
			@Override
			public Result execute() throws JCertifException {
				JsonNode jsonNode = request().body().asJson();
				
				SponsorLevel sponsorLevel = new SponsorLevel(jsonNode.findPath("label").getTextValue());
				
				SponsorLevelDB.getInstance().remove(sponsorLevel);
				return ok(JSON.serialize("Ok"));
			}
		};
		JSONPostActionTemplate template = new JSONPostActionTemplate();
		return template.doAction(callBack, true);
    }
}
