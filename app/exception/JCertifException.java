package exception;

public class JCertifException extends Exception{

    public JCertifException(Object concerned, String message){
        super(concerned.getClass().getSimpleName() + " : " + message);
    }

}