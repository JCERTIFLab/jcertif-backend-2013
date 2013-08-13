package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.exception.JCertifException;
import models.notifiers.EmailNotification;
import models.util.Constantes;
import models.util.Tools;
import models.validation.Constraints.Category;
import models.validation.Constraints.Date;
import models.validation.Constraints.DatesCoherence;
import models.validation.Constraints.NotBlank;
import models.validation.Constraints.Room;
import models.validation.Constraints.SessionStatus;

import com.mongodb.BasicDBObject;

@DatesCoherence
public class Session extends JCertifModel {
	
	public static String DRAFT_STATUS = "Brouillon";
	public static String APPROVED_STATUS = "Approuvé";
	
	@NotBlank(propertyName="Id")
	private String id;
	@NotBlank(propertyName="Title")
    private String title;
	@NotBlank(propertyName="Summary")
    private String summary;
	@NotBlank(propertyName="Description")
    private String description;
	@NotBlank(propertyName="Status") @SessionStatus
    private String status;
	@NotBlank(propertyName="Keyword")
    private String keyword;
	@NotBlank(propertyName="Category") @Category
    private List<String> category = new ArrayList<String>();
    @Date(propertyName="Start Date")
    private String start;
    @Date(propertyName="End Date")
    private String end;
    private List<String> speakers = new ArrayList<String>();
    @Room
    private String room;

    public Session(BasicDBObject basicDBObject){
    	super(basicDBObject);
        this.id = basicDBObject.getString("id");
        this.title = basicDBObject.getString("title");
        this.summary = basicDBObject.getString("summary");
        this.description = basicDBObject.getString("description");
        this.status = basicDBObject.getString("status");
        this.keyword = basicDBObject.getString("keyword");
        this.category.addAll(Tools.basicDBListToJavaList(basicDBObject.get("category")));
        this.start = basicDBObject.getString("start");
        this.end = basicDBObject.getString("end");
        this.speakers.addAll(Tools.basicDBListToJavaList(basicDBObject.get("speakers")));
        this.room = basicDBObject.getString("room");
    }

    
    public final String getId() {
        return id;
    }

    public final void setId(String id) {
        this.id = id;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public final String getSummary() {
        return summary;
    }

    public final void setSummary(String summary) {
        this.summary = summary;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(String description) {
        this.description = description;
    }

    public final String getStatus() {
        return status;
    }

    public final void setStatus(String status) {
        this.status = status;
    }

    public final String getKeyword() {
        return keyword;
    }

    public final void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public final List<String> getCategory() {
        return category;
    }

    public final void setCategory(List<String> category) {
        this.category = category;
    }

    public final String getStart() {
        return start;
    }

    public final void setStart(String start) {
        this.start = start;
    }

    public final String getEnd() {
        return end;
    }

    public final void setEnd(String end) {
        this.end = end;
    }

    public final List<String> getSpeakers() {
        return speakers;
    }

    public final void setSpeakers(List<String> speakers) {
        this.speakers = speakers;
    }

    public String getRoom() {
		return room;
	}


	public void setRoom(String room) {
		this.room = room;
	}


	@Override
    public final BasicDBObject toBasicDBObject() {
        BasicDBObject basicDBObject = super.toBasicDBObject();
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
        basicDBObject.put("room", getRoom());
        return basicDBObject;
    }

    @Override
    public int create() {
    	setId(getFinder().findNextSequence(Session.class));     
    	setStatus(DRAFT_STATUS);
        int id = super.create();       
        boolean isOK = id == 1;
		
		if(isOK){
			/* send email */
			EmailNotification.sendConfirmPropositionMail(this);
		}
		
		return id;
    }
	
	public static Session find(String idSession){
    	return getFinder().find(Session.class, Constantes.ID_ATTRIBUTE_NAME, idSession);
	}
	
	public static List<Session> findBySpeaker(String email, boolean filter){
		Map<String,Object> query = new HashMap<String,Object>();
		query.put(Constantes.SPEAKERS_ATTRIBUTE_NAME, email);
		if(filter){
			query.put(Constantes.STATUS_ATTRIBUTE_NAME, Session.APPROVED_STATUS);
		}		
    	return orderByStartDate(getFinder().findAll(Session.class, query));
	}
	
	public static List<Session> findAll(boolean filter){
		List<Session> sessions = new ArrayList<Session>();
		if(filter){
			sessions = getFinder().findAll(Session.class, Constantes.STATUS_ATTRIBUTE_NAME, APPROVED_STATUS);
		}else{
			sessions = getFinder().findAll(Session.class);
		}
		return orderByStartDate(sessions);
	}
	
	public static List<Session> findAll(String version, boolean filter){
		List<Session> sessions = new ArrayList<Session>();
		if(filter){
			sessions = getFinder().findAll(Session.class, Constantes.STATUS_ATTRIBUTE_NAME, APPROVED_STATUS, version);
		}else{
			sessions = getFinder().findAll(Session.class, version);
		}
		return orderByStartDate(sessions);
	}

	private static List<Session> orderByStartDate(List<Session> sessions) {
		// tri java provisoire, priviligier des champ date typé puis déléger le tri à la bdd
		Collections.sort(sessions, new Comparator<Session>() {

			@Override
			public int compare(Session session1, Session session2) {
				
				if(Tools.isBlankOrNull(session1.getStart()) 
						&& !Tools.isBlankOrNull(session2.getStart())){
					return 1;
				}else if(Tools.isBlankOrNull(session2.getStart()) 
						&& !Tools.isBlankOrNull(session1.getStart())){
					return -1;
				}else if(Tools.isBlankOrNull(session1.getStart()) 
						&& Tools.isBlankOrNull(session2.getStart())){
					return 0;
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat(Constantes.DATEFORMAT);
				try {
					java.util.Date startDate1 = sdf.parse(session1.getStart());
					java.util.Date startDate2 = sdf.parse(session2.getStart());
					
					return startDate1.compareTo(startDate2);
				} catch (ParseException e) {
					throw new JCertifException(e.getMessage(), e);
				}
			}
		});
		return sessions;
	}
}
