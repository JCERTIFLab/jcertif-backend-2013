package controllers;

import play.mvc.Result;
import views.html.index;

public class HomeController extends AbstractController {

    public static Result get() {
        return ok(index.render());
    }
}
