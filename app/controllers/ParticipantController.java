package controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import exception.JCertifException;
import objects.Participant;
import objects.access.ParticipantDB;
import play.mvc.Result;

public class ParticipantController extends AbstractController {

    public static Result listParticipant() {
        allowCrossOriginJson();
        return ok(JSON.serialize(ParticipantDB.participantDB.list()));
    }

    public static Result registerParticipant() {
        allowCrossOriginJson();
        String participantObjInJSONForm = request().body().asText();
        Participant participant;
        try{
            participant = new Participant((BasicDBObject) JSON.parse(participantObjInJSONForm));
        }catch(JSONParseException exception){
            return badRequest(participantObjInJSONForm);
        }

        try {
            ParticipantDB.participantDB.add(participant);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }
        return ok(JSON.serialize("Ok"));
    }

}
