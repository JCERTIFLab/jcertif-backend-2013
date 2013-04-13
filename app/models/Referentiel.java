package models;

import static models.CheckerHelper.checkLabel;
import static models.CheckerHelper.checkNull;
import models.util.Constantes;

import com.mongodb.BasicDBObject;

public abstract class Referentiel extends JCertifModel {

    private String label;

    public Referentiel(BasicDBObject basicDBObject){
        super();
        this.label  = basicDBObject.getString(Constantes.LABEL_ATTRIBUTE_NAME);
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
