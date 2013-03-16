package models.objects.access;

import com.mongodb.BasicDBObject;
import models.exception.JCertifException;
import models.objects.Sponsor;
import models.objects.checker.SponsorChecker;
import models.util.Constantes;

public final class SponsorDB extends JCertifObjectDB<Sponsor> {

    private static SponsorDB instance;

    private SponsorDB() {
        super(Constantes.COLLECTION_SPONSOR, new SponsorChecker());
    }

    public static SponsorDB getInstance(){
    	if(instance==null){
    		instance= new SponsorDB();
    	}
        return instance;
    }

    public boolean add(Sponsor sponsor) throws JCertifException {
        return super.add(sponsor.toBasicDBObject());
    }

    public boolean remove(Sponsor sponsor) throws JCertifException {
        return super.remove(sponsor.toBasicDBObject(), "email");
    }

    public boolean save(Sponsor sponsor) throws JCertifException {
        return super.save(sponsor.toBasicDBObject(), "email");
    }

    public Sponsor get(String email) throws JCertifException {
        BasicDBObject dbObject = get("email", email);
        Sponsor sponsor = null;
        if (null != dbObject) sponsor = new Sponsor(dbObject);
        return sponsor;
    }
}
