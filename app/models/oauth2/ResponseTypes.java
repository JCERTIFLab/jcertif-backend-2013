package models.oauth2;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MSOMDA
 *
 */
public enum ResponseTypes {

	/**
	 * response type allowed for code grant
	 */
	CODE("code"),
	/**
	 * response type allowed for implicit grant
	 */
	TOKEN("token");
	
	private static Map<String, ResponseTypes> ENUM_MAP;
	
	static {
		ENUM_MAP = new HashMap<String, ResponseTypes>();
		for(ResponseTypes responseType : EnumSet.allOf(ResponseTypes.class)){
			ENUM_MAP.put(responseType.getCode(), responseType);
		}
	}
	
	private String code;
	
	private ResponseTypes(String code){
		this.code = code;
	}
	
	public String getCode(){
		return code;
	}
	
	public static ResponseTypes fromValue(String responseTypeCode){
		return ENUM_MAP.get(responseTypeCode);
	}
}
