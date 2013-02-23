package controllers;

import play.mvc.Result;
import database.MongoDatabase;
import views.html.*;

public class HomeController extends AbstractController {

	public static Result get() {
        return ok(index.render());
	}
}
