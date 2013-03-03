package controllers;

import com.mongodb.util.JSON;
import models.database.MongoDatabase;
import play.mvc.Result;

public class ReferentielController extends AbstractController {

    public static Result sponsorlevel() {
        allowCrossOriginJson();
        return ok(JSON.serialize(MongoDatabase.getInstance().listAll("sponsor_level")));
    }


    public static Result categories() {
        allowCrossOriginJson();
        return ok(JSON.serialize(MongoDatabase.getInstance().listAll("category")));
    }

}
