package javatournament.map;

import javatournament.data.Draw;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author kant1
 */
public class Magma extends TypeCase {
    /**
     * Image du Magma
     */
    private static Image magma;
        
    /**
     * Création de la texture "Magma" à partir de l'image "case.png"
     * @throws SlickException 
     */
    public Magma() throws SlickException
    {
        super(null,false,1,1,1);
        
        try
        {
            magma = Draw.CASEMAP.getSubImage(30, 30, 30, 30);
            this.setSkin(magma);
        }
        catch (Exception E)
        {
            System.err.println("Erreur de chargement de l'image du Magma");
        }
    }

}

