package models.objects.access;

import java.util.List;

import com.mongodb.BasicDBObject;

import models.exception.JCertifException;
import models.objects.Referentiel;
import models.objects.checker.Checker;

/**
 * <p>Objet d'accès aux données de type {@link Referentiel}.<br/>
 * Cette Objet implémente la classe {@link IDao} et fournit des méthodes de type <code>CRUD</code>.</p>
 * 
 * @author Martial SOMDA
 *
 */
public abstract class ReferentielDB<T extends Referentiel> extends JCertifObjectDB<T>{

	public ReferentielDB(String collectionName, Checker checker) {
		super(collectionName, checker);
	}
	
	
	public boolean add(Referentiel referentiel) throws JCertifException {
		return super.add(referentiel.toBasicDBObject());
	}
	
	public List<BasicDBObject> list() {
		BasicDBObject objectToSearch = new BasicDBObject();
		BasicDBObject columnToRetrieve = new BasicDBObject("_id", 0);
		return super.list(objectToSearch, columnToRetrieve);
	}
	
	public boolean remove(Referentiel objectToDelete)
			throws JCertifException {		
		return super.remove(objectToDelete.toBasicDBObject(), "label");
	}
}
