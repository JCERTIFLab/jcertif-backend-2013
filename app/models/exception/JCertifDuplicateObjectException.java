package models.exception;

import play.mvc.Http;
import controllers.JcertifHttpMapping;

/**
 * <p>Exception spécifique aux ajouts de doublons en base.</p>
 * 
 * @author Martial SOMDA
 *
 */
@SuppressWarnings("serial")
@JcertifHttpMapping(status=Http.Status.CONFLICT)
public class JCertifDuplicateObjectException extends JCertifException{

	public JCertifDuplicateObjectException(Object concerned, String message) {
        super(concerned,message);
    }
	
	public JCertifDuplicateObjectException(String message) {
		super(message);
	}
}
