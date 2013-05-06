package models.database;

import java.io.IOException;
import java.net.UnknownHostException;

import models.util.Constantes;
import models.util.Tools;
import play.Logger;
import play.Play;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

public final class MongoDB {

    private DB db = null;

	private static MongoDB instance = new MongoDB();

	private MongoDB() {
        super();
        Logger.info("Enter MongoDB()");
		String dbhost = Play.application().configuration().getString(
				"database.host");
		String dbname = Play.application().configuration().getString(
				"database.name");
		String user = Play.application().configuration().getString(
				"database.user");
		String password = Play.application().configuration().getString(
				"database.password");
		int dbport = Integer.parseInt(Play.application().configuration().getString(
				"database.port"));

        Logger.debug("dbhost=" + dbhost + ", dbname=" + dbname + ", user=" + user + ", password=***, dbport=" + dbport);

        try {
            MongoClient mongoClient;
			mongoClient = new MongoClient(new ServerAddress(dbhost, dbport),
					getDBOptions());
			db = mongoClient.getDB(dbname);
			if (user != null && !"".equals(user.trim())) {
				db.authenticate(user, password.toCharArray());
			}
			
		} catch (UnknownHostException e) {
			Logger.error("Impossible d'initialiser le client MongoClient", e);
		} 
        Logger.info("Exit MongoDB()");
	}

	public static MongoDB getInstance() {
		return instance;
	}

	private MongoClientOptions getDBOptions() {
        Logger.info("Enter getDBOptions()");

        MongoClientOptions.Builder mco = new MongoClientOptions.Builder();

		try {
			mco.connectionsPerHost(Integer.parseInt(Play.application().configuration().getString("database.pool.size", "50")));
		} catch (NumberFormatException e) {
			mco.connectionsPerHost(Integer.valueOf("50").intValue());
		}

        Logger.info("Exit getDBOptions()");
		return mco.build();
	}

	public void loadDbWithData(String filenameContainsData) throws IOException {
        Logger.info("Enter loadDbWithData(" + filenameContainsData + ")");
        db.doEval(Tools.getFileContent(filenameContainsData));
        Logger.info("Exit getDBOptions()");
	}

	public DBCollection getCollection(String collectionName) {
		return db.getCollection(collectionName);
	}

	public DB getDb() {
		return db;
	}

	public DBCursor list(String collectionName, DBObject query,
			DBObject columnToReturn) {
        Logger.debug("list(collectionName=" + collectionName + ", query=" + query + ", columnToReturn=" + columnToReturn +")");
		return db.getCollection(collectionName).find(query, columnToReturn);
	}

	public DBCursor list(String collectionName) {
		// Retourne le résultat en JSON sans le paramètre par défaut _id
        Logger.debug("list(collectionName=" + collectionName + ")");
		return db.getCollection(collectionName).find(null,
				new BasicDBObject(Constantes.MONGOD_ID_ATTRIBUTE_NAME, 0).append("password", 0));
	}

	public String listAll(String collectionName) {
		// Retourne le résultat en JSON sans le paramètre par défaut _id
        Logger.debug("listAll(collectionName=" + collectionName + ")");
		return JSON.serialize(db.getCollection(collectionName).find(null,
				new BasicDBObject(Constantes.MONGOD_ID_ATTRIBUTE_NAME, 0).append("password", 0)));
	}

	public WriteResult create(String collectionName,
            DBObject objectToCreate) {
        Logger.debug("create(collectionName=" + collectionName + ", objectToCreate="+objectToCreate+")");
		return db.getCollection(collectionName).insert(objectToCreate,
				WriteConcern.SAFE);
	}

	public WriteResult update(String collectionName,
			DBObject objectToUpdate) {
        Logger.debug("update(collectionName=" + collectionName + ", objectToUpdate="+objectToUpdate+")");
		return db.getCollection(collectionName).update(
				new BasicDBObject(Constantes.MONGOD_ID_ATTRIBUTE_NAME, objectToUpdate.get(Constantes.MONGOD_ID_ATTRIBUTE_NAME)),
				objectToUpdate);
	}

	public WriteResult save(String collectionName, DBObject objectToUpdate) {
        Logger.debug("save(collectionName=" + collectionName + ", objectToUpdate="+objectToUpdate+")");
		return db.getCollection(collectionName).save(objectToUpdate);
	}

	public BasicDBObject readOne(String collectionName, DBObject query) {
        Logger.debug("readOne(collectionName=" + collectionName + ", query="+query+")");
		return (BasicDBObject) db.getCollection(collectionName).findOne(query);
	}
	
	public BasicDBObject readNextSequence(String collectionName) {
		BasicDBObject query = new BasicDBObject(Constantes.MONGOD_ID_ATTRIBUTE_NAME, collectionName);
		BasicDBObject seqField = new BasicDBObject(Constantes.SEQ_ATTRIBUTE_NAME, 1);
		BasicDBObject update = new BasicDBObject("$inc", seqField);
        Logger.debug("readNextSequence(collectionName=counters , query="+query+")");
		return (BasicDBObject) db.getCollection("counters").findAndModify(query, seqField, null, false, update, true, true);
	}

	public DBCursor list(String collectionName, DBObject query) {
        Logger.debug("list(collectionName=" + collectionName + ", query="+query+")");
		return db.getCollection(collectionName).find(query);
	}

	public WriteResult delete(String collectionName,
			DBObject objectToDelete) {
        Logger.debug("delete(collectionName=" + collectionName + ", objectToDelete="+objectToDelete+")");
		return db.getCollection(collectionName).remove(objectToDelete,
				WriteConcern.SAFE);
	}
}
