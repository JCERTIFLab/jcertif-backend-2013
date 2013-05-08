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
	
	public SessionStatus(BasicDBObject basicDBObject){
		super(basicDBObject);
	}
	
	public static SessionStatus find(String status){
    	return getFinder().find(SessionStatus.class, Constantes.LABEL_ATTRIBUTE_NAME, status);
	}
	
	public static List<SessionStatus> findAll(){
		return getFinder().findAll(SessionStatus.class);
	}
}
