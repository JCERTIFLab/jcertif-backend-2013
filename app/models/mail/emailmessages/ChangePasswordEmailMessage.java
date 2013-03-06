package models.mail.emailmessages;

public class ChangePasswordEmailMessage extends EmailMessage{

    private String toFullname;

    public ChangePasswordEmailMessage(){
        setSubject("[JCertif] Changement de votre mot de passe");
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
        getText().append("Votre mot de passe a été changé");
        getText().append("<br>");
        getText().append("<br>");
        getText().append("Vous pouvez vous connecter et réaliser votre agenda personnalisé sur http://www.jcertif.com");
        getText().append("<br>");
        getText().append("<br>");

        addJCertifSignatureFrToContent();

        getText().append("<br>");

        addJCertifFooterToContent();
    }

    public String getToFullname() {
        return toFullname;
    }

    public void setToFullname(String toFullname) {
        this.toFullname = toFullname;
    }
}
