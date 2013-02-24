package controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import exception.JCertifException;
import objects.Session;
import objects.access.SessionDB;
import play.mvc.Result;

public class SessionController extends AbstractController {

    public static Result listSession() {
        allowCrossOriginJson();
        return ok(JSON.serialize(SessionDB.sessionDB.list()));
    }

    public static Result listStatusSession() {
        allowCrossOriginJson();
        return ok(JSON.serialize(SessionDB.sessionDB.listStatus()));
    }

    public static Result newSession() {
        allowCrossOriginJson();
        String sessionObjInJSONForm = request().body().asText();
        Session session;
        try{
            session = new Session((BasicDBObject) JSON.parse(sessionObjInJSONForm));
        }catch(JSONParseException exception){
            exception.printStackTrace();
            return badRequest(sessionObjInJSONForm);
        }

        try {
            SessionDB.sessionDB.add(session);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }
        return ok(JSON.serialize("Ok"));
    }
}
