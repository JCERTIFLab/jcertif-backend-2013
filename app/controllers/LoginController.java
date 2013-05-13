package controllers;

import models.Login;
import models.Member;
import models.Participant;
import models.Speaker;
import models.exception.JCertifObjectNotFoundException;
import models.oauth2.ResourceOwnerIdentity;
import models.util.Json;

import org.codehaus.jackson.JsonNode;

import play.Logger;
import play.data.Form;
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
		
		session("email", login.getEmail());

        return ok(Json.serialize("Ok"));
    }
    
    public static Member login(Login login) {

		Member member = Participant.find(login.getEmail());
		
		if(member == null){
			member = Speaker.find(login.getEmail());
		}
		
		if(member == null){
			throw new JCertifObjectNotFoundException(Member.class,login.getEmail());
		}

		member.login(login.getPassword());	

        return member;
    }
    
    public static Result authenticateUser(){
    	
    	Form<ResourceOwnerIdentity> loginForm = Form.form(ResourceOwnerIdentity.class).bindFromRequest();
    	
    	if(loginForm.hasErrors()) {
    		Logger.info("errors : " + loginForm.errors());
            return badRequest(views.html.login.render(loginForm));
        } else {
        	login(loginForm.get());
        	Logger.info("postAuthenticateUser : ");
            return OAuth2AccessController.getAuthenticationCode(loginForm.get().getEmail(), loginForm.get().getRedirectUri(), loginForm.get().getState(), loginForm.get().getClientId());
        }
    	
    }
}
