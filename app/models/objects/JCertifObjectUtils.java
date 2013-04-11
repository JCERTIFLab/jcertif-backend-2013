package models.objects;

import java.lang.reflect.InvocationTargetException;

import models.exception.JCertifException;
import models.exception.JCertifResourceAccessException;

import com.mongodb.BasicDBObject;

/**
 * @author Martial SOMDA
 *
 */
public class JCertifObjectUtils {

	private static final String ERROR_MESSAGE = "Implossible de créer une instance de ";
	private JCertifObjectUtils(){		
	}
	
	public static <T extends JCertifObject> T instanciate(Class<T> targetClass, BasicDBObject basicDBObject) {
		try {
			return targetClass.getConstructor(BasicDBObject.class).newInstance(basicDBObject);
		} catch (InstantiationException e) {
			throw new JCertifException(ERROR_MESSAGE + targetClass.getSimpleName() , e);
		} catch (IllegalAccessException e) {
			throw new JCertifResourceAccessException(ERROR_MESSAGE + targetClass.getSimpleName() , e);
		} catch (IllegalArgumentException e) {
			throw new JCertifException(ERROR_MESSAGE + targetClass.getSimpleName() , e);
		} catch (SecurityException e) {
			throw new JCertifException(ERROR_MESSAGE + targetClass.getSimpleName() , e);
		} catch (InvocationTargetException e) {
			throw new JCertifException(ERROR_MESSAGE + targetClass.getSimpleName() , e);
		} catch (NoSuchMethodException e) {
			throw new JCertifException(ERROR_MESSAGE + targetClass.getSimpleName() , e);
		}
	}
	
	public static <T extends JCertifObject> T instanciate(Class<T> targetClass) {
		try {
			return targetClass.newInstance();
		} catch (InstantiationException e) {
			throw new JCertifException(ERROR_MESSAGE + targetClass.getSimpleName() , e);
		} catch (IllegalAccessException e) {
			throw new JCertifResourceAccessException(ERROR_MESSAGE + targetClass.getSimpleName() , e);
		}
	}
}
