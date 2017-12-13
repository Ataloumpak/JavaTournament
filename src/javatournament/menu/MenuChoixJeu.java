package javatournament.menu;

import javatournament.JavaTournament;
import javatournament.combat.Joueur;
import javatournament.combat.ListeEquipes;
import javatournament.combat.TypeJeu;
import javatournament.data.Draw;
import javatournament.data.Police;
import javatournament.emitters.Emitter;
import javatournament.personnage.*;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Menu qui permet de choisir si le jeu est en locale ou en réseau.
 * Si on choisie locale, la state nous permet aussi de crée 2 joueurs.
 * @author pyarg
 */
public class MenuChoixJeu extends BasicGameState{
    /**
     * ID de la state
     */
    private int stateID = -1;
    /**
     * Police des textes
     */
    private UnicodeFont font;
    /**
     * Tableau des personnages à afficher.
     */
    private Personnage[] pers=new Personnage[4];
    /**
     * abscisse des personnages afficher.
     */
    private float abscissePerso[]=new float[4];
    /**
     * champs texte pour le nom des joueur.
     */
    private TextField userText;
    /**
     * Particule de pluie.
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
    public MenuChoixJeu( int stateID ){
        this.stateID = stateID;
    }
 
    /**
     * Accesseur de l'ID de la state
     * @return int
     */
    @Override
    public int getID(){
        return stateID;
    }
 
     @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        //initialisation des personnages.
        pers[0]=new Clerc();
        pers[1]=new Guerrier();
        pers[2]=new Mage();
        pers[3]=new Voleur();
        for(int i=0;i<abscissePerso.length;i++ )
            abscissePerso[i]=-pers[i].getSkin(0).getWidth()*i-150*i;
        font=Police.UNICODEFONT( Police.AVERIA[Police.BOLD], 25, java.awt.Color.WHITE);
        userText=new TextField( gc, font, (gc.getWidth()/2)-150, (gc.getHeight()/2)-20, 300, 40);        
        userText.setMaxLength(15);//Nombre de caractères maximum dans le champs texte 
        userText.setFocus(false);
        
        rainEmitter = new Emitter(0, 0, 7500, 50, 0, gc.getWidth()*2, Emitter.RAIN_EMITTING, 30, true, Draw.RAIN_PART);
        Draw.initChoix(choix);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        
        g.setColor(Color.white);
        
        //on affiche le fond.
        Draw.FONDMENU.draw(0, 0, Draw.filter);
        //si le textArea n'a pas le focus on affiche le menu
        if(!userText.hasFocus() ){
            Draw.menu(gc, g, font, choix, "Jouer en local.", "Jouer en réseau.");
        }
        //si le textArea a le focus on l'affiche
        else {
            font.drawString((gc.getWidth()/2)-(font.getWidth("Nom du joueur "+(ListeEquipes.nbrEquipes()+1)+" : ")/2), (gc.getHeight()/2)-50, "Nom du joueur "+(ListeEquipes.nbrEquipes()+1)+" : ");
            userText.render(gc, g);
        }        
        //on affiche les personnages
        for(int i=0;i<pers.length;i++)
            g.drawAnimation( pers[i].getAnimPersonnage(0).getAnimation(3), abscissePerso[i], gc.getHeight()-pers[0].getSkin(0).getHeight());
        
        rainEmitter.draw(g);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        //On anime la pluie
        rainEmitter.animate(gc, delta);
        //Decalage des personnages
        for(int i=0;i<pers.length;i++){
            if(abscissePerso[i]>=gc.getWidth()){
                abscissePerso[i]=-pers[0].getSkin(0).getWidth();
            }
            else
                abscissePerso[i]+=0.08*delta;
        }        
        //Si le textArea(la zone où on tappe le nom des peronnages) n'est pas focus
        if( !userText.hasFocus() ){
            //Si on appuie sur "L" on crée une partie en locale ou si on selectionne le premier menu et qu'on appuie sur entrer.
            if( gc.getInput().isKeyPressed( Input.KEY_L ) || (gc.getInput().isKeyDown(Input.KEY_ENTER) && choix[0])){
                //INITIALISATION DES JOUEURS
                ListeEquipes.equipes(2, TypeJeu.LOCAL);
                //on affiche le textArea      
                userText.setFocus(true);    
            }
            //si on appuie sur "R" on switch vers le menuJoueur ou si on selectionne le deuxeme menu et qu'on appuie sur entrer.
            else if( gc.getInput().isKeyPressed( Input.KEY_R) || (gc.getInput().isKeyDown(Input.KEY_ENTER) && choix[1]) ){
                //On vide l'enregistrement des touches.
                gc.getInput().clearKeyPressedRecord();
                sbg.enterState(JavaTournament.menuJoueur);
            }
        }
        //si on appuie sur échape on retourne au menuPrincipale
        if( gc.getInput().isKeyPressed( Input.KEY_ESCAPE) ){
            //On vide l'enregistrement des touches.
            gc.getInput().clearKeyPressedRecord();
            sbg.enterState(JavaTournament.menuPrincipalState); //lancement du state du Menu Combat
            userText.setFocus(false);
        }//si le text area est active, si il n'est pas vide, et si on appuie sur entrer, on ajoute un personnage
        else if( userText.hasFocus() && gc.getInput().isKeyPressed( Input.KEY_ENTER ) && !userText.getText().isEmpty() ) {
            ListeEquipes.add( new Joueur( userText.getText() ) );
            userText.setText("");
        }
        //si le textArea est activer et si on a 2joueurs.
        else if( userText.hasFocus() && !ListeEquipes.isEmpty() && ListeEquipes.nbrEquipes()==2) {
            userText.setFocus(false);
            //On vide l'enregistrement des touches.
            gc.getInput().clearKeyPressedRecord();
            sbg.enterState(JavaTournament.menuMap); //lancement du state du Menu Combat
        }
        //Effet d'eclair
        Draw.eclair(dejaEclair);
    }
}