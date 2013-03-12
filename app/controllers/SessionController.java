package controllers;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;

import java.util.List;

import models.database.MongoDatabase;
import models.exception.JCertifException;
import models.objects.Session;
import models.objects.access.CategoryDB;
import models.objects.access.SessionDB;
import play.Logger;
import play.mvc.Http;
import play.mvc.Result;
import models.util.Tools;
import play.data.Form;
import models.objects.access.SessionStatusDB;
import models.objects.access.SpeakerDB;
import views.html.SessionController.form;


public class SessionController extends AbstractController {


    final static Form<Session> sessionForm = Form.form(Session.class);

    public static Result newSessionForm() {
        List<BasicDBObject> status = SessionStatusDB.getInstance().list();
        List<BasicDBObject> categories = CategoryDB.getInstance().list();
        List<BasicDBObject> speakers = SpeakerDB.getInstance().list();
        return ok(form.render(status, categories, speakers));
    }

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
}
