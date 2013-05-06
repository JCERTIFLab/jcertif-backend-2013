package util;


import com.google.common.io.Files;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

import models.database.MongoDB;
import play.Logger;
import play.Play;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static List<String> getFileContent(String relativePath) throws IOException {
        File file = Play.application().getFile(relativePath);
        return Files.readLines(file, Charset.defaultCharset());
    }

    public static void updateDatabase(String relativePath) throws IOException {
        List<String> instructions = getFileContent(relativePath);
        StringBuilder sb = new StringBuilder();

        for (String instr : instructions){
            sb.append(instr);
        }
        Logger.info("############ Update database ########### \n " + sb.toString());
        MongoDB.getInstance().getDb().eval(sb.toString());

    }
    
    /**
     * Permet de charger de objets depuis la base de donnée.
     * 
     * @param collectionName Nom de la collection dans laquelle faire la recherche
     * @param query Requete à exécuter
     * @return Une liste correspondant aux résultats de la requete.
     */
    public static List<BasicDBObject> loadFromDatabase(String collectionName, BasicDBObject query) {
    	DBCursor dbCursor =  MongoDB.getInstance().list(collectionName, query);
        List<BasicDBObject> resultList = new ArrayList<BasicDBObject>();
        BasicDBObject result = null;
		while (dbCursor.hasNext()) {
			result = (BasicDBObject) dbCursor.next();
			resultList.add(result);
		}
		return resultList;

    }

}
