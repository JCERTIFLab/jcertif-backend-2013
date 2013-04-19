package models;

import static models.CheckHelper.checkId;
import static models.CheckHelper.checkNull;
import static models.CheckHelper.checkNullOrEmpty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import models.exception.JCertifInvalidRequestException;
import models.util.Constantes;
import models.util.Tools;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class Session extends JCertifModel {

	private String id;
    private String title;
    private String summary;
    private String description;
    private String status;
    private String keyword;
    private List<String> category = new ArrayList<String>();
    private String start;
    private String end;
    private List<String> speakers = new ArrayList<String>();

    public Session(BasicDBObject basicDBObject){
        super();
        this.id = basicDBObject.getString("id");
        this.title = basicDBObject.getString("title");
        this.summary = basicDBObject.getString("summary");
        this.description = basicDBObject.getString("description");
        this.status = basicDBObject.getString("status");
        this.keyword = basicDBObject.getString("keyword");
        this.category.addAll(Tools.basicDBListToJavaList((BasicDBList) basicDBObject.get("category")));
        this.start = basicDBObject.getString("start");
        this.end = basicDBObject.getString("end");
        this.speakers.addAll(Tools.basicDBListToJavaList((BasicDBList) basicDBObject.get("speakers")));
    }

    
    public final String getId() {
        return id;
    }

    public final void setId(String id1) {
        this.id = id1;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title1) {
        this.title = title1;
    }

    public final String getSummary() {
        return summary;
    }

    public final void setSummary(String summary1) {
        this.summary = summary1;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(String description1) {
        this.description = description1;
    }

    public final String getStatus() {
        return status;
    }

    public final void setStatus(String status1) {
        this.status = status1;
    }

    public final String getKeyword() {
        return keyword;
    }

    public final void setKeyword(String keyword1) {
        this.keyword = keyword1;
    }

    public final List<String> getCategory() {
        return category;
    }

    public final void setCategory(List<String> category1) {
        this.category = category1;
    }

    public final String getStart() {
        return start;
    }

    public final void setStart(String start1) {
        this.start = start1;
    }

    public final String getEnd() {
        return end;
    }

    public final void setEnd(String end1) {
        this.end = end1;
    }

    public final List<String> getSpeakers() {
        return speakers;
    }

    public final void setSpeakers(List<String> speakers1) {
        this.speakers = speakers1;
    }

    @Override
    public final BasicDBObject toBasicDBObject() {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("id", getId());
        basicDBObject.put("title", getTitle());
        basicDBObject.put("summary", getSummary());
        basicDBObject.put("description", getDescription());
        basicDBObject.put("status", getStatus());
        basicDBObject.put("keyword", getKeyword());
        basicDBObject.put("category", Tools.javaListToBasicDBList(getCategory()));
        basicDBObject.put("start", getStart());
        basicDBObject.put("end", getEnd());
        basicDBObject.put("speakers", Tools.javaListToBasicDBList(getSpeakers()));
        return basicDBObject;
    }

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

        checkNullOrEmpty("Title", session.getTitle());
        checkNullOrEmpty("Summary", session.getSummary());
        checkNullOrEmpty("Description", session.getDescription());
        checkNullOrEmpty("Status", session.getStatus());
        checkNullOrEmpty("Keyword", session.getKeyword());
        checkNullOrEmpty("Start Date", session.getStart());
        checkNullOrEmpty("End Date", session.getEnd());

        if (Tools.isBlankOrNull(session.getCategory())) {
            throw new JCertifInvalidRequestException(this, "Category cannot be empty or null");
        }

        if (!Tools.isValidDate(session.getStart())) {
            throw new JCertifInvalidRequestException(this, "Start Date is not valid, the format must be " + Constantes.DATEFORMAT);
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

        SessionStatus sessionStatus = SessionStatus.find(session.getStatus());
        
        if(null==sessionStatus){
            throw new JCertifInvalidRequestException(this, "Session Status '" + session.getStatus() + "' does not exist. Check Session Status List" );
        }
    }

	@Override
	public String getKeyName() {
		return Constantes.ID_ATTRIBUTE_NAME;
	}
	
	public static Session find(String email){
		Session session = null;
    	
    	BasicDBObject dbObject = getFinder().find(Session.class, Constantes.ID_ATTRIBUTE_NAME, email);
    	
    	if(null != dbObject){
    		session = new Session(dbObject);
    	}
		return session; 
	}
	
	public static List<BasicDBObject> findAll(){
		return getFinder().findAll(Session.class);
	}
}
