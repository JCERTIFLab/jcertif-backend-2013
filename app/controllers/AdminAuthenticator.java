package controllers;

import java.util.List;

import org.codehaus.jackson.JsonNode;

import models.util.Tools;
import play.Logger;
import play.Play;
import play.mvc.Http.Context;
import play.mvc.Result;

/**
 * @author Martial SOMDA
 *
 */
public class AdminAuthenticator extends DefaultAuthenticator {

	private List<String> adminMemebers = Play.application().configuration().getStringList("admin.members");
	private String isMocked = Play.application().configuration().getString("admin.mock");
	
	public boolean isAuthorized(String email){
		if(Boolean.valueOf(isMocked)){
			return true;
		}
		return adminMemebers.contains(email);
	}
	
	@Override
	public String getUsername(Context context) {
		String userName = null;
		if(!Tools.isBlankOrNull(super.getUsername(context))){
			String email = null;
			if ("GET".equals(context.request().method())) {
				email = context.request().getQueryString("user");
			} else {
				JsonNode emailNode = context.request().body().asJson().findPath("user");
				if(null != emailNode){
					email = emailNode.getTextValue();
				}			
			}		
			Logger.info("Logged in : " + email);
			if(isAuthorized(email)){
				Logger.info(email + " is member of admin group");
				userName = Boolean.valueOf(isMocked)? "mockUser" : email;
			}
		}
		return userName;
	}
	
	@Override
	public Result onUnauthorized(Context context) {
		return forbidden("Operation not allowed for non-administrators");
	}
	
}
