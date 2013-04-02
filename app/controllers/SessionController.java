package controllers;

import models.objects.Session;
import models.objects.access.SessionDB;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;


public class SessionController extends AbstractController {

    public static Result listSession() {

        return ok(JSON.serialize(SessionDB.getInstance().list()));
    }

    public static Result newSession() {
		JsonNode jsonNode = request().body().asJson();
		
    	Session session = new Session((BasicDBObject)JSON.parse(jsonNode.toString()));
		
    	session.add();
		return ok(JSON.serialize("Ok"));
    }

    public static Result removeSession() {
		JsonNode jsonNode = request().body().asJson();
		
		Session session = new Session((BasicDBObject)JSON.parse(jsonNode.toString()));
		
		session.remove();
		return ok(JSON.serialize("Ok"));
    }

    public static Result updateSession() {
		JsonNode jsonNode = request().body().asJson();
		
		Session session = new Session((BasicDBObject)JSON.parse(jsonNode.toString()));

		session.save();
		return ok(JSON.serialize("Ok"));
   
   }
}
