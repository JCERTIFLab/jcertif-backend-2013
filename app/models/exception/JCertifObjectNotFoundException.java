package models.exception;

import play.mvc.Http;
import controllers.JcertifHttpMapping;

/**
 * <p>Exception spécifique aux recherches d'objets inexistant en base.</p>
 * 
 * @author Martial SOMDA
 *
 */
@SuppressWarnings("serial")
@JcertifHttpMapping(status=Http.Status.NOT_FOUND)
public class JCertifObjectNotFoundException extends JCertifException{

	public JCertifObjectNotFoundException(Object concerned, String message) {
		super(concerned,message);
    }
	
	public JCertifObjectNotFoundException(String message) {
		super(message);
	}

}
