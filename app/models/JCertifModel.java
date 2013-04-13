package models;


public abstract class JCertifModel extends Model {
    
    public boolean create() {
		return add(toBasicDBObject(), getKeyName());
	}
    
    public boolean save(){
    	return update(toBasicDBObject(), getKeyName());
    }
    
    public boolean remove(){
    	return remove(toBasicDBObject(), getKeyName());
    }
}
