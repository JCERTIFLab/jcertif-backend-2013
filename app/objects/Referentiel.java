package objects;

import com.mongodb.BasicDBObject;

public class Referentiel extends JCertfifObject {

    private String code;
    private String label;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        basicDBObject.put("code", code);
        basicDBObject.put("label", label);
        return basicDBObject;
    }
}
