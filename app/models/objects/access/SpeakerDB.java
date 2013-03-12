package models.objects.access;

import com.mongodb.BasicDBObject;
import models.exception.JCertifException;
import models.objects.Speaker;
import models.objects.checker.SpeakerChecker;
import models.util.Constantes;

public class SpeakerDB extends JCertifObjectDB<Speaker> {

    private  static SpeakerDB instance;

    public SpeakerDB() {
        super(Constantes.COLLECTION_SPEAKER, new SpeakerChecker());
    }

    
    public static SpeakerDB getInstance(){
    	if(instance==null){
    		instance=new SpeakerDB();
    	}
        return instance;
    }
    public boolean add(Speaker speaker) throws JCertifException {
        return add(speaker.toBasicDBObject());
    }

    public boolean remove(Speaker speaker) throws JCertifException {
        return remove(speaker.toBasicDBObject(), "email");
    }

    public boolean save(Speaker speaker) throws JCertifException {
        return save(speaker.toBasicDBObject(), "email");
    }

    public Speaker get(String email) throws JCertifException {
        BasicDBObject dbObject = get("email", email);
        Speaker speaker = null;
        if (null != dbObject) speaker = new Speaker(dbObject);
        return speaker;
    }
}
