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
	
	public SponsorLevel(BasicDBObject basicDBObject) {
		super(basicDBObject);
	}
	
	public static SponsorLevel find(String level){
    	return getFinder().find(SponsorLevel.class, Constantes.LABEL_ATTRIBUTE_NAME, level);
	}
	
	public static List<SponsorLevel> findAll(){
		return getFinder().findAll(SponsorLevel.class);
	}
	
}
