package controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import models.exception.JCertifException;
import models.mail.Sendmail;
import models.mail.emailmessages.ChangePasswordEmailMessage;
import models.mail.emailmessages.DesinscrireParticipantSessionEmailMessage;
import models.mail.emailmessages.InscrireParticipantSessionEmailMessage;
import models.mail.emailmessages.ReinitPasswordEmailMessage;
import models.objects.Participant;
import models.objects.Session;
import models.objects.access.ParticipantDB;
import models.objects.access.SessionDB;
import models.util.Constantes;
import models.util.Tools;
import models.util.crypto.CryptoUtil;
import org.codehaus.jackson.JsonNode;
import play.mvc.Http.RequestBody;
import play.mvc.Result;

public class ParticipantController extends AbstractController {

    public static Result updateParticipant() {
        allowCrossOriginJson();

        RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            return badRequest(e.getMessage());
        }

        String participantObjInJSONForm = request().body().asJson().toString();

        Participant participantToUpdate;
        try {
            participantToUpdate = new Participant(
                    (BasicDBObject) JSON.parse(participantObjInJSONForm));
        } catch (JSONParseException exception) {
            return badRequest(participantObjInJSONForm);
        }

        Participant participantFromRepo;
        try {
            participantFromRepo = ParticipantDB.getInstance().get(
                    participantToUpdate.getEmail());
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        if (participantFromRepo == null) {
            return internalServerError(JSON.serialize("Participant with email \""
                    + participantToUpdate.getEmail() + "\" does not exist"));
        }

        participantToUpdate.setPassword(participantFromRepo.getPassword()); // We
        // ensure
        // that
        // we
        // don't
        // modify
        // the
        // password

        try {
            ParticipantDB.getInstance().save(participantToUpdate);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        return ok(JSON.serialize("Ok"));
    }

    public static Result getParticipant(String emailParticipant) {
        allowCrossOriginJson();

        Participant participant;
        try {
            participant = ParticipantDB.getInstance().get(emailParticipant);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        if (participant == null) {
            return internalServerError(JSON.serialize("Participant with email \""
                    + emailParticipant + "\" does not exist"));
        }

        participant.setPassword("-"); // We ensure that we don't return the
        // password

        return ok(JSON.serialize(participant.toBasicDBObject()));
    }

    public static Result listParticipant() {
        allowCrossOriginJson();
        return ok(JSON.serialize(ParticipantDB.getInstance().list()));
    }

    /**
     * enregistrement d'un nouveau participant
     *
     * @return
     */
    public static Result registerParticipant() {
        allowCrossOriginJson();

        RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            return badRequest(e.getMessage());
        }

        JsonNode participantObjInJSONForm = requestBody.asJson();
        String participantObjInJSONFormText = participantObjInJSONForm.toString();

        Participant participant;
        try {
            participant = new Participant(
                    (BasicDBObject) JSON.parse(participantObjInJSONFormText));
        } catch (JSONParseException exception) {
            return badRequest(participantObjInJSONForm);
        }

        if (!ParticipantDB.getInstance().getChecker()
                .checkPassword(participant.getPassword(), null, false)) {
            return internalServerError(JSON.serialize("Password does not match policy (minimum length : " + Constantes.PASSWORD_MIN_LENGTH + " )"));
        }

        try {
            ParticipantDB.getInstance().add(participant);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

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

        RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            return badRequest(e.getMessage());
        }

        Participant participant;
        try {
            participant = ParticipantDB.getInstance().get(emailParticipant);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        if (participant == null) {
            return internalServerError(JSON.serialize(changePasswordErrorMessage));
        }

        String objInJSONForm = requestBody.asJson().toString();
        BasicDBObject passwords;
        try {
            passwords = (BasicDBObject) JSON.parse(objInJSONForm);
        } catch (JSONParseException exception) {
            return badRequest(objInJSONForm);
        }

        String oldPassword = passwords.getString("oldpassword");
        String newPassword = passwords.getString("newpassword");

        if (!ParticipantDB.getInstance().getChecker()
                .checkPassword(oldPassword, newPassword, true)) {
            return internalServerError(JSON.serialize("Password does not match policy (minimum length : " + Constantes.PASSWORD_MIN_LENGTH + " )"));
        }

        try {
            if (!CryptoUtil.verifySaltedPassword(oldPassword.getBytes(), participant.getPassword())) {   //We compare oldPassword with the hashed password
                return internalServerError(JSON.serialize(changePasswordErrorMessage));
            }
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        try {
            participant.setPassword(CryptoUtil.getSaltedPassword(newPassword.getBytes()));
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        try {
            ParticipantDB.getInstance().save(participant);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        try {
            ChangePasswordEmailMessage changePasswordEmailMessage = new ChangePasswordEmailMessage();
            changePasswordEmailMessage.setToFullname(participant.getFirstname().concat(" ").concat(participant.getLastname()));
            changePasswordEmailMessage.addToRecipient(participant.getEmail());

            Sendmail.getInstance().send(changePasswordEmailMessage);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        return ok(JSON.serialize("Ok"));
    }

    public static Result reinitPasswordParticipant(String emailParticipant) {

        Participant participant;
        try {
            participant = ParticipantDB.getInstance().get(emailParticipant);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        if (participant == null) {
            return internalServerError(JSON.serialize("Participant with email \""
                    + emailParticipant + "\" does not exist"));
        }

        String newPassword = CryptoUtil.generateRandomPassword();

        try {
            participant.setPassword(CryptoUtil.getSaltedPassword(newPassword.getBytes()));
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        try {
            ParticipantDB.getInstance().save(participant);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        try {
            ReinitPasswordEmailMessage reinitPasswordEmailMessage = new ReinitPasswordEmailMessage();
            reinitPasswordEmailMessage.setNewPassword(newPassword);
            reinitPasswordEmailMessage.setToFullname(participant.getFirstname().concat(" ").concat(participant.getLastname()));
            reinitPasswordEmailMessage.addToRecipient(participant.getEmail());

            Sendmail.getInstance().send(reinitPasswordEmailMessage);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        return ok(JSON.serialize("Ok"));
    }

    public static Result inscrireParticipantSession(String emailParticipant, String idSession) {

        Participant participant;
        try {
            participant = ParticipantDB.getInstance().get(emailParticipant);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        if (participant == null) {
            return internalServerError(JSON.serialize("Participant with email \""
                    + emailParticipant + "\" does not exist"));
        }

        Session session;
        try {
            session = SessionDB.getInstance().get(idSession);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        if (session == null) {
            return internalServerError(JSON.serialize("Session with id \""
                    + idSession + "\" does not exist"));
        }

        if (!participant.getSessions().contains(idSession)) {
            participant.getSessions().add(idSession);

            try {
                ParticipantDB.getInstance().save(participant);
            } catch (JCertifException jcertifException) {
                return internalServerError(jcertifException.getMessage());
            }

            try {
                InscrireParticipantSessionEmailMessage inscrireParticipantSessionEmailMessage = new InscrireParticipantSessionEmailMessage();
                inscrireParticipantSessionEmailMessage.setSessionTitle(session.getTitle());
                inscrireParticipantSessionEmailMessage.setSessionSummary(session.getSummary());
                inscrireParticipantSessionEmailMessage.setSessionStartDate(session.getStart());
                inscrireParticipantSessionEmailMessage.setSessionEndDate(session.getEnd());
                inscrireParticipantSessionEmailMessage.setFullname(participant.getFirstname().concat(" ").concat(participant.getLastname()));
                inscrireParticipantSessionEmailMessage.addToRecipient(participant.getEmail());

                Sendmail.getInstance().send(inscrireParticipantSessionEmailMessage);
            } catch (JCertifException jcertifException) {
                return internalServerError(jcertifException.getMessage());
            }
        }
        return ok(JSON.serialize("Ok"));
    }

    public static Result desinscrireParticipantSession(String emailParticipant, String idSession) {

        Participant participant;
        try {
            participant = ParticipantDB.getInstance().get(emailParticipant);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        if (participant == null) {
            return internalServerError(JSON.serialize("Participant with email \""
                    + emailParticipant + "\" does not exist"));
        }

        Session session;
        try {
            session = SessionDB.getInstance().get(idSession);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        if (session == null) {
            return internalServerError(JSON.serialize("Session with id \""
                    + idSession + "\" does not exist"));
        }

        if (participant.getSessions().contains(idSession)) {
            participant.getSessions().remove(idSession);

            try {
                ParticipantDB.getInstance().save(participant);
            } catch (JCertifException jcertifException) {
                return internalServerError(jcertifException.getMessage());
            }

            try {
                DesinscrireParticipantSessionEmailMessage desinscrireParticipantSessionEmailMessage = new DesinscrireParticipantSessionEmailMessage();
                desinscrireParticipantSessionEmailMessage.setSessionTitle(session.getTitle());
                desinscrireParticipantSessionEmailMessage.setSessionSummary(session.getSummary());
                desinscrireParticipantSessionEmailMessage.setSessionStartDate(session.getStart());
                desinscrireParticipantSessionEmailMessage.setSessionEndDate(session.getEnd());
                desinscrireParticipantSessionEmailMessage.setFullname(participant.getFirstname().concat(" ").concat(participant.getLastname()));
                desinscrireParticipantSessionEmailMessage.addToRecipient(participant.getEmail());

                Sendmail.getInstance().send(desinscrireParticipantSessionEmailMessage);
            } catch (JCertifException jcertifException) {
                return internalServerError(jcertifException.getMessage());
            }
        }

        return ok(JSON.serialize("Ok"));
    }

    public static Result listParticipantSession(String emailParticipant) {

        Participant participant;
        try {
            participant = ParticipantDB.getInstance().get(emailParticipant);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        if (participant == null) {
            return internalServerError(JSON.serialize("Participant with email \""
                    + emailParticipant + "\" does not exist"));
        }

        return ok(JSON.serialize(participant.getSessions()));
    }

}
