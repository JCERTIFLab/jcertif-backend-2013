package models.objects;

import com.mongodb.BasicDBObject;

public class SessionStatus extends JCertifObject {

    private String label;

    public SessionStatus(BasicDBObject basicDBObject) {
        this.label = basicDBObject.getString("label");
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label1) {
        this.label = label1;
    }

    @Override
    public BasicDBObject toBasicDBObject() {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("label", getLabel());
        return basicDBObject;
    }

}
