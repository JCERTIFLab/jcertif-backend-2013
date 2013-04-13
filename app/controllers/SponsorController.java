package controllers;

import models.Sponsor;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;
import play.mvc.Security.Authenticated;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class SponsorController extends AbstractController {

    public static Result listSponsor() {

        return ok(JSON.serialize(Sponsor.findAll()));
    }

    @Authenticated(Admin.class)
    public static Result updateSponsor() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Sponsor sponsor = new Sponsor((BasicDBObject)JSON.parse(jsonNode.toString()));
		
    	sponsor.save();
		return ok(JSON.serialize("Ok"));
    }

    @Authenticated(Admin.class)
    public static Result addSponsor() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Sponsor sponsor = new Sponsor((BasicDBObject)JSON.parse(jsonNode.toString()));
		
    	sponsor.create();
		return ok(JSON.serialize("Ok"));
    }

    @Authenticated(Admin.class)
    public static Result removeSponsor() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Sponsor sponsor = new Sponsor((BasicDBObject)JSON.parse(jsonNode.toString()));
		
    	sponsor.remove();
		return ok(JSON.serialize("Ok"));
    }
}
