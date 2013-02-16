package database;

import com.mongodb.*;
import com.mongodb.util.JSON;
import play.Play;
import util.Constantes;
import util.properties.PropUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;

public class MongoDatabase {

    private DB db = null;
    private MongoClient mongoClient = null;

    public static MongoDatabase JCERTIFINSTANCE = new MongoDatabase();

    public MongoDatabase() {
        String dbhost = PropUtils.JCERTIFINSTANCE.getProperty("jcertifbackend.database.host");
        String dbname = PropUtils.JCERTIFINSTANCE.getProperty("jcertifbackend.database.name");
        int dbport = Integer.parseInt(PropUtils.JCERTIFINSTANCE.getProperty("jcertifbackend.database.port"));

        try {
            mongoClient = new MongoClient(new ServerAddress(dbhost, dbport), getDBOptions());
            db = mongoClient.getDB(dbname);
        } catch (UnknownHostException e) {
            e.printStackTrace();
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
        File file = Play.application().getFile(filenameContainsData);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            db.doEval(line);
        }
    }

    public void initializeJCertifDB() throws IOException {
        loadDbWithData(Constantes.JCERTIFBACKEND_SAMPLE_DATA_FILE);
    }

    public DBCollection getCollection(String collectionName){
        return db.getCollection(collectionName);
    }

    public DB getDb(){
        return db;
    }

    public String listAll(String collectionName) {
        // Retourne le résultat en JSON sans le paramètre par défaut _id
        return JSON.serialize(db.getCollection(collectionName).find(null,
                new BasicDBObject("_id", 0)));
    }

    public void configureJCertifDatabase(){
        //Cette fonction configure la base de données JCertif (Création des collections, création des index)
        //TODO
    }
}
