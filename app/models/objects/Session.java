package models.objects;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import models.util.Tools;

import java.util.ArrayList;
import java.util.List;

public class Session extends JCertifObject {
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

    public final List getSpeakers() {
        return speakers;
    }

    public final void setSpeakers(List speakers1) {
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

}
