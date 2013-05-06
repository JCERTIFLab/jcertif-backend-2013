package models;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import models.exception.JCertifException;
import models.util.Tools;

import com.mongodb.BasicDBObject;


public abstract class JCertifModel extends Model {

	public JCertifModel(BasicDBObject basicDBObject){
		super(basicDBObject);
	}

	public int create() {
		setVersion(String.format("%02d", 1));
		setDeleted(false);
		return super.add();
	}
    
    public int save(){
    	setDeleted(false);
    	int id = super.update();
    	setVersion(Integer.toString(id));
		return id;
    }
    
    public int remove(){
    	setDeleted(true);
    	int id = super.update();
    	setVersion(Integer.toString(id));
		return id;
    }
    
    public void merge(BasicDBObject objectToUpdate) {

		Map<String, Field> fieldMap = new HashMap<String, Field>();
		Class<?> clazz = this.getClass();
		while(null != clazz){
			for(Field field : clazz.getDeclaredFields()){
				field.setAccessible(true);
				fieldMap.put(field.getName(), field);
			}
			clazz = clazz.getSuperclass();
		}

		Map<String, Object> fieldToUpdateMap = objectToUpdate.toMap();
		try {
			for (Entry<String, Object> entry : (Set<Entry<String, Object>>)fieldToUpdateMap.entrySet()) {
				if(entry.getValue() != null && 
						(!(entry.getValue() instanceof List<?>)||
						((entry.getValue() instanceof List<?>) &&
								!Tools.isBlankOrNull((ArrayList<?>)entry.getValue())))){
					
					if(null != fieldMap.get(entry.getKey())){	
							fieldMap.get(entry.getKey()).set(this, entry.getValue());
					}
				}
			}
		} catch (IllegalArgumentException e) {
			throw new JCertifException(e.getMessage(),e);
		} catch (IllegalAccessException e) {
			throw new JCertifException(e.getMessage(),e);
		}


	}
    
    @Override
	public String toString() {
		return toBasicDBObject().toString();
	}
}
