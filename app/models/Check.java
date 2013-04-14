package models;

import com.mongodb.BasicDBObject;

public interface Check {

	/**
     * <p>Méthode validant un objet du model avant une opération de mise à jour en base</p>
     * 
     * @param objectToCheck Objet à valider
     */
    void updateCheck(BasicDBObject objectToCheck);

    /**
     * <p>Méthode validant un objet du model avant une opération de suppression de la base</p>
     * 
     * @param objectToCheck Objet à valider
     */
    void deleteCheck(BasicDBObject objectToCheck);

    /**
     * <p>Méthode validant un objet du model avant une opération d'ajout en base</p>
     * 
     * @param objectToCheck Objet à valider
     */
    void addCheck(BasicDBObject objectToCheck);

}
