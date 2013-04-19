package models;

import java.util.List;

import models.util.Constantes;

import com.mongodb.BasicDBObject;



/**
 * <p>Objet metier representant un niveau de partenariat.</p>
 * Ex : 
 * <ul>
 * <li>Gold</li>
 * <li>Platinium</li>
 * <li>Premium</li>
 * <li>...</li>
 * </ul>
 * 
 * @author Martial SOMDA
 *
 */
public class SponsorLevel extends Referentiel {
	
	public SponsorLevel(String label) {
		super(label);
	}
	
	public SponsorLevel(BasicDBObject basicDBObject) {
		super(basicDBObject);
	}
	
	public static SponsorLevel find(String level){
		SponsorLevel sponsorLevel = null;
    	
    	BasicDBObject dbObject = getFinder().find(SponsorLevel.class, Constantes.LABEL_ATTRIBUTE_NAME, level);
    	
    	if(null != dbObject){
    		sponsorLevel = new SponsorLevel(dbObject);
    	}
		return sponsorLevel; 
	}
	
	public static List<BasicDBObject> findAll(){
		return getFinder().findAll(SponsorLevel.class);
	}
	
}
