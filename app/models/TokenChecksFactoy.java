package models;

import java.util.HashMap;
import java.util.Map;

import models.util.Tools;

import org.codehaus.jackson.JsonNode;

import play.Logger;
import play.libs.WS;
import play.libs.WS.Response;

/**
 * <p>Cette classe est une fabrique d'objets de type {@link TokenCheck}.
 * Elle renvoie une unique implémentation de <code>TokenCheck</code> par fournisseur d'accès OAuth v2.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class TokenChecksFactoy {

	private static final TokenChecksFactoy INSTANCE = new TokenChecksFactoy();
	private Map<String, TokenCheck> tokenChecks;
	
	private TokenChecksFactoy(){
		tokenChecks = new HashMap<String, TokenChecksFactoy.TokenCheck>();
		tokenChecks.put("google", new GoogleTokenCheck());
		tokenChecks.put("dummy", new DummyTokenCheck());
	}
	
	public static TokenChecksFactoy getInstance(){
		return INSTANCE;
	}
	
	public TokenCheck getCheck(String providerId){
		TokenCheck check = tokenChecks.get(providerId);
		if(null == check){
			Logger.info("provider with Id " + providerId + " is not supported");
			check = tokenChecks.get("dummy");
		}
		return check;
	}
	
	/**
	 * Les classes implémentant cette interface permettent de vérifier la validité d'un
	 * token d'accès au près d'un fournisseur d'accès OAuth v2
	 */
	public interface TokenCheck {
		boolean isValid(String accessToken);
	}
	
	public static class DummyTokenCheck implements TokenCheck {
		@Override
		public boolean isValid(String accessToken) {
			return false;
		}	
	}
	
	public static class GoogleTokenCheck implements TokenCheck {

		@Override
		public boolean isValid(String accessToken) {
			boolean isValid = false;
			Token token = Token.find(accessToken);
			if(null != token){
				return !token.isExpired();
			}
			Response response = WS.url("https://www.googleapis.com/oauth2/v1/tokeninfo")
						.setQueryParameter("access_token",accessToken).get().get();
			Logger.info("Google check response : " + response.getBody());
			JsonNode jsonNode = response.asJson().findPath("error");
			if(Tools.isBlankOrNull(jsonNode.getTextValue())){
				isValid = true;
				token = new Token();
				token.setAccessToken(accessToken);
				token.setExpiresIn(jsonNode.findPath("expires_in").getIntValue());
				token.create();
			}
			return isValid;
		}	
	}
}
