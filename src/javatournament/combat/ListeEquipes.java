package javatournament.combat;

import java.util.ArrayList;
import java.util.Collections;
import javatournament.personnage.Personnage;

/**
 * Classe qui contient les joueurs.
 * @author Pyarg, Tony
 */
public class ListeEquipes {
    
    /**
     * Liste contenant les équipes
     */
    private static ArrayList<Joueur> equipes=null;
    /**
     * Nombre de Joueurs maximum que peut contenir la liste des equipes.
     */
    private static int nombreJoueursMax=0;
    /**
     * Indice du Joueur qui doit Jouer.
     */
    private static int joueurCourant=0;
    /**
     * Nombre de personnages que peut posseder chaque joueur.
     */
    private static int nbrPersonnages=4;
    /**
     * Type de jeu (Local ou réseau).
     */
    private static TypeJeu type;
     
    /**
     * Constructeur de la classe Equipes
     * Une equipe peut contenir 2, 4 ou 8 joueurs.
     */
    private ListeEquipes( int nbrJoueurs){
        if( nbrJoueurs%2==0){
            equipes = new ArrayList<Joueur>();
            nombreJoueursMax=nbrJoueurs;
            nbrPersonnages=8/nbrJoueurs;
        }
        else
            System.out.println("Le nombre de joueurs d'une equipe doit être 2, 4 ou 8.");       
    }
    /**
     * Méthode qui génères les équipes si elle ne son pas déjà crées.
     * Une equipe peut contenir 2, 4 ou 8 joueurs.
     * @param nbrJoueurs - Nombre de Joueur du combat.
     */
    public static void equipes(int nbrJoueurs, TypeJeu tJ){
        if( nombreJoueursMax==0 ){
            new ListeEquipes( nbrJoueurs );
            type = tJ;
        }
        else
            System.err.println("ListeEquipes : Les équipes sont déjà crées.");
    }   
 
    /**
     * Ajoute un Joueur dans l'équipe.
     * @param P - Joueur à ajouter dans l'équipe.
     */
    public static void add( Joueur j ){
        if( type.equals(TypeJeu.RESEAU)  )
            equipes.add(j.getIdentifiant(), j);
        else 
            equipes.add(j);
        equipes.get(0).setCouleur(Joueur.BLEU);
        if(equipes.size()==2)
            equipes.get(1).setCouleur(Joueur.ROUGE);
    }
    
    /**
     * Melange les équipes et les personnages de chaque Joueurs.
     */
    public static void melanger(){
        for( Joueur tmp : equipes ){
            tmp.melanger();
        }
        Collections.shuffle( equipes );        
    }
    /**
     * Accesseur au nombres d'équipes que doit contenir la partie.
     * @return int
     */
    public static int nbrEquipesMax(){
        return nombreJoueursMax;
    }
    
    /**
     * Accesseur au nombres d'équipes crées.
     * @return int
     */
    public static int nbrEquipes(){
        if(equipes != null)
            return equipes.size();
        return -1;
    }
    
    /**
     * Methode qui retourne un joueur de la liste des equipes.
     * @param i - indice du personnage
     * @return joueur
     */
    public static Joueur getJoueur(int i)
    {
        if(i < equipes.size())
        {
             return equipes.get(i);
        }
        return new Joueur("Inacesseible");
    }

    /**
     * Méthode permettant au joueur de passer son Tour.
     */
    public static void passer(){
        equipes.get( joueurCourant ).passer();
        if( joueurCourant==0 )
            joueurCourant++;
        else if( joueurCourant==ListeEquipes.nbrEquipes()-1)
                joueurCourant=0;
    }
    /**
     * Methode qui retourne l'indice du Joueur qui doit jouer.
     * @return int
     */
    public static int getJoueurCourant(){
        return joueurCourant;
    }
    /**
     * Retourne le personnage qui doit jouer.
     * @return Personnage
     */
    public static Personnage getPersonnageCourant()
    {
        if(equipes.size() > 0 && equipes.get(joueurCourant).nbrPersonnage() > 0)
            return equipes.get(joueurCourant).getPersonnage(0);
        return null;
    }
    /**
     * Retourne le personnage qui doit jouer.
     * @return Personnage
     */
    public static Personnage getPersonnageSuivant(){
        int joueurSuivant= joueurCourant+1==equipes.size()? 0 : joueurCourant+1;
        return ListeEquipes.getJoueur( joueurSuivant ).getPersonnage(0);
    }
    
    /**
     * Retourne le nombres de personnages de la partie.
     * @return int
     */
    public static int getNombrePersonnages(){
        int nombrePerso=0;
        for(Joueur tmp : equipes)
            nombrePerso+=tmp.nbrPersonnage();
        return nombrePerso;
    }
    
    /**
     * Getter du nombre de personnage maximum de chaque joueur.
     * @return int
     */
    public static int getNombrePersonnageMax(){
        return nbrPersonnages;
    }
    
    /**
     * Methode qui retourne le personnage graçe à sont identifiant.
     * @param int - Identifiant du personnage
     * @return Personnage
     */
     public static Personnage getPersonnage( int i){

        ListeEquipes.getJoueur( i/10 );
         Personnage p = ListeEquipes.getJoueur( i/10 ).getPersonnageById( i );
         return p;
     }
     
     /**
      * Methode qui détruit les equipes.
      */
     public static void clear(){
         if(equipes!=null){
             equipes.clear();
             nombreJoueursMax=0;
             nbrPersonnages=0;
         }
     }
     
     /**
      * Méthode pour afficher les points de vie de tout les personnages de chaque Joueur.
      */
     public static void affichePDV(){
         for( int j=0; j<equipes.size(); j++)
             for( int y=0; y<ListeEquipes.getJoueur(j).nbrPersonnage(); y++ )
                System.out.println("Joueur "+j+", Personnage "+ListeEquipes.getJoueur(j).getPersonnage(y).getNumPers()+", PDV : "+ListeEquipes.getJoueur(j).getPersonnage(y).getPDVCrt());
         System.out.println();
     }
     
     /**
      * Méthode qui retourn le type de jeu.
      * @return TypeJeu - Hôte du servuer.
      */
     public static TypeJeu getTypeJeu(){
         return type;
     }
     /**
      * Méthode qui supprime un personnage.
      * Modifie le nombre de personnage de chaque joueur si tous les joueurs ont des personnages en moins.
      * @param indice - Indice du personnage à supprimer dans la liste du joueur.
      */
    public static void remove( int indiceJoueur, int indicePerso){
        boolean actualisation=true; //Booleen pour savoir si on modifie le nombre de personnage de ListeEquipe.
        //On supprime le personnage voulu.
        ListeEquipes.getJoueur( indiceJoueur ).remove(indicePerso);
        //on verifie le nombre de personnages de chaque joueur, si ils possèdent tous le même nombre
        //de Personnages, on actualise le nombre de personnage de ListeEquipe.
        int nbrPerso=ListeEquipes.getJoueur(0).nbrPersonnage();
        for(int i=1; i<equipes.size();i++){
            if( ListeEquipes.getJoueur(i).nbrPersonnage()!=nbrPerso )
                actualisation=false;
        }
        if( actualisation && nbrPersonnages>nbrPerso )
            nbrPersonnages=nbrPerso;
    }
    
    /**
     * Méthode pour savoir si la liste des joueurs est vide.
     * @return boolean - True si la liste est vide, false sinon.
     */
    public static boolean isEmpty(){
        return equipes == null || equipes.isEmpty();
    }
    /**
     * Méthode qui affiche tout les joueurs de l'equipes(id et nom) dans la console.
     */
    public static void afficheEquipeConsole(){
        System.out.println("\nListes des Equipes et leurs joueurs :");
        for(Joueur tmp : equipes ){
            System.out.println("Joueur : "+tmp.getIdentifiant()+" "+tmp.getNom());
            tmp.affichePersonnagesConsole();
        }
    }
    /**
     * Méthode qui dit si un joueur existe dans la listeEquipess
     * @param id Identifiant du joueur.
     * @return boolean
     */
    public static boolean isJoueurExiste(int id){
        for( Joueur j : equipes){
            if( j.getIdentifiant()==id )
                return true;
        }
        return false;
    }
    
        /**
     * Accesseur aux Personnages toujours prÃ©sent en jeu.
     * @return ArrayList<Personnage> - Liste des Personnages encore en jeu.
     */
    public static ArrayList<Personnage> getPersonnages() {
        ArrayList personnages = new ArrayList();
        for(Joueur J : ListeEquipes.equipes)
        {
            for(int i = 0 ; i < J.nbrPersonnage() ; i++)
                personnages.add(J.getPersonnage(i));
        }
        return personnages;
    }
    
    /**
     * Méthode qui supprime un joueur si il ne possède pas de personnage.
     * @param indice Indice du personnage à supprimer.
     */
    public static void remove(int indice)
    {
        if( equipes.get(indice).isEmpty() )
        {
            equipes.remove(indice);
        }
    }
    
    public static Joueur getWinner()
    {
        Joueur winner = null;
        int cpt = 0;
        for(Joueur j : equipes)
        {
            if(!j.hasLose())
            {
                cpt++;
                winner = j;
            }
        }
        if(cpt == 1)
        {
            return winner;
        }
        return null;
        
    }
}