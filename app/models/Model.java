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
import models.exception.JCertifStaleObjectException;
import models.util.Constantes;
import models.util.Tools;

import org.apache.commons.lang.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;

public abstract class Model implements CRUD, Check {
	
	private static final Finder FINDER = new Finder();
	public static Finder getFinder() {
		return FINDER;
	}
	
	private String version;
	private String deleted;
	
	public Model(BasicDBObject basicDBObject){
		this.version = basicDBObject.getString("version");
		this.deleted = basicDBObject.getString("deleted");
	}

	public abstract String getKeyName();
	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}
	
	public boolean isDeleted() {
		return Boolean.valueOf(deleted);
	}

	public void setDeleted(boolean isDeleted) {
		this.deleted = Boolean.toString(isDeleted);
	}

	public BasicDBObject toBasicDBObject(){
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("version", version);
		dbObject.put("deleted", deleted);
		return dbObject;
	}
    
	private int increment(BasicDBObject basicDBObject){
		int currentVersion = Integer.parseInt(basicDBObject.getString("version"));
		int nextVersion = currentVersion+1;
		if(nextVersion < 10){
			basicDBObject.put("version", String.format("%02d", nextVersion));
		}else{
			basicDBObject.put("version", Integer.toString(nextVersion));
		}		
		return nextVersion++;
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

	public final int add(BasicDBObject objectToAdd, String idKeyname) {
		addCheck(objectToAdd);		
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put(idKeyname, objectToAdd.get(idKeyname));
		BasicDBObject existingObjectToAdd = MongoDatabase.getInstance()
				.readOne(getCollectionName(getClass()), dbObject);
		if (null != existingObjectToAdd) {
			throw new JCertifDuplicateObjectException(this.getClass(), existingObjectToAdd.getString(idKeyname));
		}

		WriteResult result = MongoDatabase.getInstance().create(
				getCollectionName(getClass()), objectToAdd);
		if (!Tools.isBlankOrNull(result.getError())) {
			throw new JCertifException(this.getClass(), result.getError());
		}
		return 1;
	}

	/**
	 * This method sould be revisited
	 * 
	 * @param objectToUpdate
	 * @param idKeyname
	 * @return
	 * @throws JCertifException
	 */
	public final int update(BasicDBObject objectToUpdate, String idKeyname) {
		updateCheck(objectToUpdate);
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put(idKeyname, objectToUpdate.get(idKeyname));
		BasicDBObject existingObjectToUpdate = MongoDatabase.getInstance()
				.readOne(getCollectionName(getClass()), dbObject);
		if (null == existingObjectToUpdate) {
			throw new JCertifObjectNotFoundException(this.getClass(), objectToUpdate.get(idKeyname).toString());
		}
		
		if(!existingObjectToUpdate.getString("version").equals(objectToUpdate.getString("version"))){
			throw new JCertifStaleObjectException(this.getClass(), objectToUpdate.get(idKeyname).toString());
		}
		
		existingObjectToUpdate = merge(objectToUpdate,existingObjectToUpdate);
		int newId = increment(existingObjectToUpdate);

		WriteResult result = MongoDatabase.getInstance().update(
				getCollectionName(getClass()), existingObjectToUpdate);
		if (!Tools.isBlankOrNull(result.getError())) {
			throw new JCertifException(this.getClass(), result.getError());
		}
		return newId;
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

	/**
	 * <p>This class provides static methods to retrieve data from database.</p>
	 * 
	 * @author Martial SOMDA
	 *
	 */
	public static class Finder {


        public BasicDBObject find(Class<?> clazz, String keyName, Object keyValue) {
        	return MongoDatabase.getInstance().readOne(
        			getCollectionName(clazz), 
        			new BasicDBObject(keyName, keyValue).append("deleted", "false"));
        }
        
        public List<BasicDBObject> findAll(Class<?> clazz, String keyName, Object keyValue) {
        	DBCursor dbCursor = MongoDatabase.getInstance().list(
    				getCollectionName(clazz), 
    				new BasicDBObject(keyName, keyValue).append("deleted", "false"), 
    				new BasicDBObject(Constantes.MONGOD_ID_ATTRIBUTE_NAME, 0));
    		return buildResultList(dbCursor);
        }

        public List<BasicDBObject> findAll(Class<?> clazz) {
        	DBCursor dbCursor = MongoDatabase.getInstance().list(
    				getCollectionName(clazz), 
    				new BasicDBObject().append("deleted", "false"));
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
}
