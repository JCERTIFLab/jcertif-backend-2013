package controllers.oauth2;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * <p>Objet encapsulant une authentification pour un utilisateur.
 * Il porte notamment l'identifiant de l'utilisateur ainsi que l'information
 * sur le fait qu'il est été authentifié ou non.</p>
 * 
 * @author Martial SOMDA
 *
 */
@SuppressWarnings("serial")
public class UserAuthentication extends AbstractAuthenticationToken {

	private String principal;
	
	public UserAuthentication(String name, boolean authenticated) {
		super(null);
		setAuthenticated(authenticated);
		principal = name;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.Authentication#getCredentials()
	 */
	@Override
	public Object getCredentials() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.Authentication#getPrincipal()
	 */
	@Override
	public Object getPrincipal() {
		return this.principal;
	}

}
