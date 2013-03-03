package models.objects;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import models.util.Tools;

import java.util.ArrayList;
import java.util.List;

public class Participant extends JCertfifObject {
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
    private List sessions = new ArrayList<String>();

    public Participant(BasicDBObject basicDBObject){
        this.setEmail(basicDBObject.getString("email"));
        this.setPassword(basicDBObject.getString("password"));
        this.setTitle(basicDBObject.getString("title"));
        this.setLastname(basicDBObject.getString("lastname"));
        this.setFirstname(basicDBObject.getString("firstname"));
        this.setWebsite(basicDBObject.getString("website"));
        this.setCity(basicDBObject.getString("city"));
        this.setCountry(basicDBObject.getString("country"));
        this.setCompany(basicDBObject.getString("company"));
        this.setPhone(basicDBObject.getString("phone"));
        this.setPhoto(basicDBObject.getString("photo"));
        this.setBiography(basicDBObject.getString("biography"));
        this.getSessions().addAll(Tools.basicDBListToJavaList((BasicDBList) basicDBObject.get("sessions")));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public List getSessions() {
        return sessions;
    }

    public void setSessions(List sessions) {
        this.sessions = sessions;
    }

    @Override
    public BasicDBObject toBasicDBObject() {
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
