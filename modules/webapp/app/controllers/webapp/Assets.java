package controllers.webapp;

import play.api.mvc.Action;
import play.api.mvc.AnyContent;
import play.mvc.Controller;

/**
 * @author Martial SOMDA
 *
 */
public class Assets extends Controller{

	//can be referenced as `controllers.webapp.Assets.delegate.at` in the route file
	public static controllers.AssetsBuilder delegate = new controllers.AssetsBuilder();
	
	public static Action<AnyContent> at(String file) {
		return delegate.at("/public", file);
	}
}
