package javatournament.map;


import javatournament.data.Draw;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author kant1
 */
public class Glace extends TypeCase
{
    /**
     * Image de l'eau
     */
    private static Image glace;
           
    /**
     * Création de la texture "glace" à partir de l'image "case.png"
     * @throws SlickException 
     */
    public Glace() throws SlickException
    {
        super(null,true,1,1,1);
        try
        {
            glace = Draw.CASEMAP.getSubImage(90,0, 30, 30);
            this.setSkin(glace);
        }
        catch (Exception E)
        {
            System.err.println("Erreur de chargement de l'image de glace");
        }
    }
}
