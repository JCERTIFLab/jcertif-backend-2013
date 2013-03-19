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

    public static Result updateSession() {
   	 Logger.info("Enter updateSession()");
		 allowCrossOriginJson();
		try {
           checkAdmin();
       } catch (JCertifException jcertifException) {
           Logger.info("access rejected for non-administrator");
           Logger.info("Exit updateSession()");
           return forbidden(jcertifException.getMessage());
       }

		Http.RequestBody requestBody = request().body();
		try {
			Tools.verifyJSonRequest(requestBody);
		} catch (JCertifException e) {
           Logger.error(e.getMessage());
           Logger.info("Exit updateSession()");
			return badRequest(e.getMessage());
		}
		String sessionObjInJSONForm = request().body().asJson().toString();

		Session sessionToUpdate;
		try {
			sessionToUpdate = new Session(
					(BasicDBObject) JSON.parse(sessionObjInJSONForm));
		} catch (JSONParseException exception) {
           Logger.error(exception.getMessage());
           Logger.info("Exit updateSession()");
			return badRequest(sessionObjInJSONForm);
		}
		Session sessionFromRepo;
		try {
			sessionFromRepo = SessionDB.getInstance().get(
					sessionToUpdate.getId());
		} catch (JCertifException jcertifException) {
           Logger.error(jcertifException.getMessage());
           Logger.info("Exit updateSession()");
			return internalServerError(jcertifException.getMessage());
		}

		if (sessionFromRepo == null) {
           Logger.info("Session with id " + sessionToUpdate.getId() + " does not exist");
           Logger.info("Exit updateSession()");
			return internalServerError(JSON
					.serialize("Session with id\""
                           + sessionToUpdate.getId()
                           + "\" does not exist"));
		}
		/**  S'assurer que tous les champs sont renseignés...**/
		if(Tools.isBlankOrNull(sessionToUpdate.getTitle()))
		{
			Logger.info("Session Title " + sessionToUpdate.getTitle() + " is empty");
           Logger.info("Exit updateSession()");
			return internalServerError(JSON
					.serialize("Session Title\""
                           + sessionToUpdate.getTitle()
                           + "\"  is empty"));
		}
		if(Tools.isBlankOrNull(sessionToUpdate.getSummary()) )
		{
			Logger.info("Session Summary " + sessionToUpdate.getSummary() + " is empty");
           Logger.info("Exit updateSession()");
			return internalServerError(JSON
					.serialize("Session Summary\""
                           + sessionToUpdate.getSummary()
                           + "\"  is empty"));
		}
		if(Tools.isBlankOrNull(sessionToUpdate.getDescription()) )
		{
			Logger.info("Session Description " + sessionToUpdate.getDescription() + " is empty");
           Logger.info("Exit updateSession()");
			return internalServerError(JSON
					.serialize("Session Description\""
                           + sessionToUpdate.getDescription()
                           + "\"  is empty"));
			
		}
		if(Tools.isBlankOrNull(sessionToUpdate.getStatus()))
		{
			Logger.info("Session Status " + sessionToUpdate.getStatus() + " is empty");
           Logger.info("Exit updateSession()");
			return internalServerError(JSON
					.serialize("Session Status\""
                           + sessionToUpdate.getStatus()
                           + "\"  is empty"));
		}
		if(Tools.isBlankOrNull(sessionToUpdate.getKeyword()) )
		{
			Logger.info("Session Keyword " + sessionToUpdate.getKeyword() + " is empty");
           Logger.info("Exit updateSession()");
			return internalServerError(JSON
					.serialize("Session Keyword\""
                           + sessionToUpdate.getKeyword()
                           + "\"  is empty"));
		}
		if(Tools.isBlankOrNull(sessionToUpdate.getStart()) )
		{
			Logger.info("Session Start " + sessionToUpdate.getStart() + " is empty");
           Logger.info("Exit updateSession()");
			return internalServerError(JSON
					.serialize("Session Start\""
                           + sessionToUpdate.getStart()
                           + "\"  is empty"));
			
		}
		if(Tools.isBlankOrNull(sessionToUpdate.getEnd()) )
		{
			Logger.info("Session End " + sessionToUpdate.getStart() + " is empty");
           Logger.info("Exit updateSession()");
			return internalServerError(JSON
					.serialize("Session End\""
                           + sessionToUpdate.getEnd()
                           + "\"  is empty"));
		}
		if(Tools.isBlankOrNull(sessionToUpdate.getCategory()) )
		{
			Logger.info("Session Category " + sessionToUpdate.getCategory() + " is empty");
           Logger.info("Exit updateSession()");
			return internalServerError(JSON
					.serialize("Session Category\""
                           + sessionToUpdate.getCategory()
                           + "\"  is empty"));
		}
		if(Tools.isBlankOrNull(sessionToUpdate.getSpeakers()) )		{
			Logger.info("Session Speakers " + sessionToUpdate.getSpeakers() + " is empty");
           Logger.info("Exit updateSession()");
			return internalServerError(JSON
					.serialize("Session Speakers\""
                           + sessionToUpdate.getSpeakers()
                           + "\"  is empty"));
		}
		
		try {
			SessionDB.getInstance().save(sessionToUpdate);
		} catch (JCertifException jcertifException) {
           Logger.error(jcertifException.getMessage());
           Logger.info("Exit updateSession()");
			return internalServerError(jcertifException.getMessage());
		}

       Logger.info("Successfull update Session");
       Logger.info("Exit updateSession()");
       return ok(JSON.serialize("Ok"));
   
   }
}
