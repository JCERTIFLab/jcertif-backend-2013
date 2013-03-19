package notifiers;

import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;
import models.objects.Participant;
import models.objects.Session;
import models.objects.Speaker;
import play.Logger;
import views.html.*;

/**
 * Cette classe se chargera de tous les envoi de mails 'ala play framework'.
 * Chaque email utilise son propre template situé dans view.html.
 *
 * @author bashizip
 */
public class EmailNotification {

    /**
     * fromEmail représente l'expéditeur du courrier. De preference le lire dans un fichier de propriétés
     *
     */
    private static String fromEmail = "jcertif2013.debug@gmail.com";


    /**
     * Send a welcome message
     *
     * @param user
     */
    public static void sendWelcomeMail(Participant user) {
        Logger.info("Enter sendWelcomeMail()");
        Logger.debug("Enter sendWelcomeMail(user=" + user + ")");

        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject("Bienvenue !");
        mail.addRecipient(user.getEmail());
        mail.addFrom(fromEmail);

        String body = welcome.render(user).body();

        mail.sendHtml(body);

        Logger.info("Exit sendWelcomeMail()");
    }

    public static void sendChangePwdMail(Participant user) {
        Logger.info("Enter sendChangePwdMail()");
        Logger.debug("Enter sendChangePwdMail(user=" + user + ")");

        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject("[JCertif] Changement de votre mot de passe");
        mail.addRecipient(user.getEmail());
        mail.addFrom(fromEmail);

        String body = pwdchange.render(user).body();

        mail.sendHtml(body);

        Logger.info("Exit sendChangePwdMail()");
    }

    public static void sendReinitpwdMail(Participant user, String newPassword) {
        Logger.info("Enter sendReinitpwdMail()");
        Logger.debug("Enter sendReinitpwdMail(user=" + user + ")");

        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject("[JCertif] Réinitialisation de votre mot de passe");
        mail.addRecipient(user.getEmail());
        mail.addFrom(fromEmail);

        String body = pwdinit.render(user, newPassword).body();

        mail.sendHtml(body);

        Logger.info("Exit sendReinitpwdMail()");
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
        // TODO Auto-generated method stub

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

    public static void sendChangePwdMail(Speaker speaker) {
        Logger.info("Enter sendChangePwdMail()");
        Logger.debug("Enter sendChangePwdMail(user=" + speaker + ")");

        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject("[JCertif] Changement de votre mot de passe");
        mail.addRecipient(speaker.getEmail());
        mail.addFrom(fromEmail);

        String body = pwdchangeSpeaker.render(speaker).body();

        mail.sendHtml(body);

        Logger.info("Exit sendChangePwdMail()");
    }

    public static void sendReinitpwdMail(Speaker speaker, String newPassword) {
        Logger.info("Enter sendReinitpwdMail()");
        Logger.debug("Enter sendReinitpwdMail(user=" + speaker + ")");

        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject("[JCertif] Réinitialisation de votre mot de passe");
        mail.addRecipient(speaker.getEmail());
        mail.addFrom(fromEmail);

        String body = pwdinitSpeaker.render(speaker, newPassword).body();

        mail.sendHtml(body);

        Logger.info("Exit sendReinitpwdMail()");
    }

}

