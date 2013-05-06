package models;

import models.exception.JCertifDuplicateObjectException;
import models.util.Constantes;
import models.validation.Constraints.NotBlank;

import com.mongodb.BasicDBObject;

public abstract class Referentiel extends JCertifModel {

	@NotBlank(propertyName="Label")
    private String label;

    public Referentiel(BasicDBObject basicDBObject){
    	super(basicDBObject);
        this.label  = basicDBObject.getString(Constantes.LABEL_ATTRIBUTE_NAME);
    }

    public final String getLabel() {
        return label;
    }

    public final void setLabel(String label1) {
        this.label = label1;
    }

    @Override
    public final BasicDBObject toBasicDBObject() {
        BasicDBObject basicDBObject = super.toBasicDBObject();
        basicDBObject.put(Constantes.LABEL_ATTRIBUTE_NAME, label);
        return basicDBObject;
    }
    
    @Override
    public int create() {
    	if(getFinder().find(getClass(),Constantes.LABEL_ATTRIBUTE_NAME,label) != null){
    		throw new JCertifDuplicateObjectException(getClass(), label);
    	}
    	return super.create();
    }

}
