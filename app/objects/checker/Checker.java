package objects.checker;

import com.mongodb.BasicDBObject;
import exception.JCertifException;

public abstract class Checker {

    public abstract void check(BasicDBObject objectToCheck) throws JCertifException;
}
