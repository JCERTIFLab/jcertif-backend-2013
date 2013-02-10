package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
  public static Result index() {
      //views.html.index.render("Toles")

    //return ok(index.render("Your new application is ready.") );
      return ok("Your new Application is ready");
  }
  
}