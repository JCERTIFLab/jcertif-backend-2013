package models;

import static models.CheckHelper.checkLabel;
import static models.CheckHelper.checkNull;
import models.util.Constantes;

import com.mongodb.BasicDBObject;

public abstract class Referentiel extends JCertifModel {

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
	public String getKeyName() {
		return Constantes.LABEL_ATTRIBUTE_NAME;
	}
    
    public final void check(BasicDBObject objectToCheck) {
		checkNull(objectToCheck);
		checkLabel(objectToCheck);
	}

	@Override
	public final void updateCheck(BasicDBObject objectToCheck) {
		check(objectToCheck);
	}
	
	@Override
	public final void deleteCheck(BasicDBObject objectToCheck) {
		check(objectToCheck);
	}

	@Override
	public final void addCheck(BasicDBObject objectToCheck) {
		check(objectToCheck);
	}

}
