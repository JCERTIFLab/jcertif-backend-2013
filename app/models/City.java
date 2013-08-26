package models;

import java.util.List;

import com.mongodb.BasicDBObject;

import models.util.Constantes;
import models.validation.Constraints.Country;
import models.validation.Constraints.NotBlank;

/**
 * <p>Represents a city.<p>
 * 
 * @author Martial SOMDA
 *
 */
public class City extends JCertifModel {

	@NotBlank(propertyName="Name")
	private String name;
	@NotBlank(propertyName="Cid") @Country
	private String cid;
	
	public City(BasicDBObject basicDBObject) {
		super(basicDBObject);
		this.name = basicDBObject.getString("name");
    	this.cid = basicDBObject.getString("cid");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	
	@Override
	public BasicDBObject toBasicDBObject() {
		BasicDBObject dbObject = super.toBasicDBObject();
		dbObject.put("name", getName());
		dbObject.put("cid", getCid());		
		return dbObject;
	}
	
	public static City find(String name){
		return getFinder().find(City.class, Constantes.NAME_ATTRIBUTE_NAME, name);
	}
	
	public static List<City> findAll(){
		return getFinder().findAll(City.class);
	}
	
	public static List<City> findAll(String version){
		return getFinder().findAll(City.class, version);
	}
	
	public static List<City> findByCountryId(String countryId){
		return getFinder().findAll(City.class, Constantes.CID_ATTRIBUTE_NAME, countryId);
	}
}
