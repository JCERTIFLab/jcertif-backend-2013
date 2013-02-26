package util.properties;

import play.Play;
import util.Constantes;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropUtils {

    Properties properties;
    String propertiesFileName;

    public static  PropUtils instance;

    private  PropUtils() {
        try {
            init(Constantes.JCERTIFBACKEND_PROPERTIES_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            properties = null;
        }
    }
    
    public static PropUtils getInstance(){
    	if(instance==null){
    		instance=new PropUtils();
    	}return instance;
    }

    @Deprecated
    /**
     * Utiliser le singleton via getInstace()
     * @param propertiesFileName
     * @throws IOException
     */
    public PropUtils (String propertiesFileName) throws IOException {
        init(propertiesFileName);
    }

    public void reloadProperties() throws IOException {
        FileInputStream inputStream = new FileInputStream(propertiesFileName);
        properties.load(inputStream);
    }

    private void init(String propertiesFileName) throws IOException {
        properties = new Properties();
        properties.load(Play.application().resourceAsStream(propertiesFileName));
        this.propertiesFileName = propertiesFileName;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

}
