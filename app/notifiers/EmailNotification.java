package notifiers;

import models.objects.Participant;
import models.objects.Session;

import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;

import views.html.welcome;
import views.html.pwdchange;
import views.html.pwdinit;
import views.html.unenroll;
import views.html.enroll;

/**
 * Cette classe se chargera de tous les envoi de mails 'ala play framework'.
 * Chaque email utilise son propre template situé dans view.html.
 * @author bashizip
 *
 */
public class EmailNotification {

	private static final String FROM_EMAIL="jcertif2013.debug@gmail.com"; //de preference le lire dans un fichier de propriétés
	

	/**
	 * Send a welcome message
	 * @param user
	 */
		public static void sendWelcomeMail(Participant user) {
			
			MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
			mail.setSubject("Bienvenu !");
			mail.addRecipient(user.getEmail());
			mail.addFrom(FROM_EMAIL);
			
			String body = welcome.render(user).body();
		
		    mail.sendHtml(body);
		}
		
		public static void sendChangePwdMail(Participant user){

			MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
			mail.setSubject("[JCertif] Changement de votre mot de passe");
			mail.addRecipient(user.getEmail());
			mail.addFrom(FROM_EMAIL);
			
			String body = pwdchange.render(user).body();
		
		    mail.sendHtml(body);
		}

		public static void sendReinitpwdMail(Participant user) {

			MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
			mail.setSubject("[JCertif] Changement de votre mot de passe");
			mail.addRecipient(user.getEmail());
			mail.addFrom(FROM_EMAIL);
			
			String body = pwdinit.render(user).body();
			
		    mail.sendHtml(body);
			
		    
		}

		
		
		public static void sendUnenrollpwdMail(Participant user,Session session) {
			MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
			mail.setSubject("[JCertif] Désinscription à une session");
			mail.addRecipient(user.getEmail());
			mail.addFrom(FROM_EMAIL);
			
			String body = unenroll.render(user,session).body();
		
		    mail.sendHtml(body);
			// TODO Auto-generated method stub
			
		}

		public static void sendenrollMail(Participant user,
				Session session) {
			MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
			mail.setSubject("[JCertif] Inscription à une session");
			mail.addRecipient(user.getEmail());
			mail.addFrom(FROM_EMAIL);
			
			String body = enroll.render(user,session).body();
		
		    mail.sendHtml(body);
			
		}
	
}

