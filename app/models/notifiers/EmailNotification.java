package models.notifiers;

import models.Member;
import models.Participant;
import models.Session;
import models.Speaker;
import play.Logger;
import play.Play;
import views.html.confirmProposition;
import views.html.enroll;
import views.html.pwdchange;
import views.html.pwdchangeSpeaker;
import views.html.pwdinit;
import views.html.pwdinitSpeaker;
import views.html.unenroll;
import views.html.welcome;

import com.typesafe.plugin.MailerAPI;

/**
 * Cette classe se chargera de tous les envoi de mails 'ala play framework'.
 * Chaque email utilise son propre template situé dans view.html.
 *
 * @author bashizip
 */
public final class EmailNotification {

	private EmailNotification(){		
	}
	
    /**
     * fromEmail représente l'expéditeur du courrier. De preference le lire dans un fichier de propriétés
     *
     */
    private static String fromEmail = Play.application().configuration().getString("smtp.from");


    /**
     * Send a welcome message
     *
     * @param user
     */
    public static void sendWelcomeMail(Participant user) {
        Logger.info("Enter sendWelcomeMail()");
        Logger.debug("Enter sendWelcomeMail(user=" + user + ")");

        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject("[JCertif] Bienvenue !");
        mail.addRecipient(user.getEmail());
        mail.addFrom(fromEmail);

        String body = welcome.render(user).body();

        mail.sendHtml(body);

        Logger.info("Exit sendWelcomeMail()");
    }

    public static void sendUnenrollpwdMail(Participant user, Session session) {
        Logger.info("Enter sendUnenrollpwdMail()");
        Logger.debug("Enter sendUnenrollpwdMail(user=" + user + ", session=" + session + ")");

        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject("[JCertif] Désinscription à une session");
        mail.addRecipient(user.getEmail());
        mail.addFrom(fromEmail);

        String body = unenroll.render(user, session).body();

        mail.sendHtml(body);

        Logger.info("Exit sendUnenrollpwdMail()");
    }

    public static void sendenrollMail(Participant user,
                                      Session session) {
        Logger.info("Enter sendenrollMail()");
        Logger.debug("Enter sendenrollMail(user=" + user + ", session=" + session + ")");

        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject("[JCertif] Inscription à une session");
        mail.addRecipient(user.getEmail());
        mail.addFrom(fromEmail);

        String body = enroll.render(user, session).body();

        mail.sendHtml(body);

        Logger.info("Exit sendenrollMail()");
    }
    
    public static void sendChangePwdMail(Member member) {
        Logger.info("Enter sendChangePwdMail()");
        Logger.debug("Enter sendChangePwdMail(user=" + member + ")");

        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject("[JCertif] Changement de votre mot de passe");
        mail.addRecipient(member.getEmail());
        mail.addFrom(fromEmail);

        String body = getChangePwdMailBody(member);
        
        mail.sendHtml(body);

        Logger.info("Exit sendChangePwdMail()");
    }

    private static String getChangePwdMailBody(Member member) {
    	String body = "";
		
		if(member instanceof Participant){
			body = pwdchange.render((Participant)member).body();
		}else if(member instanceof Speaker){
			body = pwdchangeSpeaker.render((Speaker)member).body();
		}
		
		return body;
	}

	public static void sendReinitpwdMail(Member member, String newPassword) {
        Logger.info("Enter sendReinitpwdMail()");
        Logger.debug("Enter sendReinitpwdMail(user=" + member + ")");

        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject("[JCertif] Réinitialisation de votre mot de passe");
        mail.addRecipient(member.getEmail());
        mail.addFrom(fromEmail);

        String body = getReinitpwdMailBody(member, newPassword);

        mail.sendHtml(body);

        Logger.info("Exit sendReinitpwdMail()");
    }

	private static String getReinitpwdMailBody(Member member, String newPassword) {
		String body = "";
		
		if(member instanceof Participant){
			body = pwdinit.render((Participant)member, newPassword).body();
		}else if(member instanceof Speaker){
			body = pwdinitSpeaker.render((Speaker)member, newPassword).body();
		}
		
		return body;
	}

	public static void sendConfirmPropositionMail(Session session) {
		Logger.info("Enter sendConfirmPropositionMail()");
        Logger.debug("Enter sendConfirmPropositionMail(session=" + session + ")");

        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject("[JCertif] Proposition de présentation enregistrée");
        String[] recipients = new String[session.getSpeakers().size()];
        session.getSpeakers().toArray(recipients);
        mail.addRecipient(recipients);
        mail.addFrom(fromEmail);

        String body = confirmProposition.render().body();

        mail.sendHtml(body);

        Logger.info("Exit sendConfirmPropositionMail()");
	}

}

