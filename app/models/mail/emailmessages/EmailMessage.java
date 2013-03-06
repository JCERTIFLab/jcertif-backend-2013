package models.mail.emailmessages;

import models.exception.JCertifException;
import models.util.Tools;
import models.util.properties.EmailPropUtils;

import java.util.LinkedList;
import java.util.List;

public abstract class EmailMessage {
    private List<String> to;
    private String from;
    private String subject;
    private List<String> cc;
    private List<String> bcc;
    private List<String> replyTo;
    private StringBuilder text;

    public EmailMessage(){
        setTo(new LinkedList<String>());
        setCc(new LinkedList<String>());
        setBcc(new LinkedList<String>());
        setReplyTo(new LinkedList<String>());
        setText(new StringBuilder());

        setFrom(getJCertifFrom());
    }

    public void addToRecipient(String recipientEmail) throws JCertifException {
        if(Tools.isValidEmail(recipientEmail)){
            to.add(recipientEmail);
        }else{
            throw new JCertifException(this, "addToRecipient(), " + recipientEmail + " is not a valid email");
        }
    }

    public void addCcRecipient(String recipientEmail) throws JCertifException {
        if(Tools.isValidEmail(recipientEmail)){
           cc.add(recipientEmail);
        }else{
            throw new JCertifException(this, "addCcRecipient(), " + recipientEmail + " is not a valid email");
        }
    }

    public void addBccRecipient(String recipientEmail) throws JCertifException {
        if(Tools.isValidEmail(recipientEmail)){
            bcc.add(recipientEmail);
        }else{
            throw new JCertifException(this, "addBccRecipient(), " + recipientEmail + " is not a valid email");
        }
    }

    public void addReplyToRecipient(String recipientEmail) throws JCertifException {
        if(Tools.isValidEmail(recipientEmail)){
            replyTo.add(recipientEmail);
        }else{
            throw new JCertifException(this, "addReplyToRecipient(), " + recipientEmail + " is not a valid email");
        }
    }

    public int getNbToRecipient(){
        return to.size();
    }

    public int getNbCcRecipient(){
        return cc.size();
    }

    public int getNbBccRecipient(){
        return bcc.size();
    }

    public int getNbReplyToRecipient(){
        return replyTo.size();
    }

    protected void clearRecipientToList(){
        to.clear();
    }

    protected void clearRecipientCcList(){
        cc.clear();
    }

    protected void clearRecipientBccList(){
        bcc.clear();
    }

    public boolean hasRecipient(){
        return !to.isEmpty() || !cc.isEmpty() || !bcc.isEmpty();
    }

    public List<String> getTo() {
        LinkedList<String> copy = new LinkedList<String>();
        copy.addAll(to);
        return copy;
    }

    private void setTo(List<String> to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    protected void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    protected void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getCc() {
        LinkedList<String> copy = new LinkedList<String>();
        copy.addAll(cc);
        return copy;
    }

    private void setCc(List<String> cc) {
        this.cc = cc;
    }

    public List<String> getBcc() {
        LinkedList<String> copy = new LinkedList<String>();
        copy.addAll(bcc);
        return copy;
    }

    private void setReplyTo(List<String> replyTo) {
        this.replyTo = replyTo;
    }

    public List<String> getReplyTo() {
        LinkedList<String> copy = new LinkedList<String>();
        copy.addAll(replyTo);
        return copy;
    }

    private void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public StringBuilder getText() {
        return text;
    }

    protected void setText(StringBuilder text) {
        this.text = text;
    }

    public String getJCertifFrom(){
        return EmailPropUtils.getInstance().getProperty("mail.from", "info@jcertif.com");
    }

    /**
     * This method is call before sending message. It constructs the body of the message
     */
    public abstract void constructMessageContent();

    public void addJCertifHeaderToContent(){
      text.append(EmailPropUtils.getInstance().getProperty("mail.content.header"));
    }

    public void addJCertifFooterToContent(){
        text.append(EmailPropUtils.getInstance().getProperty("mail.content.footer"));
    }

    public void addJCertifSignatureFrToContent(){
      text.append(EmailPropUtils.getInstance().getProperty("mail.content.footer.signature.fr"));
    }

    public void addJCertifSignatureEnToContent(){
        text.append(EmailPropUtils.getInstance().getProperty("mail.content.footer.signature.en"));
    }
}
