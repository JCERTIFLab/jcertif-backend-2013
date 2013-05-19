package models;

import java.util.List;

import models.util.Constantes;

import com.mongodb.BasicDBObject;

/**
 * <p>Objet metier représentant un administrateur JCertif Conference.</p>
 *
 */
public class Admin extends Member {
	
    public Admin(BasicDBObject basicDBObject) {
        super(basicDBObject);
    }
    
    public static Admin find(String email){
    	return getFinder().find(Admin.class, Constantes.EMAIL_ATTRIBUTE_NAME, email);
	}
    
    public static List<Admin> findAll(){
		return getFinder().findAll(Admin.class);
	}
    
    public static List<Admin> findAll(String version){
		return getFinder().findAll(Admin.class, version);
	}
}
