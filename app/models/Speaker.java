package models;

import java.util.List;

import models.util.Constantes;

import com.mongodb.BasicDBObject;

public class Speaker extends Member {
	
    public Speaker(BasicDBObject basicDBObject) {
        super(basicDBObject);
    }
    
    public static Speaker find(String email){
    	
    	return buildSpeaker(getFinder().find(Speaker.class, Constantes.EMAIL_ATTRIBUTE_NAME, email));
	}
    
    /*public static Speaker findByName(String name){
  	
    	return buildSpeaker(getFinder().find(Speaker.class, Constantes.NAME_ATTRIBUTE_NAME, name));
	}*/
    
    private static Speaker buildSpeaker(BasicDBObject dbObject){
    	Speaker speaker = null;
    	
    	if(null != dbObject){
    		speaker = new Speaker(dbObject);
    	}
		return speaker; 
    }
    
    public static List<BasicDBObject> findAll(){
		return getFinder().findAll(Speaker.class);
	}
}
