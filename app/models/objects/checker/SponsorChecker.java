package models.objects.checker;

import static models.objects.checker.CheckerHelper.checkEmail;
import static models.objects.checker.CheckerHelper.checkNull;
import models.exception.JCertifInvalidRequestException;
import models.objects.Sponsor;
import models.util.Tools;

import com.mongodb.BasicDBObject;

public class SponsorChecker extends Checker {

    @Override
    public final void updateCheck(BasicDBObject objectToCheck) {
    	checkNull(objectToCheck);
		checkEmail(objectToCheck);
    }

    @Override
    public final void deleteCheck(BasicDBObject objectToCheck) {
    	checkNull(objectToCheck);
		checkEmail(objectToCheck);
    }

    @Override
    public void addCheck(BasicDBObject objectToCheck) {
    	checkNull(objectToCheck);
    	
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
}
