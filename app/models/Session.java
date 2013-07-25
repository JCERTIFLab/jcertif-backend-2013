package models;

import java.util.ArrayList;
import java.util.List;

import models.util.Constantes;
import models.util.Tools;
import models.validation.Constraints.Category;
import models.validation.Constraints.Date;
import models.validation.Constraints.DatesCoherence;
import models.validation.Constraints.NotBlank;
import models.validation.Constraints.Room;
import models.validation.Constraints.SessionStatus;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

@DatesCoherence
public class Session extends JCertifModel {

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
	@NotBlank(propertyName="Start Date") @Date(propertyName="Start Date")
    private String start;
	@NotBlank(propertyName="End Date") @Date(propertyName="End Date")
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
        this.category.addAll(Tools.basicDBListToJavaList((BasicDBList) basicDBObject.get("category")));
        this.start = basicDBObject.getString("start");
        this.end = basicDBObject.getString("end");
        this.speakers.addAll(Tools.basicDBListToJavaList((BasicDBList) basicDBObject.get("speakers")));
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
        return super.create();
    }
	
	public static Session find(String idSession){
    	return getFinder().find(Session.class, Constantes.ID_ATTRIBUTE_NAME, idSession);
	}
	
	public static List<Session> findBySpeaker(String email){
    	return getFinder().findAll(Session.class, Constantes.SPEAKERS_ATTRIBUTE_NAME, email);
	}
	
	public static List<Session> findAll(){
		return getFinder().findAll(Session.class);
	}
	
	public static List<Session> findAll(String version){
		return getFinder().findAll(Session.class, version);
	}
}
