package models.database;

import java.io.IOException;

import models.Model;
import models.util.Json;
import models.util.Tools;
import play.Logger;

/**
 * <p>Cette classe est un paliatif à la fonction $eval qui ne peut être utilisée en PROD pour insérer des données dinitialisation en base. 
 * <br/>Elle permet d'interpreter un fichier javascripit d'initalisation et de convertir les ordres javascripts en commandes Mongo DB</p>
 * 
 * @author Martial SOMDA
 *
 */
public final class DBInitializer {

	private static final String COMMAND_SEPARATOR = ";";
	private static final String COMMAND_START = "db.";
	
	private DBInitializer(){		
	}
	
	public static void init(String initFile) throws IOException {
		String file = Tools.getFileContent(initFile);
		String[] commands = file.split(COMMAND_SEPARATOR);
		Logger.info("about to run " + commands.length + " commands against database");
		for(String command : commands){
			command = command.trim();
			if(isValidSyntax(command)){
				readAndExecuteCommand(command);
			}else{
				Logger.error("Cannot execute command, check syntax : " + command);
			}
		}
	}

	private static void readAndExecuteCommand(String command) {
		//suppression de db.
		command = command.substring(COMMAND_START.length());
		//supression du caractère de fin de commande ;
		command = command.substring(0,command.length() -1);
		
		String[] insertCommandAndArg = command.split(".insert");
		String[] removeCommandAndArg = command.split(".remove");
		if(insertCommandAndArg.length == 2){
			readAndExecuteInsertCommand(insertCommandAndArg[0], 
					insertCommandAndArg[1].replace("(", "").replace(")", ""));
		}else if(removeCommandAndArg.length == 2){
			readAndExecuteRemoveCommand(removeCommandAndArg[0]);
		}else{
			Logger.error("Unsupported command, check syntax : " + command);
		}
		
	}
	
	private static void readAndExecuteInsertCommand(String collectionName, String data) {		
		try {
			Class<? extends Model> modelClass = Model.getModelClass(collectionName);
			Model model = Json.parse(modelClass, data);
			model.create();	
		} catch (ClassNotFoundException e) {
			Logger.error("Unsupported collectionName : " + collectionName);
		}		
	}

	private static void readAndExecuteRemoveCommand(String collectionName) {		
		try {
			Class<? extends Model> modelClass = Model.getModelClass(collectionName);
			for(Model model : Model.getFinder().findAll(modelClass)){
				model.remove();
			}
		} catch (ClassNotFoundException e) {
			Logger.error("Unsupported collectionName : " + collectionName);
		}		
	}

	private static boolean isValidSyntax(String command) {
		if(command.startsWith(COMMAND_START)){
			return true;
		}else{
			return false;
		}
	}
}
