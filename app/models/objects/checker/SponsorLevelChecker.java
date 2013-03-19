package models.objects.checker;

import models.exception.JCertifDuplicateObjectException;
import models.exception.JCertifException;
import models.exception.JCertifObjectNotFoundException;
import models.objects.access.SponsorLevelDB;

import com.mongodb.BasicDBObject;

/**
 * <p>Implémentation d'un {@link ReferentielChecker} pour la validation
 * des beans de type {@link SponsorLevel}.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class SponsorLevelChecker extends ReferentielChecker{

	@Override
	public void deleteCheck(BasicDBObject objectToCheck)
			throws JCertifException {
		BasicDBObject dbObject = SponsorLevelDB.getInstance().get("label", objectToCheck.getString("label"));
        if (null == dbObject) {
            throw new JCertifObjectNotFoundException(this, "Sponsor level '" + objectToCheck.getString("label") + "' already exists");
        }
	}

	@Override
	public void addCheck(BasicDBObject objectToCheck) throws JCertifException {
		BasicDBObject dbObject = SponsorLevelDB.getInstance().get("label", objectToCheck.getString("label"));
        if (null != dbObject) {
            throw new JCertifDuplicateObjectException(this, "Sponsor level '" + objectToCheck.getString("label") + "' already exists");
        }
	}
}
