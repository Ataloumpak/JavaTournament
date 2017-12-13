package javatournament.map;


import javatournament.data.Draw;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author kant1
 */
public class Ronce extends TypeCase {
    /**
     * Image de ronce
     */
    private static Image ronce;
    /**
     * Création de la texture "ronce" à partir de l'image "case.png"
     * @throws SlickException 
     */
    public Ronce() throws SlickException
    {
        super(null,true,0.90f,0.80f,0.90f);
        
        try
        {
            ronce = Draw.CASEMAP.getSubImage(60, 0, 30, 30);
           this.setSkin(ronce);
        }
        catch (Exception E)
        {
            System.err.println("Erreur de chargement de l'image des Ronces");
        }
    }
}
