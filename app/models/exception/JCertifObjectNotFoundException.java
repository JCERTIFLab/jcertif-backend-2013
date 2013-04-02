package models.exception;

import play.mvc.Http;

/**
 * <p>Exception spécifique aux recherches d'objets inexistant en base.</p>
 * 
 * @author Martial SOMDA
 *
 */
@SuppressWarnings("serial")
@JCertifExceptionMapping(status=Http.Status.NOT_FOUND)
public class JCertifObjectNotFoundException extends JCertifException{

	public JCertifObjectNotFoundException(Object concerned, String message) {
		super(concerned,message);
    }
	
	public JCertifObjectNotFoundException(String message) {
		super(message);
	}

}
