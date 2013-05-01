package models;

import static models.CheckHelper.checkId;
import static models.CheckHelper.checkNull;
import static models.CheckHelper.checkNullOrEmpty;

import java.util.List;

import models.exception.JCertifInvalidRequestException;
import models.util.Constantes;

import com.mongodb.BasicDBObject;

/**
 * <p>Objet metier representant une salle physique pouvant accueillir un évènement.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class Room extends JCertifModel {

	private String id;
    private String name;
    private String site;
    private String seats;
    private String description;
    private String photo;
    
    public Room(BasicDBObject basicDBObject){
    	super(basicDBObject);
    	this.id = basicDBObject.getString("id");
    	this.name = basicDBObject.getString("name");
    	this.site = basicDBObject.getString("site");
    	this.seats = basicDBObject.getString("seats");
    	this.description = basicDBObject.getString("description");
    	this.photo = basicDBObject.getString("photo");
    }
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	
	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public void updateCheck(BasicDBObject objectToCheck) {
		checkNull(objectToCheck);
    	checkId(objectToCheck);
	}

	@Override
	public void deleteCheck(BasicDBObject objectToCheck) {
		checkNull(objectToCheck);
    	checkId(objectToCheck);
	}

	@Override
	public void addCheck(BasicDBObject objectToCheck) {
		checkNull(objectToCheck);
    	checkId(objectToCheck);

        Room salle = new Room(objectToCheck);

        checkNullOrEmpty("Name", salle.getName());
        checkNullOrEmpty("Site", salle.getSite());
        checkNullOrEmpty("Seats", salle.getSeats());
        checkNullOrEmpty("Description", salle.getDescription());
        
        Site roomSite = Site.find(salle.getSite());
        
        if(null==roomSite){
            throw new JCertifInvalidRequestException("Room '" + salle.getSite() + "' does not exist. Check Room List" );
        }
	}

	@Override
	public BasicDBObject toBasicDBObject() {
		BasicDBObject dbObject = super.toBasicDBObject();
		dbObject.put("id", getId());
		dbObject.put("name", getName());
		dbObject.put("site", getSite());
		dbObject.put("seats", getSeats());
		dbObject.put("description", getDescription());
		dbObject.put("photo", getPhoto());
		return dbObject;
	}

	@Override
	public String getKeyName() {
		return Constantes.ID_ATTRIBUTE_NAME;
	}
	
	public static Room find(String idRoom){
		Room salle = null;
    	
    	BasicDBObject dbObject = getFinder().find(Room.class, Constantes.ID_ATTRIBUTE_NAME, idRoom);
    	
    	if(null != dbObject){
    		salle = new Room(dbObject);
    	}
		return salle; 
	}
	
	public static List<BasicDBObject> findAll(){
		return getFinder().findAll(Room.class);
	}
	
	public static List<BasicDBObject> findAll(String idSite){
		return getFinder().findAll(Room.class, Constantes.SITE_ATTRIBUTE_NAME, idSite);
	}

}
