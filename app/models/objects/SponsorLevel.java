package models.objects;

import models.objects.access.JCertifObjectDB;
import models.objects.access.SponsorLevelDB;


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

	@Override
	@SuppressWarnings("unchecked")
	protected JCertifObjectDB<SponsorLevel> getDBObject() {
		return SponsorLevelDB.getInstance();
	}
	
}
