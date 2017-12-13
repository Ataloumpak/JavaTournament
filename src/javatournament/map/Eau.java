package javatournament.map;

import javatournament.data.Draw;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author kant1
 */
public class Eau extends TypeCase{
    /**
     * Image de l'eau
     */
    private static Image eau;
          
    /**
     * Création de la texture "eau" à partir de l'image "case.png"
     * @throws SlickException 
     */
    public Eau() throws SlickException
    {
        super(null,false,1,1,1);
        try
        {
            eau = Draw.CASEMAP.getSubImage(60, 30, 30, 30);
            this.setSkin(eau);
        }
        catch (Exception E)
        {
            System.err.println("Erreur de chargement de l'image de l'Eau");
        }
    }
    
}
