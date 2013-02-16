package util;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import play.Play;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;

public enum MongoUtils {
	INSTANCE;

	private DB db = initializeDB();

	private MongoClient getMongoClient() {
		try {
			return new MongoClient();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

	private DB initializeDB() {
		final DB jcertifdb = getMongoClient().getDB("jcertif");
		// Initialisation des données référentielles
		final File file = Play.application().getFile("/data/referentiel.js");
		try  {
            BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				jcertifdb.doEval(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return jcertifdb;
	}

	public String find(String collectionName) {
		// Retourne le résultat en JSON sans le paramètre par défaut _id
		return JSON.serialize(db.getCollection(collectionName).find(null,
				new BasicDBObject("_id", 0)));
	}
}
