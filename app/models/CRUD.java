package models;


/**
 * <p>Interface to access data from database. This CRUD interface intentionally doesn't contain read method.</p>
 * Read method are made available through statics methods of a specific finder class.
 * 
 * @author bashizip
 * @see Model.Finder
 */
public interface CRUD {

	boolean create();

	boolean save();

	boolean remove();

}
