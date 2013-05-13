package controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.oauth2.Client;
import models.oauth2.Errors;
import models.oauth2.GrantTypes;
import models.oauth2.ResourceOwnerIdentity;
import models.oauth2.ResponseTypes;
import models.util.Json;
import models.util.Tools;

import org.codehaus.jackson.JsonNode;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.DefaultAuthorizationRequest;
import org.springframework.security.oauth2.provider.code.AuthorizationRequestHolder;

import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

import controllers.oauth2.Oauth2AccessProvider;
import controllers.oauth2.UserAuthentication;

public class OAuth2AccessController extends Controller {
	
	public static Result registerApplication(){
		JsonNode jsonNode = request().body().asJson();
		
		BasicDBObject params = (BasicDBObject)JSON.parse(jsonNode.toString());
		
		String appName = params.getString("name");
		Set<String> scope = Tools.basicDBListToJavaSet((BasicDBList)params.get("scope"));
		
		Client client = new Client();
		client.setClientId(appName);
		client.setClientSecret(Oauth2AccessProvider.getRamdomGenerator().generate());
		client.setScope(scope);
		Set<String> grantTypes = new HashSet<String>();
		grantTypes.add(GrantTypes.CODE.getCode());
		grantTypes.add(GrantTypes.REFRESH_TOKEN.getCode());
		client.setAuthorizedGrantTypes(grantTypes);
		client.setAccessTokenValiditySeconds(3600);
		client.setRefreshTokenValiditySeconds(7200);
		Logger.info("client : " + client);
		Oauth2AccessProvider.getClientDetailsService().addClientDetails(client);
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("client_id", client.getClientId());
		map.put("client_secret", client.getClientSecret());
		return ok(JSON.serialize(map));
	}
	
	public static Result authorize(){
		String responseType = request().getQueryString("response_type");
		String clientId = request().getQueryString("client_id");
		String redirect = request().getQueryString("redirect_uri");
		String state = request().getQueryString("state");
		String scope = request().getQueryString("scope");
		
		if(null == Oauth2AccessProvider.getClientDetailsService().loadClientByClientId(clientId)){
			redirect(redirect + "?error=" + Errors.UNAUTHORIZED_CLIENT.getCode() + "&state=" + state);
		}
		
		if(ResponseTypes.CODE != ResponseTypes.fromValue(responseType)){
			redirect(redirect + "?error=" + Errors.UNSUPORTED_RESPONSE_TYPE.getCode() + "&state=" + state);
		}

		flash("client_id", clientId);
		Logger.info("state : " + state);
		flash("state", state);
		Logger.info("redirect_uri : " + redirect);
		flash("redirect_uri", redirect);
		Logger.info("scope : " + scope);
		flash("scope", scope);
		return ok(login.render(Form.form(ResourceOwnerIdentity.class)));
	}
	
	public static Result getAuthenticationCode(String email, String redirectUri, String state, String clientId){
		AuthorizationRequestHolder requestHolder = new AuthorizationRequestHolder(
				new DefaultAuthorizationRequest(clientId,null),
				new UserAuthentication(email, true));
		String code = Oauth2AccessProvider.getAuthorizationCodeServices().createAuthorizationCode(requestHolder);
		String redirectUrl = redirectUri + "?state=" + state + "&code=" + code + "&user=" + email;
		Logger.info("redirectUrl : " + redirectUrl);
		return redirect(redirectUrl);
	}
	
	public static Result getAccessToken(){
		
		JsonNode jsonNode = request().body().asJson();
		
		BasicDBObject tokenRequest = (BasicDBObject)JSON.parse(jsonNode.toString());
		String clientId = tokenRequest.getString("client_id");
		Set<String> scope = Tools.basicDBListToJavaSet((BasicDBList)tokenRequest.get("scope"));
		
		tokenRequest.removeField("client_id");
		tokenRequest.removeField("scope");
		@SuppressWarnings("unchecked")
		AuthorizationRequest authorizationRequest = new DefaultAuthorizationRequest(tokenRequest.toMap(), null, clientId, scope);
		OAuth2AccessToken accessToken = Oauth2AccessProvider.getAuthorisationCodeGranter().grant(GrantTypes.CODE.getCode(), authorizationRequest);
		Logger.info("accessToken : " + accessToken);
		return ok(Json.jacksonSerialize(accessToken));
	}
}
