package controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.oauth2.Client;
import models.oauth2.Errors;
import models.oauth2.GrantTypes;
import models.oauth2.OAuth2Parameter;
import models.oauth2.ResourceOwnerIdentity;
import models.oauth2.ResponseTypes;
import models.util.Json;
import models.util.Tools;

import org.codehaus.jackson.JsonNode;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
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
import controllers.oauth2.URL;
import controllers.oauth2.UserAuthentication;

public class OAuth2AccessController extends Controller {
	
	public static Result registerApplication(){
		JsonNode jsonNode = request().body().asJson();
		
		BasicDBObject params = (BasicDBObject)JSON.parse(jsonNode.toString());
		
		String appName = params.getString("name");
		Set<String> scope = Tools.basicDBListToJavaSet((BasicDBList)params.get(OAuth2Parameter.SCOPE));
		
		Client client = new Client();
		client.setClientId(appName);
		client.setClientSecret(Oauth2AccessProvider.generateSecret());
		client.setScope(scope);
		Set<String> grantTypes = new HashSet<String>();
		grantTypes.add(GrantTypes.CODE.getCode());
		grantTypes.add(GrantTypes.REFRESH_TOKEN.getCode());
		client.setAuthorizedGrantTypes(grantTypes);
		client.setAccessTokenValiditySeconds(3600);
		client.setRefreshTokenValiditySeconds(7200);
		Logger.info("client : " + client);
		Oauth2AccessProvider.addClient(client);
		
		Map<String,String> map = new HashMap<String, String>();
		map.put(OAuth2Parameter.CLIENT_ID, client.getClientId());
		map.put(OAuth2Parameter.CLIENT_SECRET, client.getClientSecret());
		return ok(JSON.serialize(map));
	}
	
	public static Result authorize(){
		String responseType = request().getQueryString(OAuth2Parameter.RESPONSE_TYPE);
		String clientId = request().getQueryString(OAuth2Parameter.CLIENT_ID);
		String redirect = request().getQueryString(OAuth2Parameter.REDIRECT_URI);
		String state = request().getQueryString(OAuth2Parameter.STATE);
		String scope = request().getQueryString(OAuth2Parameter.SCOPE);
		
		if(null == Oauth2AccessProvider.loadClientByClientId(clientId)){
			redirect(
					new URL.Builder().withBase(redirect)
						.withQueryParam(OAuth2Parameter.ERROR, Errors.UNAUTHORIZED_CLIENT.getCode())
						.withQueryParam(OAuth2Parameter.STATE, state).build());
		}
		
		if(ResponseTypes.CODE != ResponseTypes.fromValue(responseType)){
			redirect(
					new URL.Builder().withBase(redirect)
					.withQueryParam(OAuth2Parameter.ERROR, Errors.UNSUPORTED_RESPONSE_TYPE.getCode())
					.withQueryParam(OAuth2Parameter.STATE, state).build());
		}

		flash(OAuth2Parameter.CLIENT_ID, clientId);
		Logger.info("state : " + state);
		flash(OAuth2Parameter.STATE, state);
		Logger.info("redirect_uri : " + redirect);
		flash(OAuth2Parameter.REDIRECT_URI, redirect);
		Logger.info("scope : " + scope);
		flash(OAuth2Parameter.SCOPE, scope);
		return ok(login.render(Form.form(ResourceOwnerIdentity.class)));
	}
	
	public static Result getAuthorizationCode(String email, String redirectUri, String state, String clientId, Set<String> scope){
		AuthorizationRequestHolder requestHolder = new AuthorizationRequestHolder(
				new DefaultAuthorizationRequest(clientId, scope),
				new UserAuthentication(email, true));
		String code = Oauth2AccessProvider.createAuthorizationCode(requestHolder);
		String redirectUrl = 
			new URL.Builder().withBase(redirectUri)
				.withQueryParam(OAuth2Parameter.STATE, state)
				.withQueryParam(OAuth2Parameter.CODE, code)
				.withQueryParam("user", email).build();

		Logger.info("redirectUrl : " + redirectUrl);
		return redirect(redirectUrl);
	}
	
	@SuppressWarnings("unchecked")
	public static Result getAccessToken(){
		
		JsonNode jsonNode = request().body().asJson();
		
		BasicDBObject tokenRequest = (BasicDBObject)JSON.parse(jsonNode.toString());		
		Set<String> scope = Tools.basicDBListToJavaSet((BasicDBList)tokenRequest.get(OAuth2Parameter.SCOPE));
		tokenRequest.removeField(OAuth2Parameter.SCOPE);
		tokenRequest.put(OAuth2Parameter.SCOPE, OAuth2Utils.formatParameterList(scope));
		
		Map<String, String> authorizationParameters = new HashMap<String, String>();
		authorizationParameters.putAll(tokenRequest.toMap());
		
		AuthorizationRequest authorizationRequest = Oauth2AccessProvider.createAuthorizationRequest(authorizationParameters);
		OAuth2AccessToken accessToken = Oauth2AccessProvider.grant(authorizationRequest);
		Logger.info("accessToken : " + accessToken);
		return ok(Json.jacksonSerialize(accessToken));
	}
}
