package models.objects;

import com.mongodb.BasicDBObject;

public abstract class Referentiel extends JCertifObject {

    private String label;

    public Referentiel(BasicDBObject basicDBObject){
        this.label  = basicDBObject.getString("label");
    }
    
    public Referentiel(String label){
        this.label  = label;
    }
    
    public Referentiel(){}

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public BasicDBObject toBasicDBObject() {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("label", label);
        return basicDBObject;
    }

}
