package models;

import java.util.ArrayList;
import java.util.List;

import models.util.Constantes;

import com.mongodb.BasicDBObject;

/**
 * <p>Objet metier représentant un présentateur JCertif Conference.</p>
 *
 */
public class Speaker extends Member {
	
    public Speaker(BasicDBObject basicDBObject) {
        super(basicDBObject);
    }
    
    public static Speaker find(String email){
    	return getFinder().find(Speaker.class, Constantes.EMAIL_ATTRIBUTE_NAME, email);
	}
    
    public static List<SpeakerInfo> findAll(){
		return adapt(getFinder().findAll(Speaker.class));
	}
    
    public static List<SpeakerInfo> findAll(String version){
		return adapt(getFinder().findAll(Speaker.class, version));
	}
    
    public static List<SpeakerInfo> adapt(List<Speaker> speakers){
		List<SpeakerInfo> speakersInfo = new ArrayList<SpeakerInfo>();
		for(Speaker speaker : speakers){
			speakersInfo.add(new SpeakerInfo(speaker));
		}
		return speakersInfo;
	}
}
