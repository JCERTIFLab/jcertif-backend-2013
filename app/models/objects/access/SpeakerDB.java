package models.objects.access;

import com.mongodb.BasicDBObject;
import models.objects.Speaker;
import models.objects.checker.SpeakerChecker;
import models.util.Constantes;

public final class SpeakerDB extends JCertifObjectDB<Speaker> {

    private static final SpeakerDB INSTANCE = new SpeakerDB();

    public SpeakerDB() {
        super(Constantes.COLLECTION_SPEAKER, new SpeakerChecker());
    }

    
    public static SpeakerDB getInstance(){
        return INSTANCE;
    }
    public boolean add(Speaker speaker) {
        return add(speaker.toBasicDBObject());
    }

    public boolean remove(Speaker speaker) {
        return remove(speaker.toBasicDBObject(), "email");
    }

    public boolean save(Speaker speaker) {
        return save(speaker.toBasicDBObject(), "email");
    }

    public Speaker get(String email) {
        BasicDBObject dbObject = get("email", email);
        Speaker speaker = null;
        if (null != dbObject){
            speaker = new Speaker(dbObject);
        }
        return speaker;
    }
}
