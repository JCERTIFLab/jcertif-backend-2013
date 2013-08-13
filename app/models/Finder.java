package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import models.database.MongoDB;
import models.util.Constantes;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.QueryBuilder;

/**
 * <p>This class provides static methods to retrieve data from database.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class Finder {


	public String findNextSequence(Class<?> clazz) {
    	int id = Integer.parseInt(MongoDB.getInstance().readNextSequence(
    			Model.getCollectionName(clazz)).getString(Constantes.SEQ_ATTRIBUTE_NAME));
    	
    	if(id < 10){
    		return String.format("%02d", id);
    	}else{
    		return Integer.toString(id);
    	}
    }
	
    public <T extends Model> T find(Class<T> clazz, String keyName, Object keyValue) {
    	
    	T object = null;
    	BasicDBObject dbObject = MongoDB.getInstance().readOne(
    			Model.getCollectionName(clazz), 
    			QueryBuilder.start().put(keyName).is(keyValue)
    			.put(Constantes.DELETED_ATTRIBUTE_NAME).is(Constantes.FALSE).get());
    	
    	if(null != dbObject){
    		object = ModelUtils.instanciate(clazz, dbObject);
    	}
    	return object;
    }
    
    public <T extends Model> T find(Class<T> clazz, Map<String, Object> keyValuePairs) {
    	
    	T object = null;
    	QueryBuilder builder = QueryBuilder.start();
    	for(Entry<String, Object> entry : keyValuePairs.entrySet()){
    		builder.put(entry.getKey()).is(entry.getValue());
    	}
    	BasicDBObject dbObject = MongoDB.getInstance().readOne(
    			Model.getCollectionName(clazz), 
    			builder.put(Constantes.DELETED_ATTRIBUTE_NAME).is(Constantes.FALSE).get());
    	
    	if(null != dbObject){
    		object = ModelUtils.instanciate(clazz, dbObject);
    	}
    	return object;
    }
    
    public <T extends Model> List<T> findAll(Class<T> clazz, String keyName, Object keyValue) {
    	DBCursor dbCursor = MongoDB.getInstance().list(
    			Model.getCollectionName(clazz), 
				QueryBuilder.start().put(keyName).is(keyValue)
    			.put(Constantes.DELETED_ATTRIBUTE_NAME).is(Constantes.FALSE).get());
		return ModelUtils.instanciate(clazz, buildResultList(dbCursor));
    }

    public <T extends Model> List<T> findAll(Class<T> clazz) {
    	DBCursor dbCursor = MongoDB.getInstance().list(
    			Model.getCollectionName(clazz), 
				QueryBuilder.start().put(Constantes.DELETED_ATTRIBUTE_NAME).is(Constantes.FALSE).get());
    	return ModelUtils.instanciate(clazz, buildResultList(dbCursor));
    }
    
    public <T extends Model> List<T> findAll(Class<T> clazz, String keyName, Object keyValue, String version) {
    	DBCursor dbCursor = MongoDB.getInstance().list(
    			Model.getCollectionName(clazz), 
				QueryBuilder.start().put(keyName).is(keyValue)
    			.put(Constantes.DELETED_ATTRIBUTE_NAME).is(Constantes.FALSE)
    			.put(Constantes.VERSION_ATTRIBUTE_NAME).greaterThan(version).get());
		return ModelUtils.instanciate(clazz, buildResultList(dbCursor));
    }
    
    public <T extends Model> List<T> findAll(Class<T> clazz, String version) {
    	DBCursor dbCursor = MongoDB.getInstance().list(
    			Model.getCollectionName(clazz), 
				QueryBuilder.start().put(Constantes.DELETED_ATTRIBUTE_NAME).is(Constantes.FALSE)
    			.put(Constantes.VERSION_ATTRIBUTE_NAME).greaterThan(version).get());
    	return ModelUtils.instanciate(clazz, buildResultList(dbCursor));
	}
    
    private List<BasicDBObject> buildResultList(DBCursor dbCursor) {
    	BasicDBObject object;
		List<BasicDBObject> resultList = new ArrayList<BasicDBObject>();
		while (dbCursor.hasNext()) {
			object = (BasicDBObject) dbCursor.next();
			resultList.add(object);
		}
		return resultList;			
	}
}
