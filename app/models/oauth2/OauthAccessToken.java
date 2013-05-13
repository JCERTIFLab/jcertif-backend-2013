package models.oauth2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.JCertifModel;
import models.util.Constantes;
import models.util.Tools;
import models.validation.Constraints.NotBlank;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

/**
 * @author Martial SOMDA
 *
 */
public class OauthAccessToken extends JCertifModel {

	@NotBlank(propertyName="Access Token")
	private String accessToken;
	@NotBlank(propertyName="Token Type")
	private String tokenType;
	@NotBlank(propertyName="Expire In")
	private String expiresIn;
	private String expirationDate;
	private String refreshToken;
	@NotBlank(propertyName="Scope")
	private Set<String> scope;
	@NotBlank(propertyName="Client ID")
	private String clientId;
	private String user;
	
	public OauthAccessToken() {
		super(new BasicDBObject());
	}
	
	public OauthAccessToken(BasicDBObject basicDBObject) {
		super(basicDBObject);
		this.accessToken = basicDBObject.getString("access_token");
		this.tokenType = basicDBObject.getString("token_type");
		this.expiresIn = basicDBObject.getString("expires_in");
		this.expirationDate = basicDBObject.getString("expirationDate");
		this.refreshToken = basicDBObject.getString("refresh_token");
		this.scope = Tools.basicDBListToJavaSet((BasicDBList)basicDBObject.get("scope"));
		this.clientId = basicDBObject.getString("client_id");
		this.user = basicDBObject.getString("user");
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expireIn) {
		this.expiresIn = expireIn;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Set<String> getScope() {
		return scope;
	}

	public void setScope(Set<String> scope) {
		this.scope = scope;
	}
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public BasicDBObject toBasicDBObject() {
		BasicDBObject dbObject = super.toBasicDBObject();
		dbObject.put("access_token", getAccessToken());
		dbObject.put("token_type", getTokenType());
		dbObject.put("expires_in", getExpiresIn());
		dbObject.put("refresh_token", getRefreshToken());
		dbObject.put("scope", getScope());
		dbObject.put("client_id", getClientId());
		dbObject.put("user", getUser());
		return dbObject;
	}
	
	public static OauthAccessToken find(String accessToken){
    	return getFinder().find(OauthAccessToken.class, Constantes.ACCESS_TOKKEN_ATTRIBUTE_NAME, accessToken);
	}
	
	public static OauthAccessToken findByRefreshToken(String refreshToken){
    	return getFinder().find(OauthAccessToken.class, Constantes.REFRESH_TOKKEN_ATTRIBUTE_NAME, refreshToken);
	}

	public static List<OauthAccessToken> findAll(){
    	return getFinder().findAll(OauthAccessToken.class);
	}

	public static List<OauthAccessToken> findByUser(String user) {
		return getFinder().findAll(OauthAccessToken.class, Constantes.USER_ATTRIBUTE_NAME, user);
	}
	
	public static List<OauthAccessToken> findByClientId(String clientId) {
		return getFinder().findAll(OauthAccessToken.class, Constantes.CLIENTID_ATTRIBUTE_NAME, clientId);
	}

	public static OauthAccessToken findByAuthProfile(String clientId, String user, Set<String> scope) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("client_id", clientId);
		map.put("user", user);
		map.put("scope", scope);
		return getFinder().find(OauthAccessToken.class, map);
	}
}
