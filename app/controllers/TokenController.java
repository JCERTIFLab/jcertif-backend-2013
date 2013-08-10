package controllers;

import java.util.UUID;

import controllers.Security.BasicAuth;

import models.Token;
import models.TokenChecksFactoy.WebAppTokenCheck;
import models.exception.JCertifObjectNotFoundException;
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
		
		String user = request().getQueryString("user");

		return newToken(user, WebAppTokenCheck.ID);
    }
	
	public static Result refreshToken() {
		
		String user = request().getQueryString("user");
		String refreshToken = request().getQueryString("refresh_token");
		
		Token expiredToken = Token.findTokenByRefreshIdAndProvider(refreshToken, WebAppTokenCheck.ID);
		
		if(expiredToken == null || !expiredToken.isExpired()){
			throw new JCertifObjectNotFoundException("No valid token found for refresh token " + refreshToken);
		}
		
		return newToken(user,  WebAppTokenCheck.ID);
    }

	public static Result newToken(String user, String provider) {
		
    	Token token = new Token();
    	token.setAccessToken(UUID.randomUUID().toString());
    	token.setRefreshToken(UUID.randomUUID().toString());
    	token.setUser(user);
    	token.setProvider(provider);
    	token.setExpiresIn(3600);
		
    	token.create();
		return ok(Json.serialize(token));
    }
}
