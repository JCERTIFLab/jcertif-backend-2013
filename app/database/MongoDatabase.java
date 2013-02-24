package database;

import com.mongodb.*;
import com.mongodb.util.JSON;
import play.Logger;
import util.Constantes;
import util.Tools;
import util.properties.PropUtils;

import java.io.IOException;
import java.net.UnknownHostException;

public class MongoDatabase {

    private DB db = null;
    private MongoClient mongoClient = null;

    public static final MongoDatabase JCERTIFINSTANCE = new MongoDatabase();

    public MongoDatabase() {
        String dbhost = PropUtils.JCERTIFINSTANCE.getProperty("jcertifbackend.database.host");
        String dbname = PropUtils.JCERTIFINSTANCE.getProperty("jcertifbackend.database.name");
        String user = PropUtils.JCERTIFINSTANCE.getProperty("jcertifbackend.database.user");
        String password = PropUtils.JCERTIFINSTANCE.getProperty("jcertifbackend.database.password");
        int dbport = Integer.parseInt(PropUtils.JCERTIFINSTANCE.getProperty("jcertifbackend.database.port"));

        try {
            mongoClient = new MongoClient(new ServerAddress(dbhost, dbport), getDBOptions());
            db = mongoClient.getDB(dbname);
            if (user != null && !user.trim().equals("")) {
                db.authenticate(user, password.toCharArray());
            }
            initializeJCertifDB();
        } catch (UnknownHostException e) {
            Logger.error("Impossible d'initialiser le client MongoClient", e);
        } catch (IOException e) {
            Logger.error("Impossible d'initialiser la base avec les données référentielles", e);
        }
    }

    public MongoDatabase(String host, int port, String databasename) throws UnknownHostException {
        mongoClient = new MongoClient(new ServerAddress(host, port), getDBOptions());
        db = mongoClient.getDB(databasename);
    }

    private MongoClientOptions getDBOptions() {
        MongoClientOptions.Builder mco = new MongoClientOptions.Builder();

        mco.connectionsPerHost(Integer.parseInt(PropUtils.JCERTIFINSTANCE.getProperty("jcertifbackend.database.pool.size", "50")));

        return mco.build();
    }

    public void loadDbWithData(String filenameContainsData) throws IOException {
        db.doEval(Tools.getFileContent(filenameContainsData));
    }

    private void initializeJCertifDB() throws IOException {
        loadDbWithData(Constantes.JCERTIFBACKEND_SAMPLE_DATA_FILE);
    }

    public DBCollection getCollection(String collectionName) {
        return db.getCollection(collectionName);
    }

    public DB getDb() {
        return db;
    }

    public DBCursor list(String collectionName) {
        // Retourne le résultat en JSON sans le paramètre par défaut _id
        return db.getCollection(collectionName).find(null,
                new BasicDBObject("_id", 0));
    }

    public String listAll(String collectionName) {
        // Retourne le résultat en JSON sans le paramètre par défaut _id
        return JSON.serialize(db.getCollection(collectionName).find(null,
                new BasicDBObject("_id", 0)));
    }

    public void configureJCertifDatabase() {
        //Cette fonction configure la base de données JCertif (Création des collections, création des index)
        db.createCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_LOGIN, null);
        db.getCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_LOGIN).createIndex(new BasicDBObject("email", 1));

        db.createCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_REFERENTIEL, null);
        db.getCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_REFERENTIEL).createIndex(new BasicDBObject("code", 1));

        db.createCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_PARTICIPANT, null);
        db.getCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_PARTICIPANT).createIndex(new BasicDBObject("email", 1));
        db.getCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_PARTICIPANT).createIndex(new BasicDBObject("lastname", 1));
        db.getCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_PARTICIPANT).createIndex(new BasicDBObject("firstname", 1));

        db.createCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_SPEAKER, null);
        db.getCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_SPEAKER).createIndex(new BasicDBObject("email", 1));
        db.getCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_SPEAKER).createIndex(new BasicDBObject("lastname", 1));
        db.getCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_SPEAKER).createIndex(new BasicDBObject("firstname", 1));

        db.createCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_SPONSOR, null);
        db.getCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_SPONSOR).createIndex(new BasicDBObject("email", 1));
        db.getCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_SPONSOR).createIndex(new BasicDBObject("name", 1));

        db.createCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_SESSION, null);
        db.getCollection(Constantes.JCERTIFBACKEND_COLLECTIONNAME_SESSION).createIndex(new BasicDBObject("title", 1));
    }

    public WriteResult create(String collectionName, BasicDBObject objectToCreate) {
        return db.getCollection(collectionName).insert(objectToCreate, WriteConcern.SAFE);
    }

    public WriteResult update(String collectionName, BasicDBObject objectToUpdate) {
        return db.getCollection(collectionName).update(new BasicDBObject("_id", objectToUpdate.get("_id")), objectToUpdate);
    }

    public BasicDBObject readOne(String collectionName, BasicDBObject query) {
        return (BasicDBObject) db.getCollection(collectionName).findOne(query);
    }

    public DBCursor read(String collectionName, BasicDBObject query) {
        return db.getCollection(collectionName).find(query);
    }

    public WriteResult delete(String collectionName, BasicDBObject objectToDelete) {
        return db.getCollection(collectionName).remove(objectToDelete, WriteConcern.SAFE);
    }
}
