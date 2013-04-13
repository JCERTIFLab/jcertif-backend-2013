package controllers;

import models.SessionStatus;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;

import com.mongodb.util.JSON;

public class SessionStatusController extends AbstractController {

    public static Result listStatusSession() {

        return ok(JSON.serialize(SessionStatus.findAll()));
    }

    public static Result addSessionStatus() {
		JsonNode jsonNode = request().body().asJson();
		
		SessionStatus sessionStatus = new SessionStatus(jsonNode.findPath("label").getTextValue());
		
		sessionStatus.create();
		return ok(JSON.serialize("Ok"));
    }

    public static Result removeSessionStatus() {
		JsonNode jsonNode = request().body().asJson();
		
		SessionStatus sessionStatus = new SessionStatus(jsonNode.findPath("label").getTextValue());
		
		sessionStatus.remove();
		return ok(JSON.serialize("Ok"));
    }
}
