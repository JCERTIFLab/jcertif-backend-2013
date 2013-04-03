package models.objects;

import models.objects.access.JCertifObjectDB;
import models.objects.access.SpeakerDB;

import com.mongodb.BasicDBObject;

public class Speaker extends Member {

    public Speaker(BasicDBObject basicDBObject) {
        super(basicDBObject);
    }

    @Override
	@SuppressWarnings("unchecked")
	protected JCertifObjectDB<Speaker> getDBObject() {
		return SpeakerDB.getInstance();
	}
}
