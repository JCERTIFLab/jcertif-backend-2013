package models;

import com.mongodb.BasicDBObject;


public abstract class JCertifModel extends Model {

	public JCertifModel(BasicDBObject basicDBObject){
		super(basicDBObject);
	}

	public int create() {
		setVersion(String.format("%02d", 1));
		setDeleted(false);
		return add(toBasicDBObject(), getKeyName());
	}
    
    public int save(){
    	setDeleted(false);
    	int id = update(toBasicDBObject(), getKeyName());
    	setVersion(Integer.toString(id));
		return id;
    }
    
    public int remove(){
    	setDeleted(true);
    	int id = update(toBasicDBObject(), getKeyName());
    	setVersion(Integer.toString(id));
		return id;
    }
}
