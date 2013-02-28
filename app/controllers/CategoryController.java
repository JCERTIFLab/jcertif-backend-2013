package controllers;


import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import database.MongoDatabase;
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
                if(newValue == null  || newValue.equals("")){
                    MongoDatabase.getInstance().create("category", new BasicDBObjectBuilder().append("label",newValue).get());
                    response = ok();
                } else {
                    response = badRequest();
                }
            }
        } else {
            response = forbidden();
        }
        return response;
    }
}
