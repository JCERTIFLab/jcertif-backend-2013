package models;

import java.util.List;

import models.util.Tools;

import com.mongodb.BasicDBObject;

/**
 * @author Martial SOMDA
 *
 */
public class SessionInfo extends JCertifModel {

	private String id;
    private String title;
    private String summary;
    private String description;
    private String keyword;
    private List<String> category;
    private String start;
    private String end;
    
	public SessionInfo(Session session) {
		super(new BasicDBObject());
		this.id = session.getId();
		this.title = session.getTitle();
		this.summary = session.getSummary();
		this.description = session.getDescription();
		this.keyword = session.getKeyword();
		this.category = session.getCategory();
		this.start = session.getStart();
		this.end = session.getEnd();
	}

	public final String getId() {
        return id;
    }

    public final void setId(String id) {
        this.id = id;
    }
    
	public String getTitle() {
		return title;
	}

	public String getSummary() {
		return summary;
	}

	public String getDescription() {
		return description;
	}

	public String getKeyword() {
		return keyword;
	}

	public List<String> getCategory() {
		return category;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}
	
	@Override
	public BasicDBObject toBasicDBObject() {
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("id", getId());
		dbObject.put("title", getTitle());
		dbObject.put("summary", getSummary());
		dbObject.put("description", getDescription());
		dbObject.put("keyword", getKeyword());
		dbObject.put("category", Tools.javaListToBasicDBList(getCategory()));
		dbObject.put("start", getStart());
		dbObject.put("end", getEnd());
		return dbObject;
	}

}
