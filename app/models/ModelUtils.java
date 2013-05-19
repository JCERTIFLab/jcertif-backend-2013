package models;

import java.util.ArrayList;
import java.util.List;

import models.exception.JCertifException;

import com.mongodb.BasicDBObject;

/**
 * @author Martial SOMDA
 *
 */
public class ModelUtils {

	private ModelUtils(){	
	}
	
	public static <T extends Model> T instanciate (Class<T> clazz, BasicDBObject dbObject){
		T object = null;
		
		try {
			object = clazz.getConstructor(BasicDBObject.class).newInstance(dbObject);
		} catch (Exception e) {
			throw new JCertifException(clazz,e.getMessage());
		}
		
		return object;
	}
	
	public static <T extends Model> List<T> instanciate (Class<T> clazz, List<BasicDBObject> dbObjects){
		List<T> objectsList = new ArrayList<T>();
		T object = null;
		
		for (BasicDBObject dbObject : dbObjects) {
			object  = instanciate(clazz, dbObject);
			if(null != object){
				objectsList.add(object);
			}
		}
		
		return objectsList;
	}
}
