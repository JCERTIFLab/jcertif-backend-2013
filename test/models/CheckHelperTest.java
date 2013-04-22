package models;

import models.exception.JCertifInvalidRequestException;
import models.util.Constantes;

import org.junit.Test;

import com.mongodb.BasicDBObject;

/**
 * @author Martial SOMDA
 *
 */
public class CheckHelperTest {

	@Test(expected=JCertifInvalidRequestException.class)
	public void checkNull(){
		BasicDBObject objectToCheck = null;
		CheckHelper.checkNull(objectToCheck);
	}
	
	@Test
	public void checkNotNull(){
		BasicDBObject objectToCheck = new BasicDBObject();
		CheckHelper.checkNull(objectToCheck);
	}
	
	@Test(expected=JCertifInvalidRequestException.class)
	public void checkNullOrEmpty(){
		String value = null;
		CheckHelper.checkNullOrEmpty("test", value);
	}
	
	@Test
	public void checkNotNullNorEmpty(){
		String value = "test";
		CheckHelper.checkNullOrEmpty("test", value);
	}
	
	@Test(expected=JCertifInvalidRequestException.class)
	public void checkNullEmail(){
		BasicDBObject objectToCheck = new BasicDBObject();
		CheckHelper.checkEmail(objectToCheck);
	}
	
	@Test(expected=JCertifInvalidRequestException.class)
	public void checkBlankEmail(){
		BasicDBObject objectToCheck = new BasicDBObject();
		objectToCheck.append(Constantes.EMAIL_ATTRIBUTE_NAME, " ");
		CheckHelper.checkEmail(objectToCheck);
	}
	
	@Test(expected=JCertifInvalidRequestException.class)
	public void checkInvalidEmail(){
		BasicDBObject objectToCheck = new BasicDBObject();
		objectToCheck.append(Constantes.EMAIL_ATTRIBUTE_NAME, "12test___");
		CheckHelper.checkEmail(objectToCheck);
	}
	
	@Test
	public void checkValidEmail(){
		BasicDBObject objectToCheck = new BasicDBObject();
		objectToCheck.append(Constantes.EMAIL_ATTRIBUTE_NAME, "test@gmail.com");
		CheckHelper.checkEmail(objectToCheck);
	}
	
	@Test(expected=JCertifInvalidRequestException.class)
	public void checkNullLabel(){
		BasicDBObject objectToCheck = new BasicDBObject();
		CheckHelper.checkLabel(objectToCheck);
	}
	
	@Test
	public void checkLabel(){
		BasicDBObject objectToCheck = new BasicDBObject();
		objectToCheck.append(Constantes.LABEL_ATTRIBUTE_NAME, "test");
		CheckHelper.checkLabel(objectToCheck);
	}
	
	@Test(expected=JCertifInvalidRequestException.class)
	public void checkNullPassword(){
		BasicDBObject objectToCheck = new BasicDBObject();
		CheckHelper.checkPassword(objectToCheck);
	}
	
	@Test
	public void checkPassword(){
		BasicDBObject objectToCheck = new BasicDBObject();
		objectToCheck.append(Constantes.PASSWORD_ATTRIBUTE_NAME, "testjcertif");
		CheckHelper.checkPassword(objectToCheck);
	}
	
	@Test(expected=JCertifInvalidRequestException.class)
	public void checkPasswordsNotMatched(){
		CheckHelper.checkPassword("test", "test1", true);
	}
	
	@Test(expected=JCertifInvalidRequestException.class)
	public void checkPasswordsNotMatchedFirstNull(){
		String firstPaswword = null;
		CheckHelper.checkPassword(firstPaswword, "testjcertif", true);
	}
	
	@Test(expected=JCertifInvalidRequestException.class)
	public void checkPasswordsNotMatchedSecondNull(){
		String secondPaswword = null;
		CheckHelper.checkPassword("testjcertif", secondPaswword, true);
	}
	
	@Test(expected=JCertifInvalidRequestException.class)
	public void checkPasswordsDoesNoRespectPolicy(){
		CheckHelper.checkPassword("test", "test", true);
	}
	
	@Test(expected=JCertifInvalidRequestException.class)
	public void checkPasswordDoesNoRespectPolicy(){
		CheckHelper.checkPassword("test", "", false);
	}
	
	@Test(expected=JCertifInvalidRequestException.class)
	public void checkPasswordNullDoesNoRespectPolicy(){
		String oldPassword = null;
		CheckHelper.checkPassword(oldPassword, "", false);
	}
	
	
	@Test
	public void checkTwoPassword(){
		CheckHelper.checkPassword("testjcertif", "testjcertifnew", true);
	}
	
	@Test(expected=JCertifInvalidRequestException.class)
	public void checkNullId(){
		BasicDBObject objectToCheck = new BasicDBObject();
		CheckHelper.checkId(objectToCheck);
	}
	
	@Test(expected=JCertifInvalidRequestException.class)
	public void checkNonNumberId(){
		BasicDBObject objectToCheck = new BasicDBObject();
		objectToCheck.append(Constantes.PASSWORD_ATTRIBUTE_NAME, "notANumber");
		CheckHelper.checkId(objectToCheck);
	}
	
	@Test
	public void checkId(){
		BasicDBObject objectToCheck = new BasicDBObject();
		objectToCheck.append(Constantes.ID_ATTRIBUTE_NAME, "1");
		CheckHelper.checkId(objectToCheck);
	}
}
