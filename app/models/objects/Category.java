package models.objects;

import com.mongodb.BasicDBObject;

public class Category extends JCertifObject {

    private String label;

    public Category(BasicDBObject basicDBObject) {
        this.label = basicDBObject.getString("label");
    }

    public final String getLabel() {
        return label;
    }

    public final void setLabel(final String label1) {
        this.label = label1;
    }

    @Override
    public final BasicDBObject toBasicDBObject() {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("label", getLabel());
        return basicDBObject;
    }
}
