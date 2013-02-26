package objects.access;

import com.mongodb.BasicDBObject;
import exception.JCertifException;
import objects.Sponsor;
import objects.checker.SponsorChecker;
import util.Constantes;

public class SponsorDB extends JCertifObjectDB<Sponsor> {

    private static SponsorDB instance;

    private SponsorDB() {
        super(Constantes.JCERTIFBACKEND_COLLECTIONNAME_SPONSOR, new SponsorChecker());
    }

    public static synchronized SponsorDB getInstance(){
    	if(instance==null){
    		instance= new SponsorDB();
    	} return instance;
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
