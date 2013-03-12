package controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import models.exception.JCertifException;
import models.objects.Participant;
import models.objects.Session;
import models.objects.access.ParticipantDB;
import models.objects.access.SessionDB;
import models.util.Constantes;
import models.util.Tools;
import models.util.crypto.CryptoUtil;
import notifiers.EmailNotification;
import org.codehaus.jackson.JsonNode;
import play.Logger;
import play.mvc.Http.RequestBody;
import play.mvc.Result;

public class ParticipantController extends AbstractController {

	/**
	 * Update user's informations
	 * 
	 * @return
	 */
	public static Result updateParticipant() {
        Logger.info("Enter updateParticipant()");

		allowCrossOriginJson();

		RequestBody requestBody = request().body();
		try {
			Tools.verifyJSonRequest(requestBody);
		} catch (JCertifException e) {
            Logger.error(e.getMessage());
            Logger.info("Exit updateParticipant()");
			return badRequest(e.getMessage());
		}

		String participantObjInJSONForm = request().body().asJson().toString();

		Participant participantToUpdate;
		try {
			participantToUpdate = new Participant(
					(BasicDBObject) JSON.parse(participantObjInJSONForm));
		} catch (JSONParseException exception) {
            Logger.error(exception.getMessage());
            Logger.info("Exit updateParticipant()");
			return badRequest(participantObjInJSONForm);
		}

		Participant participantFromRepo;
		try {
			participantFromRepo = ParticipantDB.getInstance().get(
					participantToUpdate.getEmail());
		} catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit updateParticipant()");
			return internalServerError(jcertifException.getMessage());
		}

		if (participantFromRepo == null) {
            Logger.info("Participant with email " + participantToUpdate.getEmail() + " does not exist");
            Logger.info("Exit updateParticipant()");
			return internalServerError(JSON
					.serialize("Participant with email \""
                            + participantToUpdate.getEmail()
                            + "\" does not exist"));
		}

		/**
		 * We ensure that we don't modify the password
		 */
		participantToUpdate.setPassword(participantFromRepo.getPassword()); // We

		try {
			ParticipantDB.getInstance().save(participantToUpdate);
		} catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit updateParticipant()");
			return internalServerError(jcertifException.getMessage());
		}

        Logger.info("Successfull update participant");
        Logger.info("Exit updateParticipant()");
        return ok(JSON.serialize("Ok"));
	}

	/**
	 * Get participant
	 * 
	 * @param emailParticipant
	 * @return
	 */
	public static Result getParticipant(String emailParticipant) {
        Logger.info("Enter getParticipant()");
        Logger.debug("Enter getParticipant(emailParticipant=" + emailParticipant + ")");

		allowCrossOriginJson();

		Participant participant;
		try {
			participant = ParticipantDB.getInstance().get(emailParticipant);
		} catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit getParticipant()");
            return internalServerError(jcertifException.getMessage());
		}

		if (participant == null) {
            Logger.info("Participant with email " + emailParticipant + " does not exist");
            Logger.info("Exit getParticipant()");
			return internalServerError(JSON
					.serialize("Participant with email \"" + emailParticipant
                            + "\" does not exist"));
		}

		participant.setPassword("-"); // We ensure that we don't return the
		// password

        Logger.info("Successfull get participant");
        Logger.info("Exit getParticipant()");
		return ok(JSON.serialize(participant.toBasicDBObject()));
	}

	public static Result listParticipant() {
        Logger.info("Enter listParticipant()");
		allowCrossOriginJson();
        Logger.info("Successfull list participants");
        Logger.info("Exit listParticipant()");
		return ok(JSON.serialize(ParticipantDB.getInstance().list()));
	}

	/**
	 * Enregistrement d'un nouveau participant
	 * 
	 * @return
	 */
	public static Result registerParticipant() {
        Logger.info("Enter registerParticipant()");

		allowCrossOriginJson();

		RequestBody requestBody = request().body();
		try {
			Tools.verifyJSonRequest(requestBody);
		} catch (JCertifException e) {
            Logger.error(e.getMessage());
            Logger.info("Exit registerParticipant()");
			return badRequest(e.getMessage());
		}

		JsonNode participantObjInJSONForm = requestBody.asJson();
		String participantObjInJSONFormText = participantObjInJSONForm
				.toString();

		Participant participant;
		try {
			participant = new Participant(
					(BasicDBObject) JSON.parse(participantObjInJSONFormText));
		} catch (JSONParseException exception) {
            Logger.error(exception.getMessage());
            Logger.info("Exit registerParticipant()");
			return badRequest(participantObjInJSONForm);
		}

		if (!ParticipantDB.getInstance().getChecker()
				.checkPassword(participant.getPassword(), null, false)) {
            Logger.info("password does not match policy");
            Logger.info("Exit registerParticipant()");
			return internalServerError(JSON
					.serialize("Password does not match policy (minimum length : "
                            + Constantes.PASSWORD_MIN_LENGTH + " )"));
		}

		try {

			ParticipantDB.getInstance().add(participant);

			EmailNotification.sendWelcomeMail(participant); // send email

		} catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit registerParticipant()");
			return internalServerError(jcertifException.getMessage());
		}

        Logger.info("Successfull register participant");
        Logger.info("Exit registerParticipant()");

		return ok(JSON.serialize("Ok"));
	}

	/**
	 * Password change request
	 * 
	 * @param emailParticipant
	 * @return
	 */
	public static Result changePasswordParticipant(String emailParticipant) {
		String changePasswordErrorMessage = "Errors attempt when changing password";

        Logger.info("Enter changePasswordParticipant()");
        Logger.debug("Enter changePasswordParticipant(emailParticipant=" + emailParticipant + ")");

        RequestBody requestBody = request().body();
		try {
			Tools.verifyJSonRequest(requestBody);
		} catch (JCertifException e) {
            Logger.error(e.getMessage());
            Logger.info("Exit changePasswordParticipant()");
			return badRequest(e.getMessage());
		}

		Participant participant;
		try {
			participant = ParticipantDB.getInstance().get(emailParticipant);
		} catch (JCertifException jcertifException) {
            Logger.info("Exit changePasswordParticipant()");
			return internalServerError(jcertifException.getMessage());
		}

		if (participant == null) {
            Logger.info("Participant with email " + emailParticipant + " does not exist");
            Logger.info("Exit changePasswordParticipant()");
			return internalServerError(JSON
					.serialize(changePasswordErrorMessage));
		}

		String objInJSONForm = requestBody.asJson().toString();
		BasicDBObject passwords;
		try {
			passwords = (BasicDBObject) JSON.parse(objInJSONForm);
		} catch (JSONParseException exception) {
            Logger.error(exception.getMessage());
            Logger.info("Exit changePasswordParticipant()");
			return badRequest(objInJSONForm);
		}

		String oldPassword = passwords.getString("oldpassword");
		String newPassword = passwords.getString("newpassword");

		if (!ParticipantDB.getInstance().getChecker()
				.checkPassword(oldPassword, newPassword, true)) {
            Logger.info("password does not match policy");
            Logger.info("Exit changePasswordParticipant()");
			return internalServerError(JSON
					.serialize("Password does not match policy (minimum length : "
                            + Constantes.PASSWORD_MIN_LENGTH + " )"));
		}

		try {
			if (!CryptoUtil.verifySaltedPassword(oldPassword.getBytes(),
					participant.getPassword())) { // We compare oldPassword with
													// the hashed password
                Logger.info("old password does not match the current password");
                Logger.info("Exit changePasswordParticipant()");
                return internalServerError(JSON
						.serialize(changePasswordErrorMessage));
			}
		} catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit changePasswordParticipant()");
			return internalServerError(jcertifException.getMessage());
		}

		try {
			participant.setPassword(CryptoUtil.getSaltedPassword(newPassword
					.getBytes()));
		} catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit changePasswordParticipant()");
			return internalServerError(jcertifException.getMessage());
		}

		try {
			ParticipantDB.getInstance().save(participant);
		} catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit changePasswordParticipant()");
			return internalServerError(jcertifException.getMessage());
		}

		EmailNotification.sendChangePwdMail(participant);

        Logger.info("Successfull change password's participant");
        Logger.info("Exit changePasswordParticipant()");

        return ok(JSON.serialize("Ok"));
	}

	/**
	 * 
	 * @param emailParticipant
	 * @return
	 */
	public static Result reinitPasswordParticipant(String emailParticipant) {

        Logger.info("Enter reinitPasswordParticipant()");
        Logger.debug("Enter reinitPasswordParticipant(emailParticipant=" + emailParticipant + ")");

        Participant participant;
		try {
			participant = ParticipantDB.getInstance().get(emailParticipant);
		} catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit reinitPasswordParticipant()");
			return internalServerError(jcertifException.getMessage());
		}

		if (participant == null) {
            Logger.info("Exit reinitPasswordParticipant()");
			return internalServerError(JSON
					.serialize("Participant with email \"" + emailParticipant
                            + "\" does not exist"));
		}

		String newPassword = CryptoUtil.generateRandomPassword();

		try {
			participant.setPassword(CryptoUtil.getSaltedPassword(newPassword
					.getBytes()));
		} catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit reinitPasswordParticipant()");
			return internalServerError(jcertifException.getMessage());
		}

		try {
			ParticipantDB.getInstance().save(participant);
			EmailNotification.sendReinitpwdMail(participant);

		} catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit reinitPasswordParticipant()");
			return internalServerError(jcertifException.getMessage());
		}

        Logger.info("Exit reinitPasswordParticipant()");
		return ok(JSON.serialize("Ok"));
	}
	
	
/**
 * 
 * @param emailParticipant
 * @param idSession
 * @return
 */
	public static Result inscrireParticipantSession(String emailParticipant,
			String idSession) {
        Logger.info("Enter inscrireParticipantSession()");
        Logger.debug("Enter inscrireParticipantSession(emailParticipant=" + emailParticipant + ", idSession=" + idSession + ")");

		Participant participant;
		try {
			participant = ParticipantDB.getInstance().get(emailParticipant);
		} catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit inscrireParticipantSession()");
			return internalServerError(jcertifException.getMessage());
		}

		if (participant == null) {
            Logger.info("Participant with email " + emailParticipant + " does not exist");
            Logger.info("Exit inscrireParticipantSession()");
			return internalServerError(JSON
					.serialize("Participant with email \"" + emailParticipant
                            + "\" does not exist"));
		}

		Session session;
		try {
			session = SessionDB.getInstance().get(idSession);
		} catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit inscrireParticipantSession()");
			return internalServerError(jcertifException.getMessage());
		}

		if (session == null) {
            Logger.info("Session with id " + idSession + " does not exist");
            Logger.info("Exit inscrireParticipantSession()");
            return internalServerError(JSON.serialize("Session with id \""
					+ idSession + "\" does not exist"));
		}

		if (!participant.getSessions().contains(idSession)) {

			participant.getSessions().add(idSession);

			try {

				ParticipantDB.getInstance().save(participant);
				EmailNotification.sendenrollMail(participant, session);

			} catch (JCertifException jcertifException) {
                Logger.info("Unable to add participant " + emailParticipant + " to session " + idSession);
                Logger.info("Exit inscrireParticipantSession()");
				return internalServerError(jcertifException.getMessage());
			}
            Logger.info("Successfull add participant " + emailParticipant + " to session " + idSession);

		} else {
            Logger.info("participant " + emailParticipant + " is already register to session " + idSession);
        }

        Logger.info("Exit inscrireParticipantSession()");
		return ok(JSON.serialize("Ok"));
	}

	public static Result desinscrireParticipantSession(String emailParticipant,
			String idSession) {

        Logger.info("Enter desinscrireParticipantSession()");
        Logger.debug("Enter desinscrireParticipantSession(emailParticipant=" + emailParticipant + ", idSession=" + idSession + ")");

		Participant participant;
		try {
			participant = ParticipantDB.getInstance().get(emailParticipant);
		} catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit desinscrireParticipantSession()");
			return internalServerError(jcertifException.getMessage());
		}

		if (participant == null) {
            Logger.info("Participant with email " + emailParticipant + " does not exist");
            Logger.info("Exit desinscrireParticipantSession()");
			return internalServerError(JSON
					.serialize("Participant with email \"" + emailParticipant
                            + "\" does not exist"));
		}

		Session session;
		try {
			session = SessionDB.getInstance().get(idSession);
		} catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
			return internalServerError(jcertifException.getMessage());
		}

		if (session == null) {
            Logger.info("Session with id " + idSession + " does not exist");
            Logger.info("Exit desinscrireParticipantSession()");
			return internalServerError(JSON.serialize("Session with id \""
					+ idSession + "\" does not exist"));
		}

		if (participant.getSessions().contains(idSession)) {
			participant.getSessions().remove(idSession);

			try {
				
				ParticipantDB.getInstance().save(participant);
				EmailNotification.sendUnenrollpwdMail(participant, session);

			} catch (JCertifException jcertifException) {
                Logger.info("Unable to remove participant " + emailParticipant + " to session " + idSession);
                Logger.info("Exit desinscrireParticipantSession()");
				return internalServerError(jcertifException.getMessage());
			}

		} else {
            Logger.info("participant " + emailParticipant + " is already unregister to session " + idSession);
        }

        Logger.info("Successfull remove participant " + emailParticipant + " to session " + idSession);
        Logger.info("Exit desinscrireParticipantSession()");

		return ok(JSON.serialize("Ok"));
	}

	public static Result listParticipantSession(String emailParticipant) {

        Logger.info("Enter listParticipantSession()");

        Participant participant;
		try {
			participant = ParticipantDB.getInstance().get(emailParticipant);
		} catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit listParticipantSession()");
			return internalServerError(jcertifException.getMessage());
		}

		if (participant == null) {
            Logger.info("Participant with email " + emailParticipant + " does not exist");
            Logger.info("Exit listParticipantSession()");
			return internalServerError(JSON
					.serialize("Participant with email \"" + emailParticipant
                            + "\" does not exist"));
		}

        Logger.info("Exit listParticipantSession()");

        return ok(JSON.serialize(participant.getSessions()));
	}

}
