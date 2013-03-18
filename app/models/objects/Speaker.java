package models.objects;

import com.mongodb.BasicDBObject;

public class Speaker extends JCertifObject {
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

    public Speaker(BasicDBObject basicDBObject) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email1) {
        this.email = email1;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password1) {
        this.password = password1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title1) {
        this.title = title1;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname1) {
        this.lastname = lastname1;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname1) {
        this.firstname = firstname1;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website1) {
        this.website = website1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city1) {
        this.city = city1;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country1) {
        this.country = country1;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company1) {
        this.company = company1;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone1) {
        this.phone = phone1;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo1) {
        this.photo = photo1;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography1) {
        this.biography = biography1;
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

}
