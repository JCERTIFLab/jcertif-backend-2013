package models;

import java.util.List;

import models.util.Constantes;
import models.validation.Constraints.NotBlank;

import com.mongodb.BasicDBObject;

/**
 * <p>Objet metier representant un site physique pouvant accueillir un évènement.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class Site extends JCertifModel {

	@NotBlank(propertyName="Id")
	private String id;
	@NotBlank(propertyName="Name")
    private String name;
	@NotBlank(propertyName="Street")
    private String street;
	@NotBlank(propertyName="City")
    private String city;
	@NotBlank(propertyName="Country")
    private String country;
	@NotBlank(propertyName="Contact")
    private String contact;
    private String website;
    @NotBlank(propertyName="Description")
    private String description;
    private String photo;
    
    public Site(BasicDBObject basicDBObject){
    	super(basicDBObject);
    	this.id = basicDBObject.getString("id");
        this.name = basicDBObject.getString("name");
        this.street = basicDBObject.getString("street");
        this.city = basicDBObject.getString("city");
        this.country = basicDBObject.getString("country");
        this.contact = basicDBObject.getString("contact");
        this.website = basicDBObject.getString("website");
        this.description = basicDBObject.getString("description");
        this.photo = basicDBObject.getString("photo");
    }
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public BasicDBObject toBasicDBObject() {
		BasicDBObject basicDBObject = super.toBasicDBObject();
        basicDBObject.put("id", getId());
        basicDBObject.put("name", getName());
        basicDBObject.put("street", getStreet());
        basicDBObject.put("city", getCity());
        basicDBObject.put("country", getCountry());
        basicDBObject.put("contact", getContact());
        basicDBObject.put("website", getWebsite());
        basicDBObject.put("description", getDescription());
        basicDBObject.put("photo", getPhoto());
        return basicDBObject;
	}
	
	@Override
	public int create() {
		setId(getFinder().findNextSequence(Site.class));
		return super.create();
	}
	
	public static Site find(String idSite){
		Site site = null;
    	
    	BasicDBObject dbObject = getFinder().find(Site.class, Constantes.ID_ATTRIBUTE_NAME, idSite);
    	
    	if(null != dbObject){
    		site = new Site(dbObject);
    	}
		return site; 
	}
	
	public static List<BasicDBObject> findAll(){
		return getFinder().findAll(Site.class);
	}

}
