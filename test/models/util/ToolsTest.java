package models.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import models.exception.JCertifInvalidRequestException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonCreator;
import org.junit.Test;

import play.libs.Json;
import play.mvc.Http.RequestBody;
import play.test.FakeRequest;
import play.test.Helpers;

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
	public void test_fullText_isBlankOrNull(){
		Assert.assertFalse(Tools.isBlankOrNull("this is a full text."));
	}
	
	@Test
	public void test_filledCollection_isBlankOrNull(){
		List<String> collection = new ArrayList<String>();
		collection.add("A");
		Assert.assertFalse(Tools.isBlankOrNull(collection));
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
	public void test_NullIsNotValidDate(){
		Assert.assertFalse(Tools.isValidDate(null));
	}
	
	@Test
	public void test_isValidDate(){
		Assert.assertTrue(Tools.isNotValidNumber("08/06/2013 12:34"));
	}
	
	@Test(expected=JCertifInvalidRequestException.class)
	public void test_badRequest_verifyJSonRequest(){
		Tools.verifyJSonRequest(new RequestBody());
	}
	
	@Test
	public void test_verifyJSonRequest(){
		//TODO réecrire ce test
		final Map<String, String> map = new HashMap<String, String>();
		map.put("name", "test");
		RequestBody request = new RequestBody() {
			@Override
			public JsonNode asJson() {
				return Json.toJson(map);
			}
		};
		Tools.verifyJSonRequest(request);
	}
}
