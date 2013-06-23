package controllers;

import models.SessionStatus;
import models.exception.JCertifInvalidRequestException;
import models.exception.JCertifObjectNotFoundException;
import models.util.Constantes;
import models.util.Json;
import models.util.Tools;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;
import controllers.Security.Admin;

public class SessionStatusController extends Controller {

    public static Result listStatusSession() {

        return ok(Json.serialize(SessionStatus.findAll()));
    }

    @Admin
    public static Result addSessionStatus() {
		JsonNode jsonNode = request().body().asJson();
		
		SessionStatus sessionStatus = Json.parse(SessionStatus.class, jsonNode.toString());
		
		sessionStatus.create();
		return ok(Json.serialize("Ok"));
    }

    @Admin
    public static Result removeSessionStatus() {
		JsonNode jsonNode = request().body().asJson();
		
		String label = jsonNode.findPath(Constantes.LABEL_ATTRIBUTE_NAME).getTextValue();
		
		if(Tools.isBlankOrNull(label)){
			throw new JCertifInvalidRequestException("Label cannot be null or empty");
		}
		
		SessionStatus sessionStatus = SessionStatus.find(label);
		
		if(null == sessionStatus){
			throw new JCertifObjectNotFoundException(SessionStatus.class, label);
		}

		sessionStatus.delete();
		return ok(Json.serialize("Ok"));
    }
}
