package controllers;

import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.Context;


/**
 * @author Martial SOMDA
 *
 */
public class DefaultAuthenticator extends Security.Authenticator {

	@Override
	public String getUsername(Context context) {
		return context.session().get("email");
	}
	
	@Override
	public Result onUnauthorized(Context context) {
		return unauthorized("Operation not allowed for non-connected people");
	}
}
