package controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import models.exception.JCertifException;
import models.objects.Speaker;
import models.objects.access.SpeakerDB;
import models.util.Constantes;
import models.util.Tools;
import models.util.crypto.CryptoUtil;
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

        if (!SpeakerDB.getInstance().getChecker()
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
}
