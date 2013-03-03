package models.mail.emailmessages;

public class ReinitPasswordEmailMessage extends EmailMessage{

    private String toFullname;
    private String newPassword;

    public ReinitPasswordEmailMessage(){
        setSubject("[JCertif] Votre nouveau mot de passe");
    }

    @Override
    public void constructMessageContent() {
        getText().delete(0,getText().length()); //If we want to use the same object to send several mails

        addJCertifHeaderToContent();

        getText().append("<br>");
        getText().append("<br>");
        getText().append("Bonjour " + toFullname + ",");
        getText().append("<br>");
        getText().append("<br>");
        getText().append("Voici votre nouveau mot de passe : <b>" + newPassword + "</b>");
        getText().append("<br>");
        getText().append("<br>");
        getText().append("Vous pouvez vous connecter et réaliser votre agenda personnalisé sur http://www.jcertif.com");
        getText().append("<br>");
        getText().append("<br>");

        addJCertifSignatureToContent();

        getText().append("<br>");

        addJCertifFooterToContent();
    }

    public String getToFullname() {
        return toFullname;
    }

    public void setToFullname(String toFullname) {
        this.toFullname = toFullname;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
