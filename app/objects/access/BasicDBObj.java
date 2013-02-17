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

public class BasicDBObj {

    private Checker checker;

    private String collectionName;

    public BasicDBObj(String collectionName){
        this.setCollectionName(collectionName);
        checker = null;
    }

    public BasicDBObj(String collectionName,Checker checker){
        this.setCollectionName(collectionName);
        this.setChecker(checker);
    }

    protected Checker getChecker() {
        return checker;
    }

    protected void setChecker(Checker checker) {
        this.checker = checker;
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
        DBCursor dbCursor = MongoDatabase.JCERTIFINSTANCE.read(getCollectionName(), query);
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
        if (null == objectToGet) {
            throw new JCertifException(this, "Object to get does not exist");
        }
        return objectToGet;
    }

    public boolean add(BasicDBObject basicDBObject) throws JCertifException {
        getChecker().check(basicDBObject);
        WriteResult result = MongoDatabase.JCERTIFINSTANCE.create(getCollectionName(), basicDBObject);
        if (!Tools.isBlankOrNull(result.getError())) {
            throw new JCertifException(this, result.getError());
        }
        return true;
    }

    public boolean update(BasicDBObject objectToUpdate, String idKeyname) throws JCertifException {
        getChecker().check(objectToUpdate);
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put(idKeyname, objectToUpdate.get(idKeyname));
        BasicDBObject existingObjectToUpdate = MongoDatabase.JCERTIFINSTANCE.readOne(getCollectionName(), dbObject);
        if (null == existingObjectToUpdate) {
            throw new JCertifException(this, "Object to update does not exist");
        }

        String currentId = existingObjectToUpdate.getString("_id");
        existingObjectToUpdate.putAll(objectToUpdate.toMap());
        existingObjectToUpdate.put("_id", currentId);

        WriteResult result = MongoDatabase.JCERTIFINSTANCE.update(getCollectionName(), existingObjectToUpdate);
        if (!Tools.isBlankOrNull(result.getError())) {
            throw new JCertifException(this, result.getError());
        }
        return true;
    }

    public boolean remove(BasicDBObject objectToDelete, String idKeyname) throws JCertifException {
        getChecker().check(objectToDelete);
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
