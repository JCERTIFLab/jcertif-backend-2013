package models.exception;

/**
 * <p>Exception spécifique aux recherches d'objets inexistant en base.</p>
 * 
 * @author Martial SOMDA
 *
 */
@SuppressWarnings("serial")
public class JCertifObjectNotFoundException extends JCertifException{

	public JCertifObjectNotFoundException(Object concerned, String message) {
		super(concerned,message);
    }
	
	public JCertifObjectNotFoundException(String message) {
		super(message);
	}

}
