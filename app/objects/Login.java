package objects;

import com.mongodb.BasicDBObject;

public class Login {
    private String email;
    private String password;

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

    public BasicDBObject toDBObject(){
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("email", email);
        basicDBObject.put("password", password);
        return basicDBObject;
    }
}
