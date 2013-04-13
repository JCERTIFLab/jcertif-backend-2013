package models;

import models.exception.JCertifInvalidRequestException;
import models.util.Constantes;
import models.util.Tools;

import com.mongodb.BasicDBObject;

/**
 * @author Martial SOMDA
 *
 */
public final class CheckerHelper {

	private CheckerHelper(){		
	}
	
	public static void checkNull(BasicDBObject objectToCheck){
		if (null == objectToCheck) {
            throw new JCertifInvalidRequestException("Object cannot be null");
        }
	}
	
	public static void checkEmail(BasicDBObject objectToCheck){
		String email = objectToCheck.getString(Constantes.EMAIL_ATTRIBUTE_NAME);
		if (Tools.isBlankOrNull(email)) {
            throw new JCertifInvalidRequestException("Email cannot be empty or null");
        }
		if (!Tools.isValidEmail(email)) {
            throw new JCertifInvalidRequestException(email + " is not a valid email");
        }
	}
	
	public static void checkLabel(BasicDBObject objectToCheck){
		if (Tools.isBlankOrNull(objectToCheck.getString(Constantes.LABEL_ATTRIBUTE_NAME))) {
            throw new JCertifInvalidRequestException("Label cannot be empty or null");
        }
	}
	
	public static void checkPassword(BasicDBObject objectToCheck){
		if (Tools.isBlankOrNull(objectToCheck.getString(Constantes.PASSWORD_ATTRIBUTE_NAME))) {
            throw new JCertifInvalidRequestException("Password cannot be empty or null");
        }
	}
	
	public static void checkPassword(String oldPassword, String newPassword, boolean checkTwo){
        boolean isPasswordValid = false;
		if(checkTwo){

            if (oldPassword==null || newPassword==null) {
            	isPasswordValid = false;
            }

            if(oldPassword.equals(newPassword)){
            	isPasswordValid = false;
            }

            isPasswordValid = oldPassword.length() >= Constantes.PASSWORD_MIN_LENGTH && newPassword.length() >= Constantes.PASSWORD_MIN_LENGTH;
        }else{
        	isPasswordValid = oldPassword!=null && oldPassword.length() >= Constantes.PASSWORD_MIN_LENGTH;
        }
		
		if(!isPasswordValid){
			throw new JCertifInvalidRequestException("Password does not match policy (minimum length : "
                            + Constantes.PASSWORD_MIN_LENGTH + " )");
		}
    }
	
	public static void checkId(BasicDBObject objectToCheck){
		String id = objectToCheck.getString(Constantes.ID_ATTRIBUTE_NAME);
    	
    	if (Tools.isBlankOrNull(id)) {
    		throw new JCertifInvalidRequestException("Id cannot be empty or null");
        }
    	
    	if (Tools.isNotValidNumber(id)) {
    		throw new JCertifInvalidRequestException("Id must be a valid number");
        }
	}
}
