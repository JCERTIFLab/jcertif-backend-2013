package models;

import static models.CheckerHelper.checkEmail;
import static models.CheckerHelper.checkNull;
import static models.CheckerHelper.checkPassword;

import java.util.List;

import models.util.Constantes;

import com.mongodb.BasicDBObject;

public class Login extends JCertifModel {
	
	private String email;
    private String password;

    public Login(BasicDBObject basicDBObject) {
        super();
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
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("email", email);
        basicDBObject.put("password", password);
        return basicDBObject;
    }

    public final void check(BasicDBObject objectToCheck) {
    	checkNull(objectToCheck);
    	checkEmail(objectToCheck);
    	checkPassword(objectToCheck);
    }

	@Override
    public final void updateCheck(BasicDBObject objectToCheck) {
    	check(objectToCheck);
    }

    @Override
    public final void deleteCheck(BasicDBObject objectToCheck) {
    	check(objectToCheck);
    }

    @Override
    public void addCheck(BasicDBObject objectToCheck) {
    	check(objectToCheck);
    }

	@Override
	public String getKeyName() {
		return Constantes.EMAIL_ATTRIBUTE_NAME;
	}
	
	public static List<BasicDBObject> findAll(){
		return new Model.Finder().findAll(Login.class);
	}
}
