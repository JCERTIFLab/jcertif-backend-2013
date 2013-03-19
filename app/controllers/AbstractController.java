package controllers;

import models.exception.JCertifException;
import models.exception.JCertifResourceAccessException;
import play.mvc.Controller;

public abstract class AbstractController extends Controller {

    protected static void allowCrossOriginJson() {
        response().setHeader("Access-Control-Allow-Origin", "*");
        response().setHeader("Access-Control-Allow-Methods", "GET,POST");
        response().setHeader("Access-Control-Allow-Headers", "Content-Type");
        response().setHeader("Content-Type", "application/json; charset=utf-8");
    }

    protected static boolean isAdmin(){
        return session().get("admin") != null;
    }

    protected static void checkAdmin() throws JCertifException{
        if(!isAdmin()){
            throw new JCertifResourceAccessException("Operation not allowed for non-administrators");
        }
    }

}
