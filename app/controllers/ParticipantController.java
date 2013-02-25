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

    public static Result changePasswordParticipant(String emailParticipant){
        String changePasswordErrorMessage = "Errors attempt when changing password";
        Participant participant;
        try {
            participant = ParticipantDB.participantDB.get(emailParticipant);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        if(participant==null){
            return internalServerError(changePasswordErrorMessage);
        }

        String objInJSONForm = request().body().asText();
        BasicDBObject passwords ;
        try {
            passwords = (BasicDBObject) JSON.parse(objInJSONForm);
        } catch (JSONParseException exception) {
            return badRequest(objInJSONForm);
        }

        String oldPassword = passwords.getString("oldpassword");
        String newPassword = passwords.getString("newpassword");

        if(!ParticipantDB.participantDB.getChecker().checkPassword(oldPassword, newPassword)){
            return internalServerError("New password does not match policy ");
        }

        if(!oldPassword.equals(participant.getPassword())){
            return internalServerError(changePasswordErrorMessage);
        }

        participant.setPassword(newPassword);

        try {
            ParticipantDB.participantDB.save(participant);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        return ok(JSON.serialize("Ok"));
    }

}
