package models.objects.access;

import models.objects.SessionStatus;
import models.objects.checker.SessionStatusChecker;
import models.util.Constantes;

/**
 * <p>Implémentation d'un {@link ReferentielDB} pour la persistance
 * des objets de type {@link SessionStatus}</p>
 *
 */
public final class SessionStatusDB extends ReferentielDB<SessionStatus> {

    private static SessionStatusDB instance = new SessionStatusDB();

    public SessionStatusDB() {
        super(Constantes.COLLECTION_SESSION_STATUS, new SessionStatusChecker());
    }

    public static SessionStatusDB getInstance() {
        return instance;
    }
}
