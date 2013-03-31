package models.objects;

import java.util.List;

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
    
    @SuppressWarnings("unchecked")
	public <T extends JCertifObject> List<T> listAll(){
    	return (List<T>) getDBObject().listAll();
    }
}
