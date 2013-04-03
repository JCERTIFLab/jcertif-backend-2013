package models.objects;

import notifiers.EmailNotification;
import models.exception.JCertifInvalidRequestException;
import models.objects.checker.CheckerHelper;
import models.util.Constantes;
import models.util.crypto.CryptoUtil;

import com.mongodb.BasicDBObject;

/**
 * <p>Represente un membre de JCertif Conference.</p>
 * Ex:
 * <ul>
 * <li>Speaker</li>
 * <li>Partcicipant</li>
 * <li>...</li>
 * </ul>
 * 
 * @author Martial SOMDA
 *
 */
public abstract class Member extends JCertifObject {
	
	private String email;
    private String password;
    private String title;
    private String lastname;
    private String firstname;
    private String website;
    private String city;
    private String country;
    private String company;
    private String phone;
    private String photo;
    private String biography;

    public Member(BasicDBObject basicDBObject) {
        super();
        this.email = basicDBObject.getString("email");
        this.password = basicDBObject.getString("password");
        this.title = basicDBObject.getString("title");
        this.lastname = basicDBObject.getString("lastname");
        this.firstname = basicDBObject.getString("firstname");
        this.website = basicDBObject.getString("website");
        this.city = basicDBObject.getString("city");
        this.country = basicDBObject.getString("country");
        this.company = basicDBObject.getString("company");
        this.phone = basicDBObject.getString("phone");
        this.photo = basicDBObject.getString("photo");
        this.biography = basicDBObject.getString("biography");
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = email;
    }

    public final String getPassword() {
        return password;
    }

    public final void setPassword(String password) {
        this.password = password;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public final String getLastname() {
        return lastname;
    }

    public final void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public final String getFirstname() {
        return firstname;
    }

    public final void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public final String getCompany() {
        return company;
    }

    public final void setCompany(String company) {
        this.company = company;
    }

    public final String getPhone() {
        return phone;
    }

    public final void setPhone(String phone) {
        this.phone = phone;
    }

    public final String getPhoto() {
        return photo;
    }

    public final void setPhoto(String photo) {
        this.photo = photo;
    }

    public final String getBiography() {
        return biography;
    }

    public final void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public BasicDBObject toBasicDBObject() {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("email", getEmail());
        basicDBObject.put("password", getPassword());
        basicDBObject.put("title", getTitle());
        basicDBObject.put("lastname", getLastname());
        basicDBObject.put("firstname", getFirstname());
        basicDBObject.put("website", getWebsite());
        basicDBObject.put("city", getCity());
        basicDBObject.put("country", getCountry());
        basicDBObject.put("company", getCompany());
        basicDBObject.put("phone", getPhone());
        basicDBObject.put("photo", getPhoto());
        basicDBObject.put("biography", getBiography());
        return basicDBObject;
    }
    
    @Override
	public String getKeyName() {
		return Constantes.EMAIL_ATTRIBUTE_NAME;
	}
    
    public void changePassword(String oldPassword, String newPassword) {
		
		CheckerHelper.checkPassword(oldPassword, newPassword, true);
		
		if (!CryptoUtil.verifySaltedPassword(oldPassword.getBytes(), getPassword())) {
				/* We compare oldPassword with the hashed password  */
           throw new JCertifInvalidRequestException("old password does not match the current password");
		}
		
		setPassword(CryptoUtil.getSaltedPassword(newPassword.getBytes()));
		boolean isOK = save();
		
		if(isOK){
			EmailNotification.sendChangePwdMail(this);
		}

	}

	public void reinitPassword() {
		
		String newPassword = CryptoUtil.generateRandomPassword();
		setPassword(CryptoUtil.getSaltedPassword(newPassword.getBytes()));
		
		boolean isOK = save();
		
		if(isOK){
			EmailNotification.sendReinitpwdMail(this, newPassword);
		}
	}
}
