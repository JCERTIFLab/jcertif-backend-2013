package models.objects.checker;

import models.exception.JCertifDuplicateObjectException;
import models.exception.JCertifException;
import models.exception.JCertifObjectNotFoundException;
import models.objects.access.SponsorLevelDB;

import com.mongodb.BasicDBObject;
import models.util.Constantes;

/**
 * <p>Implémentation d'un {@link ReferentielChecker} pour la validation
 * des beans de type {@link SponsorLevel}.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class SponsorLevelChecker extends ReferentielChecker{

	@Override
	public final void deleteCheck(BasicDBObject objectToCheck)
			throws JCertifException {
		BasicDBObject dbObject = SponsorLevelDB.getInstance().get(Constantes.LABEL_ATTRIBUTE_NAME, objectToCheck.getString(Constantes.LABEL_ATTRIBUTE_NAME));
        if (null == dbObject) {
            throw new JCertifObjectNotFoundException(this, "Sponsor level '" + objectToCheck.getString(Constantes.LABEL_ATTRIBUTE_NAME) + "' doesn't exists");
        }
	}

	@Override
	public final void addCheck(BasicDBObject objectToCheck) throws JCertifException {
		BasicDBObject dbObject = SponsorLevelDB.getInstance().get(Constantes.LABEL_ATTRIBUTE_NAME, objectToCheck.getString(Constantes.LABEL_ATTRIBUTE_NAME));
        if (null != dbObject) {
            throw new JCertifDuplicateObjectException(this, "Sponsor level '" + objectToCheck.getString(Constantes.LABEL_ATTRIBUTE_NAME) + "' already exists");
        }
	}
}
