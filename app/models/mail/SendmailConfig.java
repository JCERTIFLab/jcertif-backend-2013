package models.mail;

import models.util.Constantes;
import models.util.Tools;
import models.util.properties.EmailPropUtils;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class SendmailConfig {

    private Session session;

    private static SendmailConfig instance;

    public SendmailConfig() {
        /**Récupérer la session du conteneur (Tomcat, Glassfish, Weblogic ...)
         * http://tomcat.apache.org/tomcat-6.0-doc/jndi-resources-howto.html#JavaMail_Sessions
         *
         * si on n'y arrive pas, en créer une avec les paramètres de l'application (jcertifbackend_email.properties)
         * http://javamail.kenai.com/nonav/javadocs/com/sun/mail/smtp/package-summary.html
         *
         */
        try {
            Context initCtx = null;
            session = null;
            initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            session = (Session) envCtx.lookup(Constantes.JCERTIFBACKEND_EMAIL_WEBCONTAINER_RESOURCE_NAME);
        } catch (NamingException e) {
            play.Logger.info(e.getMessage());
        }

        if (null == session) {
            play.Logger.info("Unable to get mail Session from Web Container");
            play.Logger.info("Trying to create a session with application parameters");

            Properties properties = new Properties();
            properties.put("mail.debug", EmailPropUtils.getInstance().getProperty("mail.debug", "false"));
            properties.put("mail.smtp.host", EmailPropUtils.getInstance().getProperty("mail.smtp.host", "localhost"));
            properties.put("mail.smtp.port", EmailPropUtils.getInstance().getProperty("mail.smtp.port", "25"));

            SMTPAuthenticator smtpAuthenticator = new SMTPAuthenticator();

            smtpAuthenticator.setUsername(EmailPropUtils.getInstance().getProperty("mail.smtp.username", ""));
            smtpAuthenticator.setPassword(EmailPropUtils.getInstance().getProperty("mail.smtp.password", ""));

            if (!Tools.isBlankOrNull(smtpAuthenticator.getUsername())) {
                session = Session.getDefaultInstance(properties, smtpAuthenticator);
            } else {
                session = Session.getDefaultInstance(properties, null);
            }
        }
    }

    public static SendmailConfig getInstance() {
        if (instance == null) {
            instance = new SendmailConfig();
        }
        return instance;
    }

    public Session getSession() {
        return session;
    }

    protected void setSession(Session session) {
        this.session = session;
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        private String username;
        private String password;

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(getUsername(), getPassword());
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
