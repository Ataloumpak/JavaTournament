package javatournament.personnage.sorts.competences;

import javatournament.combat.PhaseJeu;
import javatournament.personnage.Personnage;

/**
 * Classe Degat qui hérite de Competence, permet de gérer les compétences apportant des dégâts
 * @author Baptiste
 */
public class Degat implements Competence
{

    public Degat() {
    }
    
    /**
     * Surchargement de la methode effet qui prend en paramètre
     * @param P - Personnage sur lequelle on fais l'effet
     * @param puissance - Puissance de l'effet
     */
    @Override
    public void effet(Personnage P, int puissance)
    {
        PhaseJeu.getLog().ajoutLogDegat(P.getNom(), puissance);
        P.setPDVCrt(P.getPDVCrt()-puissance);
    }
}
