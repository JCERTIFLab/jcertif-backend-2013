package controllers.oauth2;

import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;


/**
 * @author Martial SOMDA
 *
 */
public class JCertifTokenServices extends DefaultTokenServices {
	
	public JCertifTokenServices(ClientDetailsService clientDetailService){
		setClientDetailsService(clientDetailService);
		setSupportRefreshToken(true);
		setReuseRefreshToken(false);
		setTokenStore(new JCertifTokenStore());
	}

}
