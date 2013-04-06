package models.util;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

/**
 * <p>Tests unitaires pour la classe {@link Tools}</p>
 * 
 * @author Martial SOMDA
 *
 */
public class ToolsTest {

	@Test
	public void test_blank_isBlankOrNull(){
		Assert.assertTrue(Tools.isBlankOrNull(" "));
	}
	
	@Test
	public void test_null_isBlankOrNull(){
		String string = null;
		Assert.assertTrue(Tools.isBlankOrNull(string));
	}
	
	@Test
	public void test_empty_isBlankOrNull(){
		Assert.assertTrue(Tools.isBlankOrNull(new ArrayList<String>()));
	}
	
	@Test
	public void test_isValidEmail(){
		Assert.assertTrue(Tools.isValidEmail("test@jcertif.com"));
	}
	
	@Test
	public void test_isNotValidEmail(){
		Assert.assertFalse(Tools.isValidEmail("1_2.test.com"));
	}
	
	@Test
	public void test_isNotValidNumber(){
		Assert.assertTrue(Tools.isNotValidNumber("az12"));
	}
	
	@Test
	public void test_isValidNumber(){
		Assert.assertFalse(Tools.isNotValidNumber("123"));
	}
	
	@Test
	public void test_isNotValidDate(){
		Assert.assertFalse(Tools.isValidDate("2013/06/08 12:34"));
	}
	
	@Test
	public void test_isValidDate(){
		Assert.assertTrue(Tools.isNotValidNumber("08/06/2013 12:34"));
	}
}
