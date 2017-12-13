package javatournament.menu;

import java.awt.Color;
import javatournament.JavaTournament;
import javatournament.data.Draw;
import javatournament.data.Police;
import javatournament.data.Son;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Xeheros
 *
 */

public class Intro extends BasicGameState
{
    /**
     * Identifiant de la state.
     */
    private int stateID;
    
    private String[] lignes;
    
    private UnicodeFont font;

    public Intro( int stateID )
    {
        this.stateID = stateID;
    }

    @Override
    public int getID() 
    {
        return stateID;
    }


    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
    {
        Draw.imageIntro();
        
        Son.INTRO.play();
        
        font = Police.UNICODEFONT(Police.AVERIA[Police.BOLD_ITALIC], 16, Color.white);
        
        this.lignes = new String[14];
        this.lignes[0] = "Dans un monde pas si différent du nôtre, la nation de Runz organisait chaque année un grand tournoi appelé,";
        this.lignes[1] = "le « Java Tournament » !";
        this.lignes[2] = "\nUniversellement connu, le Java Tournament est devenu l'emblème de la paix grandissante qui règne encore aujourd’hui";
        this.lignes[3] = "\nsur Yxcehmel...";
        this.lignes[4] = "\n\nPlusieurs centaines d'années ce sont écoulées depuis le premier Java Tournament, nous sommes dorénavant dans l'ère";
        this.lignes[5] = "\n\ndu Vost, les générations se sont succédées, le grand Khü trôna pendant près d'un siècle, après avoir élevé sa";
        this.lignes[6] = "\n\nnation au niveau de première puissance, nul Homme ne l'avait contredit depuis son arrivée au pouvoir...";
        this.lignes[7] = "\n\nle fils du grand Khü, doit succéder à son père dans quelques jours...";
        this.lignes[8] = "\n\n\nLors de son accès au trône, Tuhane le fils du grand Khü voulut faire honneur à son père, il décida alors";
        this.lignes[9] = "\n\n\nde revendiquer le « Java Tournament », qui se déroulait à Paryx depuis pas moins d'un millénaire... Il envoya une";
        this.lignes[10] = "\n\n\nmissive au roi, afin de faire part de sa requête. Il fut arrangé que dorénavant la nation vainqueur devra héberger";
        this.lignes[11] = "\n\n\nl'évènement l'année suivante...";
        this.lignes[12] = "\n\n\n\nLe roi Tuhane envoya alors des émissaires dans chaque grande ville du royaume de Täh.";
        this.lignes[13] = "\n\n\n\n\nVotre nouvelle vie ne fait que commencer..., elle va vous en faire perdre la tête (ou juste vos cheveux...).";
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics graph) throws SlickException 
    {
        Draw.TITRE.draw(60, 100, 1);
        Draw.LOGO.draw(700, 0, 1);
        
        drawHistoire(graph);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
    {
        Son.INTRO.gestion(gc);
        
        if( gc.getInput().isKeyPressed(Input.KEY_ESCAPE) || gc.getInput().isKeyPressed(Input.KEY_ENTER) || gc.getInput().isKeyPressed(Input.KEY_SPACE) )
        {
            if(Son.INTRO != null)
                Son.INTRO.stop();
            Son.MENU_PRINCIPAL.play();
            sbg.enterState(JavaTournament.menuPrincipalState);
        }
            
        gc.getInput().clearKeyPressedRecord();
    }
    
    public void drawHistoire(Graphics graph)
    {
        for(int i = 0 ; i < this.lignes.length ; i++)
        {
            font.drawString(10, 250+i*17, this.lignes[i]);
        }
    }
}
