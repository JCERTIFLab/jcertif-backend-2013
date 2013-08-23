package controllers;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import models.Login;
import models.TokenChecksFactoy.WebAppTokenCheck;
import models.exception.JCertifException;
import models.util.Json;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;

import controllers.Security.Admin;

import play.Play;
import play.api.libs.Files;
import play.mvc.Controller;
import play.mvc.Result;

public class AdminController extends Controller {
	
	private static List<String> adminMemebers = Play.application().configuration().getStringList("admin.members");
	private static String isMocked = Play.application().configuration().getString("admin.mock");
	private static String tempFilesLocation = !StringUtils.isBlank(Play.application().configuration().getString("admin.tempfiles.location"))? 
			Play.application().configuration().getString("admin.tempfiles.location") : System.getProperty("java.io.tmpdir");
	
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
			return TokenController.newToken(login.getEmail(), WebAppTokenCheck.ID);
		}else{
			return unauthorized();
		}
		
    }

    @Admin
	public static Result writeExport() {	
    	JsonNode jsonNode = request().body().asJson();
    	File file = new File(tempFilesLocation + '/' + jsonNode.findPath("user").getTextValue());
		try {
			file.mkdirs();
			file = new File(file, jsonNode.findPath("filename").getTextValue());
			if(!file.exists()){
				file.createNewFile();
			}
		} catch (IOException e) {
			throw new JCertifException("Cannot save export file", e);
		}
    	Files.writeFile(file, jsonNode.findPath("data").getTextValue());
    	
        return ok("Ok");
    }
	
    @Admin
	public static Result readExport() {
		String fileName = request().getQueryString("filename");
		String user = request().getQueryString("user");
		File file = new File(tempFilesLocation + '/' + user + '/' + fileName);
		if(file.exists()){
			response().setContentType("application/octet-stream");
			response().setHeader("Content-Disposition","attachment;filename=" + fileName);
			return ok(file).as("text/csv");
		}
        return ok("Download Failed");
    }
	
    public static Result ping(String nameUrl) {
        return ok();
    }
}
