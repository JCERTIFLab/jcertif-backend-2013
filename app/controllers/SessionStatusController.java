package controllers;

import models.SessionStatus;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;

import com.mongodb.util.JSON;

import controllers.Security.Admin;

public class SessionStatusController extends Controller {

    public static Result listStatusSession() {

        return ok(JSON.serialize(SessionStatus.findAll()));
    }

    @Admin
    public static Result addSessionStatus() {
		JsonNode jsonNode = request().body().asJson();
		
		SessionStatus sessionStatus = new SessionStatus(jsonNode.findPath("label").getTextValue());
		
		sessionStatus.create();
		return ok(JSON.serialize("Ok"));
    }

    @Admin
    public static Result removeSessionStatus() {
		JsonNode jsonNode = request().body().asJson();
		
		SessionStatus sessionStatus = new SessionStatus(jsonNode.findPath("label").getTextValue());
		
		sessionStatus.remove();
		return ok(JSON.serialize("Ok"));
    }
}
