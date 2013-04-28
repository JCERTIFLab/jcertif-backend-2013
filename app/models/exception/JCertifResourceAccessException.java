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

	public static final String MESSAGE = "Access denied";
	
	public JCertifResourceAccessException(String message,Exception exception) {
        super(message,exception);
    }
	
	public JCertifResourceAccessException(Class<?> concerned, String message) {
		super(concerned,message);
    }
	
	public JCertifResourceAccessException(String message) {
		super(message);
	}
	
	public JCertifResourceAccessException() {
		super(MESSAGE);
    }

}
