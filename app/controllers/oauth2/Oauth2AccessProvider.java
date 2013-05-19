package controllers.oauth2;

import java.util.Map;

import models.oauth2.Client;
import models.oauth2.GrantTypes;
import models.oauth2.OAuth2Parameter;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.AuthorizationRequestManager;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.DefaultAuthorizationRequestManager;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationRequestHolder;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;


/**
 * <p>Cette classe fournit les services nécessaires à l'authentification via le protocole OAuth v2.</p>
 * <ul>
 * <li>Un service de gestion des clients OAuth autorisés</li>
 * <li>Un service de gestion des codes d'autorisation, ils sont générés et stockés temporairement puis échangés par des token</li>
 * <li>Un service de gestion des tokens : génération, association user/client, stockage, révocation etc.</li>
 * </ul>
 * 
 * @author Martial SOMDA
 *
 */
public class Oauth2AccessProvider {

	private static JCertifClientDetailsService clientDetailsService = new JCertifClientDetailsService();
	private static InMemoryAuthorizationCodeServices authorizationCodeServices = new InMemoryAuthorizationCodeServices();
	private static JCertifTokenServices authorizationServerTokenServices = new JCertifTokenServices(clientDetailsService);
	private static AuthorizationRequestManager authorisationRequestManager = new DefaultAuthorizationRequestManager(clientDetailsService);
	private static AuthorizationCodeTokenGranter authorisationCodeGranter = new AuthorizationCodeTokenGranter(authorizationServerTokenServices, authorizationCodeServices, clientDetailsService);
	private static RandomValueStringGenerator ramdomGenerator = new RandomValueStringGenerator(21);
	
	
	public static String createAuthorizationCode(AuthorizationRequestHolder requestHolder) {
		return authorizationCodeServices.createAuthorizationCode(requestHolder);
	}

	public static AuthorizationRequest createAuthorizationRequest(Map<String, String> authorizationParameters) {
		authorisationRequestManager.validateParameters(authorizationParameters, loadClientByClientId(authorizationParameters.get(OAuth2Parameter.CLIENT_ID)));
		return authorisationRequestManager.createAuthorizationRequest(authorizationParameters);	
	}

	public static OAuth2AccessToken grant(AuthorizationRequest authorizationRequest) {
		return authorisationCodeGranter.grant(GrantTypes.CODE.getCode(), authorizationRequest);
	}


	public static ClientDetails loadClientByClientId(String clientId) {
		return clientDetailsService.loadClientByClientId(clientId);
	}


	public static String generateSecret() {
		return ramdomGenerator.generate();
	}


	public static void addClient(Client client) {
		clientDetailsService.addClientDetails(client);		
	}

	public static OAuth2Authentication loadAuthentication(String accessToken) {
		return authorizationServerTokenServices.loadAuthentication(accessToken);
	}
	
	

}
