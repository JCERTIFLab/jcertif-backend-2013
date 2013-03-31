package models.objects.checker;

import com.mongodb.BasicDBObject;

public abstract class Checker {

	/**
     * <p>Méthode validant un objet du model avant une opération de mise à jour en base</p>
     * 
     * @param objectToCheck Objet à valider
     */
    public abstract void updateCheck(BasicDBObject objectToCheck);

    /**
     * <p>Méthode validant un objet du model avant une opération de suppression de la base</p>
     * 
     * @param objectToCheck Objet à valider
     */
    public abstract void deleteCheck(BasicDBObject objectToCheck);

    /**
     * <p>Méthode validant un objet du model avant une opération d'ajout en base</p>
     * 
     * @param objectToCheck Objet à valider
     */
    public abstract void addCheck(BasicDBObject objectToCheck);

}
