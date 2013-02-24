package objects.access;

import com.mongodb.BasicDBObject;
import exception.JCertifException;
import objects.Sponsor;
import objects.checker.SponsorChecker;
import util.Constantes;

public class SponsorDB extends JCertifObjectDB {

    public static SponsorDB sponsorDB = new SponsorDB();

    public SponsorDB() {
        super(Constantes.JCERTIFBACKEND_COLLECTIONNAME_SPONSOR, new SponsorChecker());
    }

    public boolean add(Sponsor sponsor) throws JCertifException {
        return add(sponsor.toBasicDBObject());
    }

    public boolean remove(Sponsor sponsor) throws JCertifException {
        return remove(sponsor.toBasicDBObject(), "email");
    }

    public boolean update(Sponsor sponsor) throws JCertifException {
        return update(sponsor.toBasicDBObject(), "email");
    }

    public Sponsor get(String email) throws JCertifException {
        BasicDBObject dbObject = get("email", email);
        Sponsor sponsor = null;
        if (null != dbObject) sponsor = new Sponsor(dbObject);
        return sponsor;
    }
}
