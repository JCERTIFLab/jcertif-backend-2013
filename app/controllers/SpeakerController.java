package controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import models.exception.JCertifException;
import models.objects.Speaker;
import models.objects.access.SpeakerDB;
import models.objects.checker.CheckerHelper;
import models.util.Constantes;
import models.util.Tools;
import models.util.crypto.CryptoUtil;
import notifiers.EmailNotification;
import org.codehaus.jackson.JsonNode;
import play.Logger;
import play.mvc.Http;
import play.mvc.Result;

public class SpeakerController extends AbstractController {

    public static Result listSpeaker() {
        allowCrossOriginJson();
        return ok(JSON.serialize(SpeakerDB.getInstance().list()));
    }

    public static Result registerSpeaker() {
        Logger.info("Enter registerSpeaker()");
        allowCrossOriginJson();

        try {
            checkAdmin();
        } catch (JCertifException jcertifException) {
            Logger.info("access rejected for non-administrator");
            Logger.info("Exit registerSpeaker()");
            return forbidden(jcertifException.getMessage());
        }

        Http.RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            Logger.error(e.getMessage());
            Logger.info("Exit registerSpeaker()");
            return badRequest(e.getMessage());
        }

        String speakerObjInJSONForm = requestBody.asJson().toString();
        Speaker speaker;
        try{
            speaker = new Speaker((BasicDBObject) JSON.parse(speakerObjInJSONForm));
        }catch(JSONParseException exception){
            Logger.error(exception.getMessage());
            Logger.info("Exit registerSpeaker()");
            return badRequest(speakerObjInJSONForm);
        }

        if (!CheckerHelper
                .checkPassword(speaker.getPassword(), null, false)) {
            Logger.error("Password does not match policy");
            Logger.info("Exit registerSpeaker()");
            return internalServerError(JSON.serialize("Password does not match policy (minimum length : " + Constantes.PASSWORD_MIN_LENGTH + " )"));
        }

        try{
            speaker.setPassword(CryptoUtil.getSaltedPassword(speaker.getPassword().getBytes()));
        }catch (JCertifException jcertifException){
            Logger.info("Exit registerSpeaker()");
            return internalServerError(jcertifException.getMessage());
        }

        try {
            SpeakerDB.getInstance().add(speaker);
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit registerSpeaker()");
            return internalServerError(jcertifException.getMessage());
        }

        Logger.info("Successfull register a speaker()");
        Logger.info("Exit registerSpeaker()");

        return ok(JSON.serialize("Ok"));
    }

    public static Result updateSpeaker() {
        Logger.info("Enter updateSpeaker()");

        allowCrossOriginJson();

        try {
            checkAdmin();
        } catch (JCertifException jcertifException) {
            Logger.info("access rejected for non-administrator");
            Logger.info("Exit updateSpeaker()");
            return forbidden(jcertifException.getMessage());
        }

        Http.RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            Logger.error(e.getMessage());
            Logger.info("Exit updateSpeaker()");
            return badRequest(e.getMessage());
        }

        String speakerObjInJSONForm = request().body().asJson().toString();

        Speaker speakerToUpdate;
        try {
            speakerToUpdate = new Speaker(
                    (BasicDBObject) JSON.parse(speakerObjInJSONForm));
        } catch (JSONParseException exception) {
            Logger.error(exception.getMessage());
            Logger.info("Exit updateSpeaker()");
            return badRequest(speakerObjInJSONForm);
        }

        Speaker speakerFromRepo;
        try {
            speakerFromRepo = SpeakerDB.getInstance().get(
                    speakerToUpdate.getEmail());
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit updateSpeaker()");
            return internalServerError(jcertifException.getMessage());
        }

        if (speakerFromRepo == null) {
            Logger.info("Speaker with email " + speakerToUpdate.getEmail() + " does not exist");
            Logger.info("Exit updateSpeaker()");
            return internalServerError(JSON
                    .serialize("Speaker with email \""
                            + speakerToUpdate.getEmail()
                            + "\" does not exist"));
        }

        /**
         * We ensure that we don't modify the password
         */
        speakerToUpdate.setPassword(speakerFromRepo.getPassword());


        try {
            SpeakerDB.getInstance().save(speakerToUpdate);
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit updateSpeaker()");
            return internalServerError(jcertifException.getMessage());
        }

        Logger.info("Successfull update speaker");
        Logger.info("Exit updateSpeaker()");
        return ok(JSON.serialize("Ok"));
    }

    public static Result removeSpeaker() {
        Logger.info("Enter removeSpeaker()");
        allowCrossOriginJson();

        try {
            checkAdmin();
        } catch (JCertifException jcertifException) {
            Logger.info("access rejected for non-administrator");
            Logger.info("Exit removeSpeaker()");
            return forbidden(jcertifException.getMessage());
        }

        Http.RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            Logger.error(e.getMessage());
            Logger.info("Exit removeSpeaker()");
            return badRequest(e.getMessage());
        }

        String emailSpeaker;

        JsonNode jsonNode = requestBody.asJson().get("email");
        if(jsonNode == null){
            Logger.info("Exit removeSpeaker()");
            return badRequest(requestBody.asJson().toString());
        }

        emailSpeaker = jsonNode.getTextValue();

        Speaker speakerFromRepo;
        try {
            speakerFromRepo = SpeakerDB.getInstance().get(
                    emailSpeaker);
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit removeSpeaker()");
            return internalServerError(jcertifException.getMessage());
        }

        if (speakerFromRepo == null) {
            Logger.info("Speaker with email " + emailSpeaker + " does not exist");
            Logger.info("Exit removeSpeaker()");
            return internalServerError(JSON
                    .serialize("Speaker with email \""
                            + emailSpeaker
                            + "\" does not exist"));
        }

        try{
            SpeakerDB.getInstance().remove(speakerFromRepo);
        }catch(JCertifException jcertifException){
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit removeSpeaker()");
            return internalServerError(jcertifException.getMessage());
        }

        Logger.info("Speaker '" + emailSpeaker + "' removed");
        Logger.info("Enter removeSpeaker()");

        return ok(JSON.serialize("Ok"));
    }

    /**
     * Password change request
     *
     * @param emailSpeaker
     * @return
     */
    public static Result changePasswordSpeaker(String emailSpeaker) {
        String changePasswordErrorMessage = "Errors attempt when changing password";

        Logger.info("Enter changePasswordSpeaker()");
        Logger.debug("Enter changePasswordSpeaker(emailSpeaker=" + emailSpeaker + ")");

        Http.RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            Logger.error(e.getMessage());
            Logger.info("Exit changePasswordSpeaker()");
            return badRequest(e.getMessage());
        }

        Speaker speaker;
        try {
            speaker = SpeakerDB.getInstance().get(emailSpeaker);
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit changePasswordSpeaker()");
            return internalServerError(jcertifException.getMessage());
        }

        if (speaker == null) {
            Logger.info("Speaker with email " + emailSpeaker + " does not exist");
            Logger.info("Exit changePasswordSpeaker()");
            return internalServerError(JSON
                    .serialize(changePasswordErrorMessage));
        }

        String objInJSONForm = requestBody.asJson().toString();
        BasicDBObject passwords;
        try {
            passwords = (BasicDBObject) JSON.parse(objInJSONForm);
        } catch (JSONParseException exception) {
            Logger.error(exception.getMessage());
            Logger.info("Exit changePasswordSpeaker()");
            return badRequest(objInJSONForm);
        }

        String oldPassword = passwords.getString("oldpassword");
        String newPassword = passwords.getString("newpassword");

        if (!CheckerHelper
                .checkPassword(oldPassword, newPassword, true)) {
            Logger.info("password does not match policy");
            Logger.info("Exit changePasswordSpeaker()");
            return internalServerError(JSON
                    .serialize("Password does not match policy (minimum length : "
                            + Constantes.PASSWORD_MIN_LENGTH + " )"));
        }

        try {
            if (!CryptoUtil.verifySaltedPassword(oldPassword.getBytes(),
                    speaker.getPassword())) {
                /* We compare oldPassword with the hashed password */
                Logger.info("old password does not match the current password");
                Logger.info("Exit changePasswordSpeaker()");
                return internalServerError(JSON
                        .serialize(changePasswordErrorMessage));
            }
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit changePasswordSpeaker()");
            return internalServerError(jcertifException.getMessage());
        }

        try {
            speaker.setPassword(CryptoUtil.getSaltedPassword(newPassword
                    .getBytes()));
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit changePasswordSpeaker()");
            return internalServerError(jcertifException.getMessage());
        }

        try {
            SpeakerDB.getInstance().save(speaker);
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit changePasswordSpeaker()");
            return internalServerError(jcertifException.getMessage());
        }

        EmailNotification.sendChangePwdMail(speaker);

        Logger.info("Successfull change password's speaker");
        Logger.info("Exit changePasswordSpeaker()");

        return ok(JSON.serialize("Ok"));
    }

    /**
     *
     * @param emailSpeaker
     * @return
     */
    public static Result reinitPasswordSpeaker(String emailSpeaker) {

        Logger.info("Enter reinitPasswordSpeaker()");
        Logger.debug("Enter reinitPasswordSpeaker(emailSpeaker=" + emailSpeaker + ")");

        Speaker speaker;
        try {
            speaker = SpeakerDB.getInstance().get(emailSpeaker);
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit reinitPasswordSpeaker()");
            return internalServerError(jcertifException.getMessage());
        }

        if (speaker == null) {
            Logger.info("Speaker with email " + emailSpeaker + " does not exist");
            Logger.info("Exit reinitPasswordSpeaker()");
            return internalServerError(JSON
                    .serialize("Speaker with email \"" + emailSpeaker
                            + "\" does not exist"));
        }

        String newPassword = CryptoUtil.generateRandomPassword();

        try {
            speaker.setPassword(CryptoUtil.getSaltedPassword(newPassword
                    .getBytes()));
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit reinitPasswordSpeaker()");
            return internalServerError(jcertifException.getMessage());
        }

        try {
            SpeakerDB.getInstance().save(speaker);
            EmailNotification.sendReinitpwdMail(speaker, newPassword);

        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit reinitPasswordSpeaker()");
            return internalServerError(jcertifException.getMessage());
        }

        Logger.info("Successfull reinit password's speaker");
        Logger.info("Exit reinitPasswordSpeaker()");
        return ok(JSON.serialize("Ok"));
    }
}
