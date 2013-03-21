package models.objects.access;

import com.mongodb.BasicDBObject;
import models.objects.Sponsor;
import models.objects.checker.SponsorChecker;
import models.util.Constantes;

public final class SponsorDB extends JCertifObjectDB<Sponsor> {

    private static final SponsorDB INSTANCE = new SponsorDB();

    private SponsorDB() {
        super(Constantes.COLLECTION_SPONSOR, new SponsorChecker());
    }

    public static SponsorDB getInstance(){
        return INSTANCE;
    }

    public boolean add(Sponsor sponsor) {
        return super.add(sponsor.toBasicDBObject());
    }

    public boolean remove(Sponsor sponsor) {
        return super.remove(sponsor.toBasicDBObject(), "email");
    }

    public boolean save(Sponsor sponsor) {
        return super.save(sponsor.toBasicDBObject(), "email");
    }

    public Sponsor get(String email) {
        BasicDBObject dbObject = get("email", email);
        Sponsor sponsor = null;
        if (null != dbObject){
            sponsor = new Sponsor(dbObject);
        }
        return sponsor;
    }
}
