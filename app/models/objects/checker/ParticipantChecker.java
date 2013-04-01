package models.objects.checker;

import static models.objects.checker.CheckerHelper.checkEmail;
import static models.objects.checker.CheckerHelper.checkNull;
import static models.objects.checker.CheckerHelper.checkPassword;
import models.exception.JCertifInvalidRequestException;
import models.objects.Participant;
import models.util.Tools;
import models.util.crypto.CryptoUtil;

import com.mongodb.BasicDBObject;

public class ParticipantChecker extends Checker {

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
    public final void addCheck(BasicDBObject objectToCheck) {
    	checkNull(objectToCheck);
    	checkEmail(objectToCheck);

        Participant participant = new Participant(objectToCheck);

        checkPassword(participant.getPassword(), null, false);
        
        //after check the password compliance according to policy we encrypt
        objectToCheck.put("password", CryptoUtil.getSaltedPassword(participant.getPassword().getBytes()));
        
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
}
