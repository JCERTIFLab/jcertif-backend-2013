package models.objects.access;

import models.objects.Category;
import models.util.Constantes;

/**
 * <p>Implémentation d'un {@link ReferentielDB} pour la persistance
 * des objets de type {@link Category}</p>
 * 
 * @author Martial SOMDA
 *
 */
public class CategoryDB extends ReferentielDB<Category> {

	private static final CategoryDB INSTANCE = new CategoryDB();
	
	public CategoryDB() {
		super(Constantes.COLLECTION_CATEGORY);
	}
	
	public static CategoryDB getInstance(){
		return INSTANCE;
	}

}
