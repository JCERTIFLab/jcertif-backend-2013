package notifiers;

import models.objects.Participant;
import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;

import views.html.welcome;

/**
 * Cette classe se chargera de tous les envoi de mails ' ala play framework'.
 * Chaque email utilise son propre template situé dans view.html.
 * @author bashizip
 *
 */
public class EmailNotification {

	

	 //-----------------------------------	send a welcome message -----------------------------------
		public static void sendWelcomeMail(Participant user) {
			
			MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
			mail.setSubject("Bienvenu !");
			mail.addRecipient(user.getEmail());
			mail.addFrom("jcertif2013.debug@gmail.com");
			
			String body = welcome.render(user).body();
		
		    mail.sendHtml(body);
		}
		
		public static void sendChangePaswdMail(Participant user){
			
		}
	
}

