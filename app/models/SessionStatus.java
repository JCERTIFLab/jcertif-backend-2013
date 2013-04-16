package models;

import java.util.List;

import models.util.Constantes;

import com.mongodb.BasicDBObject;

/**
 * <p>Objet metier representant le statut d'une sessoin.</p>
 * Ex : 
 * <ul>
 * <li>Approuvé</li>
 * <li>Brouillon</li>
 * <li>...</li>
 * </ul>
 */
public class SessionStatus extends Referentiel {
	
	public SessionStatus(String label) {
		super(label);
	}
	
	public SessionStatus(BasicDBObject basicDBObject){
		super(basicDBObject);
	}
	
	public static SessionStatus find(String status){
		SessionStatus sessionStatus = null;
    	
    	BasicDBObject dbObject = finder.find(SessionStatus.class, Constantes.LABEL_ATTRIBUTE_NAME, status);
    	
    	if(null != dbObject){
    		sessionStatus = new SessionStatus(dbObject);
    	}
		return sessionStatus;
	}
	
	public static List<BasicDBObject> findAll(){
		return finder.findAll(SessionStatus.class);
	}
}
