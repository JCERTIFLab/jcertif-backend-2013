package objects;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import util.Tools;

import java.util.ArrayList;
import java.util.List;

public class Session extends JCertfifObject {
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
        this.setId(basicDBObject.getString("id"));
        this.setTitle(basicDBObject.getString("title"));
        this.setSummary(basicDBObject.getString("summary"));
        this.setDescription(basicDBObject.getString("description"));
        this.setStatus(basicDBObject.getString("status"));
        this.setKeyword(basicDBObject.getString("keyword"));
        this.getCategory().addAll(Tools.basicDBListToJavaList((BasicDBList) basicDBObject.get("category")));
        this.setStart(basicDBObject.getString("start"));
        this.setEnd(basicDBObject.getString("end"));
        this.getSpeakers().addAll(Tools.basicDBListToJavaList((BasicDBList) basicDBObject.get("speakers")));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List speakers) {
        this.speakers = speakers;
    }

    @Override
    public BasicDBObject toBasicDBObject() {
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
