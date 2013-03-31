package models.objects.access;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import play.Logger;

import models.database.MongoDatabase;
import models.exception.JCertifDuplicateObjectException;
import models.exception.JCertifException;
import models.exception.JCertifObjectNotFoundException;
import models.objects.JCertifObject;
import models.objects.checker.Checker;
import models.util.Constantes;
import models.util.Tools;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;

public abstract class JCertifObjectDB<T extends JCertifObject> implements
		IDao<BasicDBObject> {

	private Checker checker;
	private String collectionName;
	private Class<T> implementationClass;
	
	public JCertifObjectDB(){
		this.implementationClass = 
			(Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public JCertifObjectDB(String collectionName1) {
        super();
		this.collectionName = collectionName1;
		checker = null;
	}

	public JCertifObjectDB(String collectionName1, Checker checker1) {
        super();
		this.collectionName = collectionName1;
		this.checker = checker1;
	}

	public final Checker getChecker() {
		return checker;
	}

	protected final void setChecker(Checker checker1) {
		this.checker = checker1;
	}

	/**
	 * 
	 * @param query
	 * @param columnToReturn
	 * @return
	 */
	@Override
	public final List<BasicDBObject> list(BasicDBObject query,
			BasicDBObject columnToReturn) {
		DBCursor dbCursor = MongoDatabase.getInstance().list(
				getCollectionName(), query, columnToReturn);
		BasicDBObject object;
		List<BasicDBObject> resultList = new ArrayList<BasicDBObject>();
		while (dbCursor.hasNext()) {
			object = (BasicDBObject) dbCursor.next();
			resultList.add(object);
		}
		return resultList;
	}

	@Override
	public final List<BasicDBObject> list() {
		DBCursor dbCursor = MongoDatabase.getInstance().list(
				getCollectionName());
		BasicDBObject object;
		List<BasicDBObject> resultList = new ArrayList<BasicDBObject>();
		while (dbCursor.hasNext()) {
			object = (BasicDBObject) dbCursor.next();
			resultList.add(object);
		}
		return resultList;
	}

	@Override
	public final List<BasicDBObject> list(BasicDBObject query) {
		DBCursor dbCursor = MongoDatabase.getInstance().list(
				getCollectionName(), query);
		BasicDBObject object;
		List<BasicDBObject> resultList = new ArrayList<BasicDBObject>();
		while (dbCursor.hasNext()) {
			object = (BasicDBObject) dbCursor.next();
			resultList.add(object);
		}
		return resultList;
	}

	public final List<T> listAll() {
		BasicDBObject objectToSearch = new BasicDBObject();
		BasicDBObject columnToRetrieve = new BasicDBObject(Constantes.MONGOD_ID_ATTRIBUTE_NAME, 0);
		List<BasicDBObject> dbObjects = list(objectToSearch, columnToRetrieve);
		List<T> objects = new ArrayList<T>();
		T object = null;
		for(Iterator<BasicDBObject> itr = dbObjects.iterator();itr.hasNext();){
			object = JCertifObjectDBUtils.instanciate(implementationClass, itr.next());
			objects.add(object);
		}
		return objects;
	}
	
	@Override
	public final BasicDBObject get(String keyName, Object keyValue) {
		if (null == keyName){
			return null;
        }
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put(keyName, keyValue);
		
		return get(dbObject);
	}
	
	public final BasicDBObject get(BasicDBObject objectToGet) {
		if (null == objectToGet){
			return null;
        }

		/* If the object does not exist, null is returned */
		return MongoDatabase.getInstance().readOne(
				getCollectionName(), objectToGet);
	}

	@Override
	public final boolean add(BasicDBObject objectToAdd, String idKeyname) {
		getChecker().addCheck(objectToAdd);		
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put(idKeyname, objectToAdd.get(idKeyname));
		BasicDBObject existingObjectToAdd = MongoDatabase.getInstance()
				.readOne(getCollectionName(), dbObject);
		if (null != existingObjectToAdd) {
			throw new JCertifDuplicateObjectException(this, "Object '" + existingObjectToAdd.getString(idKeyname) + "' already exists");
		}
		
		WriteResult result = MongoDatabase.getInstance().create(
				getCollectionName(), objectToAdd);
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
	@Override
	public final boolean update(BasicDBObject objectToUpdate, String idKeyname) {
		getChecker().updateCheck(objectToUpdate);
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put(idKeyname, objectToUpdate.get(idKeyname));
		BasicDBObject existingObjectToUpdate = MongoDatabase.getInstance()
				.readOne(getCollectionName(), dbObject);
		if (null == existingObjectToUpdate) {
			Logger.info("not found");
			throw new JCertifObjectNotFoundException(this, "Object to update does not exist");
		}

		Map fieldMap = objectToUpdate.toMap();
		Map fieldToSaveMap = new HashMap();
		for (Entry entry : (Set<Entry>)fieldMap.entrySet()) {
			if(entry.getValue() != null){
				fieldToSaveMap.put(entry.getKey(), entry.getValue());
			}
		}
		existingObjectToUpdate.putAll(fieldToSaveMap);

		WriteResult result = MongoDatabase.getInstance().update(
				getCollectionName(), existingObjectToUpdate);
		if (!Tools.isBlankOrNull(result.getError())) {
			throw new JCertifException(this, result.getError());
		}
		return true;
	}

	@Override
	public final boolean remove(BasicDBObject objectToDelete, String idKeyname) {
		Logger.info("remove");
		getChecker().deleteCheck(objectToDelete);
		Logger.info("checked");
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put(idKeyname, objectToDelete.get(idKeyname));
		BasicDBObject existingObjectToDelete = MongoDatabase.getInstance()
				.readOne(getCollectionName(), dbObject);
		
		if (null == existingObjectToDelete) {
			throw new JCertifObjectNotFoundException(this, "Object to delete does not exist");
		}
		Logger.info("found");
		WriteResult result = MongoDatabase.getInstance().delete(
				getCollectionName(), existingObjectToDelete);
		if (!Tools.isBlankOrNull(result.getError())) {
			Logger.info("error");
			throw new JCertifException(this, result.getError());
		}
		return true;
	}

	public final String getCollectionName() {
		return collectionName;
	}

	public final void setCollectionName(String collectionName1) {
		this.collectionName = collectionName1;
	}
}
