package models.objects.access;

import com.mongodb.BasicDBObject;
import models.objects.Participant;
import models.objects.checker.ParticipantChecker;
import models.util.Constantes;

public final class ParticipantDB extends JCertifObjectDB<Participant>{

    private static final ParticipantDB INSTANCE = new ParticipantDB();

    private ParticipantDB() {
        super(Constantes.COLLECTION_PARTICIPANT, new ParticipantChecker());
    }
    public static ParticipantDB getInstance(){
        return INSTANCE;
    }

    public boolean add(Participant participant) {
        return add(participant.toBasicDBObject());
    }

    public boolean remove(Participant participant) {
        return remove(participant.toBasicDBObject(), Constantes.LABEL_ATTRIBUTE_NAME);
    }

    public boolean save(Participant participant) {
        return save(participant.toBasicDBObject(), Constantes.LABEL_ATTRIBUTE_NAME);
    }

    public Participant get(String email) {
        BasicDBObject dbObject = get(Constantes.LABEL_ATTRIBUTE_NAME, email);
        Participant participant = null;
        if (null != dbObject){
            participant = new Participant(dbObject);
        }
        return participant;
    }
}
