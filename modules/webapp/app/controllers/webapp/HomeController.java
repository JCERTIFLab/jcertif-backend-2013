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
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import securesocial.core.Identity;
import securesocial.core.java.SecureSocial;
import securesocial.core.java.SecureSocial.UserAwareAction;
import views.html.index;

@UserAwareAction
public class HomeController extends Controller {

	
    public static Result get() {
    	Identity user = (Identity) ctx().args.get(SecureSocial.USER_KEY);
    	Logger.info("user : " + user);    	
        return ok(index.render(user));
    }
	
	public static Result getParticipantProxy(String emailParticipant) {
		return callBackendJSonService("http://" + request().host() + "/participant/get/" + emailParticipant);
	}
	
	public static Result newCategoryProxy() {		
		return callBackendJSonService("http://" + request().host() + "/ref/category/new");
	}
	
	public static Result removeCategoryProxy() {		
		return callBackendJSonService("http://" + request().host() + "/ref/category/remove");
	}
	
	public static Result addSponsorLevelProxy() {		
		return callBackendJSonService("http://" + request().host() + "/ref/sponsorlevel/new");
	}
	
	public static Result removeSponsorLevelProxy() {		
		return callBackendJSonService("http://" + request().host() + "/ref/sponsorlevel/remove");
	}
	
	public static Result addTitleProxy() {		
		return callBackendJSonService("http://" + request().host() + "/ref/title/new");
	}
	
	public static Result removeTitleProxy() {		
		return callBackendJSonService("http://" + request().host() + "/ref/title/remove");
	}
	
	public static Result addSessionStatusProxy() {		
		return callBackendJSonService("http://" + request().host() + "/ref/sessionstatus/new");
	}
	
	public static Result removeSessionStatusProxy() {		
		return callBackendJSonService("http://" + request().host() + "/ref/sessionstatus/remove");
	}
	
	public static Result callBackendJSonService(String url) {
		
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
		if(Http.Status.UNAUTHORIZED == response.getStatus()){
			Logger.info("access_token probabl expired");
			Logger.info("refresh_token : " + user.oAuth2Info().get().refreshToken().get());
		}
		return Results.status(response.getStatus(),response.getBody());
	}
}
