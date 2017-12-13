package javatournament.combat;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import javatournament.data.Draw;
import javatournament.map.Map;
import javatournament.personnage.*;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Pyarg
 */
public class Joueur {
    /**
     * Nom du Joueur
     */
    private String nom;
    /**
     * Identifiant du Joueur
     */
    private int identifiant;
    /**
     * Couleur du Joueur
     */
    private int couleur=0;
    /*
     * Image du cadre du Joueur.
     */
    private Image cadre;
     /*
     * Image du cercle d'appartenance du Joueur.
     */
    private Image cercle;
    /**
     * Liste des personnages du joueur.
     */
    private ArrayList<Personnage> personnages = new ArrayList<>();
    /**
     * Nombre de personnage maximum du joueur.
     */
    public int nbrPersonnageMax=4;
    /**
     * Liste d'attributs static des modifications du joueur et de ces personnages.
     */
    public static final int MAGE=0;
    public static final int GUERRIER=1;
    public static final int VOLEUR=2;
    public static final int CLERC=3;    
    
    public static final int DEFAUT=0;
    public static final int BLEU=1;
    public static final int ROUGE=2;
    
    /**
     * Le joueur a perdu.
     */
    private boolean hasLose = false;
    
    /**
     * Constucteur d'équipe initialisant le nom de l'équipe.
     * @param nom - Nom de l'équipe.
     */
    public Joueur(String nom){
        this.nom = nom;
        this.identifiant=ListeEquipes.nbrEquipes();
    }
    
    /**
     * Constucteur d'équipe initialisant le nom de l'équipe.
     * @param nom - Nom de l'équipe.
     * @param id - Identifiant du joueur.
     */
    public Joueur(String nom, int id){
        this.nom = nom;
        this.identifiant=id;
    }
    
    /**
     * Constucteur d'équipe initialisant le nom de l'équipe.
     * @param nom - Nom de l'équipe.
     */
    public Joueur(){
        this.nom = "Joueur "+ListeEquipes.nbrEquipes();
        this.identifiant=ListeEquipes.nbrEquipes();
    }
    /**
     * Méthode qui retourne un personnage
     * @param indice - Numero du personnage voulu
     * @return Personnage
     */
    public Personnage getPersonnage(int indice){
        if( indice<this.personnages.size() )
            return this.personnages.get(indice);
        else 
            return null;
    }
    /**
     * Méthode qui retourne un personnage
     * @param indice - Numero du personnage voulu
     * @return Personnage
     */
    public Personnage getDernierPersonnage(){
        return this.personnages.get( personnages.size()-1 );
    }
    /**
     * Méthode qui retourne true si le personnage est dans la liste du Joueur.
     * @param Personnage - Personnage rechercher.
     * @return Boolean
     */
    public Boolean isPersonnage( Personnage p ){
        return this.personnages.contains(p);
    }
    
    /**
     * Méthode qui retourne un personnage
     * Retourne le personnage voulu ou null si il n'existe pas.
     * @param indice - Identifiant du personnage voulu
     * @return Personnage
     */
    public Personnage getPersonnageById(int id){
        for( Personnage tmp : personnages ){
            if( tmp.getNumPers()==( id ) )
                return tmp;
        }
        return null;
    }
    
    /**
     * Méthode permettant d'ajouter un Personnage à l'équipe.
     * Utiliser les variables statiques pour avoir le numero du personages
     * (Joueur.Clerc ; Joueur.Mage ...)
     * Retourne true si l'ajout a bien été effectuer, false si la liste à atteint sa limite.
     * @param indicePerso - Numero du personnage.
     * @param id Identifiant du personnage.
     * @param posX Abscisse du personnage.
     * @param posY Ordonnée du personnage.
     * @return boolean
     * @throws SlickException 
     */
    public boolean ajoutPersonnage(int indicePerso, int id, int posX, int posY) throws SlickException {
        //Si la liste contient 8 personnages ou plus, on renvoie false.
        if( isComplete() ) return false;
        
        switch( indicePerso ){
            case 0:
                this.personnages.add( new Mage() );
            break;                
            case 1:
                this.personnages.add( new Guerrier() );
            break;                
            case 2:
                this.personnages.add( new Voleur() );
            break;                
            case 3:
                this.personnages.add( new Clerc() );
        }
        this.personnages.get( this.personnages.size()-1 ).setNumPers(id);
        this.personnages.get( this.personnages.size()-1 ).setPosX(posX);
        this.personnages.get( this.personnages.size()-1 ).setPosY(posY);
        StaticData.map.setCasePersonnage( posX, posY+Map.tailleCaseMap, id );
        return true;
    }
    
    /**
     * Méthode permettant d'ajouter un Personnage à l'équipe.
     * Retourne true si l'ajout a bien été effectuer, false si la liste à atteint sa limite.
     * @param Perso Nom du type de personnage.
     * @param id Identifiant du personnage.
     * @param posX Abscisse du personnage.
     * @param posY Ordonnée du personnage.
     * @return boolean
     * @throws SlickException 
     */
    public boolean ajoutPersonnage(String Perso, int id, int posX, int posY) throws SlickException
    {
        //Si la liste contient 8 personnages ou plus, on renvoie false.
        if( isComplete() ) return false;
        
        switch( Perso ){
            case "mage":
                this.personnages.add( new Mage() );
            break;                
            case "guerrier":
                this.personnages.add( new Guerrier() );
            break;                
            case "voleur":
                this.personnages.add( new Voleur() );
            break;                
            case "clerc":
                this.personnages.add( new Clerc() );
        }
        this.personnages.get( this.personnages.size()-1 ).setNumPers(id);
        this.personnages.get( this.personnages.size()-1 ).setPosX(posX);
        this.personnages.get( this.personnages.size()-1 ).setPosY(posY);
        StaticData.map.setCasePersonnage( posX, posY+Map.tailleCaseMap, id );
        return true;
    }
    
    /**
     * Accesseur du Nom du Joueur
     * @return String
     */
    public String getNom(){
        return nom;
    }
    
    /**
     * Modificateur du Nom du Joueur
     * @param s - Nom du Joueur.
     */
    public void setNom(String s){
        this.nom=s;
    }
    
    /**
     * Getter de la couleur du Joueur.
     * @param couleur - Couleur du Joueur
     */
    public void setCouleur( int couleur ) {
        Draw.imageJoueur();
        this.couleur=couleur;
        this.cadre=Draw.CADRE[couleur-1];
        this.cercle=Draw.CERCLE[couleur-1];
    }
    
    /**
     * Accesseur de la couleur du Joueur
     * @return int
     */
    public int getCouleur(){
        return this.couleur;
    }
    
    /**
     * Retourne le nombre de personnage du Joueur.
     * @return int
     */
    public int nbrPersonnage(){
        return this.personnages.size();
    }
    
    /**
     * Retourne vraie si la liste de personnages du Joueur est vide.
     * @return boolean
     */
    public boolean isEmpty(){
        return this.personnages.isEmpty();
    }
    
    /**
     * Retourn vraie si la liste de personnage du Joueur est complete.
     * (Elle contient 8 personnages)
     * @return boolean
     */
    public boolean isComplete(){
        return this.personnages.size()==nbrPersonnageMax;
    }
    
    /**
     * Melanger les personnages du Joueur
     */
    public void melanger(){
        Collections.shuffle( personnages );
    }
    
    /**
     * Méthode pour retirer un personnage de la liste des personnages du Joueur.
     * @param int - Indice du personnage dans la liste 
     * @return Personnage
     */
    public Personnage remove(int indice){
        Personnage p = personnages.get(indice);
        personnages.remove(indice);
        return p;
    }
    
    /**
     * Retourne l'image du cadre des personnages du Joueur.
     * @return Image
     */
    public Image getCadre(){
        return this.cadre;
    }
    
    /**
     * Retourne l'image du cercle des personnages du Joueur.
     * @return 
     */
    public Image getCercle(){
        return this.cercle;
    }

    /**
     * Méthode permettant au joueur de passer son Tour.
     */
    public void passer(){
        personnages.add( personnages.remove(0) );
    }    

    /**
     * Méthode qui détruit tout les personnages du Joueur.
     */
    public void clear(){
        personnages.clear();
    }
    
    /**
     * Méthode pour envoyer un joueur et ces personnages.
     * @param OutputStream - Flux sur lequel envoyer le joueur.
     */
    public void envoieJoueur(OutputStream out){
        PrintStream print = new PrintStream(out);
        String id = StaticData.transformeInt(this.identifiant);
        print.println( "02"+id+this.nom );
        for( Personnage tmp : personnages) {
            String idPers = StaticData.transformeInt( tmp.getNumPers() );
            String x=String.valueOf(tmp.getPosX());
            String y=String.valueOf(tmp.getPosY());
            if( tmp.getPosX()<100 ){
                x="0"+tmp.getPosX();
                if( tmp.getPosX()<10 )
                    x="0"+x;
            }
            if( tmp.getPosY()<100 ){
                y="0"+tmp.getPosY();
                if( tmp.getPosY()<10 )
                    y="0"+y;
            }
            if( tmp instanceof Clerc)
                print.println( "03"+id+idPers+x+y+"clerc" );
            else if( tmp instanceof Guerrier)
                print.println( "03"+id+idPers+x+y+"guerrier" );
            else if( tmp instanceof Mage)
                print.println( "03"+id+idPers+x+y+"mage" );
            else if( tmp instanceof Voleur)
                print.println( "03"+id+idPers+x+y+"voleur" );
        }
    }       
    /**
     * Methode qui retourne l'eidentifiant du joueur.
     * @return int
     */
    public int getIdentifiant(){
        return this.identifiant;
    }
    /**
     * Méthode pour verifier si le personnage n'existe pas.
     * @param id - Identifiant du personnage
     * @return boolean
     */
    public boolean isPersonnageExiste(int id){
        for( Personnage tmp : personnages) {
            if( tmp.getNumPers()==id )
                return true;
        }
        return false;
    }
    public void affichePersonnagesConsole(){
        for( Personnage tmp : personnages) {
            System.out.println("    "+tmp.getNumPers()+" "+tmp.getNom());
        }        
    }
    
    /**
     * Modificateur de l'attribut de défaite du joueur.
     */
    public void defeat()
    {
        if(this.personnages.isEmpty())
        {
            this.hasLose = true;
        }
    }
    
    /**
     * Accesseur de l'attribut de défaite du joueur.
     */
    public boolean hasLose()
    {
        return this.hasLose;
    }

    @Override
    public String toString() {
        return this.nom;
    }
}