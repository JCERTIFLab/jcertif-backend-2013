package objects;

import com.mongodb.BasicDBObject;

import java.util.Date;
import java.util.List;

public class Session extends JCertfifObject {
    private String id;
    private String title;
    private String summary;
    private String description;
    private String status;
    private String keyword;
    private String category;
    private Date start;
    private Date end;
    private List speakers;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
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
        //TODO
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
