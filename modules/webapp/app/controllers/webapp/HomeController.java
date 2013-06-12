package controllers.webapp;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

import play.Logger;
import play.Play;
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

	private static final String BackendHost = Play.application().configuration().getString("backend.host");
	
    public static Result get() {
    	Identity user = (Identity) ctx().args.get(SecureSocial.USER_KEY);
    	Logger.info("user : " + user);    	
        return ok(index.render(user));
    }
	
	public static Result getParticipantProxy(String emailParticipant) {
		return callBackendJSonService(BackendHost + "/participant/get/" + emailParticipant);
	}
	
	public static Result newCategoryProxy() {		
		return callBackendJSonService(BackendHost + "/ref/category/new");
	}
	
	public static Result removeCategoryProxy() {		
		return callBackendJSonService(BackendHost + "/ref/category/remove");
	}
	
	public static Result addSponsorLevelProxy() {		
		return callBackendJSonService(BackendHost + "/ref/sponsorlevel/new");
	}
	
	public static Result removeSponsorLevelProxy() {		
		return callBackendJSonService(BackendHost + "/ref/sponsorlevel/remove");
	}
	
	public static Result addTitleProxy() {		
		return callBackendJSonService(BackendHost + "/ref/title/new");
	}
	
	public static Result removeTitleProxy() {		
		return callBackendJSonService(BackendHost + "/ref/title/remove");
	}
	
	public static Result addSessionStatusProxy() {		
		return callBackendJSonService(BackendHost + "/ref/sessionstatus/new");
	}
	
	public static Result removeSessionStatusProxy() {		
		return callBackendJSonService(BackendHost + "/ref/sessionstatus/remove");
	}
	
	public static Result callBackendJSonService(String url) {
		
		WSRequestHolder reqHolder = WS.url(url);
		Identity user = (Identity) ctx().args.get(SecureSocial.USER_KEY);
		
		JsonNode jsonNode = request().body().asJson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("label", jsonNode.findPath("label").getTextValue());
		Logger.info("label : " + jsonNode.findPath("label").getTextValue());
		if(user != null){							
			map.put("provider", user.id().providerId());
			Logger.info("provider : " + user.id().providerId());
			map.put("email", user.email().get());
			Logger.info("email : " + user.email().get());
			map.put("access_token", user.oAuth2Info().get().accessToken());
			Logger.info("access_token : " + user.oAuth2Info().get().accessToken());
		}

		Response response = reqHolder.post(Json.toJson(map)).get();
		if(Http.Status.UNAUTHORIZED == response.getStatus()){
			Logger.info("access_token probably expired");
			Logger.info("refresh_token : " + user.oAuth2Info().get().refreshToken().get());
		}
		return Results.status(response.getStatus(),response.getBody());
	}
}
