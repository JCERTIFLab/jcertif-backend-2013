package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import models.database.MongoDatabase;
import models.exception.JCertifDuplicateObjectException;
import models.exception.JCertifException;
import models.exception.JCertifObjectNotFoundException;
import models.util.Constantes;
import models.util.Tools;

import org.apache.commons.lang.StringUtils;

import play.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;

public abstract class Model implements CRUD, Check {
	
	public abstract BasicDBObject toBasicDBObject();
    public abstract String getKeyName();
    private static final Finder FINDER = new Finder();
   
    public static Finder getFinder() {
		return FINDER;
	}
    
	/**
	 * Retourne le nom conventionnel d'un collection en fonction du nom de la classe du model.
	 * <br/>Une classe du model portant le nom <code>VoiciMonModel</code> aurra pour nom de collection
	 * voici_mon_model
	 * 
	 * @param clazz Classe du model
	 * @return Le nom conventionnel de la collection associe à la classe
	 */
	protected static String getCollectionName(Class<?> clazz){
		String collectionName = "";
		for(String name : StringUtils.splitByCharacterTypeCamelCase(clazz.getSimpleName())){
			collectionName += "_" + name;
		}
		return collectionName.substring(1).toLowerCase();
	}

	/**
	 * <p>This class provides static methods to retrieve data from database.</p>
	 * 
	 * @author Martial SOMDA
	 *
	 */
	public static class Finder {


        public BasicDBObject find(Class<?> clazz, String keyName, Object keyValue) {
        	BasicDBObject objectToFind = new BasicDBObject();
        	objectToFind.put(keyName, keyValue);
        	return MongoDatabase.getInstance().readOne(
    				getCollectionName(clazz), objectToFind);
        }
        
        public List<BasicDBObject> findAll(Class<?> clazz, String keyName, Object keyValue) {
        	DBCursor dbCursor = MongoDatabase.getInstance().list(
    				getCollectionName(clazz), new BasicDBObject(keyName, keyValue), 
    				new BasicDBObject(Constantes.MONGOD_ID_ATTRIBUTE_NAME, 0));
    		return buildResultList(dbCursor);
        }

        public List<BasicDBObject> findAll(Class<?> clazz) {
        	DBCursor dbCursor = MongoDatabase.getInstance().list(
    				getCollectionName(clazz));
        	return buildResultList(dbCursor);
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

	public final boolean add(BasicDBObject objectToAdd, String idKeyname) {
		addCheck(objectToAdd);		
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put(idKeyname, objectToAdd.get(idKeyname));
		BasicDBObject existingObjectToAdd = MongoDatabase.getInstance()
				.readOne(getCollectionName(getClass()), dbObject);
		if (null != existingObjectToAdd) {
			throw new JCertifDuplicateObjectException(this, "Object '" + existingObjectToAdd.getString(idKeyname) + "' already exists");
		}
		
		WriteResult result = MongoDatabase.getInstance().create(
				getCollectionName(getClass()), objectToAdd);
		if (!Tools.isBlankOrNull(result.getError())) {
			throw new JCertifException(this, result.getError());
		}
		return true;
	}

	/**
	 * This method sould be revisited
	 * 
	 * @param objectToUpdate
	 * @param idKeyname
	 * @return
	 * @throws JCertifException
	 */
	public final boolean update(BasicDBObject objectToUpdate, String idKeyname) {
		updateCheck(objectToUpdate);
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put(idKeyname, objectToUpdate.get(idKeyname));
		BasicDBObject existingObjectToUpdate = MongoDatabase.getInstance()
				.readOne(getCollectionName(getClass()), dbObject);
		if (null == existingObjectToUpdate) {
			Logger.info("not found");
			throw new JCertifObjectNotFoundException(this, "Object to update does not exist");
		}

		existingObjectToUpdate = merge(objectToUpdate,existingObjectToUpdate);

		WriteResult result = MongoDatabase.getInstance().update(
				getCollectionName(getClass()), existingObjectToUpdate);
		if (!Tools.isBlankOrNull(result.getError())) {
			throw new JCertifException(this, result.getError());
		}
		return true;
	}

	/**
	 * @param objectToUpdate
	 * @param existingObjectToUpdate
	 * @return
	 */
	private BasicDBObject merge(BasicDBObject objectToUpdate,
			BasicDBObject existingObjectToUpdate) {
		
		Map<String, Object> fieldMap = objectToUpdate.toMap();
		Map<String, Object> fieldToSaveMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : (Set<Entry<String, Object>>)fieldMap.entrySet()) {
			if(entry.getValue() != null && 
					(!(entry.getValue() instanceof List<?>)||
					((entry.getValue() instanceof List<?>) &&
							!Tools.isBlankOrNull((ArrayList<?>)entry.getValue())))){
				fieldToSaveMap.put(entry.getKey(), entry.getValue());
			}
		}
		existingObjectToUpdate.putAll(fieldToSaveMap);
		
		return existingObjectToUpdate;
	}

	public final boolean remove(BasicDBObject objectToDelete, String idKeyname) {
		deleteCheck(objectToDelete);
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put(idKeyname, objectToDelete.get(idKeyname));
		BasicDBObject existingObjectToDelete = MongoDatabase.getInstance()
				.readOne(getCollectionName(getClass()), dbObject);
		
		if (null == existingObjectToDelete) {
			throw new JCertifObjectNotFoundException(this, "Object to delete does not exist");
		}

		WriteResult result = MongoDatabase.getInstance().delete(
				getCollectionName(getClass()), existingObjectToDelete);
		if (!Tools.isBlankOrNull(result.getError())) {
			throw new JCertifException(this, result.getError());
		}
		return true;
	}

}
