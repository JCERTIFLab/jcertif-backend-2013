package models.objects.checker;

import models.exception.JCertifInvalidRequestException;
import models.objects.Referentiel;
import models.util.Tools;

import com.mongodb.BasicDBObject;

/**
 * Objet de type {@link Checker} permettant de valider les 
 * instances de bean {@link Referentiel}.
 * 
 * @author Martial SOMDA
 *
 */
public abstract class ReferentielChecker extends Checker {

	@Override
	public final void check(BasicDBObject objectToCheck) {
		if (null == objectToCheck) {
            throw new JCertifInvalidRequestException(this, "Object cannot be null");
        }
        
        if(Tools.isBlankOrNull(objectToCheck.getString("label"))){
        	throw new JCertifInvalidRequestException(this, "Label cannot be empty or null");
        }
	}

	@Override
	public final void updateCheck(BasicDBObject objectToCheck) {
	}
}
