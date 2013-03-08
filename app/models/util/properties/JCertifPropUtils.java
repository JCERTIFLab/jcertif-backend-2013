package models.util.properties;

import models.util.Constantes;

import java.io.IOException;

public class JCertifPropUtils extends PropUtils {

    public static JCertifPropUtils instance;

    public JCertifPropUtils() throws IOException {
        super(Constantes.PROPERTIES_FILE);
    }

    public static JCertifPropUtils getInstance() {
        if (instance == null) {
            try {
                instance = new JCertifPropUtils();
            } catch (IOException exInstance1) {
                play.Logger.error("Unable to create JCertifPropUtils Instance", exInstance1);
            }
        }
        return instance;
    }
}
