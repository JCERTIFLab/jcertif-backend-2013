package controllers;

import models.Login;
import models.Member;
import models.Participant;
import models.Speaker;
import models.exception.JCertifObjectNotFoundException;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class LoginController extends AbstractController {

    public static Result logins() {

        return ok(JSON.serialize(Login.findAll()));
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
		
		Member member = Participant.find(login.getEmail());
		
		if(member == null){
			member = Speaker.find(login.getEmail());
		}
		
		if(member == null){
			throw new JCertifObjectNotFoundException("Membre '" + login.getEmail() + "' inexistant");
		}

		member.login(login.getPassword());	
		
		session("email", login.getEmail());

        return ok(JSON.serialize("Ok"));
    }
}
