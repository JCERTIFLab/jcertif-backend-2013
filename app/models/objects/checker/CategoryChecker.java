package models.objects.checker;

import models.exception.JCertifDuplicateObjectException;
import models.exception.JCertifException;
import models.exception.JCertifObjectNotFoundException;
import models.objects.Category;
import models.objects.access.CategoryDB;

import com.mongodb.BasicDBObject;
import models.util.Constantes;

/**
 * <p>Implémentation d'un {@link ReferentielChecker} pour la validation
 * des beans de type {@link Category}.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class CategoryChecker extends ReferentielChecker {

	@Override
	public final void deleteCheck(BasicDBObject objectToCheck)
			throws JCertifException {
		BasicDBObject dbObject = CategoryDB.getInstance().get(Constantes.LABEL_ATTRIBUTE_NAME, objectToCheck.getString(Constantes.LABEL_ATTRIBUTE_NAME));
        if (null == dbObject) {
            throw new JCertifObjectNotFoundException(this, "Category '" + objectToCheck.getString(Constantes.LABEL_ATTRIBUTE_NAME) + "' already exists");
        }
	}

	@Override
	public final void addCheck(BasicDBObject objectToCheck) throws JCertifException {
		BasicDBObject dbObject = CategoryDB.getInstance().get(Constantes.LABEL_ATTRIBUTE_NAME, objectToCheck.getString(Constantes.LABEL_ATTRIBUTE_NAME));
        if (null != dbObject) {
            throw new JCertifDuplicateObjectException(this, "Category '" + objectToCheck.getString(Constantes.LABEL_ATTRIBUTE_NAME) + "' already exists");
        }
	}

}
