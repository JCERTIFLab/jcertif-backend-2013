package models.exception;

/**
 * <p>Exception spécifique aux accès non autorisés.</p>
 * 
 * @author Martial SOMDA
 *
 */
@SuppressWarnings("serial")
public class JCertifResourceAccessException extends JCertifException{

	public JCertifResourceAccessException(Object concerned, String message) {
		super(concerned,message);
    }
	
	public JCertifResourceAccessException(String message) {
		super(message);
	}

}
