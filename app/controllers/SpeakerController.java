package controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import exception.JCertifException;
import objects.Speaker;
import objects.access.SpeakerDB;
import play.mvc.Result;

public class SpeakerController extends AbstractController {

    public static Result listSpeaker() {
        allowCrossOriginJson();
        return ok(JSON.serialize(SpeakerDB.speakerDB.list()));
    }

    public static Result registerSpeaker() {
        allowCrossOriginJson();
        String speakerObjInJSONForm = request().body().asText();
        Speaker speaker;
        try{
            speaker = new Speaker((BasicDBObject) JSON.parse(speakerObjInJSONForm));
        }catch(JSONParseException exception){
            return badRequest(speakerObjInJSONForm);
        }

        try {
            SpeakerDB.speakerDB.add(speaker);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }
        return ok(JSON.serialize("Ok"));
    }
}
