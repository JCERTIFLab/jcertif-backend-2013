package models.exception;

import java.text.MessageFormat;

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

	public static final String MESSAGE = "'{0}' doesn't exists";
	
	public JCertifObjectNotFoundException(Class<?> classConcerned, String objectReference) {
		super(classConcerned,MessageFormat.format(MESSAGE, objectReference));
    }
	
	public JCertifObjectNotFoundException(String message) {
		super(message);
	}

}
