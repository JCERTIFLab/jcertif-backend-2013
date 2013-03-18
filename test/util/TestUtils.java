package util;


import com.google.common.io.Files;
import models.database.MongoDatabase;
import play.Logger;
import play.Play;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
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
        MongoDatabase.getInstance().getDb().eval(sb.toString());

    }

}
