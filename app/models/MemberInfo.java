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

	public String getTitle() {
		return title;
	}

	public String getLastname() {
		return lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getPhoto() {
		return photo;
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
