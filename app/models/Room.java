package models;

import java.util.List;

import models.util.Constantes;
import models.validation.Constraints.NotBlank;
import models.validation.Constraints.Site;

import com.mongodb.BasicDBObject;

/**
 * <p>Objet metier representant une salle physique pouvant accueillir un évènement.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class Room extends JCertifModel {

	@NotBlank(propertyName="Id")
	private String id;
	@NotBlank(propertyName="Name")
    private String name;
	@NotBlank(propertyName="Site") @Site
    private String site;
	@NotBlank(propertyName="Seats")
    private String seats;
	@NotBlank(propertyName="Description")
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
	public int create() {
		setId(getFinder().findNextSequence(Room.class));
		return super.create();
	}
	
	public static Room find(String idRoom){
		return getFinder().find(Room.class, Constantes.ID_ATTRIBUTE_NAME, idRoom);
	}
	
	public static List<Room> findAll(){
		return getFinder().findAll(Room.class);
	}
	
	public static List<Room> findAll(String idSite){
		return getFinder().findAll(Room.class, Constantes.SITE_ATTRIBUTE_NAME, idSite);
	}

}
