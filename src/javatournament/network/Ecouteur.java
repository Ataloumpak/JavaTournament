package javatournament.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import javatournament.combat.*;
import javatournament.map.Map;
import javatournament.personnage.Personnage;
import org.newdawn.slick.SlickException;

/** 
 * Thread gérant les réponses du serveur.<br/>
 * Traite les string sous la forme de :<br/>
 * 00+identifiant du joueur+nombre de joueur de la partie.<br/>
 * 01+identifiant du joueur+modifCaseMap.<br/>
 * 02+identifiant du joueur+nom Joueur.<br/>
 * 03+identifiant du joueur+type pers<br/>
 * 04+identifiant du joueur+identifiant perso+abscisse+ordonnee<br/>
 */
class Ecouteur extends Thread {

    private BufferedReader entree;   // Flux d'entree bufferise 
    private InputStream input;

    /** Constructeur 
     * @param s Socket vers le serveur
     */
    public Ecouteur(Socket s) throws IOException {
        entree = new BufferedReader(new InputStreamReader(s.getInputStream()));
        input=s.getInputStream();
        this.start();
    }

    public void run() {
        String recu;
        boolean enLigne = true;
            while( enLigne) {
                try {
                    //System.out.println("Ecouteur : reception de type : "+entree.readLine().getClass().toString() );
                    recu = entree.readLine();
                    if (enLigne = (recu!=null)) {
                        //System.out.println("Ecouteur : reception de > "+recu);
                        traitement(recu);
                    }
                } catch (IOException ex) {
                    PhaseJeu.erreur=true;
                }
            }
    }
    /**
     * Méthode pour traiter toutes les données recu par le réseau.<br/>
     * Voir description de la classe pour connaître les strings traiter.
     * @param obj - objet à traiter.
     */
    private void traitement(String recu){
        //si le message recu commence par 0 ou 1
        //sert à verifier qu'il n'a pas de caractère inconnu
        if( recu.startsWith("0") || recu.startsWith("1") ) {
            //si le message recu ne viens pas de moi ou si il ne sont pas identifier
            if( !recu.equals("") && Integer.valueOf(recu.substring(2,4))!=StaticData.getIdentifiant() || Integer.valueOf(recu.substring(2,4))==-1 ) {

                if( recu.startsWith("00") ) { //Reception du nombre de joueur max de la partie
                    ListeEquipes.equipes( Integer.valueOf(recu.substring(6)), TypeJeu.RESEAU); //on crée les équipes.
                    StaticData.setIdentifiant( Integer.valueOf(recu.substring(4,6)) ); //On alloue l'identifiant du joueur courant
                    //System.out.println("Ecouteur : Création de la ListeEquipe avec : "+recu.substring(6)+" joueurs.");
                }
                else if( recu.startsWith("01") ) { //Reception des cases de la map
                    //si la map n'est pas définie
                    if( StaticData.map==null ){
                        try {
                            StaticData.map=new Map();//on définie une map vide.
                        } catch (SlickException ex) {
                            System.err.println("Ecouteur : impossible de crée la map. "+ex.getMessage());
                        }
                    }
                    StaticData.map.setCase(recu);
                }
                else if( recu.startsWith("02") ) { //Reception d'un nouveau joueur.
                    //Si le joueur n'existe pas on le crée
                    if( !ListeEquipes.isJoueurExiste(Integer.valueOf(recu.substring(2, 4))) )
                        ListeEquipes.add( new Joueur( recu.substring(4), Integer.valueOf(recu.substring(2, 4)) ) ); //On crée le joueur avec son identifiant
                }
                else if( recu.startsWith("03") ) { //Reception du personnage d'un joueur.
                    //si la map n'est pas définie
                    if( StaticData.map==null ){
                        try {
                            StaticData.map=new Map(); //on définie une map vide.
                        } catch (SlickException ex) {
                            System.err.println("Ecouteur : impossible de crée la map.");
                        }
                    }
                    try {
                        //Reception du personnage d'un joueur sous la forme "03"+id+idPers+x+y+type
                        //Si le personnage n'existe pas.
                        if( !ListeEquipes.getJoueur(Integer.valueOf(recu.substring(2,4))).isPersonnageExiste(Integer.valueOf(recu.substring(4,6))) )
                            //On ajoute le personnage reçu au joueur identifier
                            ListeEquipes.getJoueur( Integer.valueOf(recu.substring(2,4)) ).ajoutPersonnage(recu.substring(12),
                                                                                                       Integer.valueOf(recu.substring(4,6)),
                                                                                                       Integer.valueOf(recu.substring(6,9)),
                                                                                                       Integer.valueOf(recu.substring(9,12)));
                    } catch (SlickException ex) {
                        System.err.println("Ecouteur : impossible d'ajouter le personnage "+recu.substring(4)+" au joueur identifier "+recu.substring(2,4));
                    }
                }
                else if( recu.startsWith("10") ) { //Reception de données du combat.
                    Personnage.appliquerAction(recu);
                }
                else if(recu.startsWith("11")) { //Reception du messsage d'un joueur.
                    PhaseJeu.getLog().ajoutMessage(recu.substring(4));
                }
                else
                    System.err.println("Ecouteur : La chaine '"+recu+"' n'est pas valide.");
            }
        }
        //Si la chaine recu possède des caractères non reconnu au debut, on la decoupe pour qu'elle corresponde à nos critères.
        else {
            int debut=1;
            boolean fin=false;
            while( debut<recu.length() && !fin ){
                if( recu.substring(debut,debut+1).equals("0") || recu.substring(debut,debut+1).equals("1") )
                    if( recu.substring(debut).length()>4) {
                        traitement(recu.substring(debut));
                        fin=true;
                    }
                    else
                        System.err.println("Ecouteur : chaine reçu corrompu '"+recu.substring(debut)+"'");
                else
                    debut++;
            }
        }
    }
}
