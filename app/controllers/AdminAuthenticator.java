package controllers;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;

/**
 * @author Martial SOMDA
 *
 */
public class AdminAuthenticator extends Authenticator {

	@Override
	public String getUsername(Context context) {
		return context.session().get("admin");
	}
	
	@Override
	public Result onUnauthorized(Context context) {
		return forbidden("Operation not allowed for non-administrators");
	}
	
}
