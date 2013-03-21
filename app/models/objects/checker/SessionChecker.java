package models.objects.checker;

import com.mongodb.BasicDBObject;

import models.exception.JCertifDuplicateObjectException;
import models.exception.JCertifInvalidRequestException;
import models.exception.JCertifObjectNotFoundException;
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
    public final void check(BasicDBObject objectToCheck) {

        if (null == objectToCheck) {
            throw new JCertifInvalidRequestException(this, "Object cannot be null");
        }

        Session session = new Session(objectToCheck);

        if (Tools.isBlankOrNull(session.getId())) {
            throw new JCertifInvalidRequestException(this, "Id cannot be empty or null");
        }

        if (Tools.isBlankOrNull(session.getTitle())) {
            throw new JCertifInvalidRequestException(this, "Title cannot be empty or null");
        }

        if (Tools.isBlankOrNull(session.getSummary())) {
            throw new JCertifInvalidRequestException(this, "Summary cannot be empty or null");
        }

        if (Tools.isBlankOrNull(session.getDescription())) {
            throw new JCertifInvalidRequestException(this, "Description cannot be empty or null");
        }

        if (Tools.isBlankOrNull(session.getStatus())) {
            throw new JCertifInvalidRequestException(this, "Status cannot be empty or null");
        }

        if (Tools.isBlankOrNull(session.getKeyword())) {
            throw new JCertifInvalidRequestException(this, "Keyword cannot be empty or null");
        }

        if (Tools.isBlankOrNull(session.getCategory())) {
            throw new JCertifInvalidRequestException(this, "Category cannot be empty or null");
        }

        if (null==session.getStart()) {
            throw new JCertifInvalidRequestException(this, "Start Date cannot be null");
        }

        if (!Tools.isValidDate(session.getStart())) {
            throw new JCertifInvalidRequestException(this, "Start Date is not valid, the format must be " + Constantes.DATEFORMAT);
        }

        if (null==session.getEnd()) {
            throw new JCertifInvalidRequestException(this, "End Date cannot be null");
        }

        if (!Tools.isValidDate(session.getEnd())) {
            throw new JCertifInvalidRequestException(this, "End Date is not valid, the format must be " + Constantes.DATEFORMAT);
        }

        SimpleDateFormat sdf = new SimpleDateFormat(Constantes.DATEFORMAT, Locale.FRANCE);
        try {
            Date startDate = sdf.parse(session.getStart());
            Date endDate = sdf.parse(session.getEnd());
            if(startDate.compareTo(endDate)>=0){
                throw new JCertifInvalidRequestException(this, "Start Date must not be equals or greater than End Date" );
            }
        } catch (ParseException e) {
            throw new JCertifInvalidRequestException(this, e.getMessage() );
        }

        SessionStatus sessionStatus = SessionStatusDB.getInstance().get(session.getStatus());

        if(null==sessionStatus){
            throw new JCertifInvalidRequestException(this, "Session Status '" + session.getStatus() + "' does not exist. Check Session Status List" );
        }

    }

    @Override
    public final void updateCheck(BasicDBObject objectToCheck) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public final void deleteCheck(BasicDBObject objectToCheck) {
    	
    	Session session = new Session(objectToCheck);
    	
    	if (Tools.isBlankOrNull(session.getId())) {
    		throw new JCertifInvalidRequestException(this, "Id cannot be empty or null");
        }
    	
    	if (Tools.isNotValidNumber(session.getId())) {
    		throw new JCertifInvalidRequestException(this, "Id must be a valid number");
        }
    	
    	if(null == SessionDB.getInstance().get(session.getId())){
    		throw new JCertifObjectNotFoundException(this, "You tried to delete an unregistred session.");
    	}
    }

    @Override
    public final void addCheck(BasicDBObject objectToCheck) {
        BasicDBObject dbObject = SessionDB.getInstance().get("id", objectToCheck.getString("id"));
        if (null != dbObject) {
            throw new JCertifDuplicateObjectException(this, "Session '" + objectToCheck.getString("id") + "' already exists");
        }
    }
}
