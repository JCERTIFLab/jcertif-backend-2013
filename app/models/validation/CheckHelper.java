package models.validation;

import models.Model;
import models.exception.JCertifInvalidRequestException;
import models.exception.JCertifStaleObjectException;
import models.util.Constantes;

/**
 * @author Martial SOMDA
 *
 */
public final class CheckHelper {

	private CheckHelper(){		
	}
	
	public static void checkPassword(String oldPassword, String newPassword, boolean checkTwo){
        boolean isPasswordValid = false;
        
		if(checkTwo){
			
			boolean arePasswordsNotNull = oldPassword != null && newPassword != null;
            if (arePasswordsNotNull
            		&& !oldPassword.equals(newPassword)
            		&& newPassword.length() >= Constantes.PASSWORD_MIN_LENGTH) {
            	isPasswordValid = true;
            }
            
        }else{
        	isPasswordValid = oldPassword !=null && oldPassword.length() >= Constantes.PASSWORD_MIN_LENGTH;
        }
		
		if(!isPasswordValid){
			throw new JCertifInvalidRequestException("Password does not match policy (minimum length : "
                            + Constantes.PASSWORD_MIN_LENGTH + " )");
		}
    }
	
	public static void checkVersion(Model model, String version){
		if(!model.getVersion().equals(version)){
			throw new JCertifStaleObjectException(model.getClass(), model.toString());
		}
	}
}
