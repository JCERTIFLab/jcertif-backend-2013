package models;

import java.util.List;

import models.exception.JCertifDuplicateObjectException;
import models.util.Constantes;
import models.validation.Constraints.NotBlank;
import models.validation.Constraints.SponsorLevel;
import play.data.validation.Constraints.Email;

import com.mongodb.BasicDBObject;

/**
 * <p>Objet metier représentant un sponsor de l'évènement JCertif Conference.</p>
 *
 */
public class Sponsor extends JCertifModel {
	
	@NotBlank(propertyName="Email") @Email(message="{value} is not a valid email")
	private String email;
	@NotBlank(propertyName="Name")
    private String name;
	@NotBlank(propertyName="Logo")
    private String logo;
	@NotBlank(propertyName="Level") @SponsorLevel
    private String level;
	@NotBlank(propertyName="Website")
    private String website;
	@NotBlank(propertyName="City")
    private String city;
	@NotBlank(propertyName="Country")
    private String country;
    private String phone;
    private String about;

    public Sponsor(BasicDBObject basicDBObject) {
    	super(basicDBObject);
        this.email = basicDBObject.getString("email");
        this.name = basicDBObject.getString("name");
        this.logo = basicDBObject.getString("logo");
        this.level = basicDBObject.getString("level");
        this.website = basicDBObject.getString("website");
        this.city = basicDBObject.getString("city");
        this.country = basicDBObject.getString("country");
        this.phone = basicDBObject.getString("phone");
        this.about = basicDBObject.getString("about");
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = email;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getLogo() {
        return logo;
    }

    public final void setLogo(String logo) {
        this.logo = logo;
    }

    public final String getLevel() {
        return level;
    }

    public final void setLevel(String level) {
        this.level = level;
    }

    public final String getWebsite() {
        return website;
    }

    public final void setWebsite(String website) {
        this.website = website;
    }

    public final String getCity() {
        return city;
    }

    public final void setCity(String city) {
        this.city = city;
    }

    public final String getCountry() {
        return country;
    }

    public final void setCountry(String country) {
        this.country = country;
    }

    public final String getPhone() {
        return phone;
    }

    public final void setPhone(String phone) {
        this.phone = phone;
    }

    public final String getAbout() {
        return about;
    }

    public final void setAbout(String about) {
        this.about = about;
    }

    @Override
    public final BasicDBObject toBasicDBObject() {
        BasicDBObject basicDBObject = super.toBasicDBObject();
        basicDBObject.put("email", getEmail());
        basicDBObject.put("name", getName());
        basicDBObject.put("logo", getLogo());
        basicDBObject.put("level", getLevel());
        basicDBObject.put("website", getWebsite());
        basicDBObject.put("city", getCity());
        basicDBObject.put("country", getCountry());
        basicDBObject.put("phone", getPhone());
        basicDBObject.put("about", getAbout());
        return basicDBObject;
    }

    @Override
    public int create() {
    	if(find(email) != null){
    		throw new JCertifDuplicateObjectException(getClass(), email);
    	}
    	return super.create();
    }
    public static Sponsor find(String email){
    	Sponsor sponsor = null;
    	
    	BasicDBObject dbObject = getFinder().find(Sponsor.class, Constantes.EMAIL_ATTRIBUTE_NAME, email);
    	
    	if(null != dbObject){
    		sponsor = new Sponsor(dbObject);
    	}
		return sponsor; 
	}
    
	public static List<BasicDBObject> findAll(){
		return getFinder().findAll(Sponsor.class);
	}
	
	public static List<BasicDBObject> findAll(String version){
		return getFinder().findAll(Sponsor.class, version);
	}
}
