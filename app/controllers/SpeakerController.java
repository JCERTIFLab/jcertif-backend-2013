package controllers;

import models.Speaker;
import models.exception.JCertifInvalidRequestException;
import models.exception.JCertifObjectNotFoundException;
import models.util.Constantes;
import models.util.Json;
import models.util.Tools;
import models.validation.CheckHelper;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

import controllers.Security.Admin;
import controllers.Security.Authenticated;

public class SpeakerController extends Controller {

	public static Result listSpeaker() {

        return ok(Json.serialize(Speaker.findAll()));
    }
	
	public static Result listSpeakerVersion(String version) {

        return ok(Json.serialize(Speaker.findAll(version)));
    }

    public static Result registerSpeaker() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Speaker speaker = Json.parse(Speaker.class, jsonNode.toString());
    	
    	speaker.create();

		return ok(Json.serialize("Ok"));
    }

    @Authenticated
    public static Result updateSpeaker() {
    	JsonNode jsonNode = request().body().asJson();
		String email = jsonNode.findPath(Constantes.EMAIL_ATTRIBUTE_NAME).getTextValue();
		String version = jsonNode.findPath(Constantes.VERSION_ATTRIBUTE_NAME).getTextValue();
		
		if(Tools.isBlankOrNull(email)){
			throw new JCertifInvalidRequestException("Email cannot be null or empty");
		}
		
		Speaker speaker = Speaker.find(email);
		
		if(null == speaker){
			throw new JCertifObjectNotFoundException(Speaker.class, email);
		}
		
		CheckHelper.checkVersion(speaker, version);
		
		speaker.merge(Json.parse(Speaker.class, jsonNode.toString()));
		speaker.save();
		return ok(Json.serialize("Ok"));
    }

    @Admin
    public static Result removeSpeaker() {
    	JsonNode jsonNode = request().body().asJson();
		
    	String email = jsonNode.findPath(Constantes.EMAIL_ATTRIBUTE_NAME).getTextValue();
    	
    	if(Tools.isBlankOrNull(email)){
			throw new JCertifInvalidRequestException("Email cannot be null or empty");
		}
    	
    	Speaker speaker = Speaker.find(email);
		
		if(null == speaker){
			throw new JCertifObjectNotFoundException(Speaker.class, email);
		}

		speaker.delete();
		return ok(Json.serialize("Ok"));
    }

    /**
     * Password change request
     *
     * @param emailSpeaker
     * @return
     */
    public static Result changePasswordSpeaker(String emailSpeaker) {
    	JsonNode jsonNode = request().body().asJson();
		
		BasicDBObject passwords = (BasicDBObject)JSON.parse(jsonNode.toString());

		Speaker speaker = Speaker.find(emailSpeaker);
		
		if(speaker == null){
			throw new JCertifObjectNotFoundException(Speaker.class, emailSpeaker);
		}
		
		String oldPassword = passwords.getString("oldpassword");
		String newPassword = passwords.getString("newpassword");

		speaker.changePassword(oldPassword, newPassword);	

        return ok(Json.serialize("Ok"));
    }

    /**
     *
     * @param emailSpeaker
     * @return
     */
    public static Result reinitPasswordSpeaker(String emailSpeaker) {

    	Speaker speaker = Speaker.find(emailSpeaker);
		
		if(speaker == null){
			throw new JCertifObjectNotFoundException(Speaker.class, emailSpeaker);
		}

		speaker.reinitPassword();

        return ok(Json.serialize("Ok"));
    }
}
