package models.objects;

import com.mongodb.BasicDBObject;
import models.util.Constantes;

public abstract class Referentiel extends JCertifObject {

    private String label;

    public Referentiel(BasicDBObject basicDBObject){
        super();
        this.label  = basicDBObject.getString("label");
    }
    
    public Referentiel(String label1){
        super();
        this.label  = label1;
    }

    public final String getLabel() {
        return label;
    }

    public final void setLabel(String label1) {
        this.label = label1;
    }

    @Override
    public final BasicDBObject toBasicDBObject() {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put(Constantes.LABEL_ATTRIBUTE_NAME, label);
        return basicDBObject;
    }

}
