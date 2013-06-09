package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.exception.JCertifException;
import models.util.Constantes;
import models.util.Tools;
import play.Logger;

import com.mongodb.BasicDBObject;

/**
 * @author Martial SOMDA
 * 
 */
public class Token extends JCertifModel {

	private String accessToken;
	private int expiresIn = 0;
	private String expirationDate;
	private String email;
	private String provider;
	
	public Token() {
		super(new BasicDBObject());
	}
	
	public Token(BasicDBObject basicDBObject) {
		super(basicDBObject);
		this.accessToken = basicDBObject.getString("access_token");
		this.expiresIn = basicDBObject.getInt("expires_in");
		this.expirationDate = basicDBObject.getString("expirationDate");
		this.email = basicDBObject.getString("email");
		this.provider = basicDBObject.getString("provider");
	}


	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	@Override
	public BasicDBObject toBasicDBObject() {
		BasicDBObject dbObject = super.toBasicDBObject();
		dbObject.put("access_token", getAccessToken());
		dbObject.put("expires_in", getExpiresIn());
		dbObject.put("expirationDate", getExpirationDate());
		dbObject.put("email", getEmail());
		dbObject.put("provider", getProvider());
		return dbObject;
	}
	
	public void calculateExpiration(){
		if(Tools.isBlankOrNull(expirationDate) 
				&& expiresIn != 0){
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.SECOND, expiresIn);
			SimpleDateFormat sdf = new SimpleDateFormat(Constantes.DATEFORMAT);
			Logger.info("Expires : " + sdf.format(cal.getTime()));	
			this.expirationDate = sdf.format(cal.getTime());
		}		
	}
	
	@Override
	public int create() {
		calculateExpiration();
		return super.create();
	}
	
	public static Token find(String tokenId){
    	return getFinder().find(Token.class, Constantes.ACCESS_TOKEN_ATTRIBUTE_NAME, tokenId);
	}
	
	public static Token findTokenByIdAndProvider(String tokenId, String providerId){
		Map<String,Object> criteria = new HashMap<String,Object>();
		criteria.put(Constantes.ACCESS_TOKEN_ATTRIBUTE_NAME, tokenId);
		criteria.put(Constantes.PROVIDER_ATTRIBUTE_NAME, providerId);
    	return getFinder().find(Token.class, criteria);
	}
	
	public static List<Token> findAll(){
		return getFinder().findAll(Token.class);
	}

	public boolean isExpired() {
		SimpleDateFormat sdf = new SimpleDateFormat(Constantes.DATEFORMAT);
		Logger.info("Current date : " + sdf.format(Calendar.getInstance().getTime()));
		try {
			return Calendar.getInstance().getTime().after(sdf.parse(expirationDate));
		} catch (ParseException e) {
			throw new JCertifException(e.getMessage(), e);
		}
	}
}
