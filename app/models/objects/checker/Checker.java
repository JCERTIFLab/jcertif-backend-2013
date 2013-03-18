package models.objects.checker;

import com.mongodb.BasicDBObject;
import models.exception.JCertifException;
import models.util.Constantes;

public abstract class Checker {

    public abstract void check(BasicDBObject objectToCheck) throws JCertifException;

    public abstract void updateCheck(BasicDBObject objectToCheck) throws JCertifException;

    public abstract void deleteCheck(BasicDBObject objectToCheck) throws JCertifException;

    public abstract void addCheck(BasicDBObject objectToCheck) throws JCertifException;

    public boolean checkPassword(String oldPassword, String newPassword, boolean checkTwo){
        if(checkTwo){

            if (oldPassword==null || newPassword==null) {
                return false;
            }

            if(oldPassword.equals(newPassword)){
                return false;
            }

            return oldPassword.length() >= Constantes.PASSWORD_MIN_LENGTH && newPassword.length() >= Constantes.PASSWORD_MIN_LENGTH;
        }
        return oldPassword!=null && oldPassword.length() >= Constantes.PASSWORD_MIN_LENGTH;
    }
}
