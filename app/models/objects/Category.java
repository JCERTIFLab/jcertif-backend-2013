package models.objects;

import models.objects.access.CategoryDB;
import models.objects.access.JCertifObjectDB;

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
	
	@Override
	@SuppressWarnings("unchecked")
	protected JCertifObjectDB<Category> getDBObject() {
		return CategoryDB.getInstance();
	}

}
