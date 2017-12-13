package javatournament;

import javatournament.combat.PhaseJeu;
import javatournament.data.Draw;
import javatournament.data.Police;
import javatournament.menu.*;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Classe qui lance le jeu.
 * @author Pyarg
 *
 */

public class JavaTournament extends StateBasedGame {
    /**
    * identifiant de la state du MenuPrincipal
    */
    public static final int menuPrincipalState = 0;
    /**
    * identifiant de la state de PhaseJeu
    */
    public static final int CombatState = 1;
    /**
    * identifiant de la state du MenuPersonnage
    */
    public static final int menuPersonnageState = 2;
    /**
    * identifiant de la state du MenuRaccourci
    */
    public static final int menuRaccourciState = 3;
    /**
    * identifiant de la state de l'info
    */
    public static final int infoState = 4;
    /**
    * identifiant de la state du MenuReseau
    */
    public static final int menuReseau = 5;
    /**
    * identifiant de la state du MenuJoueur
    */
    public static final int menuJoueur = 6;
    /**
     * identifiant de la state menuMap.
     */
    public static final int menuMap = 7;
    /**
     * identifiant de la state menuConnexion.
     */
    public static final int menuConnexion = 8;
    /**
    * identifiant de la state du MenuChoix
    */
    public static final int menuChoix = 100;
    /**
    * identifiant de la state MenuEquipe
    */
    public static final int menuEquipe = 101;
    /**
    * identifiant de la state MenuEquipe
    */
    public static final int introState = 99;


    public JavaTournament()
    {
        super("JavaTournament"); //nom de la fenêtres
        this.addState(new Intro(introState));
        this.enterState(introState); //lancement du state du Menu Principal
        this.addState(new MenuPrincipal(menuPrincipalState)); //ajout du state du Menu Principal
        this.addState(new MenuPersonnage(menuPersonnageState));
        this.addState(new MenuRaccourci(menuRaccourciState));
        this.addState(new MenuInformations(infoState));
        this.addState(new PhaseJeu(CombatState));
        this.addState(new MenuChoixPersonnages(menuChoix));
        this.addState(new MenuEquipe(menuEquipe));
        this.addState(new MenuChoixJeu(menuReseau));
        this.addState(new MenuServeur(menuJoueur));
        this.addState(new MenuMap(menuMap));
        this.addState(new MenuConnexion(menuConnexion));
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {        
        Draw.imageParticule();        
        Police.POLICE();
        Draw.imageMap(); //on charge les images de la map.
    }

    public static void main(String[] args) throws SlickException
    {
        AppGameContainer fenetre = new AppGameContainer( new JavaTournament() );
        String[] icons = {"logo16.png", "logo24.png", "logo32.png"};
        fenetre.setIcons(icons);
        fenetre.setDisplayMode(1050, 600, false); //création de la fenetre
        fenetre.setShowFPS(true); //cache le taux de FPS
        fenetre.setUpdateOnlyWhenVisible( false );
        fenetre.start(); //Affichage de la fenetre et de son contenu
    }
}