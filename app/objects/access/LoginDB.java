package objects.access;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;
import database.MongoDatabase;
import exception.JCertifException;
import objects.Login;
import objects.checker.LoginChecker;
import util.Constantes;
import util.Tools;

import java.util.ArrayList;
import java.util.List;

public class LoginDB extends BasicDBObj {

    public static LoginDB loginDB = new LoginDB();

    public LoginDB() {
        super(new LoginChecker());
    }

    public boolean add(Login login) throws JCertifException {
        getChecker().check(login);
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put("email", login.getEmail());
        dbObject.put("password", login.getPassword());
        WriteResult result = MongoDatabase.JCERTIFINSTANCE.create(Constantes.JCERTIFBACKEND_COLLECTIONNAME_LOGIN, dbObject);
        if (!Tools.isBlankOrNull(result.getError())) {
            throw new JCertifException(this, result.getError());
        }
        return true;
    }

    public boolean remove(Login login) throws JCertifException {
        getChecker().check(login);
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put("email", login.getEmail());
        BasicDBObject objectToDelete = MongoDatabase.JCERTIFINSTANCE.readOne(Constantes.JCERTIFBACKEND_COLLECTIONNAME_LOGIN, dbObject);
        if (null == objectToDelete) {
            throw new JCertifException(this, "Login to delete does not exist");
        }
        WriteResult result = MongoDatabase.JCERTIFINSTANCE.delete(Constantes.JCERTIFBACKEND_COLLECTIONNAME_LOGIN, objectToDelete);
        if (!Tools.isBlankOrNull(result.getError())) {
            throw new JCertifException(this, result.getError());
        }
        return true;
    }

    public boolean update(Login login) throws JCertifException {
        getChecker().check(login);
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put("email", login.getEmail());
        BasicDBObject objectToUpdate = MongoDatabase.JCERTIFINSTANCE.readOne(Constantes.JCERTIFBACKEND_COLLECTIONNAME_LOGIN, dbObject);
        if (null == objectToUpdate) {
            throw new JCertifException(this, "Login to update does not exist");
        }
        objectToUpdate.put("password", login.getPassword());
        WriteResult result = MongoDatabase.JCERTIFINSTANCE.update(Constantes.JCERTIFBACKEND_COLLECTIONNAME_LOGIN, objectToUpdate);
        if (!Tools.isBlankOrNull(result.getError())) {
            throw new JCertifException(this, result.getError());
        }
        return true;
    }

    public Login get(String email) throws JCertifException {
        if (null == email) return null;
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put("email", email);
        BasicDBObject objectToGet = MongoDatabase.JCERTIFINSTANCE.readOne(Constantes.JCERTIFBACKEND_COLLECTIONNAME_LOGIN, dbObject);
        if (null == objectToGet) {
            throw new JCertifException(this, "Login to get does not exist");
        }
        Login login = new Login();
        login.setEmail(email);
        login.setPassword(objectToGet.get("password").toString());
        return login;
    }

    public List<Login> listOfLogin() {
        DBCursor dbCursor = MongoDatabase.JCERTIFINSTANCE.list(Constantes.JCERTIFBACKEND_COLLECTIONNAME_LOGIN);
        BasicDBObject object;
        Login login;
        List<Login> loginList = new ArrayList<Login>();
        while (dbCursor.hasNext()) {
            object = (BasicDBObject) dbCursor.next();
            login = new Login();
            login.setEmail(object.get("email").toString());
            login.setPassword(object.get("password").toString());
            loginList.add(login);
        }
        return loginList;
    }

    public List<BasicDBObject> list() {
        DBCursor dbCursor = MongoDatabase.JCERTIFINSTANCE.list(Constantes.JCERTIFBACKEND_COLLECTIONNAME_LOGIN);
        BasicDBObject object;
        List<BasicDBObject> loginList = new ArrayList<BasicDBObject>();
        while (dbCursor.hasNext()) {
            object = (BasicDBObject) dbCursor.next();
            loginList.add(object);
        }
        return loginList;
    }
}
