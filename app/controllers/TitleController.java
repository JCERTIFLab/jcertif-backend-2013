package controllers;

import models.Title;

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
		
		Title title = new Title((BasicDBObject)JSON.parse(jsonNode.toString()));
		
		title.remove();
		return ok(JSON.serialize("Ok"));
    }
}
