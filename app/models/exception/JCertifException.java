package models.exception;

import play.mvc.Http;

import com.mongodb.util.JSON;

import controllers.JcertifHttpMapping;

@SuppressWarnings("serial")
@JcertifHttpMapping(status=Http.Status.INTERNAL_SERVER_ERROR)
public class JCertifException extends RuntimeException {

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
