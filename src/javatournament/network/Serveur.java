package javatournament.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import javatournament.combat.ListeEquipes;
import javatournament.combat.StaticData;

/** Classe principale du serveur<br />
 * Etablit les connections et les stoque dans un vecteur
 */
public class Serveur extends Thread {

    /** Port d'&eacute;coute du serveur */
    protected int PORT;
    /** Le socket en attente de demande de connexion entrante */
    protected ServerSocket ecoute;
    /** Le vecteur des connexions */
    protected Vector<Connexion> connexions;
    /** Pour retirer automatiquement du vecteur les connexions inactives */
    protected Nettoyeur nettoyeur;
    /**
     * Nombre de client maximum du serveur.
     */
    private int nbClientMax;
    /**
     * Nombre de client connecter au serveur.
     */
    private int nbClient;
    

    /** Constructeur du serveur 
     * @param p Port d'écoute.
     * @param client Nombre de joueur de la partie.
     */
    public Serveur(int p, int client) {
        this.PORT = p;
        try {
            this.ecoute = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        //System.out.println("Serveur : en ecoute sur le port :" + PORT);
        connexions = new Vector<Connexion>();
        nettoyeur = new Nettoyeur(this);
        this.nbClientMax=client;
        this.nbClient=0;
        this.start();
    }

    /** 
     * Corps principal du serveur<br />
     * Boucle infinie d'attente de demande de connexion
     */
    public void run() {
        while (true) {
            Socket client=null;
            Connexion c=null;
            try {
                client = ecoute.accept();
                System.out.println("Serveur : Demande de connexion...");
                c = new Connexion(client, nettoyeur, this);
                this.nbClient=connexions.size();
            } catch (IOException ex) {
                //System.err.println("Serveur : impossible de crée le socket client. "+ex.getMessage());
            }
            // Si la connexion est un Thread en vie (il y a eu 'start')
            if(  c!=null && c.isAlive() && this.nbClient<this.nbClientMax ) {
                connexions.addElement(c);    // Pas besoin de synchronized, car la classe Vector est synchronized
                //System.out.println("Serveur : Connexion de : "+client.toString()+", client n°"+this.nbClient);
                //identifiant du joueur
                String id = StaticData.transformeInt(this.nbClient);
                //Identifiant du client locale
                String myId = String.valueOf(StaticData.getIdentifiant());
                if(StaticData.getIdentifiant()!=-1)
                    myId = StaticData.transformeInt( StaticData.getIdentifiant() );
                //envoie de la chaine de création de ListeEquipes aux clients
                c.out.println("00"+myId+id+this.nbClientMax);
                if( StaticData.getIdentifiant()!=-1 ) { //si la listeequipe n'est pas vide.
                    //Envoie du joueur
                    try {
                        ListeEquipes.getJoueur(0).envoieJoueur(client.getOutputStream());
                    } catch (IOException ex) {
                        System.err.println("Serveur : impossible d'envoyer le joueur.");
                    }
                    //Envoie de la map
                    if(StaticData.map!=null){
                        try {
                            StaticData.map.envoieMap(client.getOutputStream());
                        } catch (IOException ex) {
                            System.err.println("Serveur : impossible d'envoyer la map.");
                        }
                    }
                }
            }
        }
    }
    /**
     * Méthode pour arreter les thread du serveur.
     */
    public void arret(){
        //on suspend le thread du serveur
        this.interrupt();
        //arret du thread nettoyeur.
        nettoyeur.interrupt();
        //On arrete le thread d'ecoute.
        try {
            ecoute.close();
        } catch (IOException ex) {
            System.err.println("Serveur : impossible d'arreter le thread d'ecoute. "+ex.getMessage());
        }
        //On arrete tout les thread de connexion.
        for(Connexion tmp : connexions) {
            tmp.arret();
        }
    }
}

