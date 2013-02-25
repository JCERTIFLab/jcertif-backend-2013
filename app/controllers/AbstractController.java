package controllers;

import play.mvc.Controller;

public abstract class AbstractController extends Controller {

    protected static void allowCrossOriginJson() {
        response().setHeader("Access-Control-Allow-Origin", "*");
        response().setHeader("Access-Control-Allow-Methods", "GET,POST");
        response().setHeader("Access-Control-Allow-Headers", "Content-Type");
        response().setHeader("Content-Type", "application/json; charset=utf-8");

    }

}
