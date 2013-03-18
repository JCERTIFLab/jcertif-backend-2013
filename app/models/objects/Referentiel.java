package models.objects;

import com.mongodb.BasicDBObject;

public class Referentiel extends JCertifObject {

    private String code;
    private String label;

    public Referentiel(BasicDBObject basicDBObject){
        this.code = basicDBObject.getString("code");
        this.label  = basicDBObject.getString("label");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code1) {
        this.code = code1;
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
        basicDBObject.put("code", code);
        basicDBObject.put("label", label);
        return basicDBObject;
    }

}
