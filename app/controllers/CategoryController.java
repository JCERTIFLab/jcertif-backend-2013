package controllers;


import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import models.exception.JCertifException;
import models.objects.Category;
import models.objects.Session;
import models.objects.SessionStatus;
import models.objects.access.CategoryDB;
import models.objects.access.SessionDB;
import models.objects.access.SessionStatusDB;
import models.util.Tools;
import play.Logger;
import play.mvc.Http;
import play.mvc.Result;

public class CategoryController extends AbstractController {

    public static Result list() {
        allowCrossOriginJson();
        return ok(JSON.serialize(CategoryDB.getInstance().list()));
    }

    public static Result newCategory() {
        allowCrossOriginJson();

        try {
            checkAdmin();
        } catch (JCertifException jcertifException) {
            Logger.info("access rejected for non-administrator");
            return forbidden(jcertifException.getMessage());
        }

        Http.RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            return badRequest(e.getMessage());
        }

        String categoryObjInJSONForm = requestBody.asJson().toString();
        Category category;

        try{
            category = new Category((BasicDBObject) JSON.parse(categoryObjInJSONForm));
        }catch(JSONParseException exception){
            return badRequest(categoryObjInJSONForm);
        }

        try {
            CategoryDB.getInstance().add(category);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }
        Logger.info("Category '" + category.getLabel() + "' added");

        return ok(JSON.serialize("Ok"));
    }

    public static Result removeCategory() {
        allowCrossOriginJson();

        try {
            checkAdmin();
        } catch (JCertifException jcertifException) {
            Logger.info("access rejected for non-administrator");
            return forbidden(jcertifException.getMessage());
        }

        Http.RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            return badRequest(e.getMessage());
        }
        
        String categoryObjInJSONForm = requestBody.asJson().toString();
        Category category;

        try{
            category = new Category((BasicDBObject) JSON.parse(categoryObjInJSONForm));
        }catch(JSONParseException exception){
            return badRequest(categoryObjInJSONForm);
        }

        try {
            CategoryDB.getInstance().remove(category);
        } catch (JCertifException jcertifException) {
            return internalServerError(jcertifException.getMessage());
        }
        Logger.info("Category '" + category.getLabel() + "' removed");

        return ok(JSON.serialize("Ok"));
    }
}
