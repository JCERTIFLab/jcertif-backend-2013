package controllers;


import java.util.List;

import models.Login;
import models.util.Json;

import org.codehaus.jackson.JsonNode;

import play.Play;
import play.mvc.Controller;
import play.mvc.Result;

public class AdminController extends Controller {
	
	private static List<String> adminMemebers = Play.application().configuration().getStringList("admin.members");
	private static String isMocked = Play.application().configuration().getString("admin.mock");
	
	public static boolean isAuthorized(String email){
		if(Boolean.valueOf(isMocked)){
			return true;
		}
		return adminMemebers.contains(email);
	}
	
    public static Result check() {
    	JsonNode jsonNode = request().body().asJson();
		
		Login login = Json.parse(Login.class, jsonNode.toString());
		
		LoginController.login(login);

		if(isAuthorized(login.getEmail())){
			return TokenController.newToken(login.getEmail(), "userpass");
		}else{
			return unauthorized();
		}
		
    }

    public static Result ping(String nameUrl) {
        return ok();
    }
}
