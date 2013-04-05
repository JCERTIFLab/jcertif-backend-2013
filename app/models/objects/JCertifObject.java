package models.objects;

import models.objects.access.JCertifObjectDB;

import com.mongodb.BasicDBObject;

public abstract class JCertifObject {
    public abstract BasicDBObject toBasicDBObject();
    public abstract String getKeyName();
    protected abstract <T extends JCertifObject> JCertifObjectDB<T> getDBObject();
    
    public boolean add() {
		return getDBObject().add(toBasicDBObject(), getKeyName());
	}
    
    public boolean save(){
    	return getDBObject().update(toBasicDBObject(), getKeyName());
    }
    
    public boolean remove(){
    	return getDBObject().remove(toBasicDBObject(), getKeyName());
    }
}
