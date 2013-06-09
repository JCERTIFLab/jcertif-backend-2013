package models.webapp;

import play.Application;
import securesocial.core.PasswordInfo;
import securesocial.core.java.BasePasswordHasher;
import securesocial.core.providers.utils.PasswordHasher;

/**
 * <p>A no-operation implementation of {@link PasswordHasher}. Passwords transmitted to Backend are not encrypted</p>
 * 
 * @author Martial SOMDA
 *
 */
public class NOpPasswordHasher extends BasePasswordHasher {

	public static final String ID = "nop";
	
	public NOpPasswordHasher(Application application) {
		super(application);
	}

	/* (non-Javadoc)
	 * @see securesocial.core.Registrable#id()
	 */
	@Override
	public String id() {
		return NOpPasswordHasher.ID;
	}

	/* (non-Javadoc)
	 * @see securesocial.core.providers.utils.PasswordHasher#hash(java.lang.String)
	 */
	@Override
	public PasswordInfo hash(String plainPassword) {
		return new PasswordInfo(id(), plainPassword, scala.Option.<String>apply(null));
	}

	/* (non-Javadoc)
	 * @see securesocial.core.providers.utils.PasswordHasher#matches(securesocial.core.PasswordInfo, java.lang.String)
	 */
	@Override
	public boolean matches(PasswordInfo passwordInfo, String suppliedPassword) {
		return true;
	}

}
