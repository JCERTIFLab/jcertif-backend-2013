package models.objects.access;

import models.objects.Participant;
import models.util.Constantes;

public final class ParticipantDB extends MemberDB<Participant>{

    private static final ParticipantDB INSTANCE = new ParticipantDB();

    private ParticipantDB() {
        super(Constantes.COLLECTION_PARTICIPANT);
    }
    
    public static ParticipantDB getInstance(){
        return INSTANCE;
    }
}
