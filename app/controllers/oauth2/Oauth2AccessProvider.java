package controllers.oauth2;

import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
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
	private static AuthorizationCodeTokenGranter authorisationCodeGranter = new AuthorizationCodeTokenGranter(authorizationServerTokenServices, authorizationCodeServices, clientDetailsService);
	private static RandomValueStringGenerator ramdomGenerator = new RandomValueStringGenerator(21);
	
	public static JCertifClientDetailsService getClientDetailsService() {
		return clientDetailsService;
	}

	public static InMemoryAuthorizationCodeServices getAuthorizationCodeServices() {
		return authorizationCodeServices;
	}

	public static JCertifTokenServices getTokenServices() {
		return authorizationServerTokenServices;
	}

	public static AuthorizationCodeTokenGranter getAuthorisationCodeGranter() {
		return authorisationCodeGranter;
	}

	public static RandomValueStringGenerator getRamdomGenerator() {
		return ramdomGenerator;
	}

}
