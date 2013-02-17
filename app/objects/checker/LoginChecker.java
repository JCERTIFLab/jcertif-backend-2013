package objects.checker;

import com.mongodb.BasicDBObject;
import exception.JCertifException;
import objects.Login;
import util.Tools;

public class LoginChecker extends Checker{

    public void check(BasicDBObject objectToCheck) throws JCertifException{

        if(null==objectToCheck){
            throw new JCertifException(this, "Object cannot be null");
        }

        Login login = new Login(objectToCheck);

        if(Tools.isBlankOrNull(login.getEmail())){
            throw new JCertifException(this, "Email cannot be empty or null");
        }

        if(Tools.isBlankOrNull(login.getPassword())) {
            throw new JCertifException(this, "Password cannot be empty or null");
        }
    }
}
