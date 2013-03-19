package models.objects.checker;

import com.mongodb.BasicDBObject;
import models.exception.JCertifException;
import models.objects.Login;
import models.objects.access.LoginDB;
import models.util.Tools;

public class LoginChecker extends Checker {

    @Override
    public final void check(BasicDBObject objectToCheck) throws JCertifException {

        if (null == objectToCheck) {
            throw new JCertifException(this, "Object cannot be null");
        }

        Login login = new Login(objectToCheck);

        if (Tools.isBlankOrNull(login.getEmail())) {
            throw new JCertifException(this, "Email cannot be empty or null");
        }

        if (!Tools.isValidEmail(login.getEmail())) {
            throw new JCertifException(this, login.getEmail() + " is not a valid email");
        }

        if (Tools.isBlankOrNull(login.getPassword())) {
            throw new JCertifException(this, "Password cannot be empty or null");
        }
    }

    @Override
    public final void updateCheck(BasicDBObject objectToCheck) throws JCertifException {
    }

    @Override
    public final void deleteCheck(BasicDBObject objectToCheck) throws JCertifException {
    }

    @Override
    public void addCheck(BasicDBObject objectToCheck) throws JCertifException {
        BasicDBObject dbObject = LoginDB.getInstance().get("email", objectToCheck.getString("email"));
        if (null != dbObject) {
            throw new JCertifException(this, objectToCheck.getString("email") + " already exists");
        }
    }
}
