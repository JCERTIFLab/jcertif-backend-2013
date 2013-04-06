package controllers;

import models.exception.JCertifObjectNotFoundException;
import models.objects.Login;
import models.objects.Member;
import models.objects.access.LoginDB;
import models.objects.access.ParticipantDB;
import models.objects.access.SpeakerDB;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class LoginController extends AbstractController {

    public static Result logins() {

        return ok(JSON.serialize(LoginDB.getInstance().list()));
    }

    /**
     * Login action
     *
     * @param
     * @return
     */
    public static Result login() {

    	JsonNode jsonNode = request().body().asJson();
		
		Login login = new Login((BasicDBObject)JSON.parse(jsonNode.toString()));
		
		Member member = ParticipantDB.getInstance().get(login.getEmail());
		
		if(member == null){
			member = SpeakerDB.getInstance().get(login.getEmail());
		}
		
		if(member == null){
			throw new JCertifObjectNotFoundException("Membre '" + login.getEmail() + "' inexistant");
		}

		member.login(login.getPassword());	

        return ok(JSON.serialize("Ok"));
    }
}
