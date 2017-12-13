package javatournament.combat;

import javatournament.map.Map;
import javatournament.network.Client;
import javatournament.network.Serveur;
import org.newdawn.slick.Image;

/**
 * Classe contenant toutes les données static du jeu.
 * @author pyarg
 */
public class StaticData {
    
    /**
     * Attribut static contenant le serveur de jeu.
     */
    public static Serveur serveur=null;
    /**
     * Attribut static contenant le client du jeu.
     */
    public static Client client=null;
    /**
     * Attribut static contenant la map du jeu.
     */
    public static Map map=null;
    /**
     * Identifiant du joueur de la machine locale.
     */
    private static int identifiant=-1;
    /**
     * Fond de la map.
     */
    public static Image fond=null;
    
    /**
     * Mathode pour retourner l'identifiant du joueur courant.s
     * @return int
     */
    public static int getIdentifiant(){
        return identifiant;
    }
    /**
     * Méthode pour modifier l'identifiant du joueur cournat.
     * @param id - identifiant reçu.
     */
    public static void setIdentifiant(int id){
        identifiant=id;
    }
    /**
     * Méthode pour transformer correctement un int en string.
     * @param tmp
     * @return String
     */
    public static String transformeInt(int tmp){
        String id = tmp<10 ? "0"+String.valueOf(tmp) : String.valueOf(tmp);
        return id;
    }
    /**
     * Méthode pour mettre tout les attribus liée au jeu à null.
     * <br/>Elle concerne, le serveur, le client, la map, menuConnexion et les equipes.
     */
    public static void clear(){
        if(client!=null)
            client.arret();
        client=null;
        if(serveur!=null)
            serveur.arret();
        serveur=null;
        map=null;
        identifiant=-1;
        PhaseJeu.erreur=false;
        ListeEquipes.clear();
    }
}
