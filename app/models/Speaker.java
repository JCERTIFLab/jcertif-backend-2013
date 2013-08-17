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
    
    public static List<SpeakerInfo> findAllInfos(boolean filter){
		return adapt(findAll(filter));
	}
    
    public static List<Speaker> findAll(boolean filter){
    	List<Speaker> speakers = getFinder().findAll(Speaker.class);
    	if(filter){
    		speakers = filterApprovedSpeakers(speakers);
    	}
		return speakers;
	}

    public static List<SpeakerInfo> findAllInfos(String version, boolean filter){
		return adapt(findAll(version, filter));
	}
    
    public static List<Speaker> findAll(String version, boolean filter){
    	List<Speaker> speakers = getFinder().findAll(Speaker.class, version);
    	if(filter){
    		speakers = filterApprovedSpeakers(speakers);
    	}
		return speakers;
	}
    
    private static List<Speaker> filterApprovedSpeakers(List<Speaker> speakers) {
    	List<Speaker> approvedSpeakers = new ArrayList<Speaker>();
    	for(Speaker speaker : speakers){
    		if(Session.findBySpeaker(speaker.getEmail(), true).size() > 0){
    			approvedSpeakers.add(speaker);
    		}
    	}
		return approvedSpeakers;
	}

	public static List<SpeakerInfo> adapt(List<Speaker> speakers){
		List<SpeakerInfo> speakersInfo = new ArrayList<SpeakerInfo>();
		for(Speaker speaker : speakers){
			speakersInfo.add(new SpeakerInfo(speaker));
		}
		return speakersInfo;
	}
}
