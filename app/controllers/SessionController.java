package controllers;

import models.Session;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

import controllers.Security.Admin;


public class SessionController extends AbstractController {

    public static Result listSession() {

        return ok(JSON.serialize(Session.findAll()));
    }

    @Admin
    public static Result newSession() {
		JsonNode jsonNode = request().body().asJson();
		
    	Session session = new Session((BasicDBObject)JSON.parse(jsonNode.toString()));
		
    	session.create();
		return ok(JSON.serialize("Ok"));
    }

    @Admin
    public static Result removeSession() {
		JsonNode jsonNode = request().body().asJson();
		
		Session session = new Session((BasicDBObject)JSON.parse(jsonNode.toString()));
		
		session.remove();
		return ok(JSON.serialize("Ok"));
    }

    @Admin
    public static Result updateSession() {
		JsonNode jsonNode = request().body().asJson();
		
		Session session = new Session((BasicDBObject)JSON.parse(jsonNode.toString()));

		session.save();
		return ok(JSON.serialize("Ok"));
   
   }
}
