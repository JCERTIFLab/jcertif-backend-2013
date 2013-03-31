package models.objects.access;

import models.objects.Login;
import models.objects.checker.LoginChecker;
import models.util.Constantes;

import com.mongodb.BasicDBObject;

public final class LoginDB extends JCertifObjectDB<Login> {

	private static final LoginDB INSTANCE = new LoginDB();

	private LoginDB() {
		super(Constantes.COLLECTION_LOGIN,
				new LoginChecker());
	}

	public static LoginDB getInstance() {
		return INSTANCE;

	}

	public boolean add(Login login) {
		return add(login.toBasicDBObject());
	}

	public boolean remove(Login login) {
		return remove(login.toBasicDBObject(), Constantes.EMAIL_ATTRIBUTE_NAME);
	}

	public boolean save(Login login) {
		return save(login.toBasicDBObject(), Constantes.EMAIL_ATTRIBUTE_NAME);
	}

	public Login get(String email) {
		BasicDBObject dbObject = get(Constantes.EMAIL_ATTRIBUTE_NAME, email);
		Login login = null;
		if (null != dbObject) {
			login = new Login(dbObject);
        }
		return login;
	}

}
