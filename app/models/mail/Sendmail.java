package models.mail;

import models.exception.JCertifException;
import models.mail.emailmessages.EmailMessage;
import models.util.Tools;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Sendmail {

    private static Sendmail instance;

    public static Sendmail getInstance(){
        if(instance == null){
            instance = new Sendmail();
        }
        return instance;
    }

    public void send(EmailMessage emailMessage) throws JCertifException {

        if (emailMessage == null) {
            throw new JCertifException(this, "send(), Message to send is null");
        }

        if (!emailMessage.hasRecipient()) {
            throw new JCertifException(this, "send(), Message to send has no recipient");
        }

        if (Tools.isBlankOrNull(emailMessage.getFrom())) {
            throw new JCertifException(this, "send(), The sender (from) is empty or null");
        }

        InternetAddress fromAddress;
        InternetAddress[] toAddress = null;
        InternetAddress[] ccAddress = null;
        InternetAddress[] bccAddress = null;
        InternetAddress[] replyToAddress;

        try {
            fromAddress = new InternetAddress(emailMessage.getFrom());

            int i = 0;

            if (emailMessage.getNbToRecipient() != 0) {
                toAddress = new InternetAddress[emailMessage.getNbToRecipient()];
                for (String email : emailMessage.getTo()) {
                    toAddress[i++] = new InternetAddress(email);
                }
                i = 0;
            }

            if (emailMessage.getNbCcRecipient() != 0) {
                ccAddress = new InternetAddress[emailMessage.getNbCcRecipient()];
                for (String email : emailMessage.getCc()) {
                    ccAddress[i++] = new InternetAddress(email);
                }
                i = 0;
            }

            if (emailMessage.getNbBccRecipient() != 0) {
                bccAddress = new InternetAddress[emailMessage.getNbBccRecipient()];
                for (String email : emailMessage.getBcc()) {
                    bccAddress[i++] = new InternetAddress(email);
                }
                i = 0;
            }

            if (emailMessage.getNbReplyToRecipient() == 0) {
                emailMessage.addReplyToRecipient(emailMessage.getFrom());
            }
            replyToAddress = new InternetAddress[emailMessage.getNbReplyToRecipient()];
            for (String email : emailMessage.getReplyTo()) {
                replyToAddress[i++] = new InternetAddress(email);
            }

        } catch (AddressException ae) {
            throw new JCertifException(this, ae.getMessage());
        }

        try {
            Message simpleMessage = new MimeMessage(SendmailConfig.getInstance().getSession());


            emailMessage.constructMessageContent();

            simpleMessage.setFrom(fromAddress);
            simpleMessage.setRecipients(MimeMessage.RecipientType.TO, toAddress);
            simpleMessage.setRecipients(MimeMessage.RecipientType.CC, ccAddress);
            simpleMessage.setRecipients(MimeMessage.RecipientType.BCC, bccAddress);
            simpleMessage.setReplyTo(replyToAddress);
            simpleMessage.setSubject(emailMessage.getSubject());
            simpleMessage.setContent(emailMessage.getText().toString(), "text/html; charset=utf-8");

            Transport.send(simpleMessage);

        } catch (MessagingException e) {
            throw new JCertifException(this, e.getMessage());
        }

    }
}
