package models;

import java.util.HashMap;
import java.util.Map;

import models.util.Tools;

import org.codehaus.jackson.JsonNode;

import play.Logger;
import play.Play;
import play.libs.WS;
import play.libs.WS.Response;
import play.mvc.Http;

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
		tokenChecks.put(GitHubTokenCheck.ID, new GitHubTokenCheck());
		tokenChecks.put(WebAppTokenCheck.ID, new WebAppTokenCheck());
		tokenChecks.put(GoogleTokenCheck.ID, new GoogleTokenCheck());
		tokenChecks.put(DummyTokenCheck.ID, new DummyTokenCheck());
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
		boolean isValid(String accessToken, String email);
		String getProviderId();
	}
	
	public static class DummyTokenCheck implements TokenCheck {
		public static final String ID = "dummy";
		@Override
		public boolean isValid(String accessToken, String email) {
			return false;
		}

		@Override
		public String getProviderId() {
			return DummyTokenCheck.ID;
		}	
	}
	
	public static abstract class AbstractTokenCheck implements TokenCheck {

		@Override
		public boolean isValid(String accessToken, String email) {
			boolean isValid = false;
			Token token = Token.findTokenByIdAndProvider(accessToken, getProviderId());
			if(null != token){
				Logger.info("access token found");
				return !token.isExpired();
			}
			isValid = isTokenValidForProvider(accessToken, email);
			return isValid;
		}	
		
		protected abstract boolean isTokenValidForProvider(String accessTokentoken, String email);
	}
	
	public static class GoogleTokenCheck extends AbstractTokenCheck {

		public static final String ID = "google";
		
		@Override
		protected boolean isTokenValidForProvider(String accessToken, String email) {
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
				token.setEmail(jsonNode.findPath("email") != null? jsonNode.findPath("email").getTextValue() : email);
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
	
	public static class GitHubTokenCheck extends AbstractTokenCheck {

		public static final String ID = "github";
		private static final String CLIENT_ID = Play.application().configuration().getString("securesocial.github.clientId");
		private static final String CLIENT_SECRET = Play.application().configuration().getString("securesocial.github.clientSecret");
		
		@Override
		protected boolean isTokenValidForProvider(String accessToken, String email) {
			boolean isValid = false;
			
			Response response = WS.url("https://api.github.com/applications/"+ CLIENT_ID + "/tokens/" + accessToken)
			.setAuth(CLIENT_ID, CLIENT_SECRET).get().get();
			Logger.info("GitHub check response : " + response.getBody());
			if(Http.Status.OK == response.getStatus()){
				isValid = true;
				Token token = new Token();
				token.setAccessToken(accessToken);
				token.setExpiresIn(3600);
				token.setEmail(email);
				token.setProvider(GitHubTokenCheck.ID);
				token.create();
			}
			return isValid;
		}

		@Override
		public String getProviderId() {
			return GitHubTokenCheck.ID;
		}	
	}
	
	public static class WebAppTokenCheck extends AbstractTokenCheck {

		public static final String ID = "userpass";
		
		@Override
		protected boolean isTokenValidForProvider(String accessToken, String email) {
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
