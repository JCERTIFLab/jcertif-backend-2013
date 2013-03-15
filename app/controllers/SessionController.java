package controllers;

import models.exception.JCertifException;
import models.objects.Session;
import models.objects.access.SessionDB;
import models.util.Tools;
import org.codehaus.jackson.JsonNode;
import play.Logger;
import play.mvc.Http;
import play.mvc.Result;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;


public class SessionController extends AbstractController {

    public static Result listSession() {
        Logger.info("Enter listSession()");
        allowCrossOriginJson();
        Logger.info("Exit listSession()");
        return ok(JSON.serialize(SessionDB.getInstance().list()));
    }

    public static Result newSession() {
        Logger.info("Enter newSession()");

        allowCrossOriginJson();

        Http.RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            Logger.error(e.getMessage());
            Logger.info("Exit newSession()");
            return badRequest(e.getMessage());
        }

        String sessionObjInJSONForm = requestBody.asJson().toString();
        Session session;
        try {
            session = new Session((BasicDBObject) JSON.parse(sessionObjInJSONForm));
        } catch (JSONParseException exception) {
            Logger.error(exception.getMessage());
            Logger.info("Exit newSession()");
            return badRequest(sessionObjInJSONForm);
        }

        try {
            SessionDB.getInstance().add(session);
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit newSession()");
            return internalServerError(jcertifException.getMessage());
        }

        Logger.info("Successfull create a new session");
        Logger.info("Exit newSession()");
        return ok(JSON.serialize("Ok"));
    }

    public static Result removeSession() {
        Logger.info("Enter removeSession()");
        allowCrossOriginJson();

        try {
            checkAdmin();
        } catch (JCertifException jcertifException) {
            Logger.info("access rejected for non-administrator");
            Logger.info("Exit removeSession()");
            return forbidden(jcertifException.getMessage());
        }

        Http.RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            Logger.error(e.getMessage());
            Logger.info("Exit removeSession()");
            return badRequest(e.getMessage());
        }

        String idSession;

        JsonNode jsonNode = requestBody.asJson().get("id");
        if(jsonNode == null){
            Logger.info("Exit removeSession()");
            return badRequest(requestBody.asJson().toString());
        }

        idSession = jsonNode.getTextValue();

        Session sessionFromRepo;
        try {
            sessionFromRepo = SessionDB.getInstance().get(
                    idSession);
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit removeSession()");
            return internalServerError(jcertifException.getMessage());
        }

        if (sessionFromRepo == null) {
            Logger.info("Session with id " + idSession + " does not exist");
            Logger.info("Exit removeSession()");
            return internalServerError(JSON
                    .serialize("Session with email \""
                            + idSession
                            + "\" does not exist"));
        }

        try{
            SessionDB.getInstance().remove(sessionFromRepo);
        }catch(JCertifException jcertifException){
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit removeSession()");
            return internalServerError(jcertifException.getMessage());
        }

        Logger.info("Session '" + idSession + "' removed");
        Logger.info("Enter removeSession()");

        return ok(JSON.serialize("Ok"));
    }
}
