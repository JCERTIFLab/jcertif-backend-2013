package models.objects;

import models.objects.access.CiviliteDB;
import models.objects.access.JCertifObjectDB;

import com.mongodb.BasicDBObject;

/**
 * <p>Objet metier representant la civilité d'un membre.</p>
 * Ex : 
 * <ul>
 * <li>M.</li>
 * <li>Mme</li>
 * <li>Mlle</li>
 * <li>...</li>
 * </ul>
 * 
 * @author Martial SOMDA
 *
 */
public class Civilite extends Referentiel {

	public Civilite(String label) {
		super(label);
	}
	
	public Civilite(BasicDBObject basicDBObject){
		super(basicDBObject);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected JCertifObjectDB<Civilite> getDBObject() {
		return CiviliteDB.getInstance();
	}

}
