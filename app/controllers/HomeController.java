package controllers;

import play.Logger;
import play.Play;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.home;

/**
 * @author Martial SOMDA
 *
 */
public class HomeController extends Controller {

	public static Result get() {    	
        return ok(home.render(Play.application().configuration().getString("application.version")));
    }
}
