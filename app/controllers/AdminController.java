package controllers;


import play.mvc.Result;
import models.util.properties.JCertifPropUtils;

public class AdminController extends AbstractController {

    public static Result check() {
        allowCrossOriginJson();
        String admin = session("admin");
        if(admin == null) {
            String requestKey = request().body().asJson().get("key").asText();
            String serverKey = JCertifPropUtils.getInstance().getProperty("jcertifbackend.admin.key");
            if(serverKey.equals(requestKey)){
                // Ajout dans un cookie signé du paramètre "admin" si la clé est OK
                session("admin","admin");
            }
        }
        return ok("");
    }
}
