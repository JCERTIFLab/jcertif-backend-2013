package models.objects.checker;

import com.mongodb.BasicDBObject;
import models.exception.JCertifException;
import models.objects.Category;
import models.objects.access.CategoryDB;
import models.util.Tools;

public class CategoryChecker extends Checker {

    @Override
    public void check(BasicDBObject objectToCheck) throws JCertifException {

        if (null == objectToCheck) {
            throw new JCertifException(this, "Object cannot be null");
        }

        Category category = new Category(objectToCheck);

        if (Tools.isBlankOrNull(category.getLabel())) {
            throw new JCertifException(this, "Label cannot be empty or null");
        }
    }

    @Override
    public void updateCheck(BasicDBObject objectToCheck) throws JCertifException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteCheck(BasicDBObject objectToCheck) throws JCertifException {
        BasicDBObject dbObject = CategoryDB.getInstance().get("label", objectToCheck.getString("label"));
        if (null == dbObject) {
            throw new JCertifException(this, "Category '" + objectToCheck.getString("label") + "' does not exist");
        }
    }

    @Override
    public void addCheck(BasicDBObject objectToCheck) throws JCertifException {
        BasicDBObject dbObject = CategoryDB.getInstance().get("label", objectToCheck.getString("label"));
        if (null != dbObject) {
            throw new JCertifException(this, "Category '" + objectToCheck.getString("label") + "' already exists");
        }
    }
}
