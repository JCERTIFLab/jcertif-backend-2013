package models.objects.checker;

import com.mongodb.BasicDBObject;

import models.exception.JCertifDuplicateObjectException;
import models.exception.JCertifInvalidRequestException;
import models.objects.Login;
import models.objects.access.LoginDB;
import models.util.Tools;

public class LoginChecker extends Checker {

    @Override
    public final void check(BasicDBObject objectToCheck) {

        if (null == objectToCheck) {
            throw new JCertifInvalidRequestException(this, "Object cannot be null");
        }

        Login login = new Login(objectToCheck);

        if (Tools.isBlankOrNull(login.getEmail())) {
            throw new JCertifInvalidRequestException(this, "Email cannot be empty or null");
        }

        if (!Tools.isValidEmail(login.getEmail())) {
            throw new JCertifInvalidRequestException(this, login.getEmail() + " is not a valid email");
        }

        if (Tools.isBlankOrNull(login.getPassword())) {
            throw new JCertifInvalidRequestException(this, "Password cannot be empty or null");
        }
    }

    @Override
    public final void updateCheck(BasicDBObject objectToCheck) {
    }

    @Override
    public final void deleteCheck(BasicDBObject objectToCheck) {
    }

    @Override
    public void addCheck(BasicDBObject objectToCheck) {
        BasicDBObject dbObject = LoginDB.getInstance().get("email", objectToCheck.getString("email"));
        if (null != dbObject) {
            throw new JCertifDuplicateObjectException(this, objectToCheck.getString("email") + " already exists");
        }
    }
}
