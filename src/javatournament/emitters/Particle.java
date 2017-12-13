/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javatournament.emitters;

import org.newdawn.slick.Image;

/**
 *
 * @author Quentin
 */
public class Particle
{
    /**
     * Coordonnées de la particule. ([0]=x,[1]=y)
     */
    private float[] coords = new float[2];
    
    /**
     * Echelle de l'image.
     */
    private float scale;
    
    /**
     * Durée de vie de la particule.
     */
    private int lifetime;
    
    /**
     * Apparence de la particule.
     */
    private Image skin;
    
    /**
     * Vitesse de la particule.
     */
    private float speed;
    
    /**
     * Particule mobile ou fixe.
     */
    private boolean mobile;
    
    /**
     * Angle de la particule.
     */
    private double angle;
    
    /**
     * Cible de la particule.
     */
    private float[] target;
    
    /**
     * Constructeur de particule.
     * @param x - Placement horizontal.
     * @param y - Placement vertical.
     * @param lt - Durée de vie.
     * @param angle - Angle de direction.
     * @param img - Apparence visuelle.
     */
    public Particle(float x, float y, int lt, double angle, float spd, float[] target, boolean mob, boolean rotate, Image img)
    {
        this.coords[0] = x;
        this.coords[1] = y;
        this.lifetime = lt;
        this.angle = angle;
        this.scale = 1f;
        this.skin = img.copy();
        this.speed = spd;
        this.mobile = mob;
        this.target = target;
        if(rotate)
            this.skin.setRotation((float) Math.toDegrees(this.angle-Math.PI/2));
    }
    
    /**
     * Méthode permettant de déplacer la particule en fonction
     * de son angle et de sa vitesse.
     */
    public void animate(int delta)
    {
        if(isMobile())
        {
            this.coords[0] -= this.speed*Math.cos(this.angle)*delta;
            this.coords[1] -= this.speed*Math.sin(this.angle)*delta;
        }
        this.lifetime--;
    }

    /**
     * Accesseur aux coordonnées de la particule.
     * @return float[] - Coordonnées de la particule.
     */
    public float[] getCoords() {
        return coords;
    }

    /**
     * Accesseur à la durée de vie de la particule.
     * @return int - Durée de vie de la particule.
     */
    public int getLifetime() {
        return lifetime;
    }

    /**
     * Accesseur à la vitesse de la particule.
     * @return float - Vitesse de la particule.
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Modificateur de la vitesse de la particule.
     * @param float speed - Vitesse de la particule.
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Accesseur aux coordonnées de destination de la particule.
     * @return float[] - Coordonnées de destination de la particule.
     */
    public float[] getTarget() {
        return target;
    }

    /**
     * Accesseur à l'angle de déplacement de la particule.
     * @return double - Angle de la particule.
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Modificateur de l'angle de la particule.
     * @param double angle - Angle de la particule.
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * Accesseur à l'échelle de dessin de la particule.
     * @return float - Echelle de dessin de la particule.
     */
    public float getScale() {
        return scale;
    }

    /**
     * Modificateur de l'échelle de la particule.
     * @param float scale - Echelle de la particule.
     */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * Accesseur à l'état de mobilité de la particule.
     * @return boolean - Mobibilité de la particule.
     */
    public boolean isMobile() {
        return mobile;
    }

    /**
     * Modificateur de mobilité de la particule.
     * @param boolean mobile - Mobilité de la particule.
     */
    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }
}