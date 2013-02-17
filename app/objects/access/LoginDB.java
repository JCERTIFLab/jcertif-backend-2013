package objects.access;

import com.mongodb.BasicDBObject;
import exception.JCertifException;
import objects.Login;
import objects.checker.LoginChecker;
import util.Constantes;

public class LoginDB extends BasicDBObj {

    public static LoginDB loginDB = new LoginDB();

    public LoginDB() {
        super(Constantes.JCERTIFBACKEND_COLLECTIONNAME_LOGIN, new LoginChecker());
    }

    public boolean add(Login login) throws JCertifException {
        return add(login.toDBObject());
    }

    public boolean remove(Login login) throws JCertifException {
        return remove(login.toDBObject(),"email");
    }

    public boolean update(Login login) throws JCertifException {
        return update(login.toDBObject(), "email");
    }

    public Login get(String email) throws JCertifException {
        BasicDBObject dbObject = get("email", email);
        Login login = null;
        if(null!=dbObject) login = new Login(dbObject);
        return login;
    }

}
