/**
 * 
 */
package controllers.oauth2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.oauth2.OauthAccessToken;
import models.oauth2.OauthRefreshToken;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author MSOMDA
 *
 */
public class JCertifTokenStore implements TokenStore {

	//private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();
	
	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return readAuthentication(token.getValue());
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		return JCertifToSpringOAuth2Adapter.toOAuth2Authentication(OauthAccessToken.find(token));
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		OauthAccessToken accessToken = JCertifToSpringOAuth2Adapter.toAccessToken(token, authentication);
		accessToken.create();		
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		OauthAccessToken accessToken = OauthAccessToken.find(tokenValue);
		OauthRefreshToken refreshToken = null;
		if(null != accessToken){
			refreshToken = OauthRefreshToken.find(accessToken.getRefreshToken());
		}
		return JCertifToSpringOAuth2Adapter.toOAuth2AccessToken(accessToken, refreshToken);
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		OauthAccessToken accessToken = OauthAccessToken.find(token.getValue());
		if(null != accessToken){
			accessToken.remove();
		}
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken token, OAuth2Authentication authentication) {
		OauthRefreshToken refreshToken = JCertifToSpringOAuth2Adapter.toRefreshToken(token, authentication);
		refreshToken.create();
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		return JCertifToSpringOAuth2Adapter.toOAuth2RefreshToken(OauthRefreshToken.find(tokenValue));
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		return readAuthenticationForRefreshToken(token.getValue());
	}

	public OAuth2Authentication readAuthenticationForRefreshToken(String token) {
		return JCertifToSpringOAuth2Adapter.toOAuth2Authentication(OauthRefreshToken.find(token));
	}
	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		OauthRefreshToken refreshToken = OauthRefreshToken.find(token.getValue());
		if(null != refreshToken){
			refreshToken.remove();
		}
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken token) {
		OauthAccessToken accessToken = OauthAccessToken.findByRefreshToken(token.getValue());
		if(null != accessToken){
			accessToken.remove();
		}
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		return JCertifToSpringOAuth2Adapter.toOAuth2AccessToken(
				OauthAccessToken.findByAuthProfile(
						authentication.getAuthorizationRequest().getClientId(),
						authentication.getUserAuthentication().getPrincipal().toString(),
						authentication.getAuthorizationRequest().getScope()), 
				OauthRefreshToken.findByAuthProfile(authentication.getAuthorizationRequest().getClientId(),
						authentication.getUserAuthentication().getPrincipal().toString(),
						authentication.getAuthorizationRequest().getScope()));
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByUserName(String userName) {
		return builOAuth2AccessTokenCollection(OauthAccessToken.findByUser(userName), OauthRefreshToken.findByUser(userName));
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		return builOAuth2AccessTokenCollection(OauthAccessToken.findByClientId(clientId), OauthRefreshToken.findByClientId(clientId));
	}
	
	public Collection<OAuth2AccessToken> builOAuth2AccessTokenCollection(Collection<OauthAccessToken> accessTokens, Collection<OauthRefreshToken> refreshTokens) {
		List<OAuth2AccessToken> resultList = new ArrayList<OAuth2AccessToken>();
		Map<String, OauthRefreshToken> refreshTokensMap = new HashMap<String, OauthRefreshToken>();
		for(OauthRefreshToken refreshToken : refreshTokens){
			refreshTokensMap.put(refreshToken.getRefreshToken(), refreshToken);
		}
		for(OauthAccessToken accessToken : accessTokens){
			resultList.add(JCertifToSpringOAuth2Adapter.toOAuth2AccessToken(accessToken, refreshTokensMap.get(accessToken.getRefreshToken())));
		}
		return resultList;
	}

}
