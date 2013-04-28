package models.exception;

import play.mvc.Http;

import com.mongodb.util.JSON;


@SuppressWarnings("serial")
@JCertifExceptionMapping(status=Http.Status.INTERNAL_SERVER_ERROR)
public class JCertifException extends RuntimeException {

	public JCertifException(String message,Exception exception) {
        super(message,exception);
    }
	
    public JCertifException(Class<?> concerned, String message) {
        super(concerned.getSimpleName() + " : " + message);
    }

    public JCertifException(String message) {
        super(message);
    }

    @Override
    public final String getMessage() {
        return JSON.serialize(super.getMessage());
    }

}
