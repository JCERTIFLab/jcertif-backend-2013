package controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import models.exception.JCertifException;
import models.objects.Session;
import models.objects.access.SessionDB;
import play.mvc.Http;
import play.mvc.Result;
import models.util.Tools;

public class SessionController extends AbstractController {

    public static Result listSession() {
        allowCrossOriginJson();
        return ok(JSON.serialize(SessionDB.getInstance().list()));
    }

    public static Result newSession() {
        allowCrossOriginJson();

        Http.RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            return badRequest(e.getMessage());
        }

        String sessionObjInJSONForm = requestBody.asJson().toString();
        Session session;
        try{
            session = new Session((BasicDBObject) JSON.parse(sessionObjInJSONForm));
        }catch(JSONParseException exception){
            return badRequest(sessionObjInJSONForm);
        }

        try {
            SessionDB.getInstance().add(session);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }
        return ok(JSON.serialize("Ok"));
    }
}
