package controllers;

import models.Speaker;
import models.exception.JCertifObjectNotFoundException;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;
import play.mvc.Security.Authenticated;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class SpeakerController extends AbstractController {

    public static Result listSpeaker() {

        return ok(JSON.serialize(Speaker.findAll()));
    }

    public static Result registerSpeaker() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Speaker speaker = new Speaker((BasicDBObject)JSON.parse(jsonNode.toString()));
    	
    	speaker.create();

		return ok(JSON.serialize("Ok"));
    }

    @Authenticated(Admin.class)
    public static Result updateSpeaker() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Speaker speaker = new Speaker((BasicDBObject)JSON.parse(jsonNode.toString()));
		
    	speaker.save();
		return ok(JSON.serialize("Ok"));
    }

    @Authenticated(Admin.class)
    public static Result removeSpeaker() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Speaker speaker = new Speaker((BasicDBObject)JSON.parse(jsonNode.toString()));
    	
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
			throw new JCertifObjectNotFoundException("Speaker '" + emailSpeaker + "' inexistant");
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
			throw new JCertifObjectNotFoundException("Speaker '" + emailSpeaker + "' inexistant");
		}

		speaker.reinitPassword();

        return ok(JSON.serialize("Ok"));
    }
}
