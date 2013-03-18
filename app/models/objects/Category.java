package models.objects;

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

	public Category(String code, String label) {
		super(code, label);
	}
	
	//keep it up for backward-compatibility
	public Category(BasicDBObject basicDBObject){
		super(basicDBObject);
	}

}
