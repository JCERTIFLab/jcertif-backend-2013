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
public final class TokenChecksFactoy {

	private static final TokenChecksFactoy INSTANCE = new TokenChecksFactoy();
	private Map<String, TokenCheck> tokenChecks;
	
	private TokenChecksFactoy(){
		tokenChecks = new HashMap<String, TokenChecksFactoy.TokenCheck>();
		tokenChecks.put("userpass", new WebAppTokenCheck());
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
		String getProviderId();
	}
	
	public static class DummyTokenCheck implements TokenCheck {
		@Override
		public boolean isValid(String accessToken) {
			return false;
		}

		@Override
		public String getProviderId() {
			return "dummy";
		}	
	}
	
	public static abstract class AbstractTokenCheck implements TokenCheck {

		@Override
		public boolean isValid(String accessToken) {
			boolean isValid = false;
			Token token = Token.findTokenByIdAndProvider(accessToken, getProviderId());
			if(null != token){
				Logger.info("access token found");
				return !token.isExpired();
			}
			isValid = isTokenValidForProvider(accessToken);
			return isValid;
		}	
		
		protected abstract boolean isTokenValidForProvider(String accessTokentoken);
	}
	
	public static class GoogleTokenCheck extends AbstractTokenCheck {

		private static final String ID = "google";
		
		@Override
		protected boolean isTokenValidForProvider(String accessToken) {
			boolean isValid = false;
			Response response = WS.url("https://www.googleapis.com/oauth2/v1/tokeninfo")
			.setQueryParameter("access_token",accessToken).get().get();
			Logger.info("Google check response : " + response.getBody());
			JsonNode jsonNode = response.asJson();
			if(jsonNode != null &&
					(jsonNode.findPath("error") == null
					|| Tools.isBlankOrNull(jsonNode.findPath("error").getTextValue()))){
				isValid = true;
				Token token = new Token();
				token.setAccessToken(accessToken);
				token.setExpiresIn(jsonNode.findPath("expires_in").getIntValue());
				token.setEmail(jsonNode.findPath("email").getTextValue());
				token.setProvider(GoogleTokenCheck.ID);
				token.create();
			}
			return isValid;
		}

		@Override
		public String getProviderId() {
			return GoogleTokenCheck.ID;
		}	
	}
	
	public static class WebAppTokenCheck extends AbstractTokenCheck {

		private static final String ID = "userpass";
		
		@Override
		protected boolean isTokenValidForProvider(String accessToken) {
			//return false as WebApp is not an OAuth provider
			//and cannot certified the validity of any token
			return false;
		}

		@Override
		public String getProviderId() {
			return WebAppTokenCheck.ID;
		}
	}
}
