package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import models.database.MongoDB;
import models.exception.JCertifException;
import models.util.Constantes;
import models.util.Tools;
import models.validation.ContextualMessageInterpolator;
import models.validation.ValidationUtils;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.QueryBuilder;
import com.mongodb.WriteResult;

public abstract class Model implements CRUD {
	
	private static final ValidatorFactory FACTORY;
	
	static {
		HibernateValidatorConfiguration configuration = Validation.byProvider(HibernateValidator.class).configure().failFast(true);
		FACTORY = configuration.messageInterpolator(new ContextualMessageInterpolator(configuration.getDefaultMessageInterpolator()))
	    .buildValidatorFactory();
	}
    	
	private static final Finder FINDER = new Finder();
	public static Finder getFinder() {
		return FINDER;
	}
	
	private ObjectId _id;
	private String version;
	private String deleted;
	
	public Model(BasicDBObject basicDBObject){
		this._id = basicDBObject.getObjectId(Constantes.MONGOD_ID_ATTRIBUTE_NAME);
		this.version = basicDBObject.getString(Constantes.VERSION_ATTRIBUTE_NAME);
		this.deleted = basicDBObject.getString(Constantes.DELETED_ATTRIBUTE_NAME);
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}
	
	public boolean isDeleted() {
		return Boolean.valueOf(deleted);
	}

	public void setDeleted(boolean isDeleted) {
		this.deleted = Boolean.toString(isDeleted);
	}

	public BasicDBObject toBasicDBObject(){
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put(Constantes.VERSION_ATTRIBUTE_NAME, version);
		dbObject.put(Constantes.DELETED_ATTRIBUTE_NAME, deleted);
		return dbObject;
	}
    
	private int increment(BasicDBObject basicDBObject){
		int currentVersion = Integer.parseInt(basicDBObject.getString(Constantes.VERSION_ATTRIBUTE_NAME));
		int nextVersion = currentVersion+1;
		if(nextVersion < 10){
			basicDBObject.put(Constantes.VERSION_ATTRIBUTE_NAME, String.format("%02d", nextVersion));
		}else{
			basicDBObject.put(Constantes.VERSION_ATTRIBUTE_NAME, Integer.toString(nextVersion));
		}		
		return nextVersion++;
	}
    
	/**
	 * Retourne le nom conventionnel d'un collection en fonction du nom de la classe du model.
	 * <br/>Une classe du model portant le nom <code>VoiciMonModel</code> aurra pour nom de collection
	 * voici_mon_model
	 * 
	 * @param clazz Classe du model
	 * @return Le nom conventionnel de la collection associe à la classe
	 */
	public static String getCollectionName(Class<?> clazz){
		String collectionName = "";
		for(String name : StringUtils.splitByCharacterTypeCamelCase(clazz.getSimpleName())){
			collectionName += "_" + name;
		}
		return collectionName.substring(1).toLowerCase();
	}

	protected final int add() {
		Validator validator = FACTORY.getValidator();
		Set<ConstraintViolation<Model>> violations = validator.validate(this);				
		
		if(violations.size() > 0){
			ValidationUtils.throwException(violations);
		}
		
		BasicDBObject dbObjectToAdd = toBasicDBObject();
		
		WriteResult result = MongoDB.getInstance().create(
				getCollectionName(getClass()), dbObjectToAdd);
		if (!Tools.isBlankOrNull(result.getError())) {
			throw new JCertifException(this.getClass(), result.getError());
		}
		return 1;
	}

	protected final int update() {
		Validator validator = FACTORY.getValidator();
		Set<ConstraintViolation<Model>> violations = validator.validate(this);
		
		if(violations.size() > 0){
			ValidationUtils.throwException(violations);
		}
		
		BasicDBObject dbObjectToUpdate = toBasicDBObject();
		dbObjectToUpdate.append(Constantes.MONGOD_ID_ATTRIBUTE_NAME, _id);
		int newId = increment(dbObjectToUpdate);

		WriteResult result = MongoDB.getInstance().update(
				getCollectionName(getClass()), dbObjectToUpdate);
		if (!Tools.isBlankOrNull(result.getError())) {
			throw new JCertifException(this.getClass(), result.getError());
		}
		return newId;
	}

	/**
	 * <p>This class provides static methods to retrieve data from database.</p>
	 * 
	 * @author Martial SOMDA
	 *
	 */
	public static class Finder {


		public String findNextSequence(Class<?> clazz) {
        	int id = Integer.parseInt(MongoDB.getInstance().readNextSequence(
        			getCollectionName(clazz)).getString(Constantes.SEQ_ATTRIBUTE_NAME));
        	
        	if(id < 10){
        		return String.format("%02d", id);
        	}else{
        		return Integer.toString(id);
        	}
        }
		
        public <T extends Model> T find(Class<T> clazz, String keyName, Object keyValue) {
        	
        	T object = null;
        	BasicDBObject dbObject = MongoDB.getInstance().readOne(
        			getCollectionName(clazz), 
        			QueryBuilder.start().put(keyName).is(keyValue)
        			.put(Constantes.DELETED_ATTRIBUTE_NAME).is(Constantes.FALSE).get());
        	
        	if(null != dbObject){
        		object = ModelUtils.instanciate(clazz, dbObject);
        	}
        	return object;
        }
        
        public <T extends Model> List<T> findAll(Class<T> clazz, String keyName, Object keyValue) {
        	DBCursor dbCursor = MongoDB.getInstance().list(
    				getCollectionName(clazz), 
    				QueryBuilder.start().put(keyName).is(keyValue)
        			.put(Constantes.DELETED_ATTRIBUTE_NAME).is(Constantes.FALSE).get());
    		return ModelUtils.instanciate(clazz, buildResultList(dbCursor));
        }

        public <T extends Model> List<T> findAll(Class<T> clazz) {
        	DBCursor dbCursor = MongoDB.getInstance().list(
    				getCollectionName(clazz), 
    				QueryBuilder.start().put(Constantes.DELETED_ATTRIBUTE_NAME).is(Constantes.FALSE).get());
        	return ModelUtils.instanciate(clazz, buildResultList(dbCursor));
        }
        
        public <T extends Model> List<T> findAll(Class<T> clazz, String version) {
        	DBCursor dbCursor = MongoDB.getInstance().list(
    				getCollectionName(clazz), 
    				QueryBuilder.start().put(Constantes.DELETED_ATTRIBUTE_NAME).is(Constantes.FALSE)
        			.put(Constantes.VERSION_ATTRIBUTE_NAME).greaterThan(version).get());
        	return ModelUtils.instanciate(clazz, buildResultList(dbCursor));
		}
        
        private List<BasicDBObject> buildResultList(DBCursor dbCursor) {
        	BasicDBObject object;
    		List<BasicDBObject> resultList = new ArrayList<BasicDBObject>();
    		while (dbCursor.hasNext()) {
    			object = (BasicDBObject) dbCursor.next();
    			resultList.add(object);
    		}
    		return resultList;			
		}
    }
}
