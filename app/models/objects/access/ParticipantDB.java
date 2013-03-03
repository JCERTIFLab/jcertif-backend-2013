package models.objects.access;

import com.mongodb.BasicDBObject;
import models.exception.JCertifException;
import models.objects.Participant;
import models.objects.checker.ParticipantChecker;
import models.util.Constantes;
import models.util.crypto.CryptoUtil;

public class ParticipantDB extends JCertifObjectDB<Participant>{

    private static ParticipantDB instance;

    private ParticipantDB() {
        super(Constantes.JCERTIFBACKEND_COLLECTIONNAME_PARTICIPANT, new ParticipantChecker());
    }
    public static ParticipantDB getInstance(){
    	if(instance==null){
    		instance=new ParticipantDB();
    	}
        return instance;
    }

    public boolean add(Participant participant) throws JCertifException {
        participant.setPassword(CryptoUtil.getSaltedPassword(participant.getPassword().getBytes()));
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
