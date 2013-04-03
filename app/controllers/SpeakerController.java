package controllers;

import models.exception.JCertifObjectNotFoundException;
import models.objects.Speaker;
import models.objects.access.SpeakerDB;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class SpeakerController extends AbstractController {

    public static Result listSpeaker() {

        return ok(JSON.serialize(SpeakerDB.getInstance().list()));
    }

    public static Result registerSpeaker() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Speaker speaker = new Speaker((BasicDBObject)JSON.parse(jsonNode.toString()));
    	
    	speaker.add();

		return ok(JSON.serialize("Ok"));
    }

    public static Result updateSpeaker() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Speaker speaker = new Speaker((BasicDBObject)JSON.parse(jsonNode.toString()));
		
    	speaker.save();
		return ok(JSON.serialize("Ok"));
    }

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

		Speaker speaker = SpeakerDB.getInstance().get(emailSpeaker);
		
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

    	Speaker speaker = SpeakerDB.getInstance().get(emailSpeaker);
		
		if(speaker == null){
			throw new JCertifObjectNotFoundException("Speaker '" + emailSpeaker + "' inexistant");
		}

		speaker.reinitPassword();

        return ok(JSON.serialize("Ok"));
    }
}
