package models.objects.checker;

import models.exception.JCertifException;
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
	public void check(BasicDBObject objectToCheck) throws JCertifException {
		if (null == objectToCheck) {
            throw new JCertifInvalidRequestException(this, "Object cannot be null");
        }

        StringBuilder errorMessage = new StringBuilder();
        if (Tools.isBlankOrNull(objectToCheck.getString("code"))) {
        	errorMessage.append("Code");
        }
        
        if (Tools.isBlankOrNull(objectToCheck.getString("label"))) {
        	errorMessage.append(", Label");
        }
        
        if(!Tools.isBlankOrNull(errorMessage.toString())){
        	throw new JCertifInvalidRequestException(this, errorMessage.append(" cannot be null").toString());
        }
	}

	@Override
	public void updateCheck(BasicDBObject objectToCheck)
			throws JCertifException {
		throw new UnsupportedOperationException("Methode not implemented yet");
	}
}
