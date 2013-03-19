package models.objects.access;

import models.objects.SponsorLevel;
import models.objects.checker.SponsorLevelChecker;
import models.util.Constantes;

/**
 * <p>Implémentation d'un {@link ReferentielDB} pour la persistance
 * des objets de type {@link SponsorLevel}</p>
 * 
 * @author MSOMDA
 *
 */
public class SponsorLevelDB extends ReferentielDB<SponsorLevel> {

	private static final SponsorLevelDB instance = new SponsorLevelDB();
	
	public SponsorLevelDB() {
		super(Constantes.COLLECTION_SPONSOR_LEVEL, new SponsorLevelChecker());
	}

	public static SponsorLevelDB getInstance(){
		return instance;
	}
}
