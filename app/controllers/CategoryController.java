package controllers;


import models.Category;
import models.exception.JCertifInvalidRequestException;
import models.exception.JCertifObjectNotFoundException;
import models.util.Constantes;
import models.util.Json;
import models.util.Tools;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;
import controllers.Security.Admin;

public class CategoryController extends Controller {

    public static Result list() {

        return ok(Json.serialize(Category.findAll()));
    }
    
    public static Result listVersion(String version) {

        return ok(Json.serialize(Category.findAll(version)));
    }

    @Admin
    public static Result newCategory() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Category category = Json.parse(Category.class, jsonNode.toString());
		
    	category.create();
		return ok(Json.serialize("Ok"));
    }

    @Admin
    public static Result removeCategory() {
    	JsonNode jsonNode = request().body().asJson();
		
		String label = jsonNode.findPath(Constantes.LABEL_ATTRIBUTE_NAME).getTextValue();
		
		if(Tools.isBlankOrNull(label)){
			throw new JCertifInvalidRequestException("Label cannot be null or empty");
		}
		
		Category category = Category.find(label);
		 
		if(null == category){
			throw new JCertifObjectNotFoundException(Category.class, label);
		}

		category.delete();
		return ok(Json.serialize("Ok"));
    }
}
