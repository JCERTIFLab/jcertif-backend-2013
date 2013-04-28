package models.exception;

import java.text.MessageFormat;

import play.mvc.Http;

/**
 * <p>Exception spécifique aux ajouts de doublons en base.</p>
 * 
 * @author Martial SOMDA
 *
 */
@SuppressWarnings("serial")
@JCertifExceptionMapping(status=Http.Status.CONFLICT)
public class JCertifDuplicateObjectException extends JCertifException{

	public static final String MESSAGE = "'{0}' already exists";
	
	public JCertifDuplicateObjectException(Class<?> classConcerned, String objectReference) {
        super(classConcerned,MessageFormat.format(MESSAGE, objectReference));
    }
	
	public JCertifDuplicateObjectException(String message) {
		super(message);
	}
}
