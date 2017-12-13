/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javatournament.personnage.sorts.competences;

import javatournament.personnage.sorts.competences.Competence;
import javatournament.personnage.Personnage;

/**
 *
 * @author tony
 */
public class Consommable {
    /*
     * Lien vers la compétence utilisé par l'objet
     */
    private Competence C;

   /**
    * Nom de l'objet
    */
    String Nom;

    /**
     * Quantité restante pour l'utilisation de l'objet
     */
    int stock;

    /**
     * Puissance du consommable
     */
    int puissance;
            
    /**
     * Constructeur de la classe Consommable
     * @param nom Nom du consommable
     * @param C Compétence que l'objet declenche
     * @param stock Le nombre d'utilisation qu'il reste sur l'objet
     */
    public Consommable(String nom, Competence C,int stock, int puissance )
    {
        Nom=nom;
        this.C=C;
        this.stock=stock;
        this.puissance=puissance;
    }
    
    /**
     * Utilise le consommable en question, et renvoi true s'il reste des stocks
     * @param Personnage P personnage utilisant le consommable
     * @return boolean designant s'il reste des utilisations ou non
     */
    public boolean utiliser(Personnage P)
    {
        C.effet(P,puissance);
        stock--;
        return aCoursDeStock();
    }
    
    /**
     * utilitaire indiquant s'il reste des utilisations sur le consommable
     * @return boolean res true si il ne pas reste de consommables, sinon false
     */
    public boolean aCoursDeStock()
    {
        boolean res=false;
        if (stock==0)
            res=true;
        return res;
    }

    

    
    
    
}
