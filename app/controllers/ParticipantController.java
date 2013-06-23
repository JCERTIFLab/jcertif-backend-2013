package controllers;

import models.Participant;
import models.Session;
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

public class ParticipantController extends Controller {
	
	/**
	 * Update user's informations
	 * 
	 * @return
	 */
	@Authenticated
	public static Result updateParticipant() {
		JsonNode jsonNode = request().body().asJson();
		
		String email = jsonNode.findPath(Constantes.EMAIL_ATTRIBUTE_NAME).getTextValue();
		String version = jsonNode.findPath(Constantes.VERSION_ATTRIBUTE_NAME).getTextValue();
    	
    	if(Tools.isBlankOrNull(email)){
			throw new JCertifInvalidRequestException("Email cannot be null or empty");
		}
    	
		Participant participant = Participant.find(email);
		
		if(null == participant){
			throw new JCertifObjectNotFoundException(Participant.class, email);
		}
		
		CheckHelper.checkVersion(participant, version);
		
		participant.merge(Json.parse(Participant.class, jsonNode.toString()));
		participant.save();
		return ok(Json.serialize("Ok"));
	}

	/**
	 * Get participant
	 * 
	 * @param emailParticipant
	 * @return
	 */
	@Authenticated
	public static Result getParticipant(String emailParticipant) {
		Participant participant = Participant.find(emailParticipant);
		
		if(participant == null){
			throw new JCertifObjectNotFoundException(Participant.class, emailParticipant);
		}

		/* We ensure that we don't return the password */
		participant.setPassword("-");

		return ok(Json.serialize(participant));
	}

	public static Result listParticipant() {

		return ok(Json.serialize(Participant.findAll()));
	}

	/**
	 * Enregistrement d'un nouveau participant
	 * 
	 * @return
	 */
	public static Result registerParticipant() {
		JsonNode jsonNode = request().body().asJson();
		
    	Participant participant = Json.parse(Participant.class, jsonNode.toString());
    	
    	participant.create();

		return ok(Json.serialize("Ok"));
	}
	
	
	/**
	 * Suppression d'un participant
	 * 
	 * @return
	 */
	@Admin
	public static Result removeParticipant() {
    	JsonNode jsonNode = request().body().asJson();
		
    	String email = jsonNode.findPath(Constantes.EMAIL_ATTRIBUTE_NAME).getTextValue();
    	
    	if(Tools.isBlankOrNull(email)){
			throw new JCertifInvalidRequestException("Email cannot be null or empty");
		}
    	
    	Participant participant = Participant.find(email);
		
		if(null == participant){
			throw new JCertifObjectNotFoundException(Participant.class, email);
		}

		participant.delete();
		return ok(Json.serialize("Ok"));
    }

	/**
	 * Password change request
	 * 
	 * @param emailParticipant
	 * @return
	 */
	public static Result changePasswordParticipant(String emailParticipant) {
		JsonNode jsonNode = request().body().asJson();
		
		BasicDBObject passwords = (BasicDBObject)JSON.parse(jsonNode.toString());

		Participant participant = Participant.find(emailParticipant);
		
		if(participant == null){
			throw new JCertifObjectNotFoundException(Participant.class, emailParticipant);
		}
		
		String oldPassword = passwords.getString("oldpassword");
		String newPassword = passwords.getString("newpassword");

		participant.changePassword(oldPassword, newPassword);	

        return ok(Json.serialize("Ok"));
	}

	/**
	 * 
	 * @param emailParticipant
	 * @return
	 */
	public static Result reinitPasswordParticipant(String emailParticipant) {

		Participant participant = Participant.find(emailParticipant);
		
		if(participant == null){
			throw new JCertifObjectNotFoundException(Participant.class, emailParticipant);
		}

		participant.reinitPassword();

        return ok(Json.serialize("Ok"));
	}
	
	
	/**
	 * 
	 * @param emailParticipant
	 * @param idSession
	 * @return
	 */
	@Authenticated
	public static Result inscrireParticipantSession(String emailParticipant,
			String idSession) {
		
		Participant participant = Participant.find(emailParticipant);
		
		if(participant == null){
			throw new JCertifObjectNotFoundException(Participant.class, emailParticipant);
		}

		Session session = Session.find(idSession);

		if(session == null){
			throw new JCertifObjectNotFoundException(Session.class, idSession);
		}

		participant.addToSession(session);

		return ok(Json.serialize("Ok"));
	}

	@Authenticated
	public static Result desinscrireParticipantSession(String emailParticipant,
			String idSession) {

		Participant participant = Participant.find(emailParticipant);
		
		if(participant == null){
			throw new JCertifObjectNotFoundException(Participant.class, emailParticipant);
		}

		Session session = Session.find(idSession);

		if(session == null){
			throw new JCertifObjectNotFoundException(Session.class, idSession);
		}

		participant.removeFromSession(session);
		
		return ok(Json.serialize("Ok"));
	}

	@Authenticated
	public static Result listParticipantSession(String emailParticipant) {

		Participant participant = Participant.find(emailParticipant);
		
		if(participant == null){
			throw new JCertifObjectNotFoundException(Participant.class, emailParticipant);
		}

        return ok(JSON.serialize(participant.getSessions()));
	}

}
