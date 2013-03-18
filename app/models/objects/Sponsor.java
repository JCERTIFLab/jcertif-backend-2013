package models.objects;

import com.mongodb.BasicDBObject;

public class Sponsor extends JCertifObject {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email1) {
        this.email = email1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name1) {
        this.name = name1;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo1) {
        this.logo = logo1;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level1) {
        this.level = level1;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone1) {
        this.phone = phone1;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about1) {
        this.about = about1;
    }

    @Override
    public BasicDBObject toBasicDBObject() {
        BasicDBObject basicDBObject = new BasicDBObject();
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


}
