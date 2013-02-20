package controllers;

import com.mongodb.util.JSON;
import objects.access.LoginDB;
import play.mvc.Result;
  
public class LoginController extends AbstractController {
 
    public static Result logins() {
        allowCrossOriginJson();
        return ok(JSON.serialize(LoginDB.loginDB.list()));
    }
    
}
