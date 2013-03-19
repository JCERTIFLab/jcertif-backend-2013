package models.objects.access;

import models.objects.Category;
import models.objects.checker.CategoryChecker;
import models.util.Constantes;

/**
 * <p>Implémentation d'un {@link ReferentielDB} pour la persistance
 * des objets de type {@link Category}</p>
 * 
 * @author Martial SOMDA
 *
 */
public class CategoryDB extends ReferentielDB<Category> {

	private static final CategoryDB instance = new CategoryDB();
	
	public CategoryDB() {
		super(Constantes.COLLECTION_CATEGORY, new CategoryChecker());
	}
	
	public static CategoryDB getInstance(){
		return instance;
	}

}
