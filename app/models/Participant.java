package models;

import java.util.ArrayList;
import java.util.List;

import models.exception.JCertifDuplicateObjectException;
import models.exception.JCertifInvalidRequestException;
import models.notifiers.EmailNotification;
import models.util.Constantes;
import models.util.Tools;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
/**
 * <p>Objet mtier représentant un participant de JCertif Conference.</p>
 *
 */
public class Participant extends Member {
	
	private List<String> sessions = new ArrayList<String>();

	public Participant(BasicDBObject basicDBObject){
        super(basicDBObject);
        this.sessions.addAll(Tools.basicDBListToJavaList(basicDBObject.get("sessions")));
    }

	public final List<String> getSessions() {
        return sessions;
    }

    public final void setSessions(List<String> sessions) {
        this.sessions = sessions;
    }

    @Override
    public final BasicDBObject toBasicDBObject() {
        BasicDBObject basicDBObject = super.toBasicDBObject();
        basicDBObject.put("sessions", Tools.javaListToBasicDBList(getSessions()));
        return basicDBObject;
    }
	
	@Override
	public int create() {
		int id = super.create();
		boolean isOK = id == 1;
		
		if(isOK){
			/* send email */
			EmailNotification.sendWelcomeMail(this);
		}
		
		return id;
	}
	
	public void addToSession(Session session) {
		if(!Tools.isBlankOrNull(session.getId()) &&
				!sessions.contains(session.getId())){
			sessions.add(session.getId());
			
			boolean isOK = Integer.parseInt(getVersion()) < save();
			
			if(isOK){
				EmailNotification.sendenrollMail(this, session);
			}
		}else if (sessions.contains(session.getId())) {
			throw new JCertifDuplicateObjectException("Participant '"+ getEmail() +"' déjà inscrit à la session '"+ session.getTitle() +"'.");
		}
	}
    
    public void removeFromSession(Session session) {
		if(!Tools.isBlankOrNull(session.getId()) &&
				sessions.contains(session.getId())){
			sessions.remove(session.getId());
			
			boolean isOK = Integer.parseInt(getVersion()) < save();
			
			if(isOK){
				EmailNotification.sendUnenrollpwdMail(this, session);
			}
		}else if (!sessions.contains(session.getId())) {
			throw new JCertifInvalidRequestException("Participant '"+ getEmail() +"' non inscrit à la session '"+ session.getTitle() +"'.");
		}
	}
    
    public static Participant find(String email){
    	return getFinder().find(Participant.class, Constantes.EMAIL_ATTRIBUTE_NAME, email);
	}
    
    public static List<Participant> findAll(){
		return getFinder().findAll(Participant.class);
	}
}
