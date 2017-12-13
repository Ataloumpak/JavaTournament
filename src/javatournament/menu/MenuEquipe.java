package javatournament.menu;

import javatournament.JavaTournament;
import javatournament.combat.ListeEquipes;
import javatournament.data.Draw;
import javatournament.data.Police;
import javatournament.data.Son;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Menu qui récaptiule les equipes de chaques joueurs.
 * @author Quentin, Pyarg
 */
public class MenuEquipe extends BasicGameState
{
    /**
     * Identifiant de la class MenuEquipe.
     */
    private int stateID = -1;

    /**
     * Police des description des personnages.
     */
    private static UnicodeFont desc;

    /**
     * Police d'indication des touche.
     */
    private static UnicodeFont indicTTF;  
    
    public MenuEquipe(int ID)
    {
        this.stateID = ID;
    }

    @Override
    public int getID()
    {
        return this.stateID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame sbg) throws SlickException{
        Draw.menuEquipe();
    }

    @Override
    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage( Draw.FONDMENU, 0, 0);
        if( desc==null ) //Police des descriptions
            desc = Police.UNICODEFONT( Police.CAVIARDREAM[Police.BOLD], 20);        
        if( indicTTF==null ) //Police des indications        
            indicTTF = Police.UNICODEFONT( Police.AVERIA[Police.BOLD], 20);
        g.drawImage( Draw.MENUEQUIPE,25 ,-10 );
        g.drawImage( Draw.MENUEQUIPE,625 ,-10 );
        
        try{
            indicTTF.drawString( (container.getWidth()/2)-(indicTTF.getWidth("Appuyer sur entrée")/2), 540, "Appuyer sur entrée" );
            indicTTF.drawString( (container.getWidth()/2)-(indicTTF.getWidth("pour aller en combat")/2), 560, "pour aller en combat" );
        
            desc.drawString(60, 30, ListeEquipes.getJoueur(0).getNom(),Color.black);
            if( ListeEquipes.nbrEquipes()==2)
                desc.drawString(660, 30, ListeEquipes.getJoueur(1).getNom(),Color.black);
        }
        catch(Exception e){
            System.err.println("MenuEquipe : erreur OpenGL. "+e.getMessage());
        } 
        
        for( int i=0; i<ListeEquipes.nbrEquipes(); i++){
            int X= i==0? 202 : 829;
            int txtX= i==0? 100 : 903;
            for( int j=0; j<ListeEquipes.getJoueur(i).nbrPersonnage(); j++){
                try{
                    desc.drawString( txtX, 120+(j*110), ListeEquipes.getJoueur(i).getPersonnage(j).getNom(), Color.black);
                }catch(Exception e){
                    System.err.println("MenuEquipe : erreur OpenGL. "+e.getMessage());
                } 
                g.drawImage( ListeEquipes.getJoueur(i).getCadre(), X, 101+(j*110) );
                g.drawAnimation( ListeEquipes.getJoueur(i).getPersonnage(j).getAnimPersonnage(0).getAnimation(0), X+1 , 100+(j*110) );
            }
        }
        try{
            desc.drawString((container.getWidth()/2)-desc.getWidth("VS")/2, ((container.getHeight()/2)-desc.getHeight("VS")/2), "VS");
        }catch(Exception e){
            System.err.println("MenuEquipe : erreur OpenGL. "+e.getMessage());
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException
    {
        Input in = container.getInput();
        
        if(container.getInput().isKeyPressed(Input.KEY_ESCAPE))
        {
            in.clearKeyPressedRecord();
            desc=null;
            indicTTF=null;
            sbg.enterState(JavaTournament.menuPrincipalState);
        }
        
        if( container.getInput().isKeyPressed(Input.KEY_ENTER) && ListeEquipes.nbrEquipes()==ListeEquipes.nbrEquipesMax())
        {
            in.clearKeyPressedRecord();
            Son.MENU_PRINCIPAL.stop();
            Son.COMBAT.play();
            sbg.enterState(JavaTournament.CombatState);
        }
        container.getInput().clearKeyPressedRecord();
    }
}