package models.objects;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import models.util.Tools;

import java.util.ArrayList;
import java.util.List;

public class Participant extends JCertifObject {
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
	private List<String> sessions = new ArrayList<String>();

	public Participant(BasicDBObject basicDBObject){
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
        this.sessions.addAll(Tools.basicDBListToJavaList((BasicDBList) basicDBObject.get("sessions")));
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail(String email1) {
        this.email = email1;
    }

    public final String getPassword() {
        return password;
    }

    public final void setPassword(String password1) {
        this.password = password1;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title1) {
        this.title = title1;
    }

    public final String getLastname() {
        return lastname;
    }

    public final void setLastname(String lastname1) {
        this.lastname = lastname1;
    }

    public final String getFirstname() {
        return firstname;
    }

    public final void setFirstname(String firstname1) {
        this.firstname = firstname1;
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

    public final String getCompany() {
        return company;
    }

    public final void setCompany(String company1) {
        this.company = company1;
    }

    public final String getPhone() {
        return phone;
    }

    public final void setPhone(String phone1) {
        this.phone = phone1;
    }

    public final String getPhoto() {
        return photo;
    }

    public final void setPhoto(String photo1) {
        this.photo = photo1;
    }

    public final String getBiography() {
        return biography;
    }

    public final void setBiography(String biography1) {
        this.biography = biography1;
    }

	public final List<String> getSessions() {
        return sessions;
    }

    public final void setSessions(List<String> sessions1) {
        this.sessions = sessions1;
    }

    @Override
    public final BasicDBObject toBasicDBObject() {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("email", email);
        basicDBObject.put("password", password);
        basicDBObject.put("title", title);
        basicDBObject.put("lastname", lastname);
        basicDBObject.put("firstname", firstname);
        basicDBObject.put("website", website);
        basicDBObject.put("city", city);
        basicDBObject.put("country", country);
        basicDBObject.put("company", company);
        basicDBObject.put("phone", phone);
        basicDBObject.put("photo", photo);
        basicDBObject.put("biography", biography);
        basicDBObject.put("sessions", Tools.javaListToBasicDBList(getSessions()));
        return basicDBObject;
    }

}
