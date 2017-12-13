/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javatournament.personnage;

import java.io.InputStream;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Animation;
/**
 *
 * @author pyarg
 */
public class SkinAnimation {

    /**
     * image de l'apparence du personnage
     */
    //private Image skin;
    /**
     * nombre d'images en hauteur
     */
    private int nbrH;
    /**
     * nombre d'images en largeur
     */
    private int nbrL;
    /**
     * vitesse de l'animation en ms
     */
    private static int vitesse=200;
    /**
     * création du tableau d'animation initialisé à 4
     */
    private Animation[] animSkin=new Animation[4];
    /**
     * animation du personnage vue de face
     */
    private Animation face = new Animation(true);
    /**
     * animation du personage vue de gauche
     */
    private Animation gauche = new Animation(true);
    /**
     * animation du personnage vue de dos
     */
    private Animation dos = new Animation(true);
    /**
     * animation du personnage vue de droite
     */
    private Animation droite = new Animation(true);
    
    /**
     * Constructeur d'une animation
     * @param img - reference de l'image
     * @param nbrH - nombre d'images en hauteur
     * @param nbrL - nombre d'images en largeur
     * @throws SlickException
     */
    public SkinAnimation(String img,int nbrH, int nbrL) throws SlickException
    {
        Image skin = new Image(img);
        int Y=skin.getHeight()/nbrH; //calcul de la hauteur de chaque image en fonction de la hauteur de l'image et du nombre d'image par colonne
        int X=skin.getWidth()/nbrL; //calcul de la largeur de chaque image en fonction de la largeur de l'image et du nombre d'image par ligne
        
        int tmpX=0; //initialisation de la variable temporaire de X

        while(tmpX<X*nbrL) //si tmpX est est inférieur à Y*nombre d'image sur la ligne 
        {
            face.addFrame(skin.getSubImage(tmpX, 0, X, Y),vitesse); //ajout de la frame à l'animation
            gauche.addFrame(skin.getSubImage(tmpX, Y, X, Y),vitesse); //ajout de la frame à l'animation
            droite.addFrame(skin.getSubImage(tmpX, Y*3, X, Y),vitesse); //ajout de la frame à l'animation 
            dos.addFrame(skin.getSubImage(tmpX, Y*2, X, Y),vitesse); //ajout de la frame à l'animation    
            
            tmpX+=X; //incrément de la largeur de la frame
        }
        animSkin[0]=face;
        animSkin[1]=gauche;
        animSkin[2]=droite;
        animSkin[3]=dos;
    }
    /**
     * Constructeur d'une animation
     * @param input - InputStream de l'image.
     * @param nom - Nom de l'image.
     * @param nbrH - nombre d'images en hauteur
     * @param nbrL - nombre d'images en largeur
     * @throws SlickException
     */
    public SkinAnimation( InputStream input, String nom, int nbrH, int nbrL) throws SlickException
    {
        Image skin = new Image(input, nom, false);
        int Y=skin.getHeight()/nbrH; //calcul de la hauteur de chaque image en fonction de la hauteur de l'image et du nombre d'image par colonne
        int X=skin.getWidth()/nbrL; //calcul de la largeur de chaque image en fonction de la largeur de l'image et du nombre d'image par ligne
        
        int tmpX=0; //initialisation de la variable temporaire de X

        while(tmpX<X*nbrL) //si tmpX est est inférieur à Y*nombre d'image sur la ligne 
        {
            face.addFrame(skin.getSubImage(tmpX, 0, X, Y),vitesse); //ajout de la frame à l'animation
            gauche.addFrame(skin.getSubImage(tmpX, Y, X, Y),vitesse); //ajout de la frame à l'animation
            droite.addFrame(skin.getSubImage(tmpX, Y*3, X, Y),vitesse); //ajout de la frame à l'animation 
            dos.addFrame(skin.getSubImage(tmpX, Y*2, X, Y),vitesse); //ajout de la frame à l'animation    
            
            tmpX+=X; //incrément de la largeur de la frame
        }
        animSkin[0]=face;
        animSkin[1]=gauche;
        animSkin[2]=droite;
        animSkin[3]=dos;
    }
    /**
     * Constructeur d'une animation
     * @param img - Image à animer.
     * @param nbrH - nombre d'images en hauteur
     * @param nbrL - nombre d'images en largeur
     * @throws SlickException
     */
    public SkinAnimation( Image img, int nbrH, int nbrL) throws SlickException
    {
        Image skin = img;
        int Y=skin.getHeight()/nbrH; //calcul de la hauteur de chaque image en fonction de la hauteur de l'image et du nombre d'image par colonne
        int X=skin.getWidth()/nbrL; //calcul de la largeur de chaque image en fonction de la largeur de l'image et du nombre d'image par ligne
        
        int tmpX=0; //initialisation de la variable temporaire de X

        while(tmpX<X*nbrL) //si tmpX est est inférieur à Y*nombre d'image sur la ligne 
        {
            face.addFrame(skin.getSubImage(tmpX, 0, X, Y),vitesse); //ajout de la frame à l'animation
            gauche.addFrame(skin.getSubImage(tmpX, Y, X, Y),vitesse); //ajout de la frame à l'animation
            droite.addFrame(skin.getSubImage(tmpX, Y*3, X, Y),vitesse); //ajout de la frame à l'animation 
            dos.addFrame(skin.getSubImage(tmpX, Y*2, X, Y),vitesse); //ajout de la frame à l'animation    
            
            tmpX+=X; //incrément de la largeur de la frame
        }
        animSkin[0]=face;
        animSkin[1]=gauche;
        animSkin[2]=droite;
        animSkin[3]=dos;
    }
    /**
     * accesseur au tableau des 4 animations
     * @return Animation[] animSkin animation du skin du peronnage
     */
    public Animation[] getAnimSkin() {
        return animSkin;
    }
    /**
     * accesseur au nombre d'images en hauteur
     * @return int nbrH nombre d'images en hauteur
     */
    public int getNbrH() {
        return nbrH;
    }
    /**
     * accesseur au nombre d'images en largeur
     * @return int nbrL nombre d'images en largeur
     */
    public int getNbrL() {
        return nbrL;
    }
    /**
     * accesseur à la vitesse de l'animation
     * @return int vitesse vitesse de l'animation
     */
    public int getVitesse() {
        return vitesse;
    }
    /**
     * Accesseur au tableau d'une animation
     * @param nbr - numéro de l'animation
     * @return Animation animSkin[nbr] animation du personnage
     */
    public Animation getAnimation(int nbr) {
        return animSkin[nbr];
    }
    /**
     * Modificateur du tableau d'une animation
     * @param animSkin - le tableau d'animation
     */
    public void setAnimSkin(Animation[] animSkin) {
        this.animSkin = animSkin;
    }
    /**
     * Modificateur du nombre d'images en hauteur
     * @param nbrH - le nombre d'images en hauteur
     */
    public void setNbrH(int nbrH) {
        this.nbrH = nbrH;
    }
    /**
     * Modificateur du nombre d'images en largeur
     * @param nbrL - le nombre d'images en largeur
     */
    public void setNbrL(int nbrL) {
        this.nbrL = nbrL;
    }

    /**
     * Modificateur de la vitesse de l'animation du personnage
     * @param int vit nouvelle vitesse de l'animation du personnage
     */
    public void setVitesse(int vit) {
        SkinAnimation.vitesse = vit;
    }
    
}
