package models.exception;

import play.mvc.Http;

/**
 * <p>Exception spécifique aux accès non autorisés.</p>
 * 
 * @author Martial SOMDA
 *
 */
@SuppressWarnings("serial")
@JCertifExceptionMapping(status=Http.Status.FORBIDDEN)
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
