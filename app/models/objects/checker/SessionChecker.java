package models.objects.checker;

import com.mongodb.BasicDBObject;
import models.exception.JCertifException;
import models.objects.Session;
import models.objects.SessionStatus;
import models.objects.access.SessionDB;
import models.objects.access.SessionStatusDB;
import models.util.Constantes;
import models.util.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SessionChecker extends Checker{

    @Override
    public final void check(BasicDBObject objectToCheck) throws JCertifException {

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
            throw new JCertifException(this, "Start Date is not valid, the format must be " + Constantes.DATEFORMAT);
        }

        if (null==session.getEnd()) {
            throw new JCertifException(this, "End Date cannot be null");
        }

        if (!Tools.isValidDate(session.getEnd())) {
            throw new JCertifException(this, "End Date is not valid, the format must be " + Constantes.DATEFORMAT);
        }

        SimpleDateFormat sdf = new SimpleDateFormat(Constantes.DATEFORMAT, Locale.FRANCE);
        try {
            Date startDate = sdf.parse(session.getStart());
            Date endDate = sdf.parse(session.getEnd());
            if(startDate.compareTo(endDate)>=0){
                throw new JCertifException(this, "Start Date must not be equals or greater than End Date" );
            }
        } catch (ParseException e) {
            throw new JCertifException(this, e.getMessage() );
        }

        SessionStatus sessionStatus = SessionStatusDB.getInstance().get(session.getStatus());

        if(null==sessionStatus){
            throw new JCertifException(this, "Session Status '" + session.getStatus() + "' does not exist. Check Session Status List" );
        }

    }

    @Override
    public final void updateCheck(BasicDBObject objectToCheck) throws JCertifException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public final void deleteCheck(BasicDBObject objectToCheck) throws JCertifException {
    	
    	Session session = new Session(objectToCheck);
    	
    	if (Tools.isBlankOrNull(session.getId())) {
    		throw new JCertifException(this, "Id cannot be empty or null");
        }
    	
    	if (Tools.isNotValidNumber(session.getId())) {
    		throw new JCertifException(this, "Id must be a valid number");
        }
    	
    	if(null == SessionDB.getInstance().get(session.getId())){
    		throw new JCertifException(this, "You tried to delete an unregistred session.");
    	}
    }

    @Override
    public final void addCheck(BasicDBObject objectToCheck) throws JCertifException {
        BasicDBObject dbObject = SessionDB.getInstance().get("id", objectToCheck.getString("id"));
        if (null != dbObject) {
            throw new JCertifException(this, "Session '" + objectToCheck.getString("id") + "' already exists");
        }
    }
}
