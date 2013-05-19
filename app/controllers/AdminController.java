package controllers;


import models.Admin;
import models.exception.JCertifInvalidRequestException;
import models.exception.JCertifObjectNotFoundException;
import models.util.Constantes;
import models.util.Json;
import models.util.Tools;
import models.validation.CheckHelper;

import org.codehaus.jackson.JsonNode;

import play.Play;
import play.mvc.Controller;
import play.mvc.Result;
import controllers.Security.Authenticated;

public class AdminController extends Controller {

	public static Result registerAdmin() {
    	JsonNode jsonNode = request().body().asJson();
		
    	if (jsonNode.get("key") != null) {
            String requestKey = jsonNode.get("key").asText();
            String serverKey = Play.application().configuration().getString("admin.key");
            if (serverKey.equals(requestKey)) {
            	Admin admin = Json.parse(Admin.class, jsonNode.toString());
            	admin.create();
            } else {
                return unauthorized("Bad admin key value");
            }
        } else {
            return unauthorized("Admin user connot be created without admin key");
        }

		return ok(Json.serialize("Ok"));
    }

    @Authenticated
    public static Result updateAdmin() {
    	JsonNode jsonNode = request().body().asJson();
		String email = jsonNode.findPath(Constantes.EMAIL_ATTRIBUTE_NAME).getTextValue();
		String version = jsonNode.findPath(Constantes.VERSION_ATTRIBUTE_NAME).getTextValue();
		
		if(Tools.isBlankOrNull(email)){
			throw new JCertifInvalidRequestException("Email cannot be null or empty");
		}
		
		Admin admin = Admin.find(email);
		
		if(null == admin){
			throw new JCertifObjectNotFoundException(Admin.class, email);
		}
		
		CheckHelper.checkVersion(admin, version);
		
		admin.merge(Json.parse(Admin.class, jsonNode.toString()));
		admin.save();
		return ok(Json.serialize("Ok"));
    }
    
    public static Result check() {
        String admin = session("admin");
        if (admin == null && request().body().asJson() != null && request().body().asJson().get("key") != null) {
            String requestKey = request().body().asJson().get("key").asText();
            String serverKey = Play.application().configuration().getString("admin.key");
            if (serverKey.equals(requestKey)) {
                // Ajout dans un cookie signé du paramètre "admin" si la clé est OK
                session("admin", "admin");
            } else {
                return badRequest();
            }
        } else {
            return badRequest();
        }
        return ok("");
    }

    public static Result ping(String nameUrl) {
        return ok();
    }
}
