package objects.checker;

import exception.JCertifException;
import objects.Login;
import util.Tools;

public class LoginChecker extends Checker{

    public void check(Object objectToCheck) throws JCertifException{
        Login login = (Login) objectToCheck;

        if(null==login){
            throw new JCertifException(this, "Object cannot be null");
        }

        if(Tools.isBlankOrNull(login.getEmail())){
            throw new JCertifException(this, "Email cannot be empty or null");
        }

        if(Tools.isBlankOrNull(login.getPassword())) {
            throw new JCertifException(this, "Password cannot be empty or null");
        }
    }
}
