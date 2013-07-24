package models;

import java.util.List;

import com.mongodb.BasicDBObject;


/**
 * @author Martial SOMDA
 *
 */
public class SpeakerInfo extends JCertifModel {

	private String email;
    private String title;
    private String lastname;
    private String firstname;
    private String website;
    private String city;
    private String country;
    private String company;
    private String photo;
    private String biography;
    private BasicDBObject[] sessions;
    
	public SpeakerInfo(Speaker speaker, List<SessionInfo> sessions){
		super(new BasicDBObject());
		this.email = speaker.getEmail();
        this.title = speaker.getTitle();
        this.lastname = speaker.getLastname();
        this.firstname = speaker.getFirstname();
        this.website = speaker.getWebsite();
        this.city = speaker.getCity();
        this.country = speaker.getCountry();
        this.company = speaker.getCompany();
        this.photo = speaker.getPhoto();
        this.biography = speaker.getBiography();
        if(null != sessions){
        	this.sessions = new BasicDBObject[sessions.size()];
        	int i = 0;
        	for(SessionInfo session : sessions){
        		this.sessions[i] = session.toBasicDBObject();
        		i++;
        	}
        }
        
	}

	public final String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = email;
    }
    
	public String getTitle() {
		return title;
	}

	public String getLastname() {
		return lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getWebsite() {
		return website;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getCompany() {
		return company;
	}

	public String getPhoto() {
		return photo;
	}

	public String getBiography() {
		return biography;
	}
	
	public BasicDBObject[] getSessions() {
		return sessions;
	}
	
	@Override
	public BasicDBObject toBasicDBObject() {
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("email", getEmail());
		dbObject.put("title", getTitle());
		dbObject.put("lastname", getLastname());
		dbObject.put("firstname", getFirstname());
		dbObject.put("website", getWebsite());
		dbObject.put("city", getCity());
		dbObject.put("country", getCountry());
		dbObject.put("company", getCompany());
		dbObject.put("photo", getPhoto());
		dbObject.put("biography", getBiography());
		dbObject.put("sessions", getSessions());
		return dbObject;
	}
}
