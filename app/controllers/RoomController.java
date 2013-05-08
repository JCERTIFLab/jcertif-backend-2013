package controllers;

import models.Room;
import models.exception.JCertifInvalidRequestException;
import models.exception.JCertifObjectNotFoundException;
import models.util.Constantes;
import models.util.Json;
import models.util.Tools;
import models.validation.CheckHelper;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;
import controllers.Security.Admin;


public class RoomController extends Controller {


	public static Result listRoom() {

        return ok(Json.serialize(Room.findAll()));
    }
    
    public static Result getRoom(String idRoom) {

    	Room room = Room.find(idRoom);
		
		if(room == null){
			throw new JCertifObjectNotFoundException(Room.class, idRoom);
		}
		
        return ok(Json.serialize(room));
    }

    @Admin
    public static Result newRoom() {
		JsonNode jsonNode = request().body().asJson();
		
		Room room = Json.parse(Room.class, jsonNode.toString());
		
    	room.create();
		return ok(Json.serialize("Ok"));
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
		return ok(Json.serialize("Ok"));
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
		
		room.merge(Json.parse(Room.class, jsonNode.toString()));
		room.save();
		return ok(Json.serialize("Ok"));
   
   }
}
