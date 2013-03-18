package models.objects.access;

import com.mongodb.BasicDBObject;
import models.exception.JCertifException;
import models.objects.Category;
import models.objects.checker.CategoryChecker;
import models.util.Constantes;

public final class CategoryDB extends JCertifObjectDB<Category> {

    private static CategoryDB instance;

    public CategoryDB() {
        super(Constantes.COLLECTION_CATEGORY,
                new CategoryChecker());
    }

    public static CategoryDB getInstance() {
        if (instance == null) {
            instance = new CategoryDB();
        }
        return instance;
    }

    public boolean add(Category category) throws JCertifException {
        return super.add(category.toBasicDBObject());
    }

    public boolean remove(Category category) throws JCertifException {
        return remove(category.toBasicDBObject(), "label");
    }

    public boolean save(Category category) throws JCertifException {
        return save(category.toBasicDBObject(), "label");
    }

    public Category get(String label) throws JCertifException {
        BasicDBObject dbObject = get("label", label);
        Category category = null;
        if (null != dbObject){
            category = new Category(dbObject);
        }
        return category;
    }

}
