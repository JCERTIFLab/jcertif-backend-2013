package models.objects.checker;

import static models.objects.checker.CheckerHelper.checkEmail;
import static models.objects.checker.CheckerHelper.checkNull;
import static models.objects.checker.CheckerHelper.checkPassword;

import java.util.ArrayList;
import java.util.List;

import models.exception.JCertifInvalidRequestException;
import models.objects.JCertifObject;
import models.objects.Member;
import models.objects.access.JCertifObjectDB;
import models.util.Constantes;
import models.util.Tools;
import models.util.crypto.CryptoUtil;

import com.mongodb.BasicDBObject;

/**
 * <p>Classe réalisant la validation des beans de type {@link Member}.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class MemberChecker extends Checker {

	List<String> civilites = new ArrayList<String>();
	
	public MemberChecker(List<String> civilites){
		this.civilites = civilites;
	}
	
	@Override
    public final void updateCheck(BasicDBObject objectToCheck) {
    	checkNull(objectToCheck);
    	checkEmail(objectToCheck);
    	updateCheckTitle(objectToCheck);
    }

	@Override
    public final void deleteCheck(BasicDBObject objectToCheck) {
    	checkNull(objectToCheck);
    	checkEmail(objectToCheck);
    }

    @Override
    public final void addCheck(BasicDBObject objectToCheck) {
    	checkNull(objectToCheck);
    	checkEmail(objectToCheck);
    	addCheckTitle(objectToCheck);
    	
        Member member = new DummyMember(objectToCheck);

        checkPassword(member.getPassword(), null, false);
        
        //after check the password compliance according to policy we encrypt
        objectToCheck.put("password", CryptoUtil.getSaltedPassword(member.getPassword().getBytes()));

        if (Tools.isBlankOrNull(member.getLastname())) {
            throw new JCertifInvalidRequestException(this, "Lastname cannot be empty or null");
        }

        if (Tools.isBlankOrNull(member.getFirstname())) {
            throw new JCertifInvalidRequestException(this, "Firstname cannot be empty or null");
        }

        if (Tools.isBlankOrNull(member.getCity())) {
            throw new JCertifInvalidRequestException(this, "City cannot be empty or null");
        }

        if (Tools.isBlankOrNull(member.getCountry())) {
            throw new JCertifInvalidRequestException(this, "Country cannot be empty or null");
        }
    }
    
    private void addCheckTitle(BasicDBObject objectToCheck) {
    	String title = objectToCheck.getString(Constantes.TITLE_ATTRIBUTE_NAME);
    	if (Tools.isBlankOrNull(title)) {
            throw new JCertifInvalidRequestException(this, "Title cannot be empty or null");
        }
    	if (!civilites.contains(title)) {
            throw new JCertifInvalidRequestException(this, "Invalid title, it should be in " + civilites);
        }    	
	}
    
    private void updateCheckTitle(BasicDBObject objectToCheck) {
    	String title = objectToCheck.getString(Constantes.TITLE_ATTRIBUTE_NAME);
    	if (!Tools.isBlankOrNull(title) &&
    			!civilites.contains(title)) {
    		throw new JCertifInvalidRequestException(this, "Invalid title, it should be in " + civilites);
        }  	
	}
    
    class DummyMember extends Member {

		public DummyMember(BasicDBObject basicDBObject) {
			super(basicDBObject);
		}

		@Override
		public String getKeyName() {
			return null;
		}

		@Override
		protected <T extends JCertifObject> JCertifObjectDB<T> getDBObject() {
			return null;
		}
    	
    }
}
