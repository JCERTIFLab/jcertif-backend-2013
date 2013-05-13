package models.oauth2;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MSOMDA
 *
 */
public enum GrantTypes {

	CODE("authorization_code"),
	RESOURCE_OWNER_CREDENTIALS("password"),
	CLIENT_CREDENTIALS("client_credentials"),
	REFRESH_TOKEN("refresh_token");
	
	private static Map<String, GrantTypes> ENUM_MAP;
	
	static {
		ENUM_MAP = new HashMap<String, GrantTypes>();
		for(GrantTypes grant : EnumSet.allOf(GrantTypes.class)){
			ENUM_MAP.put(grant.getCode(), grant);
		}
	}
	
	private String code;
	
	private GrantTypes(String code){
		this.code = code;
	}
	
	public String getCode(){
		return code;
	}
	
	public static GrantTypes fromValue(String grantCode){
		return ENUM_MAP.get(grantCode);
	}
}
