package models.oauth2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.JCertifModel;
import models.exception.JCertifDuplicateObjectException;
import models.util.Constantes;
import models.util.Tools;
import models.validation.Constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

/**
 * @author Martial SOMDA
 *
 */
@SuppressWarnings("serial")
public class Client extends JCertifModel implements ClientDetails {
	
	@NotBlank(propertyName="Client ID")
	private String clientId;

	@NotBlank(propertyName="Client Secret")
	private String clientSecret;

	@NotBlank(propertyName="Scope")
	private Set<String> scope = Collections.emptySet();

	private Set<String> resourceIds = Collections.emptySet();

	private Set<String> authorizedGrantTypes = Collections.emptySet();

	private Set<String> registeredRedirectUris;

	private List<String> authorities = Collections.emptyList();

	@NotBlank(propertyName="Access Token Validity")
	private String accessTokenValiditySeconds;

	@NotBlank(propertyName="Refresh Token Validity")
	private String refreshTokenValiditySeconds;
	
	public Client() {
		super(new BasicDBObject());
	}
	
	public Client(BasicDBObject basicDBObject) {
		super(basicDBObject);
		this.clientId = basicDBObject.getString("client_id");
		this.clientSecret = basicDBObject.getString("client_secret");
		this.resourceIds = Tools.basicDBListToJavaSet((BasicDBList)basicDBObject.get("resource_ids"));
		this.authorizedGrantTypes = Tools.basicDBListToJavaSet((BasicDBList)basicDBObject.get("authorized_grant_types"));
		this.registeredRedirectUris = Tools.basicDBListToJavaSet((BasicDBList)basicDBObject.get("redirect_uri"));
		this.authorities = Tools.basicDBListToJavaList((BasicDBList)basicDBObject.get("authorities"));
		this.accessTokenValiditySeconds = basicDBObject.getString("access_token_validity");
		this.refreshTokenValiditySeconds = basicDBObject.getString("refresh_token_validity");
	}
	
	public Client(ClientDetails clientDetails) {
		this();
		setClientId(clientDetails.getClientId());
		setClientSecret(clientDetails.getClientSecret());
		setResourceIds(clientDetails.getResourceIds());
		setAuthorizedGrantTypes(clientDetails.getAuthorizedGrantTypes());
		setRegisteredRedirectUri(clientDetails.getRegisteredRedirectUri());
		setScope(clientDetails.getScope());
		setAuthorities(clientDetails.getAuthorities());
		setAccessTokenValiditySeconds(clientDetails.getAccessTokenValiditySeconds());
		setRefreshTokenValiditySeconds(clientDetails.getRefreshTokenValiditySeconds());
	}

	@Override
	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public Set<String> getResourceIds() {
		return resourceIds;
	}
	
	public void setResourceIds(Set<String> resourceIds) {
		this.resourceIds = resourceIds;
	}

	@Override
	public boolean isSecretRequired() {
		return !Tools.isBlankOrNull(getClientSecret());
	}

	@Override
	public String getClientSecret() {
		return clientSecret;
	}
	
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	@Override
	public boolean isScoped() {
		return !Tools.isBlankOrNull(scope);
	}

	@Override
	public Set<String> getScope() {
		return scope;
	}
	
	public void setScope(Set<String> scope) {
		this.scope = scope;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}
	
	public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return registeredRedirectUris;
	}

	public void setRegisteredRedirectUri(Set<String> registeredRedirectUris) {
		this.registeredRedirectUris = registeredRedirectUris;
	}
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthorites = new ArrayList<GrantedAuthority>();
		for(String authority : authorities){
			grantedAuthorites.add(GrantedAuthorities.fromValue(authority));
		}
		return grantedAuthorites;
	}
	
	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = new ArrayList<String>();
		for(GrantedAuthority authority : authorities){
			this.authorities.add(GrantedAuthorities.fromValue(authority.getAuthority()).getAuthority());
		}		
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return Integer.parseInt(accessTokenValiditySeconds);
	}
	
	public void setAccessTokenValiditySeconds(Integer accessTokenValidity) {
		this.accessTokenValiditySeconds = accessTokenValidity.toString();
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return Integer.parseInt(refreshTokenValiditySeconds);
	}
	
	public void setRefreshTokenValiditySeconds(Integer refreshTokenValidity) {
		this.refreshTokenValiditySeconds = refreshTokenValidity.toString();
	}
	
	@Override
	public Map<String, Object> getAdditionalInformation() {
		return new LinkedHashMap<String, Object>();
	}

	@Override
	public BasicDBObject toBasicDBObject() {
		BasicDBObject dbObject = super.toBasicDBObject();
		dbObject.put("client_id", getClientId());
		dbObject.put("client_secret", getClientSecret());
		dbObject.put("resource_ids", getResourceIds());
		dbObject.put("scope", getScope());
		dbObject.put("resource_ids", getResourceIds());
		dbObject.put("authorized_grant_types", getAuthorizedGrantTypes());
		dbObject.put("redirect_uri", getRegisteredRedirectUri());
		dbObject.put("authorities", getAuthorities());
		dbObject.put("access_token_validity", getAccessTokenValiditySeconds());
		dbObject.put("refresh_token_validity", getRefreshTokenValiditySeconds());
		return dbObject;
	}
	
	public boolean authenticate(String secret){   
		return this.clientSecret.equals(secret);
    }

	@Override
	public int create() {
		
		if(getFinder().find(getClass(), Constantes.CLIENTID_ATTRIBUTE_NAME, clientId) != null){
    		throw new JCertifDuplicateObjectException(getClass(), clientId);
    	}
		
		return super.create();
	}
	public static Client find(String clientId){
    	return getFinder().find(Client.class, Constantes.CLIENTID_ATTRIBUTE_NAME, clientId);
	}
	
	public static List<Client> findAll(){
    	return getFinder().findAll(Client.class);
	}
	
}
