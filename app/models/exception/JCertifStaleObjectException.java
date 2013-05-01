package models.exception;

import java.text.MessageFormat;

import play.mvc.Http;

/**
 * <p>Exception spécifique aux mises à jour concurrentes.</p>
 * 
 * @author Martial SOMDA
 *
 */
@SuppressWarnings("serial")
@JCertifExceptionMapping(status=Http.Status.CONFLICT)
public class JCertifStaleObjectException extends JCertifException {

public static final String MESSAGE = "Object {0} has been modified since your last update";
	
	public JCertifStaleObjectException(Class<?> classConcerned, String objectReference) {
		super(classConcerned,MessageFormat.format(MESSAGE, objectReference));
    }
	
	public JCertifStaleObjectException(String message) {
		super(message);
	}
}
