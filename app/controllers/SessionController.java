package controllers;

import models.Session;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;
import play.mvc.Security.Authenticated;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;


public class SessionController extends AbstractController {

    public static Result listSession() {

        return ok(JSON.serialize(Session.findAll()));
    }

    @Authenticated(Admin.class)
    public static Result newSession() {
		JsonNode jsonNode = request().body().asJson();
		
    	Session session = new Session((BasicDBObject)JSON.parse(jsonNode.toString()));
		
    	session.create();
		return ok(JSON.serialize("Ok"));
    }

    @Authenticated(Admin.class)
    public static Result removeSession() {
		JsonNode jsonNode = request().body().asJson();
		
		Session session = new Session((BasicDBObject)JSON.parse(jsonNode.toString()));
		
		session.remove();
		return ok(JSON.serialize("Ok"));
    }

    @Authenticated(Admin.class)
    public static Result updateSession() {
		JsonNode jsonNode = request().body().asJson();
		
		Session session = new Session((BasicDBObject)JSON.parse(jsonNode.toString()));

		session.save();
		return ok(JSON.serialize("Ok"));
   
   }
}
