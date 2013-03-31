package models.objects.checker;

import static models.objects.checker.CheckerHelper.checkEmail;
import static models.objects.checker.CheckerHelper.checkNull;
import static models.objects.checker.CheckerHelper.checkPassword;

import com.mongodb.BasicDBObject;

public class LoginChecker extends Checker {

    public final void check(BasicDBObject objectToCheck) {
    	checkNull(objectToCheck);
    	checkEmail(objectToCheck);
    	checkPassword(objectToCheck);
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
    public void addCheck(BasicDBObject objectToCheck) {
    	check(objectToCheck);
    }
}
