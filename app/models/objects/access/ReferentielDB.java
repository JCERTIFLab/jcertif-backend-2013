package models.objects.access;

import com.mongodb.BasicDBObject;
import models.exception.JCertifException;
import models.exception.JCertifResourceAccessException;
import models.objects.Referentiel;
import models.objects.checker.Checker;
import models.util.Constantes;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>Objet d'accès aux données de type {@link Referentiel}.<br/>
 * Cette Objet implémente la classe {@link IDao} et fournit des méthodes de type <code>CRUD</code>.</p>
 * 
 * @author Martial SOMDA
 *
 */
public abstract class ReferentielDB<T extends Referentiel> extends JCertifObjectDB<T>{

	private Class<T> referentielClass;

	@SuppressWarnings("unchecked")
	public ReferentielDB(String collectionName, Checker checker) {
		super(collectionName, checker);
		this.referentielClass = 
			(Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}	
	
		
	public final boolean add(T referentiel) {
		return super.add(referentiel.toBasicDBObject());
	}
	
	public final boolean update(T referentiel)
			throws JCertifException {
		return super.update(referentiel.toBasicDBObject(), Constantes.LABEL_ATTRIBUTE_NAME);
	}
	
	public final boolean save(T referentiel)
			throws JCertifException {
		return super.save(referentiel.toBasicDBObject(), Constantes.LABEL_ATTRIBUTE_NAME);
	}
	
	public final T get(String label) {
		BasicDBObject dbObject = super.get(Constantes.LABEL_ATTRIBUTE_NAME, label);
		if(null == dbObject){
			return null;
		}
		T object = JCertifObjectDBUtils.instanciate(referentielClass);
		object.setLabel(label);
		return object;
	}
	
	public final boolean remove(T referentielToDelete) {		
		return super.remove(referentielToDelete.toBasicDBObject(), Constantes.LABEL_ATTRIBUTE_NAME);
	}
	
}
