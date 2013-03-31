package models.objects.checker;

import static models.objects.checker.CheckerHelper.checkEmail;
import static models.objects.checker.CheckerHelper.checkNull;
import models.exception.JCertifInvalidRequestException;
import models.objects.Speaker;
import models.util.Tools;

import com.mongodb.BasicDBObject;

public class SpeakerChecker extends Checker {

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

        Speaker speaker = new Speaker(objectToCheck);

        if (Tools.isBlankOrNull(speaker.getEmail())) {
            throw new JCertifInvalidRequestException(this, "Email cannot be empty or null");
        }

        if (!Tools.isValidEmail(speaker.getEmail())) {
            throw new JCertifInvalidRequestException(this, speaker.getEmail() + " is not a valid email");
        }

        if (Tools.isBlankOrNull(speaker.getTitle())) {
            throw new JCertifInvalidRequestException(this, "Title cannot be empty or null");
        }

        if (Tools.isBlankOrNull(speaker.getLastname())) {
            throw new JCertifInvalidRequestException(this, "Lastname cannot be empty or null");
        }

        if (Tools.isBlankOrNull(speaker.getFirstname())) {
            throw new JCertifInvalidRequestException(this, "Firstname cannot be empty or null");
        }

        if (Tools.isBlankOrNull(speaker.getWebsite())) {
            throw new JCertifInvalidRequestException(this, "Website cannot be empty or null");
        }

        if (Tools.isBlankOrNull(speaker.getCity())) {
            throw new JCertifInvalidRequestException(this, "City cannot be empty or null");
        }

        if (Tools.isBlankOrNull(speaker.getCountry())) {
            throw new JCertifInvalidRequestException(this, "Country cannot be empty or null");
        }
    }
}
