package models.mail.emailmessages;

public class InscrireParticipantSessionEmailMessage extends EmailMessage{

    private String fullname;
    private String sessionStartDate;
    private String sessionEndDate;
    private String sessionTitle;
    private String sessionSummary;

    public InscrireParticipantSessionEmailMessage(){
        setSubject("[JCertif] Inscription à une session");
    }

    @Override
    public void constructMessageContent() {
        getText().delete(0,getText().length()); //If we want to use the same object to send several mails

        addJCertifHeaderToContent();

        getText().append("<br><b>Exemple de message</b><br>");
        getText().append("<br>");
        getText().append("Merci " + fullname +" pour votre inscription");
        getText().append("<br>");
        getText().append("<br>");
        getText().append("Votre inscription à la session a bien été enregistrée");
        getText().append("<br>");
        getText().append("<br>Session : " + sessionTitle);
        getText().append("<br>Résumé : " + sessionSummary);
        getText().append("<br>Date de début : " + sessionStartDate);
        getText().append("<br>Date de fin : " + sessionEndDate);
        getText().append("<br>");
        getText().append("<br>");
        getText().append("Il ne vous reste plus que deux petites étapes à suivre pour compélter la procédure");
        getText().append("<br>");
        getText().append("Etapes à suivre");
        getText().append("<br>");
        getText().append("---------------");
        getText().append("<br>");
        getText().append("1 - Contacter rapidement le collecteur de votre secteur. Consulter notre page de contact pour trouver le collecteur le plus proche de chez vous");
        getText().append("<br>");
        getText().append("2 - Payez les frais de participation et recevez votre carte d'invitation officielle et un numéro de confirmation");
        getText().append("<br>");
        getText().append("<br>");

        addJCertifFooterToContent();
        getText().append("<br>");
        addJCertifSignatureToContent();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSessionStartDate() {
        return sessionStartDate;
    }

    public void setSessionStartDate(String sessionStartDate) {
        this.sessionStartDate = sessionStartDate;
    }

    public String getSessionEndDate() {
        return sessionEndDate;
    }

    public void setSessionEndDate(String sessionEndDate) {
        this.sessionEndDate = sessionEndDate;
    }

    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
    }

    public String getSessionSummary() {
        return sessionSummary;
    }

    public void setSessionSummary(String sessionSummary) {
        this.sessionSummary = sessionSummary;
    }
}
