package objects;

import com.mongodb.BasicDBObject;

public class Login extends JCertfifObject{
    private String email;
    private String password;

    public Login(){

    }

    public Login(BasicDBObject basicDBObject){
        this.setEmail(basicDBObject.getString("email"));
        this.setPassword(basicDBObject.getString("password"));
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

    @Override
    public BasicDBObject toBasicDBObject(){
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("email", email);
        basicDBObject.put("password", password);
        return basicDBObject;
    }
}
