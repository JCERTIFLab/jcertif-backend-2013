package objects.access;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;
import database.MongoDatabase;
import exception.JCertifException;
import objects.checker.Checker;
import util.Tools;

import java.util.ArrayList;
import java.util.List;

public class JCertifObjectDB {

    private Checker checker;

    private String collectionName;

    public JCertifObjectDB(String collectionName) {
        this.setCollectionName(collectionName);
        checker = null;
    }

    public JCertifObjectDB(String collectionName, Checker checker) {
        this.setCollectionName(collectionName);
        this.setChecker(checker);
    }

    public Checker getChecker() {
        return checker;
    }

    protected void setChecker(Checker checker) {
        this.checker = checker;
    }

    /**
     *
     * @param query
     * @param columnToReturn
     * @return
     */
    public List<BasicDBObject> list(BasicDBObject query, BasicDBObject columnToReturn){
        DBCursor dbCursor = MongoDatabase.JCERTIFINSTANCE.list(getCollectionName(), query, columnToReturn);
        BasicDBObject object;
        List<BasicDBObject> resultList = new ArrayList<BasicDBObject>();
        while (dbCursor.hasNext()) {
            object = (BasicDBObject) dbCursor.next();
            resultList.add(object);
        }
        return resultList;
    }

    public List<BasicDBObject> list() {
        DBCursor dbCursor = MongoDatabase.JCERTIFINSTANCE.list(getCollectionName());
        BasicDBObject object;
        List<BasicDBObject> resultList = new ArrayList<BasicDBObject>();
        while (dbCursor.hasNext()) {
            object = (BasicDBObject) dbCursor.next();
            resultList.add(object);
        }
        return resultList;
    }

    public List<BasicDBObject> list(BasicDBObject query) {
        DBCursor dbCursor = MongoDatabase.JCERTIFINSTANCE.list(getCollectionName(), query);
        BasicDBObject object;
        List<BasicDBObject> resultList = new ArrayList<BasicDBObject>();
        while (dbCursor.hasNext()) {
            object = (BasicDBObject) dbCursor.next();
            resultList.add(object);
        }
        return resultList;
    }

    public String getCollectionName() {
        return collectionName;
    }

    private void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public BasicDBObject get(String keyName, Object keyValue) throws JCertifException {
        if (null == keyName) return null;
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put(keyName, keyValue);
        BasicDBObject objectToGet = MongoDatabase.JCERTIFINSTANCE.readOne(getCollectionName(), dbObject);
        return objectToGet; //If the object does not exist, null is returned
    }

    public boolean add(BasicDBObject basicDBObject) throws JCertifException {
        getChecker().check(basicDBObject);
        getChecker().addCheck(basicDBObject);
        WriteResult result = MongoDatabase.JCERTIFINSTANCE.create(getCollectionName(), basicDBObject);
        if (!Tools.isBlankOrNull(result.getError())) {
            throw new JCertifException(this, result.getError());
        }
        return true;
    }

    /**
     * This method sould be revisit
     * @param objectToUpdate
     * @param idKeyname
     * @return
     * @throws JCertifException
     */
    public boolean update(BasicDBObject objectToUpdate, String idKeyname) throws JCertifException {
        getChecker().check(objectToUpdate);
        getChecker().updateCheck(objectToUpdate);
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put(idKeyname, objectToUpdate.get(idKeyname));
        BasicDBObject existingObjectToUpdate = MongoDatabase.JCERTIFINSTANCE.readOne(getCollectionName(), dbObject);
        if (null == existingObjectToUpdate) {
            throw new JCertifException(this, "Object to update does not exist");
        }

        existingObjectToUpdate.putAll(objectToUpdate.toMap());

        WriteResult result = MongoDatabase.JCERTIFINSTANCE.update(getCollectionName(), existingObjectToUpdate);
        if (!Tools.isBlankOrNull(result.getError())) {
            throw new JCertifException(this, result.getError());
        }
        return true;
    }

    public boolean save(BasicDBObject objectToUpdate, String idKeyname) throws JCertifException {
        getChecker().check(objectToUpdate);
        getChecker().updateCheck(objectToUpdate);
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put(idKeyname, objectToUpdate.get(idKeyname));
        BasicDBObject existingObjectToUpdate = MongoDatabase.JCERTIFINSTANCE.readOne(getCollectionName(), dbObject);
        if (null == existingObjectToUpdate) {
            throw new JCertifException(this, "Object to update does not exist");
        }

        existingObjectToUpdate.putAll(objectToUpdate.toMap());

        WriteResult result = MongoDatabase.JCERTIFINSTANCE.save(getCollectionName(), existingObjectToUpdate);
        if (!Tools.isBlankOrNull(result.getError())) {
            throw new JCertifException(this, result.getError());
        }
        return true;
    }

    public boolean remove(BasicDBObject objectToDelete, String idKeyname) throws JCertifException {
        getChecker().check(objectToDelete);
        getChecker().deleteCheck(objectToDelete);
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put(idKeyname, objectToDelete.get(idKeyname));
        BasicDBObject existingObjectToDelete = MongoDatabase.JCERTIFINSTANCE.readOne(getCollectionName(), dbObject);
        if (null == existingObjectToDelete) {
            throw new JCertifException(this, "Object to delete does not exist");
        }
        WriteResult result = MongoDatabase.JCERTIFINSTANCE.delete(getCollectionName(), existingObjectToDelete);
        if (!Tools.isBlankOrNull(result.getError())) {
            throw new JCertifException(this, result.getError());
        }
        return true;
    }
}
