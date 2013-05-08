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
	
	public Title(BasicDBObject basicDBObject){
		super(basicDBObject);
	}

	public static Title find(String titleStr){
    	return getFinder().find(Title.class, Constantes.LABEL_ATTRIBUTE_NAME, titleStr);
	}
	
	public static List<Title> findAll(){
		return getFinder().findAll(Title.class);
	}
}
