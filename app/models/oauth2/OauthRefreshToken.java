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
public class OauthRefreshToken extends JCertifModel {

	@NotBlank(propertyName="Refresh Token")
	private String refreshToken;
	private String expirationDate;
	@NotBlank(propertyName="Scope")
	private Set<String> scope;
	@NotBlank(propertyName="Client ID")
	private String clientId;
	private String user;
	
	public OauthRefreshToken() {
		super(new BasicDBObject());
	}
	
	public OauthRefreshToken(BasicDBObject basicDBObject) {
		super(basicDBObject);
		this.refreshToken = basicDBObject.getString("refresh_token");
		this.expirationDate = basicDBObject.getString("expirationDate");
		this.scope = Tools.basicDBListToJavaSet((BasicDBList)basicDBObject.get("scope"));
		this.clientId = basicDBObject.getString("client_id");
		this.user = basicDBObject.getString("user");
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
		dbObject.put("refresh_token", getRefreshToken());
		dbObject.put("scope", getScope());
		dbObject.put("client_id", getClientId());
		dbObject.put("user", getUser());
		return dbObject;
	}
	
	public static OauthRefreshToken find(String refreshToken){
    	return getFinder().find(OauthRefreshToken.class, Constantes.REFRESH_TOKKEN_ATTRIBUTE_NAME, refreshToken);
	}
	
	public static List<OauthRefreshToken> findAll(){
    	return getFinder().findAll(OauthRefreshToken.class);
	}

	public static List<OauthRefreshToken> findByUser(String userName) {
		return getFinder().findAll(OauthRefreshToken.class, Constantes.USER_ATTRIBUTE_NAME, userName);
	}
	
	public static List<OauthRefreshToken> findByClientId(String clientId) {
		return getFinder().findAll(OauthRefreshToken.class, Constantes.CLIENTID_ATTRIBUTE_NAME, clientId);
	}

	public static OauthRefreshToken findByAuthProfile(String clientId, String user, Set<String> scope) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("client_id", clientId);
		map.put("user", user);
		map.put("scope", scope);
		return getFinder().find(OauthRefreshToken.class, map);
	}

}
