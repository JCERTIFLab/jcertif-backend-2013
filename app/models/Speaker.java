package models;

import java.util.List;

import models.util.Constantes;

import com.mongodb.BasicDBObject;

public class Speaker extends Member {
	
    public Speaker(BasicDBObject basicDBObject) {
        super(basicDBObject);
    }
    
    public static Speaker find(String email){
    	Speaker speaker = null;
    	
    	BasicDBObject dbObject = new Model.Finder().find(Speaker.class, Constantes.EMAIL_ATTRIBUTE_NAME, email);
    	
    	if(null != dbObject){
    		speaker = new Speaker(dbObject);
    	}
		return speaker; 
	}
    
    public static List<BasicDBObject> findAll(){
		return new Model.Finder().findAll(Speaker.class);
	}

}
