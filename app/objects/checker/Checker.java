package objects.checker;

import exception.JCertifException;

public abstract class Checker {

    public abstract void check(Object objectToCheck) throws JCertifException;
}
