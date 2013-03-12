package controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;

import models.exception.JCertifException;
import models.objects.Participant;
import models.objects.access.LoginDB;
import models.objects.access.ParticipantDB;
import models.util.Tools;
import models.util.crypto.CryptoUtil;
import play.mvc.Result;
import play.mvc.Http.RequestBody;

public class LoginController extends AbstractController {

    public static Result logins() {
        allowCrossOriginJson();
        return ok(JSON.serialize(LoginDB.getInstance().list()));
    }

    /**
     * Login action
     *
     * @param
     * @return
     */
    public static Result login() {

        allowCrossOriginJson();

        RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            return badRequest(e.getMessage());
        }

        String loginObjInJSONForm = request().body().asJson().toString();

        BasicDBObject params;
        try {
            params = (BasicDBObject) JSON.parse(loginObjInJSONForm);
        } catch (JSONParseException exception) {
            return badRequest(loginObjInJSONForm);
        }
        String email = params.getString("email");
        String password = params.getString("password");

        Participant participant;
        try {
            participant = ParticipantDB.getInstance().get(email);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        if (participant == null) {
            return internalServerError(JSON
                    .serialize("Participant with email \"" + email
                            + "\" does not exist"));
        }

        try {
            if (!CryptoUtil.verifySaltedPassword(password.getBytes(), participant.getPassword())) {
                return internalServerError(JSON
                        .serialize("Login failed!, Username or Password invalid"));
            }
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }

        return ok(JSON.serialize("Ok"));
    }
}
