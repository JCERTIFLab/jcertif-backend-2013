package controllers;

import models.Login;
import models.Member;
import models.MemberInfo;
import models.Participant;
import models.Speaker;
import models.TokenChecksFactoy.WebAppTokenCheck;
import models.exception.JCertifObjectNotFoundException;
import models.util.Json;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;

public class LoginController extends Controller {

    public static Result logins() {

        return ok(Json.serialize(Login.findAll()));
    }

    /**
     * Login action
     *
     * @param
     * @return
     */
    public static Result login() {

    	JsonNode jsonNode = request().body().asJson();
		
		Login login = Json.parse(Login.class, jsonNode.toString());
		
		login(login);
		
		return TokenController.newToken(login.getEmail(), WebAppTokenCheck.ID);
    }
    
    public static void login(Login login) {
		
		Member member = Participant.find(login.getEmail());
		
		if(member == null){
			member = Speaker.find(login.getEmail());
		}
		
		if(member == null){
			throw new JCertifObjectNotFoundException(Member.class,login.getEmail());
		}

		member.login(login.getPassword());	  
    }
    
    /**
	 * Find login
	 * 
	 * @param emailParticipant
	 * @return
	 */
	public static Result findLogin(String emailParticipant) {
		
		Member member = Participant.find(emailParticipant);
		
		if(member == null){
			member = Speaker.find(emailParticipant);
		}
		
		if(member == null){
			throw new JCertifObjectNotFoundException(Member.class,emailParticipant);
		}

		return ok(Json.serialize(new MemberInfo(member)));
	}
}
