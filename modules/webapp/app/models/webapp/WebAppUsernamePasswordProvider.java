package models.webapp;

import java.util.HashMap;
import java.util.Map;

import play.Play;
import play.api.Application;
import play.api.data.Form;
import play.api.mvc.Request;
import play.api.mvc.Result;
import play.libs.F.Promise;
import play.libs.Json;
import play.libs.Scala;
import play.libs.WS;
import play.libs.WS.Response;
import play.mvc.Http;
import play.mvc.Results;
import scala.Tuple2;
import scala.util.Either;
import scala.util.Left;
import scala.util.Right;
import securesocial.controllers.TemplatesPlugin;
import securesocial.core.Identity;
import securesocial.core.SocialUser;
import securesocial.core.UserId;
import securesocial.core.UserServicePlugin;
import securesocial.core.providers.UsernamePasswordProvider;

/**
 * <p>An implementation of {@link UsernamePasswordProvider} that delegates authentication to JCertif Backend services.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class WebAppUsernamePasswordProvider extends UsernamePasswordProvider {

	private static final String BackendHost = Play.application().configuration().getString("backend.host");
	
	public WebAppUsernamePasswordProvider(Application application) {
		super(application);
	}
	
	@Override
	public <A> Either<Result, SocialUser> doAuth(Request<A> request) {
		Form<Tuple2<String,String>> form = UsernamePasswordProvider.loginForm().bindFromRequest(request);
		
		Either<Result, SocialUser> result = null;
		
		if(form.hasErrors()){
			
			result = new Left<Result, SocialUser>(
					Results.badRequest(
							Play.application().plugin(TemplatesPlugin.class)
							.getLoginPage(request, form, Scala.<String>None())).getWrappedResult());
		}else{
			UserId userId = new UserId(form.get()._1(), UsernamePasswordProvider.UsernamePassword());
			Identity user = Play.application().plugin(UserServicePlugin.class).find(userId).get();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("email", user.email().get());
			map.put("password", form.get()._2());
			Promise<Response> promise = WS.url(BackendHost + "/participant/login").post(Json.toJson(map));
			
			if(user != null
					&& Http.Status.OK == promise.get().getStatus()){

				result = new Right<Result, SocialUser>(
						new SocialUser(user.id(), user.firstName(), user.lastName(), user.fullName(), 
								user.email(), user.avatarUrl(), user.authMethod(), user.oAuth1Info(), 
								user.oAuth2Info(), user.passwordInfo()));
			}else{

				result = new Left<Result, SocialUser>(
						Results.badRequest(
								Play.application().plugin(TemplatesPlugin.class)
								.getLoginPage(request, form, Scala.Option("securesocial.login.invalidCredentials"))).getWrappedResult());
			}
		}

		return result;
	}

}
