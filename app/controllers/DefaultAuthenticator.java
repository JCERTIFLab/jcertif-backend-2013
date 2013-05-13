package controllers;

import models.exception.JCertifException;
import models.oauth2.Client;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;
import sun.misc.BASE64Decoder;
import controllers.oauth2.Oauth2AccessProvider;


/**
 * @author Martial SOMDA
 *
 */
public class DefaultAuthenticator extends Authenticator {

	@Override
	public String getUsername(Context context) {
		
		String authHeader = context.request().getHeader("authorization");
		play.Logger.info("authorization : " + authHeader);
        if (authHeader == null) {
            context.response().setHeader("WWW-Authenticate", "Basic realm=Base64(client_id:client_secret)");
            return null;
        }
        
        String encodedAuth = authHeader.substring(6);
        byte[] decodedAuth;
        String[] credentials = null;
		try {
			decodedAuth = new BASE64Decoder().decodeBuffer(encodedAuth);
			credentials = new String(decodedAuth, "UTF-8").split(":");
		} catch (Exception e) {
			throw new JCertifException(e.getMessage(),e);
		}        

        if (credentials == null || credentials.length != 2) {
            return null;
        }

        String clientId = credentials[0];
        String clientSecret = credentials[1];
        
        ClientDetails client = Oauth2AccessProvider.getClientDetailsService().loadClientByClientId(clientId);
        
        if(null == client 
        		|| !((Client)client).authenticate(clientSecret)){
        	return null;
        }
		String accessToken = null;
		
		if("GET".equals(context.request().method())){
			accessToken = context.request().getQueryString("access_token");
		}else{
			accessToken = context.request().body().asJson().findPath("access_token").getTextValue();
		}
		
		OAuth2Authentication auth = Oauth2AccessProvider.getTokenServices().loadAuthentication(accessToken);
		return auth.getPrincipal().toString();
	}
	
	@Override
	public Result onUnauthorized(Context context) {
		return unauthorized("Operation not allowed for non-authenticated people");
	}
}
