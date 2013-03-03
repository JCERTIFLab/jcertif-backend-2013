package controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import models.exception.JCertifException;
import models.objects.Speaker;
import models.objects.access.SpeakerDB;
import models.util.Constantes;
import models.util.Tools;
import play.mvc.Http;
import play.mvc.Result;

public class SpeakerController extends AbstractController {

    public static Result listSpeaker() {
        allowCrossOriginJson();
        return ok(JSON.serialize(SpeakerDB.getInstance().list()));
    }

    public static Result registerSpeaker() {
        allowCrossOriginJson();

        Http.RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            return badRequest(e.getMessage());
        }

        String speakerObjInJSONForm = requestBody.asJson().toString();
        Speaker speaker;
        try{
            speaker = new Speaker((BasicDBObject) JSON.parse(speakerObjInJSONForm));
        }catch(JSONParseException exception){
            return badRequest(speakerObjInJSONForm);
        }

        if (!SpeakerDB.getInstance().getChecker()
                .checkPassword(speaker.getPassword(), null, false)) {
            return internalServerError(JSON.serialize("Password does not match policy (minimum length : " + Constantes.JCERTIFBACKEND_PASSWORD_MIN_LENGTH + " )"));
        }

        try {
            SpeakerDB.getInstance().add(speaker);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }
        return ok(JSON.serialize("Ok"));
    }
}
