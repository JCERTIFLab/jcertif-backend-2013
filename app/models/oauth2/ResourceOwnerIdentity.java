package models.oauth2;

import com.mongodb.BasicDBObject;

import models.Login;

/**
 * @author Martial SOMDA
 *
 */
public class ResourceOwnerIdentity extends Login {

	private String redirectUri;
	private String state;
	private String clientId;
	
	public ResourceOwnerIdentity() {
    	super(new BasicDBObject());
    }	

    public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public BasicDBObject toBasicDBObject() {
		BasicDBObject dbObject = super.toBasicDBObject();
		dbObject.put("redirectUri", getRedirectUri());
		dbObject.put("state", getState());
		dbObject.put("clientId", getClientId());
		return dbObject;
	}
}
