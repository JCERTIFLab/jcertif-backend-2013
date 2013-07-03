package models;

import com.mongodb.BasicDBObject;


/**
 * @author Martial SOMDA
 *
 */
public class SpeakerInfo extends JCertifModel {

    private String title;
    private String lastname;
    private String firstname;
    private String website;
    private String city;
    private String country;
    private String company;
    private String photo;
    private String biography;
    
	public SpeakerInfo(Speaker speaker){
		super(new BasicDBObject());
        this.title = speaker.getTitle();
        this.lastname = speaker.getLastname();
        this.firstname = speaker.getFirstname();
        this.website = speaker.getWebsite();
        this.city = speaker.getCity();
        this.country = speaker.getCountry();
        this.company = speaker.getCompany();
        this.photo = speaker.getPhoto();
        this.biography = speaker.getBiography();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}
	
	@Override
	public BasicDBObject toBasicDBObject() {
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("title", getTitle());
		dbObject.put("lastname", getLastname());
		dbObject.put("firstname", getFirstname());
		dbObject.put("website", getWebsite());
		dbObject.put("city", getCity());
		dbObject.put("country", getCountry());
		dbObject.put("company", getCompany());
		dbObject.put("biography", getBiography());
		return dbObject;
	}
}
