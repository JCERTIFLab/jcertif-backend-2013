package controllers;

import models.Sponsor;
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

public class SponsorController extends Controller {

    public static Result listSponsor() {

        return ok(JSON.serialize(Sponsor.findAll()));
    }
    
    public static Result listSponsorVersion(String version) {

        return ok(JSON.serialize(Sponsor.findAll(version)));
    }

    @Admin
    public static Result updateSponsor() {
    	JsonNode jsonNode = request().body().asJson();
		String email = jsonNode.findPath(Constantes.EMAIL_ATTRIBUTE_NAME).getTextValue();
		String version = jsonNode.findPath(Constantes.VERSION_ATTRIBUTE_NAME).getTextValue();
		
		if(Tools.isBlankOrNull(email)){
			throw new JCertifInvalidRequestException("Email cannot be null or empty");
		}
		
		Sponsor sponsor = Sponsor.find(email);
		
		if(null == sponsor){
			throw new JCertifObjectNotFoundException(Sponsor.class, email);
		}
		
		CheckHelper.checkVersion(sponsor, version);
		
		sponsor.merge(BasicDBObject.class.cast(JSON.parse(jsonNode.toString())));
		sponsor.save();
		return ok(JSON.serialize("Ok"));
    }

    @Admin
    public static Result addSponsor() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Sponsor sponsor = new Sponsor((BasicDBObject)JSON.parse(jsonNode.toString()));
		
    	sponsor.create();
		return ok(JSON.serialize("Ok"));
    }

    @Admin
    public static Result removeSponsor() {
    	JsonNode jsonNode = request().body().asJson();
		
    	String email = jsonNode.findPath(Constantes.EMAIL_ATTRIBUTE_NAME).getTextValue();
    	
    	if(Tools.isBlankOrNull(email)){
			throw new JCertifInvalidRequestException("Email cannot be null or empty");
		}
    	
    	Sponsor sponsor = Sponsor.find(email);
		
		if(null == sponsor){
			throw new JCertifObjectNotFoundException(Sponsor.class, email);
		}

		sponsor.remove();
		return ok(JSON.serialize("Ok"));
    }
}
