package controllers;


import models.Category;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;

import com.mongodb.util.JSON;

import controllers.Security.Admin;

public class CategoryController extends Controller {

    public static Result list() {

        return ok(JSON.serialize(Category.findAll()));
    }

    @Admin
    public static Result newCategory() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Category category = new Category(jsonNode.findPath("label").getTextValue());
		
    	category.create();
		return ok(JSON.serialize("Ok"));
    }

    @Admin
    public static Result removeCategory() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Category category = new Category(jsonNode.findPath("label").getTextValue());
		
    	category.remove();
		return ok(JSON.serialize("Ok"));
    }
}
