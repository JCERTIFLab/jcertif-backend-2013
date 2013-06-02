package controllers.webapp;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

import play.Logger;
import play.libs.Json;
import play.libs.WS;
import play.libs.WS.Response;
import play.libs.WS.WSRequestHolder;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import securesocial.core.Identity;
import securesocial.core.java.SecureSocial;
import securesocial.core.java.SecureSocial.UserAwareAction;
import views.html.index;

public class HomeController extends Controller {

	@UserAwareAction
    public static Result get() {
    	Identity user = (Identity) ctx().args.get(SecureSocial.USER_KEY);
    	Logger.info("user : " + user);    	
        return ok(index.render(user));
    }
	
	@UserAwareAction
	public static Result getParticipantProxy(String emailParticipant) {
		
		WSRequestHolder reqHolder = WS.url("http://" + request().host() + "/participant/get/" + emailParticipant);
		Identity user = (Identity) ctx().args.get(SecureSocial.USER_KEY);
		if(user != null){
			reqHolder.setQueryParameter("access_token", user.oAuth2Info().get().accessToken());
			Logger.info("access_token : " + user.oAuth2Info().get().accessToken());
			reqHolder.setQueryParameter("provider", user.id().providerId());
			Logger.info("provider : " + user.id().providerId());
		}

		Response response = reqHolder.get().get();
		return Results.status(response.getStatus(),response.getBody());
	}
	
	@UserAwareAction
	public static Result newCategoryProxy() {
		
		return updateCategoryProxy("http://" + request().host() + "/ref/category/new");
	}
	
	@UserAwareAction
	public static Result removeCategoryProxy() {
		
		return updateCategoryProxy("http://" + request().host() + "/ref/category/remove");
	}
	
	public static Result updateCategoryProxy(String url) {
		
		WSRequestHolder reqHolder = WS.url(url);
		Identity user = (Identity) ctx().args.get(SecureSocial.USER_KEY);
		
		JsonNode jsonNode = request().body().asJson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("label", jsonNode.findPath("label").getTextValue());
		Logger.info("label : " + jsonNode.findPath("label").getTextValue());
		if(user != null){						
			map.put("access_token", user.oAuth2Info().get().accessToken());
			Logger.info("access_token : " + user.oAuth2Info().get().accessToken());
			map.put("provider", user.id().providerId());
			Logger.info("provider : " + user.id().providerId());
			map.put("email", user.email().get());
			Logger.info("email : " + user.email().get());
		}

		Response response = reqHolder.post(Json.toJson(map)).get();
		return Results.status(response.getStatus(),response.getBody());
	}
}
