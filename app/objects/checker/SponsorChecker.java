package objects.checker;

import com.mongodb.BasicDBObject;
import exception.JCertifException;
import objects.Sponsor;
import objects.access.SponsorDB;
import util.Tools;

public class SponsorChecker extends Checker {

    @Override
    public void check(BasicDBObject objectToCheck) throws JCertifException {

        if (null == objectToCheck) {
            throw new JCertifException(this, "Object cannot be null");
        }

        Sponsor sponsor = new Sponsor(objectToCheck);

        if (Tools.isBlankOrNull(sponsor.getEmail())) {
            throw new JCertifException(this, "Email cannot be empty or null");
        }

        if (!Tools.isValidEmail(sponsor.getEmail())) {
            throw new JCertifException(this, sponsor.getEmail() + " is not a valid email");
        }

        if (Tools.isBlankOrNull(sponsor.getName())) {
            throw new JCertifException(this, "Name cannot be empty or null");
        }

        if (Tools.isBlankOrNull(sponsor.getLogo())) {
            throw new JCertifException(this, "Logo cannot be empty or null");
        }

        if (Tools.isBlankOrNull(sponsor.getLevel())) {
            throw new JCertifException(this, "Level cannot be empty or null");
        }

        if (Tools.isBlankOrNull(sponsor.getWebsite())) {
            throw new JCertifException(this, "Website cannot be empty or null");
        }

        if (Tools.isBlankOrNull(sponsor.getCity())) {
            throw new JCertifException(this, "City cannot be empty or null");
        }

        if (Tools.isBlankOrNull(sponsor.getCountry())) {
            throw new JCertifException(this, "Country cannot be empty or null");
        }
    }

    @Override
    public void updateCheck(BasicDBObject objectToCheck) throws JCertifException {
    }

    @Override
    public void deleteCheck(BasicDBObject objectToCheck) throws JCertifException {
    }

    @Override
    public void addCheck(BasicDBObject objectToCheck) throws JCertifException {
        BasicDBObject dbObject = SponsorDB.getInstance().get("email", objectToCheck.getString("email"));
        if (null != dbObject) {
            throw new JCertifException(this, objectToCheck.getString("email") + " already exists");
        }
    }
}
