package objects.checker;

import com.mongodb.BasicDBObject;
import exception.JCertifException;
import objects.Session;
import objects.access.SessionDB;
import util.Constantes;
import util.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SessionChecker extends Checker{

    @Override
    public void check(BasicDBObject objectToCheck) throws JCertifException {

        if (null == objectToCheck) {
            throw new JCertifException(this, "Object cannot be null");
        }

        Session session = new Session(objectToCheck);

        if (Tools.isBlankOrNull(session.getId())) {
            throw new JCertifException(this, "Id cannot be empty or null");
        }

        if (Tools.isBlankOrNull(session.getTitle())) {
            throw new JCertifException(this, "Title cannot be empty or null");
        }

        if (Tools.isBlankOrNull(session.getSummary())) {
            throw new JCertifException(this, "Summary cannot be empty or null");
        }

        if (Tools.isBlankOrNull(session.getDescription())) {
            throw new JCertifException(this, "Description cannot be empty or null");
        }

        if (Tools.isBlankOrNull(session.getStatus())) {
            throw new JCertifException(this, "Status cannot be empty or null");
        }

        if (Tools.isBlankOrNull(session.getKeyword())) {
            throw new JCertifException(this, "Keyword cannot be empty or null");
        }

        if (Tools.isBlankOrNull(session.getCategory())) {
            throw new JCertifException(this, "Category cannot be empty or null");
        }

        if (null==session.getStart()) {
            throw new JCertifException(this, "Start Date cannot be null");
        }

        if (!Tools.isValidDate(session.getStart())) {
            throw new JCertifException(this, "Start Date is not valid, the format must be " + Constantes.JCERTIFBACKEND_DATEFORMAT);
        }

        if (null==session.getEnd()) {
            throw new JCertifException(this, "End Date cannot be null");
        }

        if (!Tools.isValidDate(session.getEnd())) {
            throw new JCertifException(this, "End Date is not valid, the format must be " + Constantes.JCERTIFBACKEND_DATEFORMAT);
        }

        SimpleDateFormat sdf = new SimpleDateFormat(Constantes.JCERTIFBACKEND_DATEFORMAT);
        try {
            Date startDate = sdf.parse(session.getStart());
            Date endDate = sdf.parse(session.getEnd());
            if(startDate.compareTo(endDate)>=0){
                throw new JCertifException(this, "Start Date must not be equals or greater than End Date" );
            }
        } catch (ParseException e) {
            //This exception must not be throws
        }

    }

    @Override
    public void updateCheck(BasicDBObject objectToCheck) throws JCertifException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteCheck(BasicDBObject objectToCheck) throws JCertifException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addCheck(BasicDBObject objectToCheck) throws JCertifException {
        BasicDBObject dbObject = SessionDB.getInstance().get("id", objectToCheck.getString("id"));
        if (null != dbObject) {
            throw new JCertifException(this, "Session \"" + objectToCheck.getString("id") + "\" already exists");
        }
    }
}
