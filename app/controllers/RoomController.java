package controllers;

import models.Room;
import models.exception.JCertifInvalidRequestException;
import models.exception.JCertifObjectNotFoundException;
import models.util.Constantes;
import models.util.Tools;
import models.validation.CheckHelper;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

import controllers.Security.Admin;


public class RoomController extends Controller {


	public static Result listRoom() {

        return ok(JSON.serialize(Room.findAll()));
    }
    
    public static Result getRoom(String idRoom) {

    	Room room = Room.find(idRoom);
		
		if(room == null){
			throw new JCertifObjectNotFoundException(Room.class, idRoom);
		}
		
        return ok(JSON.serialize(room.toBasicDBObject()));
    }

    @Admin
    public static Result newRoom() {
		JsonNode jsonNode = request().body().asJson();
		
		Room room = new Room((BasicDBObject)JSON.parse(jsonNode.toString()));
		
    	room.create();
		return ok(JSON.serialize("Ok"));
    }

    @Admin
    public static Result removeRoom() {
		JsonNode jsonNode = request().body().asJson();
		
		String idRoom = jsonNode.findPath(Constantes.ID_ATTRIBUTE_NAME).getTextValue();
		
		if(Tools.isBlankOrNull(idRoom)){
			throw new JCertifInvalidRequestException("Room id cannot be null or empty");
		}
		
		if(Tools.isNotValidNumber(idRoom)){
			throw new JCertifInvalidRequestException("Id must be a valid number");
		}
		
		Room room = Room.find(idRoom);
		
		if(null == room){
			throw new JCertifObjectNotFoundException(Room.class, idRoom);
		}

		room.remove();
		return ok(JSON.serialize("Ok"));
    }

    @Admin
    public static Result updateRoom() {
		JsonNode jsonNode = request().body().asJson();
		String idRoom = jsonNode.findPath(Constantes.ID_ATTRIBUTE_NAME).getTextValue();
		String version = jsonNode.findPath(Constantes.VERSION_ATTRIBUTE_NAME).getTextValue();
		
    	if(Tools.isBlankOrNull(idRoom)){
			throw new JCertifInvalidRequestException("Room id cannot be null or empty");
		}
    	
    	if(Tools.isNotValidNumber(idRoom)){
			throw new JCertifInvalidRequestException("Id must be a valid number");
		}
    	
		Room room = Room.find(idRoom);
		
		if(null == room){
			throw new JCertifObjectNotFoundException(Room.class, idRoom);
		}
		
		CheckHelper.checkVersion(room, version);
		
		room.merge(BasicDBObject.class.cast(JSON.parse(jsonNode.toString())));
		room.save();
		return ok(JSON.serialize("Ok"));
   
   }
}
