/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javatournament.combat;

import javatournament.data.Draw;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author Alexandre
 */
public class Curseur
{
    /**
     * Position en X du Curseur.
     */
    private int posX;

    /**
     * Position en Y du Curseur.
     */
    private int posY;

    /**
     * Image de représentation du Curseur sur la carte.
     */
    private static Image img;

    /**
     * Constructeur du Curseur.
     * @param posX - Position initiale en X du Curseur.
     * @param posY - Position initiale en Y du Curseur.
     * @param ref - Référence de l'image du Curseur.
     * @throws SlickException
     */
    public Curseur(int posX, int posY, String ref) throws SlickException
    {
        this.posX = posX;
        this.posY = posY;
        try{
            this.img = new Image(ref);
        }
        catch (Exception e)
        {
            System.out.println("Impossible de crée le curseur.");
        }
    }
    /**
     * Constructeur du Curseur.
     * @param posX - Position initiale en X du Curseur.
     * @param posY - Position initiale en Y du Curseur.
     * @throws SlickException
     */
    public Curseur(int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;
        try{
            this.img = Draw.imageCurseur();            
        }
        catch (Exception e){
            e.getMessage();
        }
    }

    /**
     * Accesseur à l'image du Curseur.
     * @return - Image du Curseur.
     */
    public Image getImg()
    {
        return img;
    }

    /**
     * Accesseur à la position en X du Curseur.
     * @return - Position en X du Curseur.
     */
    public int getPosX()
    {
        return posX;
    }

    /**
     * Accesseur à la position en Y du Curseur.
     * @return - Position en Y du Curseur.
     */
    public int getPosY()
    {
        return posY;
    }

    /**
     * Modificateur de l'image du Curseur.
     * @param img - Nouvelle Image du Curseur.
     */
    public void setImg(Image img)
    {
        this.img = img;
    }

    /**
     * Modificateur de la position en X du Curseur.
     * @param posX - Nouvelle position en X du Curseur.
     */
    public void setPosX(int posX)
    {
        this.posX = posX;
    }

    /**
     * Modificateur de la position en Y du Curseur.
     * @param posY - Nouvelle position en Y du Curseur.
     */
    public void setPosY(int posY)
    {
        this.posY = posY;
    }
}
