package controllers;

import models.Admin;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import play.Logger;
import play.mvc.Http.Context;
import play.mvc.Result;

/**
 * @author Martial SOMDA
 *
 */
public class AdminAuthenticator extends DefaultAuthenticator {

	@Override
	public String getUsername(Context context) {
		String user = super.getUsername(context);
		Logger.info("on arrive ici");
		Admin admin = Admin.find(user);
		
		if(null == admin){
			return null;
		}
		
		return user;
	}
	
	@Override
	protected boolean isRightScoped(OAuth2Authentication auth) {
		return auth.getAuthorizationRequest().getScope().contains("admin");
	}
	
	@Override
	public Result onUnauthorized(Context context) {
		return forbidden("Operation not allowed for non-administrators");
	}
	
}
