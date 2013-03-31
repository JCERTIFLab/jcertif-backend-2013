package models.objects.checker;

import static models.objects.checker.CheckerHelper.checkLabel;
import static models.objects.checker.CheckerHelper.checkNull;
import models.objects.Referentiel;

import com.mongodb.BasicDBObject;

/**
 * Objet de type {@link Checker} permettant de valider les 
 * instances de bean {@link Referentiel}.
 * 
 * @author Martial SOMDA
 *
 */
public class ReferentielChecker extends Checker {

	public final void check(BasicDBObject objectToCheck) {
		checkNull(objectToCheck);
		checkLabel(objectToCheck);
	}

	@Override
	public final void updateCheck(BasicDBObject objectToCheck) {
		check(objectToCheck);
	}
	
	@Override
	public final void deleteCheck(BasicDBObject objectToCheck) {
		check(objectToCheck);
	}

	@Override
	public final void addCheck(BasicDBObject objectToCheck) {
		check(objectToCheck);
	}
}
