package javatournament.menu;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javatournament.JavaTournament;
import javatournament.combat.StaticData;
import javatournament.data.Draw;
import javatournament.data.Police;
import javatournament.emitters.Emitter;
import javatournament.network.Client;
import javatournament.network.Serveur;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Menu qui perme de crée et de rejoindre un serveur.
 * @author pyarg
 */
public class MenuServeur extends BasicGameState{
    /**
     * ID de la state
     */
    int stateID = 6;
    /**
     * Police temporaire
     */
    private UnicodeFont font;
    /**
     * Particule de pluie
     */
    private Emitter rainEmitter;
    /**
     * Booleen pour simuler les eclairs
     */
    private boolean dejaEclair = false;

    /**
     * Tableau de boolean pour selectionner un menu
     */    
    private boolean[] choix=new boolean[2];
    
    /**
     * Constructeur de la state
     * @param stateID 
     */
    public MenuServeur( int stateID ){
        this.stateID = stateID;
    }
 
    /**
     * Accesseur de l'ID de la state
     * @return 
     */
    @Override
    public int getID(){
        return stateID;
    }
 
     @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        Draw.initChoix(choix);        
        rainEmitter = new Emitter(0, 0, 7500, 50, 0, gc.getWidth()*2, Emitter.RAIN_EMITTING, 30, true, Draw.RAIN_PART); 
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        Draw.FONDMENU.draw( 0, 0, Draw.filter);
        if(font==null)
            font=Police.UNICODEFONT( Police.AVERIA[Police.BOLD], 25, java.awt.Color.white);
        //affichage du menu
        Draw.menu(gc, g, font, choix, "Partie à 2 joueurs.", "Rejoindre une partie.");
        //on affiche la pluie
        rainEmitter.draw(g);        
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {       
        //On anime la pluie
        rainEmitter.animate(gc, delta);
        
        if( gc.getInput().isKeyPressed( Input.KEY_2) || ( gc.getInput().isKeyPressed(Input.KEY_ENTER) && choix[0] ) ){
            //Lancement du serveur.
            serveur(2011,2);
            font=null;
            //On vide l'enregistrement des touches.
            gc.getInput().clearKeyPressedRecord();
            sbg.enterState(JavaTournament.menuConnexion); //lancement du state du Menu Combat
        }
        /*
        if( gc.getInput().isKeyPressed( Input.KEY_4) ){
            //Lancement du serveur.
            font=null;
            serveur(2011,4);
            
        }    
        if( gc.getInput().isKeyPressed( Input.KEY_8) ){
            //Lancement du serveur.
            font=null;
            serveur(2011,8);
        }
        */
        else if( gc.getInput().isKeyPressed( Input.KEY_R) || ( gc.getInput().isKeyDown(Input.KEY_ENTER) && choix[1] ) ){
            font=null;
            //On vide l'enregistrement des touches.
            gc.getInput().clearKeyPressedRecord();
            sbg.getState(JavaTournament.menuConnexion).init(gc, sbg);
            sbg.enterState(JavaTournament.menuConnexion); //lancement du state du Menu Combat
        }
        else if( gc.getInput().isKeyPressed( Input.KEY_ESCAPE) ){
            font=null;
            //On vide l'enregistrement des touches.
            gc.getInput().clearKeyPressedRecord();
           sbg.enterState(JavaTournament.menuPrincipalState); //lancement du state du Menu Combat
        }
        //Effet d'eclair
        Draw.eclair(dejaEclair);
    }
    /**
     * Méthode pour lancer le serveur.
     * @param port - port de connexion au serveur
     */
    private void serveur(int port, int clients) {
        this.inputEnded(); //on coupe le clavier pour ne pas faire bugger menuConnexion.
        StaticData.serveur=new Serveur(port, clients);
        try {
            StaticData.client=new Client( java.net.InetAddress.getLocalHost().getHostAddress() , port); 
        } catch (UnknownHostException ex) {
            System.err.println("MenuServeur : erreur lors de la création du client locale. "+ex.getMessage());
        } catch (IOException ex) {
                Logger.getLogger(MenuServeur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
}