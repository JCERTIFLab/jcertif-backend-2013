package models.exception;

import play.mvc.Http;
import controllers.JcertifHttpMapping;

/**
 * <p>Exception spécifique aux requetes HTTP mal formées.</p>
 * 
 * @author Martial SOMDA
 *
 */
@SuppressWarnings("serial")
@JcertifHttpMapping(status=Http.Status.BAD_REQUEST)
public class JCertifInvalidRequestException extends JCertifException{

	public JCertifInvalidRequestException(Object concerned, String message) {
        super(concerned,message);
    }
	
	public JCertifInvalidRequestException(String message) {
		super(message);
	}
}
