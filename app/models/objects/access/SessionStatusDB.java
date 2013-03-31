package models.objects.access;

import models.objects.SessionStatus;
import models.util.Constantes;

/**
 * <p>Implémentation d'un {@link ReferentielDB} pour la persistance
 * des objets de type {@link SessionStatus}</p>
 *
 */
public final class SessionStatusDB extends ReferentielDB<SessionStatus> {

    private static SessionStatusDB instance = new SessionStatusDB();

    public SessionStatusDB() {
        super(Constantes.COLLECTION_SESSION_STATUS);
    }

    public static SessionStatusDB getInstance() {
        return instance;
    }
}
