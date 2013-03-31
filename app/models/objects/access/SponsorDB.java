package models.objects.access;

import models.objects.Sponsor;
import models.objects.checker.SponsorChecker;
import models.util.Constantes;

import com.mongodb.BasicDBObject;

public final class SponsorDB extends JCertifObjectDB<Sponsor> {

    private static final SponsorDB INSTANCE = new SponsorDB();

    private SponsorDB() {
        super(Constantes.COLLECTION_SPONSOR, new SponsorChecker());
    }

    public static SponsorDB getInstance(){
        return INSTANCE;
    }

    public boolean add(Sponsor sponsor) {
        return super.add(sponsor.toBasicDBObject(), Constantes.EMAIL_ATTRIBUTE_NAME);
    }

    public boolean remove(Sponsor sponsor) {
        return super.remove(sponsor.toBasicDBObject(), Constantes.EMAIL_ATTRIBUTE_NAME);
    }

    public boolean save(Sponsor sponsor) {
        return super.update(sponsor.toBasicDBObject(), Constantes.EMAIL_ATTRIBUTE_NAME);
    }

    public Sponsor get(String email) {
        BasicDBObject dbObject = get(Constantes.EMAIL_ATTRIBUTE_NAME, email);
        Sponsor sponsor = null;
        if (null != dbObject){
            sponsor = new Sponsor(dbObject);
        }
        return sponsor;
    }
}
