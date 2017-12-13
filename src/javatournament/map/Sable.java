package javatournament.map;

import javatournament.data.Draw;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author kant1
 */
public class Sable extends TypeCase {
    /**
     * Image du sable
     */
    private static Image sable;
            
    /**
     * Création de la texture "sable" à partir de l'image "case.png"
     * @throws SlickException 
     */
    public Sable() throws SlickException
    {
        super(null,true,1,1,1);
        
        try
        {
            sable = Draw.CASEMAP.getSubImage(30, 0, 30, 30);
            this.setSkin(sable);
        }
        catch (Exception E)
        {
            System.err.println("Erreur de chargement de l'image du Sable");
        }
    }
}
