package controllers;

import models.Room;
import models.Site;
import models.exception.JCertifObjectNotFoundException;

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
		
		Site site = new Site((BasicDBObject)JSON.parse(jsonNode.toString()));
		
		site.remove();
		return ok(JSON.serialize("Ok"));
    }

    @Admin
    public static Result updateSite() {
		JsonNode jsonNode = request().body().asJson();
		
		Site site = new Site((BasicDBObject)JSON.parse(jsonNode.toString()));

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
