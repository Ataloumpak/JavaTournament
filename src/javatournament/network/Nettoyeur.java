package javatournament.network;

import javatournament.combat.PhaseJeu;

/** 
 * Classe permettant de retirer les Thread non actifs du vecteur de connexion 
 */
class Nettoyeur extends Thread {

    /** R&eacute;f&eacute;rence a l'application serveur */
    protected Serveur serveur;

    protected Nettoyeur(Serveur serveur) {
        this.serveur = serveur;
        this.start();
    }

    /** Fonctionnement en tache de fond */
    public synchronized void run() {
        while (true) {
            // On endort le Nettoyeur pour 5 secondes, mais il peut aussi etre reveille par un notify sur cette instance de Nettoyeur
            try {
                this.wait(5000);
            } catch (InterruptedException e) {
            };

            // Des que l'on peut mettre un verrou sur le vecteur
            synchronized (serveur.connexions) {
                // On recherche dans le tableau...
                for (int i = serveur.connexions.size() - 1; i >= 0; i--) {
                    Connexion c = (Connexion) serveur.connexions.elementAt(i);
                    // ... s'il y a des connexions qui ne sont pas ou plus actives ...
                    if (!c.isAlive()) {
                        // ... pour les retirer
                        serveur.connexions.removeElementAt(i);
                        System.out.println("Nettoyeur : Fin de connexion : OK");
                        PhaseJeu.erreur=true;
                    }
                }
            }
        }
    }
}
