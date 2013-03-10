package models.util;

public interface Constantes {

    String PROPERTIES_FILE = "jcertifbackend.properties";
    String INIT_DATA_FILE = "referentiel.js";

    String COLLECTION_LOGIN = "login";
    String COLLECTION_PARTICIPANT = "participant";
    String COLLECTION_REFERENTIEL = "referentiel";
    String COLLECTION_SESSION = "session";
    String COLLECTION_SESSION_STATUS = "session_status";
    String COLLECTION_SPEAKER = "speaker";
    String COLLECTION_SPONSOR = "sponsor";
    String COLLECTION_CATEGORY = "category";

    String DATEFORMAT = "dd/MM/yyyy HH:mm";

    String EMAIL_PROPERTIES_FILE = "jcertifbackend_email.properties";
    String EMAIL_WEBCONTAINER_RESOURCE_NAME = "mail/Session";

    int PASSWORD_MIN_LENGTH = 6;
}
