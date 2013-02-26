package objects.access;

import com.mongodb.BasicDBObject;
import exception.JCertifException;
import objects.Speaker;
import objects.checker.SpeakerChecker;
import util.Constantes;

public class SpeakerDB extends JCertifObjectDB<Speaker> {

    private  static SpeakerDB instance;

    public SpeakerDB() {
        super(Constantes.JCERTIFBACKEND_COLLECTIONNAME_SPEAKER, new SpeakerChecker());
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
