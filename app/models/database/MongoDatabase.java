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
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

public final class MongoDatabase {

    private DB db = null;

	private static MongoDatabase instance = new MongoDatabase();

	private MongoDatabase() {
        super();
        Logger.info("Enter MongoDatabase()");
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
        Logger.info("Exit MongoDatabase()");
	}

	public static MongoDatabase getInstance() {
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

	public DBCursor list(String collectionName, BasicDBObject query,
			BasicDBObject columnToReturn) {
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

    /**
     * Cette fonction configure la base de données JCertif (Création des collections, création des index) 
     * 
     */
    /*public void configureJCertifDatabase() {
		Logger.info("Enter configureJCertifDatabase()");

		db.createCollection(Constantes.COLLECTION_LOGIN,
				null);
		db.getCollection(Constantes.COLLECTION_LOGIN)
				.createIndex(new BasicDBObject(Constantes.EMAIL_ATTRIBUTE_NAME, 1));

		db.createCollection(
				Constantes.COLLECTION_REFERENTIEL, null);
		db.getCollection(Constantes.COLLECTION_REFERENTIEL)
				.createIndex(new BasicDBObject("code", 1));

		db.createCollection(
				Constantes.COLLECTION_PARTICIPANT, null);
		db.getCollection(Constantes.COLLECTION_PARTICIPANT)
				.createIndex(new BasicDBObject(Constantes.EMAIL_ATTRIBUTE_NAME, 1));
		db.getCollection(Constantes.COLLECTION_PARTICIPANT)
				.createIndex(new BasicDBObject("lastname", 1));
		db.getCollection(Constantes.COLLECTION_PARTICIPANT)
				.createIndex(new BasicDBObject("firstname", 1));

		db.createCollection(Constantes.COLLECTION_SPEAKER,
				null);
		db.getCollection(Constantes.COLLECTION_SPEAKER)
				.createIndex(new BasicDBObject(Constantes.EMAIL_ATTRIBUTE_NAME, 1));
		db.getCollection(Constantes.COLLECTION_SPEAKER)
				.createIndex(new BasicDBObject("lastname", 1));
		db.getCollection(Constantes.COLLECTION_SPEAKER)
				.createIndex(new BasicDBObject("firstname", 1));

		db.createCollection(Constantes.COLLECTION_SPONSOR,
				null);
		db.getCollection(Constantes.COLLECTION_SPONSOR)
				.createIndex(new BasicDBObject(Constantes.EMAIL_ATTRIBUTE_NAME, 1));
		db.getCollection(Constantes.COLLECTION_SPONSOR)
				.createIndex(new BasicDBObject("name", 1));

		db.createCollection(Constantes.COLLECTION_SESSION,
				null);
		db.getCollection(Constantes.COLLECTION_SESSION)
				.createIndex(new BasicDBObject("title", 1));
        db.createCollection(Constantes.COLLECTION_SESSION_STATUS,
                null);
        db.getCollection(Constantes.COLLECTION_SESSION_STATUS)
                .createIndex(new BasicDBObject(Constantes.LABEL_ATTRIBUTE_NAME, 1));

        Logger.info("Exit configureJCertifDatabase()");
	}*/

	public WriteResult create(String collectionName,
            BasicDBObject objectToCreate) {
        Logger.debug("create(collectionName=" + collectionName + ", objectToCreate="+objectToCreate+")");
		return db.getCollection(collectionName).insert(objectToCreate,
				WriteConcern.SAFE);
	}

	public WriteResult update(String collectionName,
			BasicDBObject objectToUpdate) {
        Logger.debug("update(collectionName=" + collectionName + ", objectToUpdate="+objectToUpdate+")");
		return db.getCollection(collectionName).update(
				new BasicDBObject(Constantes.MONGOD_ID_ATTRIBUTE_NAME, objectToUpdate.get(Constantes.MONGOD_ID_ATTRIBUTE_NAME)),
				objectToUpdate);
	}

	public WriteResult save(String collectionName, BasicDBObject objectToUpdate) {
        Logger.debug("save(collectionName=" + collectionName + ", objectToUpdate="+objectToUpdate+")");
		return db.getCollection(collectionName).save(objectToUpdate);
	}

	public BasicDBObject readOne(String collectionName, BasicDBObject query) {
        Logger.debug("readOne(collectionName=" + collectionName + ", query="+query+")");
		return (BasicDBObject) db.getCollection(collectionName).findOne(query);
	}

	public DBCursor list(String collectionName, BasicDBObject query) {
        Logger.debug("list(collectionName=" + collectionName + ", query="+query+")");
		return db.getCollection(collectionName).find(query);
	}

	public WriteResult delete(String collectionName,
			BasicDBObject objectToDelete) {
        Logger.debug("delete(collectionName=" + collectionName + ", objectToDelete="+objectToDelete+")");
		return db.getCollection(collectionName).remove(objectToDelete,
				WriteConcern.SAFE);
	}
}
