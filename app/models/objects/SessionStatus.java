package models.objects;

import com.mongodb.BasicDBObject;

public class SessionStatus extends JCertfifObject {

    private String label;

    public SessionStatus(BasicDBObject basicDBObject) {
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
