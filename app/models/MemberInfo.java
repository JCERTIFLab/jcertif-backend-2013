package models;

import com.mongodb.BasicDBObject;


/**
 * @author Martial SOMDA
 *
 */
public class MemberInfo extends JCertifModel{

	private String email;
    private String title;
    private String lastname;
    private String firstname;
    private String photo;
    
	public MemberInfo(Member member) {
		super(new BasicDBObject());
		this.email = member.getEmail();
		this.title = member.getTitle();
		this.lastname = member.getLastname();
		this.firstname = member.getFirstname();
		this.photo = member.getPhoto() == null? "" : member.getPhoto();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@Override
	public BasicDBObject toBasicDBObject() {
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("email", getEmail());
		dbObject.put("title", getTitle());
		dbObject.put("lastname", getLastname());
		dbObject.put("firstname", getFirstname());
		dbObject.put("photo", getPhoto());
		return dbObject;
	}

}
