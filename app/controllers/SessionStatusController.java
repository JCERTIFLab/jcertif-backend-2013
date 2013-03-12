package controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import models.exception.JCertifException;
import models.objects.SessionStatus;
import models.objects.access.SessionStatusDB;
import models.util.Tools;
import play.Logger;
import play.mvc.Http;
import play.mvc.Result;

public class SessionStatusController extends AbstractController {

    public static Result listStatusSession() {
        Logger.info("Enter listStatusSession()");
        allowCrossOriginJson();
        Logger.info("Exit listStatusSession()");
        return ok(JSON.serialize(SessionStatusDB.getInstance().list()));
    }

    public static Result addSessionStatus() {
        Logger.info("Enter addSessionStatus()");

        allowCrossOriginJson();

        try {
            checkAdmin();
        } catch (JCertifException jcertifException) {
            Logger.info("access rejected for non-administrator");
            return forbidden(jcertifException.getMessage());
        }

        Http.RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            Logger.error(e.getMessage());
            Logger.info("Exit addSessionStatus()");
            return badRequest(e.getMessage());
        }

        String sessionStatusObjInJSONForm = requestBody.asJson().toString();
        SessionStatus sessionStatus;
        try{
            sessionStatus = new SessionStatus((BasicDBObject) JSON.parse(sessionStatusObjInJSONForm));
        }catch(JSONParseException exception){
            Logger.error(exception.getMessage());
            Logger.info("Exit addSessionStatus()");
            return badRequest(sessionStatusObjInJSONForm);
        }

        try {
            SessionStatusDB.getInstance().add(sessionStatus);
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit addSessionStatus()");
            return internalServerError(jcertifException.getMessage());
        }

        Logger.info("Session Status '" + sessionStatus.getLabel() + "' added");
        Logger.info("Exit addSessionStatus()");

        return ok(JSON.serialize("Ok"));
    }

    public static Result removeSessionStatus() {
        Logger.info("Enter removeSessionStatus()");
        allowCrossOriginJson();

        try {
            checkAdmin();
        } catch (JCertifException jcertifException) {
            Logger.info("access rejected for non-administrator");
            return forbidden(jcertifException.getMessage());
        }

        Http.RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            Logger.error(e.getMessage());
            Logger.info("Exit removeSessionStatus()");
            return badRequest(e.getMessage());
        }

        String sessionStatusObjInJSONForm = requestBody.asJson().toString();

        SessionStatus sessionStatus = null;

        try{
            sessionStatus = new SessionStatus((BasicDBObject) JSON.parse(sessionStatusObjInJSONForm));
        }catch(JSONParseException exception){
            Logger.error(exception.getMessage());
            Logger.info("Exit removeSessionStatus()");
            return badRequest(sessionStatusObjInJSONForm);
        }

        try{
            SessionStatusDB.getInstance().remove(sessionStatus);
        }catch(JCertifException jcertifException){
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit removeSessionStatus()");
            return internalServerError(jcertifException.getMessage());
        }

        Logger.info("Session Status '" + sessionStatus.getLabel() + "' removed");
        Logger.info("Enter removeSessionStatus()");

        return ok(JSON.serialize("Ok"));
    }
}
