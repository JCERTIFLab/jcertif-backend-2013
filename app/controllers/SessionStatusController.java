package controllers;

import models.objects.SessionStatus;
import models.objects.access.SessionStatusDB;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;

import com.mongodb.util.JSON;

public class SessionStatusController extends AbstractController {

	@JCertifContext(admin=false,bodyParse=false)
    public static Result listStatusSession() {

        return ok(JSON.serialize(SessionStatusDB.getInstance().list()));
    }

	@JCertifContext
    public static Result addSessionStatus() {
		JsonNode jsonNode = request().body().asJson();
		
		SessionStatus sessionStatus = new SessionStatus(jsonNode.findPath("label").getTextValue());
		
		sessionStatus.add();
		return ok(JSON.serialize("Ok"));
    }

	@JCertifContext
    public static Result removeSessionStatus() {
		JsonNode jsonNode = request().body().asJson();
		
		SessionStatus sessionStatus = new SessionStatus(jsonNode.findPath("label").getTextValue());
		
		sessionStatus.remove();
		return ok(JSON.serialize("Ok"));
    }
}
