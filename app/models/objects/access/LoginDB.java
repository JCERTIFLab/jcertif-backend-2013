package models.objects.access;

import com.mongodb.BasicDBObject;
import models.exception.JCertifException;
import models.objects.Login;
import models.objects.checker.LoginChecker;
import models.util.Constantes;

public final class LoginDB extends JCertifObjectDB<Login> {

	private static LoginDB instance;

	private LoginDB() {
		super(Constantes.COLLECTION_LOGIN,
				new LoginChecker());
	}

	public static LoginDB getInstance() {
		if (instance == null) {
			instance = new LoginDB();
		}
		return instance;

	}

	public boolean add(Login login) throws JCertifException {
		return add(login.toBasicDBObject());
	}

	public boolean remove(Login login) throws JCertifException {
		return remove(login.toBasicDBObject(), "email");
	}

	public boolean save(Login login) throws JCertifException {
		return save(login.toBasicDBObject(), "email");
	}

	public Login get(String email) throws JCertifException {
		BasicDBObject dbObject = get("email", email);
		Login login = null;
		if (null != dbObject) {
			login = new Login(dbObject);
        }
		return login;
	}

}
