package models.objects.checker;

import com.mongodb.BasicDBObject;

import models.exception.JCertifDuplicateObjectException;
import models.exception.JCertifInvalidRequestException;
import models.objects.Sponsor;
import models.objects.access.SponsorDB;
import models.util.Tools;

public class SponsorChecker extends Checker {

    @Override
    public final void check(BasicDBObject objectToCheck) {

        if (null == objectToCheck) {
            throw new JCertifInvalidRequestException(this, "Object cannot be null");
        }

        Sponsor sponsor = new Sponsor(objectToCheck);

        if (Tools.isBlankOrNull(sponsor.getEmail())) {
            throw new JCertifInvalidRequestException(this, "Email cannot be empty or null");
        }

        if (!Tools.isValidEmail(sponsor.getEmail())) {
            throw new JCertifInvalidRequestException(this, sponsor.getEmail() + " is not a valid email");
        }

        if (Tools.isBlankOrNull(sponsor.getName())) {
            throw new JCertifInvalidRequestException(this, "Name cannot be empty or null");
        }

        if (Tools.isBlankOrNull(sponsor.getLogo())) {
            throw new JCertifInvalidRequestException(this, "Logo cannot be empty or null");
        }

        if (Tools.isBlankOrNull(sponsor.getLevel())) {
            throw new JCertifInvalidRequestException(this, "Level cannot be empty or null");
        }

        if (Tools.isBlankOrNull(sponsor.getWebsite())) {
            throw new JCertifInvalidRequestException(this, "Website cannot be empty or null");
        }

        if (Tools.isBlankOrNull(sponsor.getCity())) {
            throw new JCertifInvalidRequestException(this, "City cannot be empty or null");
        }

        if (Tools.isBlankOrNull(sponsor.getCountry())) {
            throw new JCertifInvalidRequestException(this, "Country cannot be empty or null");
        }
    }

    @Override
    public final void updateCheck(BasicDBObject objectToCheck) {
    }

    @Override
    public final void deleteCheck(BasicDBObject objectToCheck) {
    }

    @Override
    public void addCheck(BasicDBObject objectToCheck) {
        BasicDBObject dbObject = SponsorDB.getInstance().get("email", objectToCheck.getString("email"));
        if (null != dbObject) {
            throw new JCertifDuplicateObjectException(this, "Sponsor '" + objectToCheck.getString("email") + "' already exists");
        }
    }
}
