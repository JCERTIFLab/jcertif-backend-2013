package controllers;

import database.MongoDatabase;
import play.mvc.Result;

public class ReferentielController extends AbstractController {

    public static Result sponsorlevel() {
        allowCrossOriginJson();
        return ok(MongoDatabase.getInstance().listAll("sponsor_level"));
    }


    public static Result categories() {
        allowCrossOriginJson();
        return ok(MongoDatabase.getInstance().listAll("category"));
    }

}
