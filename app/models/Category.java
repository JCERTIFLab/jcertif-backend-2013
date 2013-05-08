package models;

import java.util.List;

import models.util.Constantes;

import com.mongodb.BasicDBObject;

/**
 * <p>Objet metier representant la catégorie d'appartenance d'une sessoin.</p>
 * Ex : 
 * <ul>
 * <li>Android</li>
 * <li>Java</li>
 * <li>Web Development</li>
 * <li>...</li>
 * </ul>
 * 
 * @author Martial SOMDA
 *
 */
public class Category extends Referentiel {
	
	public Category(BasicDBObject basicDBObject){
		super(basicDBObject);
	}
	
	public static Category find(String categoryStr){   	
    	return getFinder().find(Category.class, Constantes.LABEL_ATTRIBUTE_NAME, categoryStr);	
	}
	
	public static List<Category> findAll(){
		return getFinder().findAll(Category.class);
	}
	
	public static List<Category> findAll(String version){
		return getFinder().findAll(Category.class, version);
	}

}
