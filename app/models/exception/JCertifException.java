package models.exception;

import com.mongodb.util.JSON;

@SuppressWarnings("serial")
public class JCertifException extends Exception {

	public JCertifException(String message,Exception exception) {
        super(message,exception);
    }
	
    public JCertifException(Object concerned, String message) {
        super(concerned.getClass().getSimpleName() + " : " + message);
    }

    public JCertifException(String message) {
        super(message);
    }

    @Override
    public final String getMessage() {
        return JSON.serialize(super.getMessage());
    }

}
