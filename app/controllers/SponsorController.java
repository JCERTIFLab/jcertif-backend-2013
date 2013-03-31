package controllers;

import models.objects.Sponsor;
import models.objects.access.SponsorDB;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class SponsorController extends AbstractController {

	@JCertifContext(admin=false,bodyParse=false)
    public static Result listSponsor() {

        return ok(JSON.serialize(SponsorDB.getInstance().list()));
    }

	@JCertifContext
    public static Result updateSponsor() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Sponsor sponsor = new Sponsor((BasicDBObject)JSON.parse(jsonNode.toString()));
		
    	sponsor.save();
		return ok(JSON.serialize("Ok"));
    }

    @JCertifContext
    public static Result addSponsor() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Sponsor sponsor = new Sponsor((BasicDBObject)JSON.parse(jsonNode.toString()));
		
    	sponsor.add();
		return ok(JSON.serialize("Ok"));
    }

    @JCertifContext
    public static Result removeSponsor() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Sponsor sponsor = new Sponsor((BasicDBObject)JSON.parse(jsonNode.toString()));
		
    	sponsor.remove();
		return ok(JSON.serialize("Ok"));
    }
}
