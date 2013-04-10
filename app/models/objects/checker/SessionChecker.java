package models.objects.checker;

import static models.objects.checker.CheckerHelper.checkId;
import static models.objects.checker.CheckerHelper.checkNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import models.exception.JCertifInvalidRequestException;
import models.objects.Session;
import models.objects.access.SessionStatusDB;
import models.util.Constantes;
import models.util.Tools;

import com.mongodb.BasicDBObject;

public class SessionChecker extends Checker{

    @Override
    public final void updateCheck(BasicDBObject objectToCheck) {
    	checkNull(objectToCheck);
    	checkId(objectToCheck);
    }

    @Override
    public final void deleteCheck(BasicDBObject objectToCheck) {  	
    	checkNull(objectToCheck);
    	checkId(objectToCheck);
    }

    @Override
    public final void addCheck(BasicDBObject objectToCheck) {
    	checkNull(objectToCheck);
    	checkId(objectToCheck);

        Session session = new Session(objectToCheck);

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

        BasicDBObject sessionStatus = SessionStatusDB.getInstance().get(Constantes.LABEL_ATTRIBUTE_NAME, session.getStatus());
        
        if(null==sessionStatus){
            throw new JCertifInvalidRequestException(this, "Session Status '" + session.getStatus() + "' does not exist. Check Session Status List" );
        }
    }
}
