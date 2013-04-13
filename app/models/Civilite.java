package models;

import java.util.List;

import models.util.Constantes;

import com.mongodb.BasicDBObject;

/**
 * <p>Objet metier representant la civilité d'un membre.</p>
 * Ex : 
 * <ul>
 * <li>M.</li>
 * <li>Mme</li>
 * <li>Mlle</li>
 * <li>...</li>
 * </ul>
 * 
 * @author Martial SOMDA
 *
 */
public class Civilite extends Referentiel {
	
	public Civilite(String label) {
		super(label);
	}
	
	public Civilite(BasicDBObject basicDBObject){
		super(basicDBObject);
	}

	public static Civilite find(String civiliteStr){
		Civilite civilite = null;
    	
    	BasicDBObject dbObject = new Model.Finder().find(Civilite.class, Constantes.LABEL_ATTRIBUTE_NAME, civiliteStr);
    	
    	if(null != dbObject){
    		civilite = new Civilite(dbObject);
    	}
		return civilite; 
	}
	
	public static List<BasicDBObject> findAll(){
		return new Model.Finder().findAll(Civilite.class);
	}
}
