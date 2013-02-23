package objects.access;

import com.mongodb.BasicDBObject;
import exception.JCertifException;
import objects.Speaker;
import objects.checker.SpeakerChecker;
import util.Constantes;

public class SpeakerDB extends JCertifObjectDB {

    public static SpeakerDB speakerDB = new SpeakerDB();

    public SpeakerDB(){
        super(Constantes.JCERTIFBACKEND_COLLECTIONNAME_SPEAKER, new SpeakerChecker());
    }

    public boolean add(Speaker speaker) throws JCertifException {
        return add(speaker.toBasicDBObject());
    }

    public boolean remove(Speaker speaker) throws JCertifException {
        return remove(speaker.toBasicDBObject(), "email");
    }

    public boolean update(Speaker speaker) throws JCertifException {
        return update(speaker.toBasicDBObject(), "email");
    }

    public Speaker get(String email) throws JCertifException {
        BasicDBObject dbObject = get("email", email);
        Speaker speaker = null;
        if(null!=dbObject) speaker = new Speaker(dbObject);
        return speaker;
    }
}
