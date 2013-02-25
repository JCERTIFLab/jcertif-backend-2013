package objects.access;

import com.mongodb.BasicDBObject;
import exception.JCertifException;
import objects.Participant;
import objects.checker.ParticipantChecker;
import util.Constantes;

public class ParticipantDB extends JCertifObjectDB{

    public static ParticipantDB participantDB = new ParticipantDB();

    public ParticipantDB() {
        super(Constantes.JCERTIFBACKEND_COLLECTIONNAME_PARTICIPANT, new ParticipantChecker());
    }

    public boolean add(Participant participant) throws JCertifException {
        return add(participant.toBasicDBObject());
    }

    public boolean remove(Participant participant) throws JCertifException {
        return remove(participant.toBasicDBObject(), "email");
    }

    public boolean save(Participant participant) throws JCertifException {
        return save(participant.toBasicDBObject(), "email");
    }

    public Participant get(String email) throws JCertifException {
        BasicDBObject dbObject = get("email", email);
        Participant participant = null;
        if (null != dbObject) participant = new Participant(dbObject);
        return participant;
    }
}
