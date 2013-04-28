package models.exception;

import play.mvc.Http;

/**
 * <p>Exception spécifique aux requetes HTTP mal formées.</p>
 * 
 * @author Martial SOMDA
 *
 */
@SuppressWarnings("serial")
@JCertifExceptionMapping(status=Http.Status.BAD_REQUEST)
public class JCertifInvalidRequestException extends JCertifException{

	public JCertifInvalidRequestException(Class<?> concerned, String message) {
        super(concerned,message);
    }
	
	public JCertifInvalidRequestException(String message) {
		super(message);
	}
}
