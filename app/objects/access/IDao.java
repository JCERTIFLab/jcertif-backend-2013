package objects.access;

import java.util.List;

import exception.JCertifException;

/**
 * 
 * @author bashizip
 *
 * @param <T>
 */
public interface IDao<T> {

	public List<T> list(T query, T columnToReturn);

	public List<T> list();

	public List<T> list(T query);

	public T get(String keyName, Object keyValue) throws JCertifException;

	public boolean add(T newObject) throws JCertifException;

	public boolean update(T objectToUpdate, String idKeyname)
			throws JCertifException;

	public boolean save(T objectToUpdate, String idKeyname)
			throws JCertifException;

	public boolean remove(T objectToDelete, String idKeyname)
			throws JCertifException;

}
