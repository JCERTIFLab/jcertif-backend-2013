package models.objects.checker;

import com.mongodb.BasicDBObject;
import models.exception.JCertifException;
import models.objects.SessionStatus;
import models.objects.access.SessionStatusDB;
import models.util.Tools;

public class SessionStatusChecker extends Checker {

    @Override
    public void check(BasicDBObject objectToCheck) throws JCertifException {

        if (null == objectToCheck) {
            throw new JCertifException(this, "Object cannot be null");
        }

        SessionStatus sessionStatus = new SessionStatus(objectToCheck);

        if (Tools.isBlankOrNull(sessionStatus.getLabel())) {
            throw new JCertifException(this, "Label cannot be empty or null");
        }
    }

    @Override
    public void updateCheck(BasicDBObject objectToCheck) throws JCertifException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteCheck(BasicDBObject objectToCheck) throws JCertifException {
        BasicDBObject dbObject = SessionStatusDB.getInstance().get("label", objectToCheck.getString("label"));
        if (null == dbObject) {
            throw new JCertifException(this, "Session Status \"" + objectToCheck.getString("label") + "\" does not exist");
        }
    }

    @Override
    public void addCheck(BasicDBObject objectToCheck) throws JCertifException {
        BasicDBObject dbObject = SessionStatusDB.getInstance().get("label", objectToCheck.getString("label"));
        if (null != dbObject) {
            throw new JCertifException(this, "Session Status \"" + objectToCheck.getString("label") + "\" already exists");
        }
    }
}
