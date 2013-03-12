package models.objects.checker;

import com.mongodb.BasicDBObject;
import models.exception.JCertifException;
import models.objects.Speaker;
import models.objects.access.SpeakerDB;
import models.util.Tools;

public class SpeakerChecker extends Checker {

    @Override
    public void check(BasicDBObject objectToCheck) throws JCertifException {

        if (null == objectToCheck) {
            throw new JCertifException(this, "Object cannot be null");
        }

        Speaker speaker = new Speaker(objectToCheck);

        if (Tools.isBlankOrNull(speaker.getEmail())) {
            throw new JCertifException(this, "Email cannot be empty or null");
        }

        if (!Tools.isValidEmail(speaker.getEmail())) {
            throw new JCertifException(this, speaker.getEmail() + " is not a valid email");
        }

        if (Tools.isBlankOrNull(speaker.getTitle())) {
            throw new JCertifException(this, "Title cannot be empty or null");
        }

        if (Tools.isBlankOrNull(speaker.getLastname())) {
            throw new JCertifException(this, "Lastname cannot be empty or null");
        }

        if (Tools.isBlankOrNull(speaker.getFirstname())) {
            throw new JCertifException(this, "Firstname cannot be empty or null");
        }

        if (Tools.isBlankOrNull(speaker.getWebsite())) {
            throw new JCertifException(this, "Website cannot be empty or null");
        }

        if (Tools.isBlankOrNull(speaker.getCity())) {
            throw new JCertifException(this, "City cannot be empty or null");
        }

        if (Tools.isBlankOrNull(speaker.getCountry())) {
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

        BasicDBObject dbObject = SpeakerDB.getInstance().get("email", objectToCheck.getString("email"));
        if (null != dbObject) {
            throw new JCertifException(this, "Speaker " + objectToCheck.getString("email") + " already exists");
        }
    }
}
