package models.exception;

/**
 * <p>Exception spécifique aux requetes HTTP mal formées.</p>
 * 
 * @author Martial SOMDA
 *
 */
@SuppressWarnings("serial")
public class JCertifInvalidRequestException extends JCertifException{

	public JCertifInvalidRequestException(Object concerned, String message) {
        super(concerned,message);
    }
	
	public JCertifInvalidRequestException(String message) {
		super(message);
	}
}
