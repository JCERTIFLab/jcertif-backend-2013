package models.exception;

import com.mongodb.util.JSON;

public class JCertifException extends Exception {

    public JCertifException(Object concerned, String message) {
        super(concerned.getClass().getSimpleName() + " : " + message);
    }

    public JCertifException(String message) {
        super(message);
    }

    public String getMessage() {
        return JSON.serialize(super.getMessage());
    }

}
