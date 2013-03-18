package models.exception;

/**
 * <p>Exception spécifique aux ajouts de doublons en base.</p>
 * 
 * @author Martial SOMDA
 *
 */
@SuppressWarnings("serial")
public class JCertifDuplicateObjectException extends JCertifException{

	public JCertifDuplicateObjectException(Object concerned, String message) {
        super(concerned,message);
    }
	
	public JCertifDuplicateObjectException(String message) {
		super(message);
	}
}
