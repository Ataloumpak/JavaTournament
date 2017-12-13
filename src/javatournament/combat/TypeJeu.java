package javatournament.combat;

/**
 * Type du jeu en création,
 * LOCAL pour qu'il se déroule uniquement sur l'ordinateur courant,
 * RESEAU pour qu'il se déroule entre plusieurs ordinateurs.
 * @author pyarg
 */
public enum TypeJeu {
    /**
     * Le jeu se déroule uniquement sur le pc.
     */
    LOCAL,
    /**
     * Le jeu se déroule en réseau.
     */
    RESEAU;    
}
