package models.objects.checker;

import com.mongodb.BasicDBObject;
import models.exception.JCertifException;
import models.objects.access.SessionStatusDB;

public class SessionStatusChecker extends ReferentielChecker {

    @Override
    public void deleteCheck(BasicDBObject objectToCheck) throws JCertifException {
        BasicDBObject dbObject = SessionStatusDB.getInstance().get("label", objectToCheck.getString("label"));
        if (null == dbObject) {
            throw new JCertifException(this, "Session Status '" + objectToCheck.getString("label") + "' does not exist");
        }
    }

    @Override
    public void addCheck(BasicDBObject objectToCheck) throws JCertifException {
        BasicDBObject dbObject = SessionStatusDB.getInstance().get("label", objectToCheck.getString("label"));
        if (null != dbObject) {
            throw new JCertifException(this, "Session Status '" + objectToCheck.getString("label") + "' already exists");
        }
    }
}
