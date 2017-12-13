package javatournament.menu;

import javatournament.JavaTournament;
import javatournament.combat.StaticData;
import javatournament.data.Draw;
import javatournament.data.Police;
import javatournament.data.Son;
import javatournament.emitters.Emitter;
import javatournament.personnage.Mage;
import javatournament.personnage.SkinAnimation;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Menu principale du jeu.
 * @author Pyarg
 */

public class MenuPrincipal extends BasicGameState {
    /**
     * Identifiant de la class loadOgg.
     */
    int stateID = 0;

    /**
     * Image de fond.
     */
    private Image fond;

    /**
     * Animation du personnage dans le "menu".
     */
    private int animPersonnage=0;

    /**
     * Animation de l'eau.
     */
    private SkinAnimation eau;

    /**
     * Animation du forgeron.
     */
    private SkinAnimation forgeron;

    /**
     * Animation de la case servant de fenetre a un batiment, ressemblant a une case utilisee dans le celebre jeu "mario bross".
     */
    private SkinAnimation bonusMario;

    /**
     * Animation de la voyante.
     */
    private SkinAnimation voyante;

    /**
     * Personnage se déplaçant dans le "menu".
     */
    private Mage perso;

    /**
     * Permet d'eviter que le personnage ne monte sur les batiments.
     */
    private boolean stop=false;
    /**
     * boolean qui definie si on remet le personnage à sa position par defaut.
     * Utiliser lorsque on revient sur la state.
     */
    private boolean posPerso=true;
    /**
     * Particule de pluie
     */
    private Emitter clouds;
    
    /**
     * Constructeurs du menu principale
     * @param stateID 
     */
    public MenuPrincipal( int stateID ) {
        this.stateID = stateID;
    }
    /**
     * Getter de l'identifiant de la state
     * @return 
     */
    @Override
    public int getID() {
        return stateID;
    }
 
 
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException { 
        Son.loadOgg();
        Draw.ImageMenuPrincpial(); 
        fond= Draw.FOND;
        perso=new Mage();      
        eau = Draw.ACCEUIL[0];
        forgeron = Draw.ACCEUIL[1];
        bonusMario = Draw.ACCEUIL[2];
        voyante = Draw.ACCEUIL[3];    
        clouds = new Emitter(0, -150, 1000, 20000, 50, gc.getWidth(), Emitter.CLOUD_EMITTING, 2, true, Draw.CLOUD);
    }
 
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics graph) throws SlickException {
        fond.draw(0,0); //Affichage de l'image de fond
        if( posPerso ){            
            perso.setPosX(390);
            perso.setPosY(390);
            this.animPersonnage=0;
            posPerso=false;
        }
        
        graph.drawAnimation(eau.getAnimation(0), 155, 150);
        graph.drawAnimation(forgeron.getAnimation(0), 650, 320);
        graph.drawAnimation(bonusMario.getAnimation(0), 30, 400);
        graph.drawAnimation(voyante.getAnimation(0), 890, 320);
        
        if(!stop)
            graph.drawAnimation(perso.getAnimPersonnage(0).getAnimation(animPersonnage), perso.getPosX(), perso.getPosY());
        else
            graph.drawImage(perso.getAnimPersonnage(0).getAnimation(animPersonnage).getImage(0), perso.getPosX(), perso.getPosY());
        
        clouds.draw(graph);
    }
 
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        //int mouseX = gc.getInput().getMouseX();
        //int mouseY = gc.getInput().getMouseY();
        Input in = gc.getInput();
        
        clouds.animate(gc, delta);
        Son.MENU_PRINCIPAL.gestion(gc);
        
        if(in.isKeyDown(Input.KEY_C) || (perso.getPosX()>=300 && perso.getPosX()<=420 && perso.getPosY()==210 ) /* || ( gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON) &&
                ( mouseX >= menuX-(combat.getWidth()/2) && mouseX <= menuX-(combat.getWidth()/2)+combat.getWidth() ) &&
                ( mouseY >= menuY && mouseY <= menuY+combat.getHeight()) ) */) //SI on appui sur C ou si on clique sur l'image combat
        {
           StaticData.clear();
           in.clearKeyPressedRecord();
           sbg.getState(JavaTournament.menuReseau).init(gc, sbg);
           sbg.enterState(JavaTournament.menuReseau); //lancement du state du Menu Combat
           posPerso=true;
        }
        else if(in.isKeyDown(Input.KEY_P) || (perso.getPosX()>=60 && perso.getPosX()<=90 && perso.getPosY()==420 )/* || ( gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON) &&
                ( mouseX >= menuX-(personnage.getWidth()/2) && mouseX <= menuX-(personnage.getWidth()/2)+personnage.getWidth() ) &&
                ( mouseY >= menuY+100 && mouseY <= menuY+100+personnage.getHeight()) )*/ ) //SI on appui sur P ou si on clique sur l'image personnage
        {
            in.clearKeyPressedRecord();
            Son.MENU_PRINCIPAL.stop();
            Son.DEFAITE.play();
            sbg.enterState(JavaTournament.menuPersonnageState); //lancement du state du Menu Personnage
            posPerso=true;
        }
        else if(in.isKeyDown(Input.KEY_I) || (perso.getPosX()>=890 && perso.getPosX()<=960 && perso.getPosY()==360 ))
        {
            in.clearKeyPressedRecord();
            Son.MENU_PRINCIPAL.stop();
            Son.CREDITS.play();
            sbg.enterState(JavaTournament.infoState); //lancement du state du Menu Raccourci;
            posPerso=true;
        }
        else if(in.isKeyDown(Input.KEY_Q) || perso.getPosY()==570/* || ( gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON) &&
                (mouseX >= menuX-(quitter.getWidth()/2) && mouseX <= menuX-(quitter.getWidth()/2)+quitter.getWidth()) &&
                (mouseY >= menuY+300 && mouseY <= menuY+300+quitter.getHeight()) )*/ ) //SI on appui sur Q ou si on clique sur l'image quitter
            gc.exit(); //On quitte le programme
       else if(in.isKeyDown(Input.KEY_R) || (perso.getPosX()>=630 && perso.getPosX()<=690 && perso.getPosY()==360 ) ) //SI on appui sur R
       {
            in.clearKeyPressedRecord();
            sbg.enterState(JavaTournament.menuRaccourciState); //lancement du state d'informations
            posPerso=true;
       }
    
       else if(in.isKeyPressed(Input.KEY_UP) )
        {
            this.animPersonnage = 2;
            if((perso.getPosX() >= 0 && perso.getPosY() >= 450 && perso.getPosX() <= gc.getWidth() && perso.getPosY() <= gc.getHeight()) || (perso.getPosX() > 210 && perso.getPosY() > 210 && perso.getPosX() <= 580 && perso.getPosY() <= 450) || (perso.getPosX() >= 580 && perso.getPosY() >= 360))
            {
                perso.setPosY(perso.getPosY()-30);
                stop=false;
            }
        }
        else if(in.isKeyPressed(Input.KEY_DOWN))
        {
            this.animPersonnage = 0;
            if(perso.getPosY()<550)
            {
                perso.setPosY(perso.getPosY()+30);
                stop=false;
            }
        }
        else if(in.isKeyPressed(Input.KEY_RIGHT))
        {
            this.animPersonnage = 3;
            if((perso.getPosX()<1020 && perso.getPosY()>300) || (perso.getPosX()<568 && perso.getPosY()>200))
            {
                perso.setPosX(perso.getPosX()+30);
                stop=false;
            }
        }
        else if(in.isKeyPressed(Input.KEY_LEFT))
        {
            this.animPersonnage = 1;
            if(perso.getPosX()>210 || (perso.getPosX()>0 && perso.getPosY()>=420) )
            {
                perso.setPosX(perso.getPosX()-30);
                stop=false;
            }
        }
        else
            stop=true;
    }
}
