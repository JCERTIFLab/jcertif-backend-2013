package controllers;


import models.objects.Category;
import models.objects.access.CategoryDB;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;

import com.mongodb.util.JSON;

public class CategoryController extends AbstractController {

	@JCertifContext(admin=false,bodyParse=false)
    public static Result list() {
        allowCrossOriginJson();
        return ok(JSON.serialize(CategoryDB.getInstance().list()));
    }

    @JCertifContext
    public static Result newCategory() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Category category = new Category(jsonNode.findPath("label").getTextValue());
		
    	category.add();
		return ok(JSON.serialize("Ok"));
    }

    @JCertifContext
    public static Result removeCategory() {
    	JsonNode jsonNode = request().body().asJson();
		
    	Category category = new Category(jsonNode.findPath("label").getTextValue());
		
    	category.remove();
		return ok(JSON.serialize("Ok"));
    }
}
