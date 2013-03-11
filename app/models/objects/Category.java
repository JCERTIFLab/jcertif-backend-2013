package models.objects;

import com.mongodb.BasicDBObject;

public class Category extends JCertifObject {

    private String label;

    public Category(BasicDBObject basicDBObject) {
        this.setLabel(basicDBObject.getString("label"));
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public BasicDBObject toBasicDBObject() {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("label", getLabel());
        return basicDBObject;
    }
}
