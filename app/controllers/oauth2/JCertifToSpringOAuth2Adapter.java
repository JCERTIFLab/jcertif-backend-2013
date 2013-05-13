package controllers.oauth2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import models.exception.JCertifException;
import models.oauth2.OauthAccessToken;
import models.oauth2.OauthRefreshToken;
import models.util.Constantes;
import models.util.Tools;

import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.DefaultAuthorizationRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * <p>Adapter permettant d'isoler les classes JCertif du model Spring</p>
 * 
 * @author Martial SOMDA
 *
 */
public final class JCertifToSpringOAuth2Adapter {
	
	private JCertifToSpringOAuth2Adapter(){		
	}

	public static OAuth2Authentication toOAuth2Authentication(OauthAccessToken accessToken){
		if(null == accessToken){
			return null;
		}
		return toOAuth2Authentication(accessToken.getClientId(),accessToken.getUser(),accessToken.getScope());
	}
	
	public static OAuth2Authentication toOAuth2Authentication(OauthRefreshToken refreshToken) {
		if(null == refreshToken){
			return null;
		}
		return toOAuth2Authentication(refreshToken.getClientId(),refreshToken.getUser(),refreshToken.getScope());
	}
	
	public static OAuth2Authentication toOAuth2Authentication(String clientId, String user, Set<String> scope) {
		AuthorizationRequest authorizationRequest = new DefaultAuthorizationRequest(clientId,scope);
		UserAuthentication userAuthentication = new UserAuthentication(user, true);
		OAuth2Authentication authentication = new OAuth2Authentication(authorizationRequest, userAuthentication);
		return authentication;
	}
	
	public static OauthAccessToken toAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication){
		if(null == token || null == authentication){
			return null;
		}
		SimpleDateFormat sdf =new SimpleDateFormat(Constantes.DATEFORMAT);
		
		OauthAccessToken accessToken = new OauthAccessToken();
		accessToken.setClientId(authentication.getAuthorizationRequest().getClientId());
		accessToken.setAccessToken(token.getValue());
		if(null != token.getRefreshToken()){
			accessToken.setRefreshToken(token.getRefreshToken().getValue());
		}			
		accessToken.setExpiresIn(Integer.toString(token.getExpiresIn()));
		accessToken.setExpirationDate(sdf.format(token.getExpiration()));
		accessToken.setScope(token.getScope());
		accessToken.setTokenType(token.getTokenType());
		accessToken.setUser(authentication.getPrincipal().toString());
		return accessToken;
	}

	public static OAuth2AccessToken toOAuth2AccessToken(OauthAccessToken accessToken, OauthRefreshToken refreshToken) {
		if(null == accessToken){
			return null;
		}
		DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken.getAccessToken());
		token.setTokenType(accessToken.getTokenType());
		token.setScope(accessToken.getScope());
		token.setRefreshToken(toOAuth2RefreshToken(refreshToken));
		
		if(!Tools.isBlankOrNull(accessToken.getExpirationDate()) 
				&& Tools.isValidDate(accessToken.getExpirationDate())){
			Date expirationDate = null;
			try {
				expirationDate = new SimpleDateFormat(Constantes.DATEFORMAT).parse(accessToken.getExpirationDate());
			} catch (ParseException e) {
				//ne devrait jamais arriver
				throw new JCertifException(e.getMessage(),e);
			}
			token.setExpiration(expirationDate);
		}
		return token;
	}

	public static OAuth2RefreshToken toOAuth2RefreshToken(OauthRefreshToken refreshToken) {
		if(null == refreshToken){
			return null;
		}
		OAuth2RefreshToken token;
		
		if(!Tools.isBlankOrNull(refreshToken.getExpirationDate()) 
				&& Tools.isValidDate(refreshToken.getExpirationDate())){
			Date expirationDate = null;
			try {
				expirationDate = new SimpleDateFormat(Constantes.DATEFORMAT).parse(refreshToken.getExpirationDate());
			} catch (ParseException e) {
				//ne devrait jamais arriver
				throw new JCertifException(e.getMessage(),e);
			}
			token = new DefaultExpiringOAuth2RefreshToken(refreshToken.getRefreshToken(), expirationDate);
		}else{
			token = new DefaultOAuth2RefreshToken(refreshToken.getRefreshToken());
		}
		return token;
	}

	public static OauthRefreshToken toRefreshToken(OAuth2RefreshToken token, OAuth2Authentication authentication) {
		if(null == token || null == authentication){
			return null;
		}
		SimpleDateFormat sdf =new SimpleDateFormat(Constantes.DATEFORMAT);
		
		OauthRefreshToken refreshToken = new OauthRefreshToken();
		refreshToken.setClientId(authentication.getAuthorizationRequest().getClientId());
		refreshToken.setRefreshToken(token.getValue());
		if(token instanceof DefaultExpiringOAuth2RefreshToken){
			refreshToken.setExpirationDate(sdf.format(((DefaultExpiringOAuth2RefreshToken)token).getExpiration()));
		}	
		refreshToken.setScope(authentication.getAuthorizationRequest().getScope());
		refreshToken.setUser(authentication.getPrincipal().toString());
		return refreshToken;
	}
}
