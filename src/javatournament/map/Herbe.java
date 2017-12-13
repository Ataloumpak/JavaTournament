package javatournament.map;


import javatournament.data.Draw;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author kant1
 */
public class Herbe extends TypeCase
{
    /**
     * Image de l'herbe
     */
    private static Image herbe;
            
    /**
     * Création de la texture "herbe" à partir de l'image "case.png"
     * @throws SlickException 
     */
    public Herbe() throws SlickException
    {
        super(null,true,1,1,1);
        try
        {
            herbe = Draw.CASEMAP.getSubImage(0, 0, 30, 30);
            this.setSkin(herbe);
        }
        catch (Exception E)
        {
            System.err.println("Erreur de chargement de l'image de l'herbe");
        }
    }
}
