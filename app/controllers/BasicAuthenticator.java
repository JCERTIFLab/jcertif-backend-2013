package controllers;

import models.exception.JCertifException;

import org.apache.commons.codec.binary.Base64;

import play.Logger;
import play.mvc.Http.Context;
import play.mvc.Security.Authenticator;

/**
 * @author Martial SOMDA
 *
 */
public class BasicAuthenticator extends Authenticator {

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
			decodedAuth = new Base64().decode(encodedAuth.getBytes("UTF-8"));
			credentials = new String(decodedAuth, "UTF-8").split(":");
		} catch (Exception e) {
			throw new JCertifException(e.getMessage(),e);
		}        

        if (credentials == null || credentials.length != 2) {
            return null;
        }

        String clientId = credentials[0];
        Logger.info(clientId);
        String clientSecret = credentials[1];
        Logger.info(clientSecret);
        
        return ("webapp".equals(clientId) && "password".equals(clientSecret))? "Ok" : null;
	}
}
