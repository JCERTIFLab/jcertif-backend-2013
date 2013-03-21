package models.objects.checker;

import com.mongodb.BasicDBObject;

import models.exception.JCertifDuplicateObjectException;
import models.exception.JCertifInvalidRequestException;
import models.objects.Speaker;
import models.objects.access.SpeakerDB;
import models.util.Tools;

public class SpeakerChecker extends Checker {

    @Override
    public final void check(BasicDBObject objectToCheck) {

        if (null == objectToCheck) {
            throw new JCertifInvalidRequestException(this, "Object cannot be null");
        }

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

    @Override
    public final void updateCheck(BasicDBObject objectToCheck) {

    }

    @Override
    public final void deleteCheck(BasicDBObject objectToCheck) {

    }

    @Override
    public void addCheck(BasicDBObject objectToCheck) {

        BasicDBObject dbObject = SpeakerDB.getInstance().get("email", objectToCheck.getString("email"));
        if (null != dbObject) {
            throw new JCertifDuplicateObjectException(this, "Speaker '" + objectToCheck.getString("email") + "' already exists");
        }
    }
}
