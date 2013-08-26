package models;

import java.util.List;

import models.util.Constantes;
import models.validation.Constraints.NotBlank;

import com.mongodb.BasicDBObject;

/**
 * <p>Represents a country.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class Country extends JCertifModel {

	@NotBlank(propertyName="Cid")
	private String cid;
	@NotBlank(propertyName="Name")
	private String name;
	
	public Country(BasicDBObject basicDBObject) {
		super(basicDBObject);
		this.cid = basicDBObject.getString("cid");
    	this.name = basicDBObject.getString("name");
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public BasicDBObject toBasicDBObject() {
		BasicDBObject dbObject = super.toBasicDBObject();
		dbObject.put("cid", getCid());
		dbObject.put("name", getName());
		return dbObject;
	}
	
	@Override
	public int create() {
		setCid(getName());
		return super.create();
	}
	
	public static Country find(String countryId){
		return getFinder().find(Country.class, Constantes.CID_ATTRIBUTE_NAME, countryId);
	}
	
	public static Country findByName(String name){
		return getFinder().find(Country.class, Constantes.NAME_ATTRIBUTE_NAME, name);
	}
	
	public static List<Country> findAll(){
		return getFinder().findAll(Country.class);
	}
	
	public static List<Country> findAll(String version){
		return getFinder().findAll(Country.class, version);
	}
	
}
