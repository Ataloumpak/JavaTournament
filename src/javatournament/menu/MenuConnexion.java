package javatournament.menu;

import java.io.IOException;
import java.net.UnknownHostException;
import javatournament.JavaTournament;
import javatournament.combat.Joueur;
import javatournament.combat.ListeEquipes;
import javatournament.combat.StaticData;
import javatournament.data.Draw;
import javatournament.data.Police;
import javatournament.emitters.Emitter;
import javatournament.network.Client;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Menu qui nous permet de se connecter à un serveur.
 * @author pyarg
 */
public class MenuConnexion extends BasicGameState{
    /**
     * ID de la state
     */
    private int stateID = 8;
    /**
     * Police des texte de la state.
     */
    private UnicodeFont font;
    /*
     * Zone de saisie du texte dans le HUD.
     */
    private TextField userText;
    /**
     * Booleen qui sert a afficher le bon text.
     */
    private boolean nom=false;
    /**
     * Text d'instruction.
     */
    private String text="Entrer l'adresse ip du serveur (XXX.XXX.XXX.XXX).";
    /**
     * ip du serveur.
     */
    private String ip="127.0.0.1";
    /**
     * Particule de pluie
     */
    private Emitter rainEmitter;
    /**
     * Booleen pour simuler les eclairs
     */
    private boolean dejaEclair = false;
    /**
     * Boolean pour dire si on a pas pu se connecter au serveur.
     * True si la connexion est impossible, false sinon.
     */
    private boolean erreurConnexion = false;
    
    /**
     * Constructeur de la state
     * @param stateID 
     */
    public MenuConnexion( int stateID ){
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
        font=Police.UNICODEFONT( Police.AVERIA[Police.BOLD], 30, java.awt.Color.white);
        userText=new TextField( gc, font, (gc.getWidth()/2)-150, (gc.getHeight()/2)-20, 300, 40);
        userText.setMaxLength(15);//Nombre de caractères maximum dans le champs texte
        rainEmitter = new Emitter(0, 0, 7500, 50, 0, gc.getWidth()*2, Emitter.RAIN_EMITTING, 30, true, Draw.RAIN_PART);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        Draw.FONDMENU.draw( 0, 0, Draw.filter);
        font.drawString( (container.getWidth()/2)-(font.getWidth(text)/2), (container.getHeight()/2)-50, text);
        if( StaticData.client!=null){
            text="Entrer votre nom.";
            font.drawString( 10, 560, "Vous êtes connecté au serveur "+this.ip);
            nom=true;
        }
        userText.render( container, g);
        userText.setFocus( true );
        if(erreurConnexion)
            font.drawString( 10, 560, "Impossible de se connecter au serveur "+this.ip);
        //on affiche la pluie
        rainEmitter.draw(g);   
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        //On annime la pluie
        rainEmitter.animate(gc, delta);
        
        this.inputStarted(); //on réactive le clavier pour pouvoir intéragire avec le menu.
        if( gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
            sbg.enterState(JavaTournament.menuPrincipalState);
        }
        else if( gc.getInput().isKeyDown(Input.KEY_ENTER)&& ipValide(userText.getText()) && !nom ) {
            try {
                StaticData.client = new Client( userText.getText(), 2011);
                erreurConnexion=false;
                userText.setText("");
            } catch (UnknownHostException ex) {
                erreurConnexion=true;
                System.err.println(ex.getMessage());
            } catch (IOException ex) {
                erreurConnexion=true;
            }
        }
        else if( gc.getInput().isKeyDown(Input.KEY_ENTER) && !userText.getText().equals("") && nom ){
                nouveauJoueur(sbg);
        }
        //On vide l'enregistrement des touches.
        gc.getInput().clearKeyPressedRecord();
        //Effet d'eclair
        Draw.eclair(dejaEclair);
    }
    
    /**
     * Méthode qui verifie si le String passé en paramètre est bien un ip valide.
     * @param ip - String a vérifier.
     * @return boolean
     */
    private boolean ipValide(String ip){
        if( !ip.isEmpty() ) {
            String table[]=ip.split("\\.");
            if( table.length==4 ){
                for( String tmp : table ){
                    if( !tmp.matches( "([0-9])|([0-9]{2})|([0-1][0-9]{2})|(2[0-5][0-4])" ) )
                        return false;
                }
                this.ip=ip;          
                return true;
            }
        }
        return false;
    }
    
    /**
     * Méthode pour crée un joueur et switcher sur le state suivant.
     * @param StateBasedGame
     */
    private void nouveauJoueur( StateBasedGame sbg ){
        if( userText.getText()!=null ){
            //Création du ListeEquipe, si le serveur est définie, la partie est en locale, sinon en reseau
            if( StaticData.serveur!=null){
                //Création du joueur locale.
                ListeEquipes.add( new Joueur( userText.getText() ) );//ajout du joueur
                //Switch vers la state menuMap./
                sbg.enterState(JavaTournament.menuMap);
            }
            else {
                ListeEquipes.add( new Joueur( userText.getText(), StaticData.getIdentifiant() ) );
                try {
                    ListeEquipes.getJoueur(StaticData.getIdentifiant()).envoieJoueur(StaticData.client.getOutputStream());
                } catch (IOException ex) {
                    System.err.println("MenuConnexion : impossible d'envoyer le joueur "+StaticData.getIdentifiant()+" "+ex.getMessage());
                }
                sbg.enterState(JavaTournament.menuChoix);
            }
        }
    }
}