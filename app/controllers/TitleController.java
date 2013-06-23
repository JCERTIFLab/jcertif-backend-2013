package controllers;

import java.util.List;

import models.Title;
import models.exception.JCertifInvalidRequestException;
import models.exception.JCertifObjectNotFoundException;
import models.util.Constantes;
import models.util.Json;
import models.util.Tools;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;
import controllers.Security.Admin;

/**
 * <p>Controleur des civilités.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class TitleController extends Controller{

	@Admin
	public static Result addTitle() {
        
		JsonNode jsonNode = request().body().asJson();
		
		Title title = Json.parse(Title.class, jsonNode.toString());
		
		title.create();
		return ok(Json.serialize("Ok"));
    }
	
	public static Result listTitle() {
		List<Title> titles = Title.findAll();
		return ok(Json.serialize(titles));
    }
	
	@Admin
	public static Result removeTitle() {
        
		JsonNode jsonNode = request().body().asJson();
		
		String label = jsonNode.findPath(Constantes.LABEL_ATTRIBUTE_NAME).getTextValue();
		
		if(Tools.isBlankOrNull(label)){
			throw new JCertifInvalidRequestException("Label cannot be null or empty");
		}
		
		Title title = Title.find(label);
		
		if(null == title){
			throw new JCertifObjectNotFoundException(Title.class, label);
		}

		title.delete();
		return ok(Json.serialize("Ok"));
    }
}
