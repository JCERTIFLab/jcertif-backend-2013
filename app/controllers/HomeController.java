package controllers;

import play.Logger;
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
}
