package controllers;

import models.Room;
import models.Site;
import models.exception.JCertifInvalidRequestException;
import models.exception.JCertifObjectNotFoundException;
import models.util.Constantes;
import models.util.Tools;
import models.validation.CheckHelper;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

import controllers.Security.Admin;


public class SiteController extends Controller {

	public static Result listSite() {

        return ok(JSON.serialize(Site.findAll()));
    }
    
    public static Result getSite(String idSite) {

    	Site site = Site.find(idSite);
		
		if(site == null){
			throw new JCertifObjectNotFoundException(Site.class, idSite);
		}
		
        return ok(JSON.serialize(site.toBasicDBObject()));
    }

    @Admin
    public static Result newSite() {
		JsonNode jsonNode = request().body().asJson();
		
		Site site = new Site((BasicDBObject)JSON.parse(jsonNode.toString()));
		
    	site.create();
		return ok(JSON.serialize("Ok"));
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

		site.remove();
		return ok(JSON.serialize("Ok"));
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
		
		site.merge(BasicDBObject.class.cast(JSON.parse(jsonNode.toString())));
		site.save();
		return ok(JSON.serialize("Ok"));
   }
    
    public static Result listSiteRoom(String idSite) {

    	Site site = Site.find(idSite);
		
		if(site == null){
			throw new JCertifObjectNotFoundException(Site.class, idSite);
		}
		
        return ok(JSON.serialize(Room.findAll(idSite)));
    }
}
