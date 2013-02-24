package objects.checker;

import com.mongodb.BasicDBObject;
import exception.JCertifException;
import objects.Participant;
import objects.access.ParticipantDB;
import util.Tools;

public class ParticipantChecker extends Checker {

    @Override
    public void check(BasicDBObject objectToCheck) throws JCertifException {

        if (null == objectToCheck) {
            throw new JCertifException(this, "Object cannot be null");
        }

        Participant participant = new Participant(objectToCheck);

        if (Tools.isBlankOrNull(participant.getEmail())) {
            throw new JCertifException(this, "Email cannot be empty or null");
        }

        if (!Tools.isValidEmail(participant.getEmail())) {
            throw new JCertifException(this, participant.getEmail() + " is not a valid email");
        }

        if (Tools.isBlankOrNull(participant.getTitle())) {
            throw new JCertifException(this, "Title cannot be empty or null");
        }

        if (Tools.isBlankOrNull(participant.getLastname())) {
            throw new JCertifException(this, "Lastname cannot be empty or null");
        }

        if (Tools.isBlankOrNull(participant.getFirstname())) {
            throw new JCertifException(this, "Firstname cannot be empty or null");
        }

        if (Tools.isBlankOrNull(participant.getCity())) {
            throw new JCertifException(this, "City cannot be empty or null");
        }

        if (Tools.isBlankOrNull(participant.getCountry())) {
            throw new JCertifException(this, "Country cannot be empty or null");
        }

    }

    @Override
    public void updateCheck(BasicDBObject objectToCheck) throws JCertifException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteCheck(BasicDBObject objectToCheck) throws JCertifException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addCheck(BasicDBObject objectToCheck) throws JCertifException {

        BasicDBObject dbObject = ParticipantDB.participantDB.get("email", objectToCheck.getString("email"));
        if (null != dbObject) {
            throw new JCertifException(this, "Participant \"" + objectToCheck.getString("email") + "\" already exists");
        }

        //On vérifie la présence du mot de passe uniquement à la création d'un participant
        if (Tools.isBlankOrNull(objectToCheck.getString("password"))) {
            throw new JCertifException(this, "Password cannot be empty or null");
        }
    }
}
