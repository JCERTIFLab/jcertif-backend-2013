package models.objects.access;

import models.objects.Speaker;
import models.util.Constantes;

public final class SpeakerDB extends MemberDB<Speaker> {

    private static final SpeakerDB INSTANCE = new SpeakerDB();

    public SpeakerDB() {
        super(Constantes.COLLECTION_SPEAKER);
    }

    
    public static SpeakerDB getInstance(){
        return INSTANCE;
    }
}
