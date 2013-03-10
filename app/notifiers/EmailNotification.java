package notifiers;

import models.objects.Participant;
import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;

import views.html.welcome;
import views.html.pwdchange;
import views.html.pwdinit;

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
	
}

