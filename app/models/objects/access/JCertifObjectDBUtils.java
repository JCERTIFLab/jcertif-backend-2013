package models.objects.access;

import java.lang.reflect.InvocationTargetException;

import models.exception.JCertifException;
import models.exception.JCertifResourceAccessException;
import models.objects.JCertifObject;

import com.mongodb.BasicDBObject;

/**
 * @author Martial SOMDA
 *
 */
public class JCertifObjectDBUtils {

	public static <T extends JCertifObject> T instanciate(Class<T> targetClass, BasicDBObject basicDBObject) {
		try {
			return targetClass.getConstructor(BasicDBObject.class).newInstance(basicDBObject);
		} catch (InstantiationException e) {
			throw new JCertifException("Implossible de créer une instance de " 
					+ targetClass.getSimpleName() , e);
		} catch (IllegalAccessException e) {
			throw new JCertifResourceAccessException("Implossible de créer une instance de " 
					+ targetClass.getSimpleName() , e);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T extends JCertifObject> T instanciate(Class<T> targetClass) {
		try {
			return targetClass.newInstance();
		} catch (InstantiationException e) {
			throw new JCertifException("Implossible de créer une instance de " 
					+ targetClass.getSimpleName() , e);
		} catch (IllegalAccessException e) {
			throw new JCertifResourceAccessException("Implossible de créer une instance de " 
					+ targetClass.getSimpleName() , e);
		}
	}
}
