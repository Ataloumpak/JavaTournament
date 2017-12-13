package javatournament.map;


import javatournament.data.Draw;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author pyarg
 */
public class Invisible extends TypeCase
{
    /**
     * Image invisible
     */
    private static Image invisible;
    /**
     * Création de la texture "invisible" à partir de l'image "case.png"
     * @throws SlickException 
     */        
    public Invisible() throws SlickException
    {
        super(null,false,1,1,1);
        try
        {
            invisible = Draw.CASEMAP.getSubImage(90,90, 30, 30);
            this.setSkin(invisible);
        }
        catch (Exception E)
        {
            System.err.println("Erreur de chargement de l'image invisible");
        }
    }
}

