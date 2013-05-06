package models;

import models.exception.JCertifInvalidRequestException;
import models.validation.CheckHelper;

import org.junit.Test;

/**
 * @author Martial SOMDA
 *
 */
public class CheckHelperTest {
	
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
}
