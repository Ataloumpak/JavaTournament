package javatournament.personnage.sorts.competences;

import javatournament.combat.PhaseJeu;
import javatournament.personnage.Personnage;

/**
 * Classe Degat qui hérite de Competence, permet de gérer les compétences apportant des dégâts
 * @author Baptiste
 */
public class Soin implements Competence
{
    public Soin() {
    }
    
    /**
     * Surchargement de la methode effet qui prend en paramètre
     * @param P - Personnage sur lequelle on fais l'effet
     * @param puissance - Puissance de l'effet
     */
    @Override
    public void effet(Personnage P, int puissance)
    {
        PhaseJeu.getLog().ajoutLogSoin(P.getNom(), puissance);
        if(P.getPDVCrt()+puissance > P.getPDVMax())
            P.setPDVCrt(P.getPDVMax());
        else
            P.setPDVCrt(P.getPDVCrt()+puissance);
    }
}
