package javatournament.menu;

import javatournament.JavaTournament;
import javatournament.data.Draw;
import javatournament.data.Police;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

 
/**
 * Menu pour afficher les raccourci.
 * @author Pyarg, kant1
 */

public class MenuRaccourci extends BasicGameState
{
    /**
     * ID de la state
     */
    int stateID = 3;
    
    /**
     * Police pour afficher les textes des touche.
     */
    private UnicodeFont police;
    
    /**
     * Police pour afficher le titre.
     */
    private UnicodeFont titre;
    
    /**
     * Constructeur de la state
     * @param stateID 
     */
    public MenuRaccourci( int stateID ) {
        this.stateID = stateID;
    }
 
    /**
     * Accesseur de l'ID de la state
     * @return 
     */
    @Override
    public int getID() {
        return stateID;
    }
 
     @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics graph) throws SlickException {
        graph.drawImage( Draw.FONDMENU, 0, 0);          
        if(this.police==null) {
            this.police = Police.UNICODEFONT( Police.CAVIARDREAM[Police.BOLD], 17);
            this.titre = Police.UNICODEFONT( Police.AVERIA[Police.BOLD], 30);
        }
        try{                        
            this.titre.drawString( (gc.getWidth()/2)-titre.getWidth("Raccourcis utiles en combat.")/2, 20,"Raccourcis utiles en combat.");
            
            //70 d'ecart en vertical
            //Dessin des touches fleches
            Draw.dessinerTouche(graph, 55, 100, this.police, Color.red, "<-");
            Draw.dessinerTouche(graph, 100, 100, this.police, Color.red, "v");
            Draw.dessinerTouche(graph, 145, 100, this.police, Color.red, "->");
            Draw.dessinerTouche(graph, 100, 55, this.police, Color.red, "^");
            
            this.police.drawString(300, 110,"Mouvement");
            
            //Dessin de la touche mouvement
            Draw.dessinerTouche(graph, 100, 170, this.police, new Color(17,68,221), "M");
            this.police.drawString(300, 180,"Entrer/Sortir Mode Mouvement");
            
            //Dessin de la touche attaque
            Draw.dessinerTouche(graph, 100, 240, this.police, Color.cyan, "A");
            this.police.drawString(300, 250,"Entrer/Sortir Mode Attaque");
            
            //Dessin de la touche sort
            Draw.dessinerTouche(graph, 100, 310, this.police, new Color(100, 100, 100), "S");
            this.police.drawString(300, 320,"Appliquer le Sort");
            
            //Dessin de la touche afficher point de vie
            Draw.dessinerTouche(graph, 100, 380, this.police, new Color(51, 255, 0), "Alt");
            this.police.drawString(300, 400,"Afficher Points de Vie de tous les personnages.");
            
            //Dessin des touches de sort
            Draw.dessinerTouche(graph, 55, 450, this.police, new Color(192,192,192), "1");
            Draw.dessinerTouche(graph, 100, 450, this.police, new Color(192,192,192), "2");
            Draw.dessinerTouche(graph, 145, 450, this.police, new Color(192,192,192), "3");
            Draw.dessinerTouche(graph, 190, 450, this.police, new Color(192,192,192), "4");
            this.police.drawString(300, 470,"Liste des sorts");
            
            //Dessin de la touche quitter
            Draw.dessinerTouche(graph, 100, 520, this.police, Color.pink, "Esc");
            this.police.drawString(300, 540,"Quitter");
            
            //Dessin de la touche tchat
            Draw.dessinerTouche(graph, 600, 100, this.police, new Color(153, 255, 0), "T");
            this.police.drawString(800, 110,"Entrer/Sortir Tchat");
            
            //Dessin de la touche espace
            graph.setColor(Color.black);
            graph.fillRoundRect(602, 172, 100, 40, 10);
            graph.setColor(new Color(255, 255, 102));
            graph.fillRoundRect(600, 170, 100, 40, 10);
            this.police.drawString(620, 180, "Espace", Color.black);
            this.police.drawString(800, 180, "Passer son tour"); 
            
            //Dessin de la d'information sur les sors
            Draw.dessinerTouche(graph, 600, 240, this.police, Color.magenta, "Tab");
            this.police.drawString(800, 250,"Affiche les sorts du personnage.");         
        }
        catch(Exception e){}
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        if( gc.getInput().isKeyDown(Input.KEY_ESCAPE) ) { //Si on appui sur Echape
            this.police=null;
            sbg.enterState(JavaTournament.menuPrincipalState); //Retour au state du Menu Principal                   
        }
    }
 
}
