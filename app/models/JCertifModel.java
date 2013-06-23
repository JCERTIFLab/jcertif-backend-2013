package models;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
    
    public int delete(){
    	setDeleted(true);
    	int id = super.update();
    	setVersion(Integer.toString(id));
		return id;
    }
    
    public int remove() {
    	return super.suppress();
    }
    
    public <T extends Model> void merge(T objectToUpdate) {

		Map<String, Field> fieldMap = new HashMap<String, Field>();
		Class<?> clazz = this.getClass();
		while(null != clazz){
			for(Field field : clazz.getDeclaredFields()){
				field.setAccessible(true);
				fieldMap.put(field.getName(), field);
			}
			clazz = clazz.getSuperclass();
		}

		try {
			Map<String, Object> fieldToUpdateValuesMap = getFieldsMap(objectToUpdate);
			
			boolean valueExists = false;
			boolean valueIsNotEmpty = false;
			boolean fieldExists = false;
			for (Entry<String, Object> entry : (Set<Entry<String, Object>>)fieldToUpdateValuesMap.entrySet()) {
				valueExists = entry.getValue() != null;
				valueIsNotEmpty = !(entry.getValue() instanceof List<?>)||
				((entry.getValue() instanceof List<?>) &&
						!Tools.isBlankOrNull((ArrayList<?>)entry.getValue()));
				fieldExists = null != fieldMap.get(entry.getKey());
				
				if(valueExists && valueIsNotEmpty && fieldExists){
						fieldMap.get(entry.getKey()).set(this, entry.getValue());
				}
			}
		} catch (Exception e) {
			throw new JCertifException(e.getMessage(),e);
		}


	}
    
    private <T extends Model> Map<String, Object> getFieldsMap(T objectToUpdate) throws IllegalAccessException {
    	Map<String, Object> fieldValuesMap = new HashMap<String, Object>();
    	
    	Class<?> clazz = objectToUpdate.getClass();
		while(null != clazz){
			for(Field field : clazz.getDeclaredFields()){
				
				if (!Modifier.isFinal(field.getModifiers())){
					field.setAccessible(true);
					fieldValuesMap.put(field.getName(), field.get(objectToUpdate));
				}
				
			}
			clazz = clazz.getSuperclass();
		}
    	
		return fieldValuesMap;    	
    }
    
    @Override
	public String toString() {
		return toBasicDBObject().toString();
	}
}
