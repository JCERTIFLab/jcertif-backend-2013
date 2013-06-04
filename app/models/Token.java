package models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import models.util.Constantes;

import com.mongodb.BasicDBObject;

/**
 * @author Martial SOMDA
 * 
 */
public class Token extends JCertifModel {

	private String accessToken;
	private int expiresIn;
	private Date expirationDate;
	private String email;
	
	public Token() {
		super(new BasicDBObject());
	}
	
	public Token(BasicDBObject basicDBObject) {
		super(basicDBObject);
		this.accessToken = basicDBObject.getString("access_token");
		this.expiresIn = basicDBObject.getInt("expires_in");
		this.expirationDate = basicDBObject.getDate("expirationDate");
		this.email = basicDBObject.getString("email");
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

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public BasicDBObject toBasicDBObject() {
		BasicDBObject dbObject = super.toBasicDBObject();
		dbObject.put("access_token", getAccessToken());
		dbObject.put("expires_in", getExpiresIn());
		dbObject.put("expirationDate", getExpirationDate());
		dbObject.put("email", getEmail());
		return dbObject;
	}
	
	public void calculateExpiration(){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.add(Calendar.SECOND, expiresIn);
		this.expirationDate = cal.getTime();
	}
	
	@Override
	public int create() {
		calculateExpiration();
		return super.create();
	}
	
	public static Token find(String tokenId){
    	return getFinder().find(Token.class, Constantes.ACCESS_TOKEN_ATTRIBUTE_NAME, tokenId);
	}
	
	public static List<Token> findAll(){
		return getFinder().findAll(Token.class);
	}

	public boolean isExpired() {
		return Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime().after(expirationDate);
	}
}
