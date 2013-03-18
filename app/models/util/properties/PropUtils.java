package models.util.properties;

import play.Play;

import java.io.*;
import java.util.Properties;

public abstract class PropUtils {

    private Properties properties;
    private String propertiesFileName;

    public PropUtils(String propertiesFileName1) throws IOException {
        init(propertiesFileName1);
    }

    public void reloadProperties() throws IOException {
        FileInputStream inputStream = new FileInputStream(propertiesFileName);
        properties.load(inputStream);
        inputStream.close();
    }

    private void init(String propertiesFileName1) throws IOException {
        InputStream inputStream = null;
        Reader reader = null;
        try {
            properties = new Properties();
            inputStream = Play.application().resourceAsStream(propertiesFileName1);
            reader = new InputStreamReader(inputStream, "UTF-8");
            properties.load(reader);
            this.propertiesFileName = propertiesFileName1;
        } finally {
            if (reader != null){
                reader.close();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
