package models.objects.access;

import models.objects.Civilite;
import models.util.Constantes;

/**
 * <p>Implémentation d'un {@link ReferentielDB} pour la persistance
 * des objets de type {@link Civilite}</p>
 * 
 * @author Martial SOMDA
 *
 */
public class CiviliteDB extends ReferentielDB<Civilite> {

	private static final CiviliteDB INSTANCE = new CiviliteDB();
	
	public CiviliteDB() {
		super(Constantes.COLLECTION_CIVILITE);
	}
	
	public static CiviliteDB getInstance(){
		return INSTANCE;
	}

}
