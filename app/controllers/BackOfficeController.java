package controllers;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;



import play.Logger;
import play.libs.F.Promise;
import play.libs.Json;
import play.libs.WS;
import play.libs.WS.Response;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.backoffice.backoffice;

/**
 * @author MSOMDA
 *
 */
public class BackOfficeController extends Controller{

	public static Result home(){
		String state = request().getQueryString("state");
		String code = request().getQueryString("code");	
		String user = request().getQueryString("user");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("response_type", "token");
		map.put("client_id", "backoffice");
		map.put("client_secret", "test");
		map.put("state", state);
		map.put("code", code);
		map.put("scope", new String[]{"user"});
		map.put("grant_type", "authorization_code");
		Promise<Response> promise = WS.url("http://localhost:9000/login/oauth/v2/token").post(Json.toJson(map));

		Response response = promise.get();
		
		JsonNode jsonNode = response.asJson();
		String accessToken = jsonNode.findPath("access_token").getTextValue();
		Logger.info("accessToken : " + accessToken);
		String tokenType = jsonNode.findPath("token_type").getTextValue();
		Logger.info("tokenType : " + tokenType);
		String expiresIn = jsonNode.findPath("expires_in").toString();
		Logger.info("expiresIn : " + expiresIn);
		String refreshToken = jsonNode.findPath("refresh_token").getTextValue(); 
		Logger.info("refreshToken : " + refreshToken);
		String scopes = jsonNode.findPath("scopes").toString();
		Logger.info("scopes : " + scopes);
		
		
		promise = WS.url("http://localhost:9000/participant/get/" + user).setQueryParameter("access_token", accessToken)
		.setAuth("backoffice", "r876CINW3pZuu7nL7h6AP").get();
		
		response = promise.get();
		
		jsonNode = response.asJson();
		
		Logger.info("participant : " + jsonNode);
		
		return ok(backoffice.render(jsonNode.findPath("firstname").getTextValue(),jsonNode.findPath("lastname").getTextValue()));
	}
}
