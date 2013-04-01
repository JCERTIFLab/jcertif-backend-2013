package controllers;

import models.exception.JCertifObjectNotFoundException;
import models.objects.Participant;
import models.objects.Session;
import models.objects.access.ParticipantDB;
import models.objects.access.SessionDB;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class ParticipantController extends AbstractController {

	/**
	 * Update user's informations
	 * 
	 * @return
	 */
	@JCertifContext
	public static Result updateParticipant() {
		JsonNode jsonNode = request().body().asJson();
		
		Participant participant = new Participant((BasicDBObject)JSON.parse(jsonNode.toString()));
		
		participant.save();
		return ok(JSON.serialize("Ok"));
	}

	/**
	 * Get participant
	 * 
	 * @param emailParticipant
	 * @return
	 */
	@JCertifContext
	public static Result getParticipant(String emailParticipant) {
		Participant participant = ParticipantDB.getInstance().get(emailParticipant);
		
		if(participant == null){
			throw new JCertifObjectNotFoundException("Participant '" + emailParticipant + "' inexistant");
		}

		/* We ensure that we don't return the password */
		participant.setPassword("-");

		return ok(JSON.serialize(participant.toBasicDBObject()));
	}

	@JCertifContext(admin=false,bodyParse=false)
	public static Result listParticipant() {

		return ok(JSON.serialize(ParticipantDB.getInstance().list()));
	}

	/**
	 * Enregistrement d'un nouveau participant
	 * 
	 * @return
	 */
	@JCertifContext
	public static Result registerParticipant() {
		JsonNode jsonNode = request().body().asJson();
		
    	Participant participant = new Participant((BasicDBObject)JSON.parse(jsonNode.toString()));
    	
    	participant.add();

		return ok(JSON.serialize("Ok"));
	}

	/**
	 * Password change request
	 * 
	 * @param emailParticipant
	 * @return
	 */
	@JCertifContext
	public static Result changePasswordParticipant(String emailParticipant) {
		JsonNode jsonNode = request().body().asJson();
		
		BasicDBObject passwords = (BasicDBObject)JSON.parse(jsonNode.toString());

		Participant participant = ParticipantDB.getInstance().get(emailParticipant);
		
		if(participant == null){
			throw new JCertifObjectNotFoundException("Participant '" + emailParticipant + "' inexistant");
		}
		
		String oldPassword = passwords.getString("oldpassword");
		String newPassword = passwords.getString("newpassword");

		participant.changePassword(oldPassword, newPassword);	

        return ok(JSON.serialize("Ok"));
	}

	/**
	 * 
	 * @param emailParticipant
	 * @return
	 */
	@JCertifContext
	public static Result reinitPasswordParticipant(String emailParticipant) {

		Participant participant = ParticipantDB.getInstance().get(emailParticipant);
		
		if(participant == null){
			throw new JCertifObjectNotFoundException("Participant '" + emailParticipant + "' inexistant");
		}

		participant.reinitPassword();

        return ok(JSON.serialize("Ok"));
	}
	
	
	/**
	 * 
	 * @param emailParticipant
	 * @param idSession
	 * @return
	 */
	@JCertifContext
	public static Result inscrireParticipantSession(String emailParticipant,
			String idSession) {
		
		Participant participant = ParticipantDB.getInstance().get(emailParticipant);
		
		if(participant == null){
			throw new JCertifObjectNotFoundException("Participant '" + emailParticipant + "' inexistant");
		}

		Session session = SessionDB.getInstance().get(idSession);

		if(session == null){
			throw new JCertifObjectNotFoundException("Session with id '" + emailParticipant + "' inexistant");
		}

		participant.addToSession(session);

		return ok(JSON.serialize("Ok"));
	}

	@JCertifContext
	public static Result desinscrireParticipantSession(String emailParticipant,
			String idSession) {

		Participant participant = ParticipantDB.getInstance().get(emailParticipant);
		
		if(participant == null){
			throw new JCertifObjectNotFoundException("Participant '" + emailParticipant + "' inexistant");
		}

		Session session = SessionDB.getInstance().get(idSession);

		if(session == null){
			throw new JCertifObjectNotFoundException("Session with id '" + emailParticipant + "' inexistant");
		}

		participant.removeFromSession(session);
		
		return ok(JSON.serialize("Ok"));
	}

	@JCertifContext
	public static Result listParticipantSession(String emailParticipant) {

		Participant participant = ParticipantDB.getInstance().get(emailParticipant);
		
		if(participant == null){
			throw new JCertifObjectNotFoundException("Participant '" + emailParticipant + "' inexistant");
		}

        return ok(JSON.serialize(participant.getSessions()));
	}

}
