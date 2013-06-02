package controllers;

import models.TokenChecksFactoy;
import models.TokenChecksFactoy.TokenCheck;
import models.util.Tools;
import play.Logger;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;


/**
 * @author Martial SOMDA
 *
 */
public class DefaultAuthenticator extends Authenticator {

	@Override
	public String getUsername(Context context) {
		String accessToken = null;
		String provider = null;
 
		if ("GET".equals(context.request().method())) {
			accessToken = context.request().getQueryString("access_token");
			provider = context.request().getQueryString("provider");
		} else {
			accessToken = context.request().body().asJson().findPath("access_token").getTextValue();
			provider = context.request().body().asJson().findPath("provider").getTextValue();
		}
		
		if(Tools.isBlankOrNull(accessToken) ||
				Tools.isBlankOrNull(provider)){
			return null;
		}

		TokenCheck check = TokenChecksFactoy.getInstance().getCheck(provider);
		if(check.isValid(accessToken)){
			Logger.info("token is valid");
			return accessToken;
		}else{
			return null;
		}		
	}
	
	@Override
	public Result onUnauthorized(Context context) {
		return unauthorized("Operation not allowed for non-authenticated people");
	}
}
