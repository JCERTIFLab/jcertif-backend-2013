package controllers;

import com.mongodb.util.JSON;
import objects.access.SponsorDB;
import play.mvc.Result;

public class SponsorController extends AbstractController {

    public static Result listSponsor() {
        allowCrossOriginJson();
        return ok(JSON.serialize(SponsorDB.sponsorDB.list()));
    }
}
