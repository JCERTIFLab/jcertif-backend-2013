package models.objects.access;

import java.lang.reflect.ParameterizedType;

import models.exception.JCertifException;
import models.objects.Member;
import models.objects.checker.MemberChecker;
import models.util.Constantes;

import com.mongodb.BasicDBObject;

/**
 * <p>Objet d'accès aux données de type {@link Member}.<br/>
 * Cette Objet implémente la classe {@link IDao} et fournit des méthodes de type <code>CRUD</code>.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class MemberDB<T extends Member> extends JCertifObjectDB<T> {

	private Class<T> implementationClass;
	
	@SuppressWarnings("unchecked")
	public MemberDB(String collectionName) {
		super(collectionName, new MemberChecker());
		this.implementationClass = 
			(Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}		
	
	public final boolean add(T member) {
		return super.add(member.toBasicDBObject(), Constantes.EMAIL_ATTRIBUTE_NAME);
	}
	
	public final boolean save(T member)
			throws JCertifException {
		return super.update(member.toBasicDBObject(), Constantes.EMAIL_ATTRIBUTE_NAME);
	}
	
	public final T get(String email) {
		BasicDBObject dbObject = super.get(Constantes.EMAIL_ATTRIBUTE_NAME, email);
		if(null == dbObject){
			return null;
		}
		T object = JCertifObjectDBUtils.instanciate(implementationClass, dbObject);
		return object;
	}
	
	public final boolean remove(T member) {		
		return super.remove(member.toBasicDBObject(), Constantes.EMAIL_ATTRIBUTE_NAME);
	}
}
