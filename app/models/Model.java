package models;

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

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;

import com.mongodb.BasicDBObject;
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
		StringBuffer collectionName = new StringBuffer();
		for(String name : StringUtils.splitByCharacterTypeCamelCase(clazz.getSimpleName())){
			collectionName.append("_").append(name);
		}
		return collectionName.substring(1).toLowerCase();
	}
	
	@SuppressWarnings("unchecked")
	public static Class<? extends Model> getModelClass(String collectionName) throws ClassNotFoundException{
		StringBuffer className = new StringBuffer();
		for(String name : collectionName.split("_")){
			className.append(StringUtils.capitalize(name));
		}
		return (Class<? extends Model>) Class.forName(Model.class.getPackage().getName() + "." + className);
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
	
	protected final int suppress(){
		Validator validator = FACTORY.getValidator();
		Set<ConstraintViolation<Model>> violations = validator.validate(this);				
		
		if(violations.size() > 0){
			ValidationUtils.throwException(violations);
		}
		
		BasicDBObject dbObjectToSuppress = toBasicDBObject();
		
		WriteResult result = MongoDB.getInstance().delete(
                getCollectionName(getClass()), dbObjectToSuppress);
		if (!Tools.isBlankOrNull(result.getError())) {
			throw new JCertifException(this.getClass(), result.getError());
		}
		return 1;
	}

}
