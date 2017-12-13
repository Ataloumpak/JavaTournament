package javatournament.map;

import javatournament.data.Draw;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author kant1
 */
public class Rocher extends TypeCase {
    /**
     * Image de roche
     */
    private static Image rocher;
    /**
     * Création de la texture "rocher" à partir de l'image "case.png"
     * @throws SlickException 
     */        
    public Rocher() throws SlickException
    {
        super(null,true,1.20f,1.10f,1.10f);
        
        try
        {
            rocher = Draw.CASEMAP.getSubImage(0, 30, 30, 30);
            this.setSkin(rocher);
        }
        catch (Exception E)
        {
            System.err.println("Erreur de chargement de l'image de Rocher");
        }
    }
}