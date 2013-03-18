package models.objects.checker;

import models.exception.JCertifDuplicateObjectException;
import models.exception.JCertifException;
import models.exception.JCertifObjectNotFoundException;
import models.objects.Category;
import models.objects.access.CategoryDB;

import com.mongodb.BasicDBObject;

/**
 * <p>Implémentation d'un {@link ReferentielChecker} pour la validation
 * des beans de type {@link Category}.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class CategoryChecker extends ReferentielChecker {

	@Override
	public void deleteCheck(BasicDBObject objectToCheck)
			throws JCertifException {
		BasicDBObject dbObject = CategoryDB.getInstance().get("code", objectToCheck.getString("code"),objectToCheck.getString("type"));
        if (null == dbObject) {
            throw new JCertifObjectNotFoundException(this, "Category '" + objectToCheck.getString("code") + "' already exists");
        }
	}

	@Override
	public void addCheck(BasicDBObject objectToCheck) throws JCertifException {
		BasicDBObject dbObject = CategoryDB.getInstance().get("code", objectToCheck.getString("code"),objectToCheck.getString("type"));
        if (null != dbObject) {
            throw new JCertifDuplicateObjectException(this, "Category '" + objectToCheck.getString("code") + "' already exists");
        }
	}

}
