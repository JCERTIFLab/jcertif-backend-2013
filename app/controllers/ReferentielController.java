package controllers;

import play.mvc.Result;
import util.MongoUtils;

public class ReferentielController extends AbstractController {

	public static Result sponsorlevel() {
		allowCrossOriginJson();
		return ok(MongoUtils.INSTANCE.find("sponsor_level"));
	}

	
	public static Result categories() {
		allowCrossOriginJson();
		return ok(MongoUtils.INSTANCE.find("category"));
	}

}
