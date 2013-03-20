package models.objects.access;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;
import models.database.MongoDatabase;
import models.exception.JCertifException;
import models.objects.JCertifObject;
import models.objects.checker.Checker;
import models.util.Tools;

import java.util.ArrayList;
import java.util.List;

public abstract class JCertifObjectDB<T extends JCertifObject> implements
		IDao<BasicDBObject> {

	private Checker checker;
	private String collectionName;

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

	@Override
	public final BasicDBObject get(String keyName, Object keyValue)
			throws JCertifException {
		if (null == keyName){
			return null;
        }
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put(keyName, keyValue);
		
		return get(dbObject);
	}
	
	public final BasicDBObject get(BasicDBObject objectToGet)
			throws JCertifException {
		if (null == objectToGet){
			return null;
        }

		/* If the object does not exist, null is returned */
		return MongoDatabase.getInstance().readOne(
				getCollectionName(), objectToGet);
	}

	@Override
	public final boolean add(BasicDBObject basicDBObject) throws JCertifException {
		getChecker().check(basicDBObject);
		getChecker().addCheck(basicDBObject);
		WriteResult result = MongoDatabase.getInstance().create(
				getCollectionName(), basicDBObject);
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
	public final boolean update(BasicDBObject objectToUpdate, String idKeyname)
			throws JCertifException {
		getChecker().check(objectToUpdate);
		getChecker().updateCheck(objectToUpdate);
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put(idKeyname, objectToUpdate.get(idKeyname));
		BasicDBObject existingObjectToUpdate = MongoDatabase.getInstance()
				.readOne(getCollectionName(), dbObject);
		if (null == existingObjectToUpdate) {
			throw new JCertifException(this, "Object to update does not exist");
		}

		existingObjectToUpdate.putAll(objectToUpdate.toMap());

		WriteResult result = MongoDatabase.getInstance().update(
				getCollectionName(), existingObjectToUpdate);
		if (!Tools.isBlankOrNull(result.getError())) {
			throw new JCertifException(this, result.getError());
		}
		return true;
	}

	@Override
	public final boolean save(BasicDBObject objectToUpdate, String idKeyname)
			throws JCertifException {
		getChecker().check(objectToUpdate);
		getChecker().updateCheck(objectToUpdate);
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put(idKeyname, objectToUpdate.get(idKeyname));
		BasicDBObject existingObjectToUpdate = MongoDatabase.getInstance()
				.readOne(getCollectionName(), dbObject);
		if (null == existingObjectToUpdate) {
			throw new JCertifException(this, "Object to update does not exist");
		}

		existingObjectToUpdate.putAll(objectToUpdate.toMap());

		WriteResult result = MongoDatabase.getInstance().save(
				getCollectionName(), existingObjectToUpdate);
		if (!Tools.isBlankOrNull(result.getError())) {
			throw new JCertifException(this, result.getError());
		}
		return true;
	}

	@Override
	public final boolean remove(BasicDBObject objectToDelete, String idKeyname)
			throws JCertifException {
		getChecker().deleteCheck(objectToDelete);
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put(idKeyname, objectToDelete.get(idKeyname));
		BasicDBObject existingObjectToDelete = MongoDatabase.getInstance()
				.readOne(getCollectionName(), dbObject);
		if (null == existingObjectToDelete) {
			throw new JCertifException(this, "Object to delete does not exist");
		}
		WriteResult result = MongoDatabase.getInstance().delete(
				getCollectionName(), existingObjectToDelete);
		if (!Tools.isBlankOrNull(result.getError())) {
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
