package models;

import static models.CheckHelper.checkEmail;
import static models.CheckHelper.checkNull;
import static models.CheckHelper.checkNullOrEmpty;

import java.util.List;

import models.exception.JCertifInvalidRequestException;
import models.util.Constantes;

import com.mongodb.BasicDBObject;

public class Sponsor extends JCertifModel {
	
	private String email;
    private String name;
    private String logo;
    private String level;
    private String website;
    private String city;
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

    public final void setEmail(String email1) {
        this.email = email1;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name1) {
        this.name = name1;
    }

    public final String getLogo() {
        return logo;
    }

    public final void setLogo(String logo1) {
        this.logo = logo1;
    }

    public final String getLevel() {
        return level;
    }

    public final void setLevel(String level1) {
        this.level = level1;
    }

    public final String getWebsite() {
        return website;
    }

    public final void setWebsite(String website1) {
        this.website = website1;
    }

    public final String getCity() {
        return city;
    }

    public final void setCity(String city1) {
        this.city = city1;
    }

    public final String getCountry() {
        return country;
    }

    public final void setCountry(String country1) {
        this.country = country1;
    }

    public final String getPhone() {
        return phone;
    }

    public final void setPhone(String phone1) {
        this.phone = phone1;
    }

    public final String getAbout() {
        return about;
    }

    public final void setAbout(String about1) {
        this.about = about1;
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
    public final void updateCheck(BasicDBObject objectToCheck) {
    	checkNull(objectToCheck);
		checkEmail(objectToCheck);
    }

    @Override
    public final void deleteCheck(BasicDBObject objectToCheck) {
    	checkNull(objectToCheck);
		checkEmail(objectToCheck);
    }

    @Override
    public void addCheck(BasicDBObject objectToCheck) {
    	checkNull(objectToCheck);
    	checkEmail(objectToCheck);
        
    	Sponsor sponsor = new Sponsor(objectToCheck);

        checkNullOrEmpty("Name", sponsor.getName());
        checkNullOrEmpty("Logo", sponsor.getLogo());
        checkNullOrEmpty("Level", sponsor.getLevel());
        checkNullOrEmpty("Website", sponsor.getWebsite());
        checkNullOrEmpty("City", sponsor.getCity());
        checkNullOrEmpty("Country", sponsor.getCountry());
        
        SponsorLevel sponsorLevel = SponsorLevel.find(sponsor.getLevel());
        
        if(null==sponsorLevel){
            throw new JCertifInvalidRequestException("Sponsor Level '" + sponsor.getLevel() + "' does not exist. Check Sponsor Level List" );
        }
    }

	@Override
	public String getKeyName() {
		return Constantes.EMAIL_ATTRIBUTE_NAME;
	}

	public static List<BasicDBObject> findAll(){
		return getFinder().findAll(Sponsor.class);
	}
}
