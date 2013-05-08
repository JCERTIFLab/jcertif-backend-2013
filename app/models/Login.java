package models;

import java.util.List;

import models.validation.Constraints.NotBlank;
import play.data.validation.Constraints.Email;

import com.mongodb.BasicDBObject;

public class Login extends JCertifModel {
	
	@NotBlank(propertyName="Email") @Email(message="{value} is not a valid email")
	private String email;
	@NotBlank(propertyName="Password")
    private String password;

    public Login(BasicDBObject basicDBObject) {
    	super(basicDBObject);
        this.email = basicDBObject.getString("email");
        this.password = basicDBObject.getString("password");
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

    @Override
    public BasicDBObject toBasicDBObject() {
        BasicDBObject basicDBObject = super.toBasicDBObject();
        basicDBObject.put("email", email);
        basicDBObject.put("password", password);
        return basicDBObject;
    }
	
	public static List<Login> findAll(){
		return getFinder().findAll(Login.class);
	}
}
