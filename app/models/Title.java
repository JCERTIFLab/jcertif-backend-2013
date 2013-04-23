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
public class Title extends Referentiel {
	
	public Title(String label) {
		super(label);
	}
	
	public Title(BasicDBObject basicDBObject){
		super(basicDBObject);
	}

	public static Title find(String titleStr){
		Title title = null;
    	
    	BasicDBObject dbObject = getFinder().find(Title.class, Constantes.LABEL_ATTRIBUTE_NAME, titleStr);
    	
    	if(null != dbObject){
    		title = new Title(dbObject);
    	}
		return title; 
	}
	
	public static List<BasicDBObject> findAll(){
		return getFinder().findAll(Title.class);
	}
}
