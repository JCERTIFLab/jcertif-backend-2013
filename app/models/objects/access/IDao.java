package models.objects.access;

import models.exception.JCertifException;

import java.util.List;

import com.mongodb.BasicDBObject;

/**
 * 
 * @author bashizip
 *
 * @param <T>
 */
public interface IDao<T> {

	List<T> list(T query, T columnToReturn);

	List<T> list();

	List<T> list(T query);

	T get(String keyName, Object keyValue) throws JCertifException;

	boolean add(T newObject) throws JCertifException;

	boolean update(T objectToUpdate, String idKeyname)
			throws JCertifException;

	boolean save(T objectToUpdate, String idKeyname)
			throws JCertifException;

	boolean remove(T objectToDelete, String idKeyname)
			throws JCertifException;

}
