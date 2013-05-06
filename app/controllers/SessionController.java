package controllers;

import models.Session;
import models.exception.JCertifInvalidRequestException;
import models.exception.JCertifObjectNotFoundException;
import models.util.Constantes;
import models.util.Tools;
import models.validation.CheckHelper;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

import controllers.Security.Admin;


public class SessionController extends Controller {

    public static Result listSession() {

        return ok(JSON.serialize(Session.findAll()));
    }
    
    public static Result listSessionVersion(String version) {

        return ok(JSON.serialize(Session.findAll(version)));
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
		
		String idSession = jsonNode.findPath(Constantes.ID_ATTRIBUTE_NAME).getTextValue();
		
		if(Tools.isBlankOrNull(idSession)){
			throw new JCertifInvalidRequestException("Session id cannot be null or empty");
		}
		
		if(Tools.isNotValidNumber(idSession)){
			throw new JCertifInvalidRequestException("Id must be a valid number");
		}
		
		Session session = Session.find(idSession);
		
		if(null == session){
			throw new JCertifObjectNotFoundException(Session.class, idSession);
		}

		session.remove();
		return ok(JSON.serialize("Ok"));
    }

    @Admin
    public static Result updateSession() {
    	JsonNode jsonNode = request().body().asJson();
		String idSession = jsonNode.findPath(Constantes.ID_ATTRIBUTE_NAME).getTextValue();
		String version = jsonNode.findPath(Constantes.VERSION_ATTRIBUTE_NAME).getTextValue();
		
		if(Tools.isBlankOrNull(idSession)){
			throw new JCertifInvalidRequestException("Session id cannot be null or empty");
		}
		
		if(Tools.isNotValidNumber(idSession)){
			throw new JCertifInvalidRequestException("Id must be a valid number");
		}
		
		Session session = Session.find(idSession);
		
		if(null == session){
			throw new JCertifObjectNotFoundException(Session.class, idSession);
		}
		
		CheckHelper.checkVersion(session, version);
		
		session.merge(BasicDBObject.class.cast(JSON.parse(jsonNode.toString())));
		session.save();
		return ok(JSON.serialize("Ok"));
   
   }
}
