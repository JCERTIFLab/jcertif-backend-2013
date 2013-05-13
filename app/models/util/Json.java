package models.util;

import java.util.List;

import models.Model;
import models.ModelUtils;
import models.exception.JCertifException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

/**
 * @author Martial SOMDA
 *
 */
public final class Json {

	private Json(){		
	}
	
	public static String serialize(String stringToSerialize){
		return JSON.serialize(stringToSerialize);
	}
	
	public static String serialize(Model model){
		return JSON.serialize(model.toBasicDBObject());
	}
	
	public static JsonNode jacksonSerialize(Object object){
		return play.libs.Json.toJson(object);
	}
	
	public static <T> T jacksonDerialize(Class<T> clazz, String parsable){
		try {
			return new ObjectMapper().readValue(parsable, clazz);
		} catch (Exception e) {
			throw new JCertifException(e.getMessage(), e);
		}
	}
	
	public static <T extends Model> String serialize(List<T> models){
		StringBuilder buffer = new StringBuilder();
		boolean first = true;
		buffer.append("[ ");
        
		for(Model model : models){
			if (first){
				first = false;
			}
            else{
            	buffer.append(" , ");
            }
			
			JSON.serialize(model.toBasicDBObject(), buffer);
		}
		
		buffer.append("]");
		return buffer.toString();
	}
	
	public static <T extends Model> T parse(Class<T> clazz, String parsable){
		return ModelUtils.instanciate(clazz, BasicDBObject.class.cast(JSON.parse(parsable)));
	}
}
