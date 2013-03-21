package models.objects.access;

import java.util.List;

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

	T get(String keyName, Object keyValue);

	boolean add(T newObject);

	boolean update(T objectToUpdate, String idKeyname);

	boolean save(T objectToUpdate, String idKeyname);

	boolean remove(T objectToDelete, String idKeyname);

}
