package models.exception;

import play.mvc.Http;
import controllers.JcertifHttpMapping;

/**
 * <p>Exception spécifique aux accès non autorisés.</p>
 * 
 * @author Martial SOMDA
 *
 */
@SuppressWarnings("serial")
@JcertifHttpMapping(status=Http.Status.FORBIDDEN)
public class JCertifResourceAccessException extends JCertifException{

	public JCertifResourceAccessException(String message,Exception exception) {
        super(message,exception);
    }
	
	public JCertifResourceAccessException(Object concerned, String message) {
		super(concerned,message);
    }
	
	public JCertifResourceAccessException(String message) {
		super(message);
	}

}
