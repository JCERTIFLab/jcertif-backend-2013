package controllers.oauth2;

import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationRequestHolder;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

/**
 * <p>Implémentation of {@link AuthorizationCodeServices} that store codes in MongoDB.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class JCertifAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices#store(java.lang.String, org.springframework.security.oauth2.provider.code.AuthorizationRequestHolder)
	 */
	@Override
	protected void store(String code, AuthorizationRequestHolder authentication) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices#remove(java.lang.String)
	 */
	@Override
	protected AuthorizationRequestHolder remove(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
