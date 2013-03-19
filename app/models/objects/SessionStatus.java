package models.objects;

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

}
