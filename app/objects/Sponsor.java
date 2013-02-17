package objects;

import com.mongodb.BasicDBObject;

public class Sponsor extends JCertfifObject{
    private String email;
    private String name;
    private String logo;
    private String level;
    private String website;
    private String city;
    private String country;
    private String phone;
    private String about;

    public Sponsor(BasicDBObject basicDBObject){
        this.setEmail(basicDBObject.getString("email"));
        this.setName(basicDBObject.getString("name"));
        this.setLogo(basicDBObject.getString("logo"));
        this.setLevel(basicDBObject.getString("level"));
        this.setWebsite(basicDBObject.getString("website"));
        this.setCity(basicDBObject.getString("city"));
        this.setCountry(basicDBObject.getString("country"));
        this.setPhone(basicDBObject.getString("phone"));
        this.setAbout(basicDBObject.getString("about"));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public BasicDBObject toBasicDBObject() {
        //TODO
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
