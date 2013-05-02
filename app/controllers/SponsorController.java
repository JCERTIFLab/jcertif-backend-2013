package controllers;

import models.Sponsor;

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
		
    	Sponsor sponsor = new Sponsor((BasicDBObject)JSON.parse(jsonNode.toString()));
		
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
		
    	Sponsor sponsor = new Sponsor((BasicDBObject)JSON.parse(jsonNode.toString()));
		
    	sponsor.remove();
		return ok(JSON.serialize("Ok"));
    }
}
