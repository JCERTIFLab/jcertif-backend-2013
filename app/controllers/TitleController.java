package controllers;

import models.Title;
import models.exception.JCertifInvalidRequestException;
import models.exception.JCertifObjectNotFoundException;
import models.util.Constantes;
import models.util.Tools;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

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
		
		Title title = new Title((BasicDBObject)JSON.parse(jsonNode.toString()));
		
		title.create();
		return ok(JSON.serialize("Ok"));
    }
	
	public static Result listTitle() {
        
		return ok(JSON.serialize(Title.findAll()));
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

		title.remove();
		return ok(JSON.serialize("Ok"));
    }
}
