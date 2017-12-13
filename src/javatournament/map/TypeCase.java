/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javatournament.map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kant1
 */
public class TypeCase
{
    /**
     * bonus pour l'attaque
     */
    private float bonusAttaque;

    /**
     * bonus pour l'esquive
     */
    private float bonusEsquive;

    /**
     * bonus pour l'armure
     */
    private float bonusArmure;

    /**
     * possibilité dde se deplacer dans l'image
     */
    private boolean access;

    /**
     * Image de la case
     */
    private Image skin;
    
    /**
     * Constructeur de la classe TypeCase
     * @param I
     * @param acc
     * @param bArm
     * @param bAtt
     * @param bEsq
     * @throws SlickException 
     */
    public TypeCase(Image I,boolean acc, float bArm, float bAtt, float bEsq ) throws SlickException
    {
        this.skin = I;
        this.access = acc;
        this.bonusArmure = bArm;
        this.bonusAttaque = bAtt;
        this.bonusEsquive = bEsq;
    }

    /**
     * Modificateur, si la case est accessible
     * @param access - Accessibilité de la case.
     */
    public void setAccess(boolean access) {
        this.access = access;
    }
    /**
     * Modificateur de du bonus armure de la case
     * @param bonusArmure - Nouveau bonus Armure de la case.
     */
    public void setBonusArmure(float bonusArmure) {
        this.bonusArmure = bonusArmure;
    }
    /**
     * Modifiicateur du bonus attaque de la case
     * @param bonusAttaque - Nouveau bonus Attaque de la case.
     */
    public void setBonusAttaque(float bonusAttaque) {
        this.bonusAttaque = bonusAttaque;
    }
    /**
     * Modificateur du bonus Esquive de la case.
     * @param bonusEsquive  - Nouveau bonus Esquive de la case.
     */
    public void setBonusEsquive(float bonusEsquive) {
        this.bonusEsquive = bonusEsquive;
    }

    /**
     * Modificateur de l'Image de la case.
     * @param skin - Nouvelle Image de la case.
     */
    public void setSkin(Image skin) {
        this.skin = skin;
    }
    /**
     * Accesseur à l'accessibilité de la case.
     * @return - Accessibilité de la case.
     */
    public boolean isAccess() {
        return access;
    }
    /**
     * Accesseur au bonus d'Armure de la case.
     * @return - Bonus d'Armure de la case.
     */
    public float getBonusArmure() {
        return bonusArmure;
    }
    /**
     * Accesseur au bonus d'Attaque de la case.
     * @return - Bonus d'Attaque de la case.
     */
    public float getBonusAttaque() {
        return bonusAttaque;
    }
    /**
     * Accesseur au bonus d'Esquive de la case.
     * @return - Bonus d'Esquive de la case.
     */
    public float getBonusEsquive() {
        return bonusEsquive;
    }
    /**
     * Accesseur à l'Image de la case.
     * @return - Image de la case.
     */
    public Image getSkin() {
        return skin;
    }
}
