package controllers;

import java.util.UUID;

import controllers.Security.BasicAuth;

import models.Token;
import models.util.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author Martial SOMDA
 *
 */
public class TokenController extends Controller{

	@BasicAuth
	public static Result newToken() {
		
		String email = request().getQueryString("user");
		String provider = request().getQueryString("provider");

		return newToken(email, provider);
    }
	
	public static Result newToken(String email, String provider) {
		
    	Token token = new Token();
    	token.setAccessToken(UUID.randomUUID().toString());
    	token.setEmail(email);
    	token.setProvider(provider);
    	token.setExpiresIn(3600);
		
    	token.create();
		return ok(Json.serialize(token));
    }
}
