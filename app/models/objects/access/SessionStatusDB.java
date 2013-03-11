package models.objects.access;

import com.mongodb.BasicDBObject;
import models.exception.JCertifException;
import models.objects.SessionStatus;
import models.objects.checker.SessionStatusChecker;
import models.util.Constantes;

public class SessionStatusDB extends JCertifObjectDB<SessionStatus> {

    private static SessionStatusDB instance;

    public SessionStatusDB() {
        super(Constantes.COLLECTION_SESSION_STATUS,
                new SessionStatusChecker());
    }

    public static SessionStatusDB getInstance() {
        if (instance == null) {
            instance = new SessionStatusDB();
        }
        return instance;
    }

    public boolean add(SessionStatus sessionStatus) throws JCertifException {
        return super.add(sessionStatus.toBasicDBObject());
    }

    public boolean remove(SessionStatus sessionStatus) throws JCertifException {
        return remove(sessionStatus.toBasicDBObject(), "label");
    }

    public boolean save(SessionStatus sessionStatus) throws JCertifException {
        return save(sessionStatus.toBasicDBObject(), "label");
    }

    public SessionStatus get(String label) throws JCertifException {
        BasicDBObject dbObject = get("label", label);
        SessionStatus sessionStatus = null;
        if (null != dbObject)
            sessionStatus = new SessionStatus(dbObject);
        return sessionStatus;
    }

}
