package models.objects;

import com.mongodb.BasicDBObject;

public class Login extends JCertifObject {
    private String email;
    private String password;

    public Login(BasicDBObject basicDBObject) {
        this.email = basicDBObject.getString("email");
        this.password = basicDBObject.getString("password");
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

    @Override
    public BasicDBObject toBasicDBObject() {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("email", email);
        basicDBObject.put("password", password);
        return basicDBObject;
    }


}
