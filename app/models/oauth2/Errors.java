package models.oauth2;

/**
 * @author Martial SOMDA
 *
 */
public enum Errors {

	INVALID_REQUEST("invalid_request"),
	UNAUTHORIZED_CLIENT("unauthorized_client"),
	ACCESS_DENIED("access_denied"),
	UNSUPORTED_RESPONSE_TYPE("unsupported_response_type"),
	INVALID_SCOPE("invalid_scope"),
	UNEXPECTED("server_error"),
	UNAVAILABLE("temporarily_unavailable");
	
	private String code;
	
	private Errors(String code){
		this.code = code;
	}
	
	public String getCode(){
		return code;
	}
}
