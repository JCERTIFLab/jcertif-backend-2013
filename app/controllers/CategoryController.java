package controllers;


import com.mongodb.BasicDBObjectBuilder;
import models.database.MongoDatabase;
import models.util.Constantes;
import play.Logger;
import play.mvc.Result;

public class CategoryController extends AbstractController {

    public static Result list() {
        allowCrossOriginJson();
        return ok(MongoDatabase.getInstance().listAll("category"));
    }

    public static Result newCategory(){
        allowCrossOriginJson();
        Result response;
        if (isAdmin()) {
            if(request().body().asJson() == null){
                response = badRequest();
                Logger.info("newCategory : badrequest");
            }  else {
                String newValue = request().body().asJson().get("label").getTextValue();
                Logger.info("newCategory : " + newValue);
                if(newValue != null  && !newValue.equals("")){
                    String allCategories = MongoDatabase.getInstance().listAll(Constantes.COLLECTION_CATEGORY)  ;
                    if(!allCategories.contains(newValue)){

                        MongoDatabase.getInstance().create(Constantes.COLLECTION_CATEGORY, new BasicDBObjectBuilder().append("label",newValue).get());
                        response = ok();
                    } else {
                        response = badRequest("Create Category : value already exists");
                    }

                } else {
                    response = badRequest("Create Category : value is null or empty");
                }
            }
        } else {
            response = forbidden("Create Category : admin role is required");
        }
        return response;
    }
}
