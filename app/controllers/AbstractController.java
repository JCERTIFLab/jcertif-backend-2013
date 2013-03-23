package controllers;

import models.exception.JCertifResourceAccessException;
import models.util.properties.JCertifPropUtils;
import play.mvc.Controller;

public abstract class AbstractController extends Controller {

    protected static void allowCrossOriginJson() {
        response().setHeader("Access-Control-Allow-Origin", "*");
        response().setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        response().setHeader("Access-Control-Allow-Headers", "Content-Type");
        response().setHeader("Content-Type", "application/json; charset=utf-8");
    }

    protected static boolean isAdmin() {
        System.out.print("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + JCertifPropUtils.getInstance().getProperty("jcertifbackend.admin.active"));
        if(!"true".equals(JCertifPropUtils.getInstance().getProperty("jcertifbackend.admin.active"))){
            System.out.print("***********************************************************");
            return true;
        }
        return session().get("admin") != null;
    }

    protected static void checkAdmin() {
        if(!isAdmin()){
            throw new JCertifResourceAccessException("Operation not allowed for non-administrators");
        }
    }

}
