package models.objects.checker;

import com.mongodb.BasicDBObject;

import models.exception.JCertifDuplicateObjectException;
import models.exception.JCertifInvalidRequestException;
import models.objects.Participant;
import models.objects.access.ParticipantDB;
import models.util.Tools;

public class ParticipantChecker extends Checker {

    @Override
    public final void check(BasicDBObject objectToCheck) {

        if (null == objectToCheck) {
            throw new JCertifInvalidRequestException(this, "Object cannot be null");
        }

        Participant participant = new Participant(objectToCheck);

        if (Tools.isBlankOrNull(participant.getEmail())) {
            throw new JCertifInvalidRequestException(this, "Email cannot be empty or null");
        }

        if (!Tools.isValidEmail(participant.getEmail())) {
            throw new JCertifInvalidRequestException(this, participant.getEmail() + " is not a valid email");
        }

        if (Tools.isBlankOrNull(participant.getTitle())) {
            throw new JCertifInvalidRequestException(this, "Title cannot be empty or null");
        }

        if (Tools.isBlankOrNull(participant.getLastname())) {
            throw new JCertifInvalidRequestException(this, "Lastname cannot be empty or null");
        }

        if (Tools.isBlankOrNull(participant.getFirstname())) {
            throw new JCertifInvalidRequestException(this, "Firstname cannot be empty or null");
        }

        if (Tools.isBlankOrNull(participant.getCity())) {
            throw new JCertifInvalidRequestException(this, "City cannot be empty or null");
        }

        if (Tools.isBlankOrNull(participant.getCountry())) {
            throw new JCertifInvalidRequestException(this, "Country cannot be empty or null");
        }

    }

    @Override
    public final void updateCheck(BasicDBObject objectToCheck) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public final void deleteCheck(BasicDBObject objectToCheck) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public final void addCheck(BasicDBObject objectToCheck) {

        BasicDBObject dbObject = ParticipantDB.getInstance().get("email", objectToCheck.getString("email"));
        if (null != dbObject) {
            throw new JCertifDuplicateObjectException(this, "Participant '" + objectToCheck.getString("email") + "' already exists");
        }
    }
}
