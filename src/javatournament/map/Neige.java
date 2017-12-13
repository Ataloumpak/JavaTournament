package javatournament.map;

import javatournament.data.Draw;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author kant1
 */
public class Neige extends TypeCase {
    /**
     * Image de la Neige
     */
    private static Image neige;
            
    /**
     * Création de la texture "Neige" à partir de l'image "case.png"
     * @throws SlickException 
     */
    public Neige() throws SlickException
    {
        super(null,true,1,1,1);
        
        try
        {
            neige = Draw.CASEMAP.getSubImage(90,30,30,30);
            this.setSkin(neige);
        }
        catch (Exception E)
        {
            System.err.println("Erreur de chargement de l'image de neige");
        }
    }
}
