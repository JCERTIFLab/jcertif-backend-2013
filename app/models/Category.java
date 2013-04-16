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
	
	public Category(String label) {
		super(label);
	}
	
	//keep it up for backward-compatibility
	public Category(BasicDBObject basicDBObject){
		super(basicDBObject);
	}
	
	public static Category find(String categoryStr){
		Category category = null;
    	
    	BasicDBObject dbObject = finder.find(Category.class, Constantes.LABEL_ATTRIBUTE_NAME, categoryStr);
    	
    	if(null != dbObject){
    		category = new Category(dbObject);
    	}
		return category; 
	}
	
	public static List<BasicDBObject> findAll(){
		return finder.findAll(Category.class);
	}

}
