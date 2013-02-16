package util.properties;

import play.Play;
import util.Constantes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropUtils {

    Properties properties;
    String propertiesFileName;

    public static PropUtils JCERTIFINSTANCE = new PropUtils();

    public PropUtils() {
        try {
            init(Constantes.JCERTIFBACKEND_PROPERTIES_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            properties = null;
        }
    }

    public PropUtils(String propertiesFileName) throws IOException {
        init(propertiesFileName);
    }

    public void reloadProperties() throws IOException {
        FileInputStream inputStream = new FileInputStream(propertiesFileName);
        properties.load(inputStream);
    }

    private void init(String propertiesFileName) throws IOException {
        properties = new Properties();
        File file = Play.application().getFile(propertiesFileName);
        FileInputStream inputStream = new FileInputStream(file);
        properties.load(inputStream);
        this.propertiesFileName = propertiesFileName;
        inputStream.close();//Toujours fermer les connexions ouvertes
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue){
        return properties.getProperty(key, defaultValue);
    }

}
