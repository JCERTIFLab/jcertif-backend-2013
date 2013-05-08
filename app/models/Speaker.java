package models;

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
    
    public static List<Speaker> findAll(){
		return getFinder().findAll(Speaker.class);
	}
    
    public static List<Speaker> findAll(String version){
		return getFinder().findAll(Speaker.class, version);
	}
}
