package controllers;

import models.Speaker;
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
import controllers.Security.Authenticated;

public class SpeakerController extends Controller {

	public static Result listSpeaker() {

        return ok(JSON.serialize(Speaker.findAll()));
    }
	
	public static Result listSpeakerVersion(String version) {

        return ok(JSON.serialize(Speaker.findAll(version)));
    }

    public static Result registerSpeaker() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Speaker speaker = new Speaker((BasicDBObject)JSON.parse(jsonNode.toString()));
    	
    	speaker.create();

		return ok(JSON.serialize("Ok"));
    }
    
	/*public static Result getSpeaker(String name) {
		Speaker speaker = Speaker.findByName(name);
		
		if(speaker == null){
			throw new JCertifObjectNotFoundException(MessageFormat.format(SPEAKER_DOES_NOT_EXISTS, name));
		}

		// We ensure that we don't return the password
		speaker.setPassword("-");

		return ok(JSON.serialize(speaker.toBasicDBObject()));
	}*/

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
		
		speaker.merge(BasicDBObject.class.cast(JSON.parse(jsonNode.toString())));
		speaker.save();
		return ok(JSON.serialize("Ok"));
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

		speaker.remove();
		return ok(JSON.serialize("Ok"));
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

        return ok(JSON.serialize("Ok"));
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

        return ok(JSON.serialize("Ok"));
    }
}
