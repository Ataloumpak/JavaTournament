package javatournament.menu;

import java.util.ArrayList;
import javatournament.JavaTournament;
import javatournament.data.Draw;
import javatournament.data.Police;
import javatournament.data.Son;
import javatournament.personnage.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
/**
 * Menu qui affiche les informations des personnages.
 * @author pyarg
 */
public class MenuPersonnage extends BasicGameState
{
     /**
     * Modificateur de l'image du cercle de l'équipe 2
     * @param cercleEquipe chemin de l'image 2
     */
    int stateID = 2; //Identifiant du State
    
    /**
     * Position en abscisse de base pour les affichages
     */
    private static int caracX=10;
    
    /**
     * Position en ordonnée de base pour les affichages
     */
    private static int caracY=360;
    
    /**
     * Position en abscisse de l'affichage des sorts
     */
    private static int sortX=caracX+650;
    
    /**
     * Position en ordonnée de l'affichage des sorts
     */
    private static int sortY=caracY+70;
    
    /**
     * Identifiant du personage
     */
    private int idPersonnage=0;
    
    /**
     * Liste contenant tous les personnages disponibles
     */
    private ArrayList<Personnage> listPersonnage = new ArrayList<>();
    
    /**
     * Police pour les noms de personnages
     */
    private UnicodeFont nomPersonnageTTF;
    
    /**
     * Police pour les caractéristiques des personnages
     */    
    private UnicodeFont caracPersonnageTTF;
    
    /**
     * Police pour les sorts des personnages
     */
    private UnicodeFont sortsTTF;
    
    /*
     * Police pour les caractéristiques des sorts des personnages
     */
    private UnicodeFont sortsCaracTTF;
    
    /*
     * Police pour les caractéristiques des sorts des personnages
     */
    private int orientationAnimation=0;
    
    /*
     * Police pour les caractéristiques des sorts des personnages
     */
    private boolean isOrientation=false;
    
    /*
     * Police pour les caractéristiques des sorts des personnages
     */
    private boolean isPersonnage=false;
    
    /*
     * Police pour les caractéristiques des sorts des personnages
     */
    private boolean isCouleur=false;
    
    /*
     * Police pour les caractéristiques des sorts des personnages
     */
    private int couleurAnimation;
    
    /*
     * Police pour les caractéristiques des sorts des personnages
     */
    public MenuPersonnage( int stateID )
    {
        this.stateID = stateID;
    }
    /*
     * Identifiant de la touche presser
     */
    private int idTouche=999;
 
    /**
     * Méthode permettant de retourner l'identifiant de la state
     * @return stateId - l'identifiant de la state
     */
    @Override
    public int getID() 
    {
        return stateID;
    }
 
 
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        try
        {
            listPersonnage.add(new Mage()); //ajout d'un Personnage Mage dans la listePersonnage
            listPersonnage.add(new Voleur()); //ajout d'un Personnage Voleur dans la listePersonnage
            listPersonnage.add(new Clerc()); //ajout d'un Personnage Clerc dans la listePersonnage
            listPersonnage.add(new Guerrier()); //ajout d'un Personnage Guerrier dans la listePersonnage
        }
        catch (Exception e){
            System.out.println("Erreur de création des Personnages");
        }
        //Police du nom du Personnage
        nomPersonnageTTF = Police.UNICODEFONT( Police.AVERIA[Police.BOLD], 30);
        //Police des caractéristiques du personnages
        caracPersonnageTTF = Police.UNICODEFONT( Police.AVERIA[Police.BOLD], 18);
        sortsTTF = Police.UNICODEFONT( Police.CAVIARDREAM[Police.BOLD], 18);
        sortsCaracTTF = Police.UNICODEFONT( Police.AVERIA[Police.ITALIC], 14);
    }
 
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics graph) throws SlickException 
    {       
        Draw.FONDPERSO.draw(0,0); //Affichage du fond du isPersonnage
        
        try{
            caracPersonnageTTF.drawString(caracX+20,caracY+50,"Mouvement : "); //Affiche les mouvements
            caracPersonnageTTF.drawString(caracX+20,caracY+80,"Energie : "); //Affiche le texte energie
            caracPersonnageTTF.drawString(caracX+20,caracY+110,"Vie : "); //Affiche le texte vie
            caracPersonnageTTF.drawString(caracX+20,caracY+140,"Armure : "); //Affiche le texte armure
            caracPersonnageTTF.drawString(caracX+20,caracY+170,"Attaque : "); //Affiche le texte Attaque
            caracPersonnageTTF.drawString(caracX+20,caracY+200,"Esquive : "); //Affiche le texte esquive    
            try {//essai de dessiner l'animation du Personnage
                graph.drawAnimation(listPersonnage.get(idPersonnage).getAnimPersonnage(couleurAnimation).getAnimation(orientationAnimation), 500, 200);            
            }
            catch (Exception e) {
                System.out.println("Erreur lors du chargement de l'animation du personnage");
            }                   
            nomPersonnageTTF.drawString(caracX,caracY,listPersonnage.get(idPersonnage).getNom()); //Affichage du nom du isPersonnage
            caracPersonnageTTF.drawString(caracX+200,caracY+50,String.valueOf(listPersonnage.get(idPersonnage).getPMMax())); //Affichage des pm du isPersonnage
            caracPersonnageTTF.drawString(caracX+200,caracY+80,String.valueOf(listPersonnage.get(idPersonnage).getEnergieMax())); //Affichage de l'energie du isPersonnage
            caracPersonnageTTF.drawString(caracX+200,caracY+110,String.valueOf(listPersonnage.get(idPersonnage).getPDVMax())); //Affichage des pv du isPersonnage
            caracPersonnageTTF.drawString(caracX+200,caracY+140,String.valueOf(listPersonnage.get(idPersonnage).getArmure())); //Affichage de l'armure du isPersonnage
            caracPersonnageTTF.drawString(caracX+200,caracY+170,String.valueOf(listPersonnage.get(idPersonnage).getAttaque())); //Affichage de l'attaque du isPersonnage
            caracPersonnageTTF.drawString(caracX+200,caracY+200,String.valueOf(listPersonnage.get(idPersonnage).getEsquive())); //Affichage de l'esquive du isPersonnage          

            //Affichage du txt de modification de l'apparence du isPersonnage
            if( isPersonnage )
                sortsCaracTTF.drawString(100,10,"Changement de Personnage activé");
            else if(isOrientation)
                sortsCaracTTF.drawString(100,10,"Changement d'Orientation activé");
            else if(isCouleur)
                sortsCaracTTF.drawString(100,10,"Changement de Couleur activé");
        }
        catch(Exception e){}
        
        //Affichage des sorts du isPersonnage
        afficheSort(graph, 0);
        afficheSort(graph, 1);
        afficheSort(graph, 2);
        afficheSort(graph, 3);
    }
 
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
    {
        Son.DEFAITE.gestion(gc);
        
        touche(gc);
        switch( idTouche )
        {
            case 0:
                Son.DEFAITE.stop();
                Son.MENU_PRINCIPAL.play();
                sbg.enterState(JavaTournament.menuPrincipalState); //Retour au state du Menu Principal.
            break;
                
            case 1: txtModeAffichage(0);
            break;
                
            case 2: txtModeAffichage(1);
            break;
            
            case 3: txtModeAffichage(2);
            break;
            
            case 4: if( isPersonnage ){
                        switch( idPersonnage )
                        {
                            case 0:idPersonnage=3; //On affiche le isPersonnage 4.
                            break;

                            default:idPersonnage--; //On affiche le isPersonnage Précédent.
                        }
                    }
                    else if( isOrientation ){
                        switch( orientationAnimation ){
                            case 0:orientationAnimation=3; //On affiche l'animation 4.
                                break;
                            default:orientationAnimation--; //On affiche l'animation Précédente.        
                        }
                    }
                    else if( isCouleur ){
                        switch( couleurAnimation ){
                            case 0:couleurAnimation=2; //On affiche l'animation 0.
                                break;
                            default:couleurAnimation--; //On affiche l'animation suivante
                        }
                    }
                    break;
                
            case 5: if( isPersonnage ){
                        switch( idPersonnage )
                        {
                            case 3:idPersonnage=0; //On affiche le isPersonnage 0.
                            break;
                            default:idPersonnage++; //On affiche le isPersonnage Suivant.
                        }
                    }
                    else if( isOrientation ){
                        switch( orientationAnimation ){
                            case 3:orientationAnimation=0; //On affiche l'animation 0.
                                break;
                            default:orientationAnimation++; //On affiche l'animation suivante
                        }
                    }
                    else if( isCouleur ){
                        switch( couleurAnimation ){
                            case 2:couleurAnimation=0; //On affiche l'animation 0.
                                break;
                            default:couleurAnimation++; //On affiche l'animation suivante
                        }
                    }
                    break;
        }
    }
    
    /**
     * Methode pour changer le texte afficher lors de la modification de l'affichage du isPersonnage
     * @param numMode - Numero du Mode a afficher.
     */
    private void txtModeAffichage(int numMode)
    {
        if( numMode>=0 && numMode<=2)
        {
            switch( numMode )
            {
                case 0: this.isPersonnage=true;
                        this.isOrientation=false;
                        this.isCouleur=false;
                break;
                case 1: this.isPersonnage=false;
                        this.isOrientation=true;
                        this.isCouleur=false;
                break;
                case 2: this.isPersonnage=false;
                        this.isOrientation=false;
                        this.isCouleur=true;
                break;
            }
        }
        else
            System.err.println("Le numéro de mode n'est pas valide");
    }
    
    /*
     * Affichage des sorts du isPersonnage
     * @param graph 
     * @param numSort
     */
    private void afficheSort(Graphics graph, int numSort) {
        graph.drawImage(listPersonnage.get(idPersonnage).getSort( numSort ).getSkin(), sortX,sortY+(40*numSort));
        try {
            sortsTTF.drawString(sortX+30,sortY+(40*numSort),listPersonnage.get(idPersonnage).getSort( numSort ).getNom()); 
            sortsCaracTTF.drawString(sortX+30,sortY+20+(40*numSort),"Cout: "+String.valueOf(listPersonnage.get(idPersonnage).getSort( numSort ).getCout()));       
            sortsCaracTTF.drawString(sortX+120,sortY+20+(40*numSort),"Puissance: "+String.valueOf(listPersonnage.get(idPersonnage).getSort( numSort ).getPuissance()));       
            sortsCaracTTF.drawString(sortX+230,sortY+20+(40*numSort),"Portée: "+String.valueOf(listPersonnage.get(idPersonnage).getSort( numSort ).getPortee()));    
        }catch(Exception e){}
    }
    
    /*
     * Methode pour changer l'identifiant de la touche presser
     */
    private void touche( GameContainer gc )
    {
        if( gc.getInput().isKeyDown(Input.KEY_ESCAPE) )//Si on appui sur Echape.
            this.idTouche=0;
        else if( gc.getInput().isKeyDown(Input.KEY_P) )//Si on appui sur P.
            this.idTouche=1;
        else if( gc.getInput().isKeyDown(Input.KEY_O) )//Si on appui sur M.
            this.idTouche=2;
        else if( gc.getInput().isKeyDown(Input.KEY_C) )//Si on appui sur C.
            this.idTouche=3;
        else if( gc.getInput().isKeyPressed(Input.KEY_RIGHT) )//Si on appui sur  la fleche gauche.
            this.idTouche=4;
        else if( gc.getInput().isKeyPressed(Input.KEY_LEFT) )//Si on appui sur la fleche droite.
            this.idTouche=5;
        else
            this.idTouche=999;
    }
}