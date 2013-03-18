package models.objects;

import com.mongodb.BasicDBObject;

public abstract class Referentiel extends JCertifObject {

    private String code;
    private String label;

    public Referentiel(BasicDBObject basicDBObject){
        this.code = basicDBObject.getString("code");
        this.label  = basicDBObject.getString("label");
    }
    
    public Referentiel(String code, String label){
        this.code = code;
        this.label  = label;
    }

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
