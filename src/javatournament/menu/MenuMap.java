package javatournament.menu;

import java.io.File;
import javatournament.JavaTournament;
import javatournament.combat.ListeEquipes;
import javatournament.combat.StaticData;
import javatournament.combat.TypeJeu;
import javatournament.data.Draw;
import javatournament.data.Police;
import javatournament.map.Map;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Menu qui permet de choisir une map.
 * @author quentin
 */
public class MenuMap extends BasicGameState {

    /**
     * Attribut static identifiant la state.
     */
    private int stateID = -1;
    
    /**
     * Tableau contenant tout les fichiers map.
     */
    private File[] files = null;
    /**
     * Police des descriptions
     */
    private UnicodeFont desc;
    /**
     * Police du titre
     */
    private UnicodeFont titre;
    
    
    public MenuMap( int stateID ) {
       this.stateID = stateID;
    }

    /**
     * Méthode permetant d'explorer un dossier et de stocker le contenu(des fichiers) dans "files"
     * @param directoryPath
     * @return File[]
     */
    public void listFiles(String directoryPath)
    {
        File directoryToScan = new File(directoryPath);
        files = directoryToScan.listFiles(); 
    }
    
    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
        //liste tout les fichiers xml du dossier passer en parametre
        listFiles("maps");               
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, org.newdawn.slick.Graphics g) throws SlickException {
        g.drawImage( Draw.FONDMENU, 0, 0);
        if(desc==null)
            desc = Police.UNICODEFONT( Police.CAVIARDREAM[Police.BOLD], 20,java.awt.Color.BLACK );
        if(titre==null)
            titre = Police.UNICODEFONT(Police.CAVIARDREAM[Police.BOLD], 40,java.awt.Color.BLACK );
        
        desc.drawString( 415, 510, "Appuyez sur Entrée.");
        titre.drawString(310, 70, "Choix de la Carte");
        
        
        //fleche gauche
        g.drawImage(Draw.MENUCHOIX[5], 130, 460);
        //fleche droite
        g.drawImage(Draw.MENUCHOIX[4], 740, 460);
        
        g.setColor(Color.black);
        g.fillRect(305f, 165f, 395f, 285f);
                
        //Modification de l'echelle
        g.scale(0.5f,0.5f);

        // parcourir les fichiers et crée une vignette des maps
        for(int k=0;k<files.length;k++)
        {
            //On recupere le chemin du fichier
            String path = files[k].getAbsolutePath();

            //on initialise la map
            Map m = new Map(path);
            
            //AFFICHE LA MAP
            for(int i = 0; i < m.getLargeurMap(); i++)
            {
                for(int j = 0; j <m.getHauteurMap(); j++)
                {
                    //On dessine une case de la map
                    g.drawImage(m.getTypeCase(i, j).getSkin(),-200 + (i*m.tailleCaseMap)+m.centrageXMap+k*800,(j*m.tailleCaseMap)+m.centrageYMap+300);
                }
            }
            //FIN AFFICHAGE MAP
        }
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
    {
        //Si on appuie sur la fleche droite
        if(gc.getInput().isKeyPressed( Input.KEY_RIGHT ) )
        {
            //On echange la position des fichiers pour changer la Map séléctionné
            for(int i=0; i<files.length; i++)
            {
                if(i!=files.length-1)
                {
                    File f = files[i];
                    files[i] = files[i+1];
                    files[i+1] = f;
                }
            }
        }
        
        //Si on appuie sur la fleche gauche
        if(gc.getInput().isKeyPressed( Input.KEY_LEFT ) )
        {
            //On echange la position des fichiers pour changer la Map séléctionné
            for(int i=files.length-1; i>0; i--)
            {
                if(i!=0)
                {
                    File f = files[i];
                    files[i] = files[i-1];
                    files[i-1] = f;
                }
            }
        }
        
        //Si on appuie sur entrer, on selectionnne la Map
        if( gc.getInput().isKeyPressed( Input.KEY_ENTER ) ) {
            if(StaticData.serveur!=null || ListeEquipes.getTypeJeu().equals(TypeJeu.LOCAL)) {
                StaticData.map = new Map(files[1].getAbsolutePath());
            }
            desc=null;
            titre=null;
            sbg.enterState(JavaTournament.menuChoix); //lancement 
        }
        else if( gc.getInput().isKeyPressed( Input.KEY_ESCAPE) ){            
            desc=null;
            titre=null;
            sbg.enterState(JavaTournament.menuPrincipalState); //lancement du state du Menu Combat
        }
    }
}
