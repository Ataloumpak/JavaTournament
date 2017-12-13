package javatournament.menu;

import java.util.ArrayList;
import javatournament.JavaTournament;
import javatournament.data.Draw;
import javatournament.data.Police;
import javatournament.data.Son;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Menu qui affiche les personnes ayant participer au projet.
 * @author pyarg
 */
public class MenuInformations extends BasicGameState
{
    /**
     * Identifiant de la State
     */
    int stateID = 4;

    /**
     * Police du nom des personne.
     */
    private UnicodeFont nomPersonne;
    /**
     * Liste des nom et pseudo des membres
     */
    private ArrayList<String> listMembres = new ArrayList<>();

    /**
     * Constructeur info initialisant l'identifiant de la state
     * @param stateID nouvel identifiant de la state
     */
    public MenuInformations( int stateID )
    {
        this.stateID = stateID;
    }
    /**
     * Accesseur de l'identifiant de la state
     * @return int stateID identifiant de la state
     */
    @Override
    public int getID() 
    {
        return stateID;
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics graph) throws SlickException {
        //si la police est null on la genere
        if(nomPersonne==null)
            nomPersonne = Police.UNICODEFONT( Police.CAVIARDREAM[Police.BOLD], 17);
        //si la liste est vide on la remplie
        if(listMembres.isEmpty())
            membres();
        //Affichage de l'image de fond
        graph.drawImage(Draw.FONDMENU,0,0);
        
        try{
            nomPersonne.drawString(150,50,"Projet tutoré et réalisé par les personnes suivantes :",Color.black); //Affichage des caractères        
            int posY=150;
            for( int i=0; i<listMembres.size() ; i++) {
                if( i%2==1 ){
                    nomPersonne.drawString(654, posY, listMembres.get(i) ,Color.black);
                    posY+=50;
                }
                else if( i%2==0)
                    nomPersonne.drawString(350, posY, listMembres.get(i) ,Color.black);
            }

            nomPersonne.drawString(600, 150, "alias" ,Color.black);
            nomPersonne.drawString(150,450,"Tuteur :",Color.black);
            nomPersonne.drawString(350,450,"Vautrot Philippe",Color.black);
            nomPersonne.drawString(654,450,"Philou",Color.black);
            
            nomPersonne.drawString(150,550,"Remerciements :",Color.black);
            nomPersonne.drawString(350,550,"Tanchette Cédric",Color.black);
            nomPersonne.drawString(654,550,"Yotano",Color.black);
        }
        catch(Exception e){}
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
    {
        Son.CREDITS.gestion(gc);
        
        if( gc.getInput().isKeyDown(Input.KEY_ESCAPE) ) { //Si on appui sur Echap
            nomPersonne=null;
            Son.CREDITS.stop();
            Son.MENU_PRINCIPAL.play();
            sbg.enterState(JavaTournament.menuPrincipalState); //Retour au state du Menu Principal       
        }
    }
    
    /*
     * Méthode pour ajouter a la liste membres les Membres du projets et leurs alias
     */
    private void membres()
    {
        listMembres.add( "Bourguignon Nicolas" );
        listMembres.add( "Headhunterz" );
        listMembres.add( "Galloy Tony" );
        listMembres.add( "Ataloumpak" );
        listMembres.add( "Fournier Quentin" );
        listMembres.add( "Xeheros" );
        listMembres.add( "Lavancier Romain" );
        listMembres.add( "Pyarg" );
        listMembres.add( "Renaud Alexandre" );
        listMembres.add( "Hartos" );
        listMembres.add( "Tonnelier Quentin" );
        listMembres.add( "Kant1" );
    }

}