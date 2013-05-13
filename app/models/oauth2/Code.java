package models.oauth2;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import models.JCertifModel;
import models.util.Constantes;

import com.mongodb.BasicDBObject;

/**
 * @author MSOMDA
 *
 */
public class Code extends JCertifModel {
	
	private static int EXPIRE_DELAY = 10;
	private String code;
	private String expireDate;
	
	public Code(BasicDBObject basicDBObject) {
		super(basicDBObject);
		this.code = basicDBObject.getString("code");
		this.expireDate = basicDBObject.getString("expireDate");
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	
	public void calculateExpireDate(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, EXPIRE_DELAY);
		SimpleDateFormat sdf = new SimpleDateFormat(Constantes.DATEFORMAT);
		setExpireDate(sdf.format(cal.getTime()));
	}

	@Override
	public BasicDBObject toBasicDBObject() {
		BasicDBObject dbObject = super.toBasicDBObject();
		dbObject.put("code", getCode());
		dbObject.put("expireDate", getExpireDate());
		return dbObject;
	}
	
	public static Code find(String code){
    	return getFinder().find(Code.class, Constantes.CODE_ATTRIBUTE_NAME, code);
	}
}
