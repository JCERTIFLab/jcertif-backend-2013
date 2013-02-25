package objects.checker;

import com.mongodb.BasicDBObject;
import exception.JCertifException;

public abstract class Checker {

    public abstract void check(BasicDBObject objectToCheck) throws JCertifException;

    public abstract void updateCheck(BasicDBObject objectToCheck) throws JCertifException;

    public abstract void deleteCheck(BasicDBObject objectToCheck) throws JCertifException;

    public abstract void addCheck(BasicDBObject objectToCheck) throws JCertifException;

    public boolean checkPassword(String oldPassword, String newPassword){
        return oldPassword!=null && newPassword!=null && !oldPassword.equals(newPassword);
    }
}
