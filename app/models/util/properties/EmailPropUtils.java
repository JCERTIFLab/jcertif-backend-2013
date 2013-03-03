package models.util.properties;

import models.util.Constantes;

import java.io.IOException;

public class EmailPropUtils extends PropUtils {

    public static EmailPropUtils instance;

    public EmailPropUtils() throws IOException {
        super(Constantes.JCERTIFBACKEND_EMAIL_PROPERTIES_FILE);
    }

    public static EmailPropUtils getInstance() {
        if (instance == null) {
            try {
                instance = new EmailPropUtils();
            } catch (IOException exInstance1) {
                play.Logger.error("Unable to create EmailPropUtils Instance", exInstance1);
            }
        }
        return instance;
    }
}
