package controllers;

import models.Room;
import models.Site;
import models.exception.JCertifInvalidRequestException;
import models.exception.JCertifObjectNotFoundException;
import models.util.Constantes;
import models.util.Json;
import models.util.Tools;
import models.validation.CheckHelper;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;
import controllers.Security.Admin;


public class SiteController extends Controller {

	public static Result listSite() {

        return ok(Json.serialize(Site.findAll()));
    }
    
    public static Result getSite(String idSite) {

    	Site site = Site.find(idSite);
		
		if(site == null){
			throw new JCertifObjectNotFoundException(Site.class, idSite);
		}
		
        return ok(Json.serialize(site));
    }

    @Admin
    public static Result newSite() {
		JsonNode jsonNode = request().body().asJson();
		
		Site site = Json.parse(Site.class, jsonNode.toString());
		
    	site.create();
		return ok(Json.serialize("Ok"));
    }

    @Admin
    public static Result removeSite() {
		JsonNode jsonNode = request().body().asJson();
		
		String idSite = jsonNode.findPath(Constantes.ID_ATTRIBUTE_NAME).getTextValue();
		
		if(Tools.isBlankOrNull(idSite)){
			throw new JCertifInvalidRequestException("Site id cannot be null or empty");
		}
		
		if(Tools.isNotValidNumber(idSite)){
			throw new JCertifInvalidRequestException("Id must be a valid number");
		}
		
		Site site = Site.find(idSite);
		
		if(null == site){
			throw new JCertifObjectNotFoundException(Site.class, idSite);
		}

		site.delete();
		return ok(Json.serialize("Ok"));
    }

    @Admin
    public static Result updateSite() {
    	JsonNode jsonNode = request().body().asJson();
		String idSite = jsonNode.findPath(Constantes.ID_ATTRIBUTE_NAME).getTextValue();
		String version = jsonNode.findPath(Constantes.VERSION_ATTRIBUTE_NAME).getTextValue();
		
		if(Tools.isBlankOrNull(idSite)){
			throw new JCertifInvalidRequestException("Site id cannot be null or empty");
		}
		
		if(Tools.isNotValidNumber(idSite)){
			throw new JCertifInvalidRequestException("Id must be a valid number");
		}
		
		Site site = Site.find(idSite);
		
		if(null == site){
			throw new JCertifObjectNotFoundException(Site.class, idSite);
		}
		
		CheckHelper.checkVersion(site, version);
		
		site.merge(Json.parse(Site.class, jsonNode.toString()));
		site.save();
		return ok(Json.serialize("Ok"));
   }
    
    public static Result listSiteRoom(String idSite) {

    	Site site = Site.find(idSite);
		
		if(site == null){
			throw new JCertifObjectNotFoundException(Site.class, idSite);
		}
		
        return ok(Json.serialize(Room.findBySite(idSite)));
    }
}
