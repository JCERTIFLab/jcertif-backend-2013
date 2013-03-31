package models.objects;

import models.objects.access.JCertifObjectDB;
import models.objects.access.LoginDB;
import models.util.Constantes;

import com.mongodb.BasicDBObject;

public class Login extends JCertifObject {
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

    @Override
	@SuppressWarnings("unchecked")
	protected JCertifObjectDB<Login> getDBObject() {
		return LoginDB.getInstance();
	}

	@Override
	public String getKeyName() {
		return Constantes.EMAIL_ATTRIBUTE_NAME;
	}
}
