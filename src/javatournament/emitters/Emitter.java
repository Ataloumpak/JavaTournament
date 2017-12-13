/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javatournament.emitters;

import java.util.ArrayList;
import javatournament.personnage.Personnage;
import javatournament.personnage.sorts.Sort;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Quentin
 */
public class Emitter
{
    /**
     * Emission aléatoire circulaire.
     */
    public static int OVAL_AREA_EMITTING = 1;
    
    /**
     * Emission en triangle.
     */
    public static int FIRE_EMITTING = 2;
    
    /**
     * Emission circulaire avec aspiration des particules en son centre.
     */
    public static int VORTEX_EMITTING = 3;
    
    /**
     * Emission verticale ascendante.
     */
    public static int RISING_EMITTING = 4;
    
    /**
     * Emission verticale descendante.
     */
    public static int FALLING_EMITTING = 5;
    
    /**
     * Emission avec animation de pluie.
     */
    public static int RAIN_EMITTING = 6;
    
    /**
     * Emission avec animation de nuages.
     */
    public static int CLOUD_EMITTING = 7;
    
    /**
     * Emission sous forme de rayon.
     */
    public static int LASER_EMITTING = 8;
    
    /**
     * Emission de particules fixes.
     */
    public static int PICTURE_EMITTING = 9;
    
    /**
     * Coordonnées de l'émetteur de particules. ([0]=x,[1]=y)
     */
    private float[] coords;
    
    /**
     * Nombre maximal de particules en présence.
     */
    private int max;
    
    /**
     * Rayon d'émission des particules.
     */
    private float radius;
    
    /**
     * Durée d'effet de l'émitteur.
     */
    private int timer;
    
    /**
     * Liste des particles appartenant à l'émetteur.
     */
    private ArrayList<Particle> particles = new ArrayList();
    
    /**
     * Modèle de génération de la particule.
     */
    private Image model;
    
    /**
     * Destination de l'émetteur de particules.
     */
    private Personnage target;
    
    /**
     * Angle de déplacement de l'émetteur.
     */
    private double angle;
    
    /**
     * Délai entre l'apparition de deux particules.
     */
    private int delay;
    
    /**
     * Compteur permettant de créer un délai.
     */
    private int FPStability = 50;
    
    /**
     * Type de diffusion de l'émetteur.
     */
    private int animType = -1;
    
    /**
     * Durée de vie des particules émises.
     */
    private int lifetime;
    
    /**
     * Indique si l'émetteur doit être mobile ou non.
     */
    private boolean mobile;
    
    /**
     * Indique si les particules émises doivent tourner ou non.
     */
    private boolean rotate;
    
    /**
     * Sort à appliquer lors de l'impact.
     */
    private Sort sort;
    
    /**
     * Nombre de particules émises par animation.
     */
    private int repeat;
    
    /**
     * Constructeur de l'émetteur de particules fixe.
     * @param x - Abscisse de l'émetteur.
     * @param y - Ordonnée de l'émetteur.
     * @param max - Nombre maximum de particules.
     * @param lt - Durée de vies des particules émises.
     * @param delay - Délai entre les émissions en ms.
     * @param r - Rayon d'émission.
     * @param type - Type d'émission.
     * @param repeat - Nombre de particules émises par frame.
     * @param rotate - Rotation des particules ou non.
     * @param I - Image de la particule.
     */
    public Emitter(float x, float y, int max, int lt, int delay, float r, int type, int repeat, boolean rotate, Image I)
    {
        this.coords = new float[]{x, y};
        this.target = null;
        this.angle = .0;
        this.max = max;
        this.lifetime = lt;
        this.delay = delay;
        this.rotate = rotate;
        this.radius = r;
        this.model = I;
        this.animType = type;
        this.repeat = repeat;
        this.timer = 1;
    }
    
    /**
     * Constructeur d'emetteur de particules mobile.
     * @param x - Abscisse de l'émetteur.
     * @param y - Ordonnée de l'émetteur.
     * @param max - Nombre maximum de particules.
     * @param lt - Durée de vies des particules émises.
     * @param delay - Délai entre les émissions en ms.
     * @param r - Rayon d'émission.
     * @param target - Cible de l'émetteur.
     * @param S - Sort à appliquer sur la cible.
     * @param type - Type d'émission.
     * @param repeat - Nombre de particules émises par frame.
     * @param mob - Emetteur mobile ou non.
     * @param rotate - Rotation des particules ou non.
     * @param I - Image de la particule.
     */
    public Emitter(float x, float y, int max, int lt, int delay, float r, Personnage target, Sort S, int type, int repeat, boolean mob, boolean rotate, Image I)
    {
        this.coords = new float[]{x, y};
        this.target = target;
        this.max = max;
        this.lifetime = lt;
        this.delay = delay;
        this.mobile = mob;
        this.rotate = rotate;
        this.sort = S;
        this.radius = r;
        this.model = I;
        this.repeat = repeat;
        this.angle = Math.atan2(y-target.getCastingPoint()[1], x-target.getCastingPoint()[0]);
        this.animType = type;
        this.timer = 1;
    }
    
    /**
     * Méthode permettant de générer des particules aléatoirement aux alentours
     * de la position de l'émetteur en question.
     */
    public void ovalAreaEmitting(GameContainer gc)
    {
        if(this.particles.size() < this.max && System.currentTimeMillis()%(this.delay+1) == 0)
        {
            for(int i = 0 ; i < this.repeat ; i++)
            {
                particles.add(new Particle(this.coords[0]+(float)(Math.random()*this.radius), this.coords[1]+(float)(Math.random()*this.radius), this.lifetime, Math.random()*2*Math.PI, .03f, null, true, this.rotate, this.model));
            }
        }
    }
    
    /**
     * Méthode permettant de générer des particules en formant un triangle.
     * @param GameContainer gc - Conteneur du jeu.
     */
    public void fireEmitting(GameContainer gc)
    {
        if(this.particles.size() < this.max && System.currentTimeMillis()%(this.delay+1) == 0)
        {
            for(int i = 0 ; i < this.repeat ; i++)
            {
                float x = this.coords[0]-this.model.getWidth()/2+(int)(Math.random()*2*this.radius-this.radius);
                float y = this.coords[1];
                float[] targ = {this.coords[0], this.coords[1]-100};
                this.particles.add(new Particle(x, y, this.lifetime, Math.atan2(y-targ[1], x-targ[0]), .3f, targ, true, this.rotate, this.model));
            }
        }
    }
    
     /**
     * Méthode permettant de générer des particules sur une ligne.
     * @param GameContainer gc - Conteneur du jeu.
     */
    public void laserEmitting(GameContainer gc)
    {
        if(this.particles.size() < this.max && System.currentTimeMillis()%(this.delay+1) == 0)
        {
            for(int i = 0 ; i < this.repeat ; i++)
            {
                float[] targ = {this.target.getCastingPoint()[0], this.target.getCastingPoint()[1]};
                this.particles.add(new Particle(this.coords[0], this.coords[1], this.lifetime, this.angle, .3f, targ, true, this.rotate, this.model));
            }
        }
    }
    
    /**
     * Méthode permettant de générer des particules avec pour direction le centre de l'émetteur.
     * @param GameContainer gc - Conteneur du jeu.
     */
    public void vortexEmitting(GameContainer gc)
    {
        if(this.particles.size() < this.max && System.currentTimeMillis()%(this.delay+1) == 0)
        {
            for(int i = 0 ; i < this.repeat ; i++)
            {
                double random = Math.random();
                float x = (float) (this.coords[0]+Math.cos(random*2*Math.PI)*this.radius);
                float y = (float) (this.coords[1]+Math.sin(random*2*Math.PI)*this.radius);
                particles.add(new Particle(x, y, this.lifetime, Math.atan2(y-this.coords[1], x-this.coords[0]), .05f, this.target.getCastingPoint(), true, this.rotate, this.model));
            }
        }
    }
    
    /**
     * Méthode permettant une génération de particules verticale ascendante.
     * @param GameContainer gc - Conteneur du jeu.
     */
    public void risingEmitting(GameContainer gc)
    {
        if(this.particles.size() < this.max && System.currentTimeMillis()%(this.delay+1) == 0)
        {
            for(int i = 0 ; i < this.repeat ; i++)
            {
                float x = (float) (this.coords[0]+5+(Math.random()*2*this.radius)-this.radius);
                float y = this.coords[1];
                particles.add(new Particle(x, y, this.lifetime, Math.PI/2, .05f, null, true, this.rotate, this.model));
            }
        }
    }
    
    /**
     * Méthode permettant une génération de particules verticale descendante.
     * @param GameContainer gc - Conteneur du jeu.
     */
    public void fallingEmitting(GameContainer gc)
    {
        if(this.particles.size() < this.max && System.currentTimeMillis()%(this.delay+1) == 0)
        {
            for(int i = 0 ; i < this.repeat ; i++)
            {
                float x = this.coords[0]+(int)((Math.random()*2*this.radius)-this.radius);
                float y = this.coords[1];
                particles.add(new Particle(x, y, this.lifetime, -Math.PI/2, .4f, new float[]{this.target.getCastingPoint()[0], this.target.getPosY()+20}, true, this.rotate, this.model));
            }
        }
    }
    
    /**
     * Méthode permettant de générer des particules pour la pluie.
     * @param GameContainer gc - Conteneur du jeu.
     */
    public void rainEmitting(GameContainer gc)
    {
        if(this.particles.size() < this.max && (this.delay)%this.timer == 0)
        {
            float x;
            float y;
            for(int i = 0 ; i < this.repeat ; i++)
            {
                x = this.coords[0]+5+(float)((Math.random()*2*this.radius)-this.radius);
                y = this.coords[1]+(float) (Math.random()*gc.getHeight());
                Particle P = new Particle(x, y, this.lifetime, 4*(Math.PI/3), .3f, null, true, this.rotate, this.model);
                P.setScale((float) ((Math.random()*0.85f)+0.15f));
                particles.add(P);
            }
        }
    }
    
    /**
     * Méthode permettant de générer des particules pour les nuages.
     * @param GameContainer gc - Conteneur du jeu.
     */
    public void cloudEmitting(GameContainer gc)
    {
        if(this.particles.size() < this.max && this.timer%(this.delay+1) == 0)
        {
            float x;
            float y;
            for(int i = 0 ; i < this.repeat ; i++)
            {
                x = this.coords[0]+5+(float)((Math.random()*2*this.radius)-this.radius);
                y = (float) (this.coords[1]-Math.random()*100);
                Particle P = new Particle(x, y, this.lifetime, 4*(Math.PI/3), .04f, null, true, this.rotate, this.model);
                P.setScale((float) ((Math.random()*1.45f)+0.55f));
                particles.add(P);
            }
        }
    }
    
    /**
     * Méthode permettant de générer des particules fixes.
     * @param GameContainer gc - Conteneur du jeu.
     */
    public void pictureEmitting(GameContainer gc)
    {
        if(this.particles.size() < this.max && System.currentTimeMillis()%(this.delay+1) == 0)
        {
            for(int i = 0 ; i < this.repeat ; i++)
            {
                Particle P = new Particle(this.coords[0], this.coords[1], this.lifetime, Math.PI/2, .0f, null, false, this.rotate, this.model);
                particles.add(P);
            }
        }
    }
    
    /**
     * Méthode permettant d'animer les émitteurs. (émission + collision)
     * @param GameContainer gc - Conteneur du jeu.
     * @param delta - Intervalle entre les updates.
     */
    public void animate(GameContainer gc, int delta)
    {
        if(gc.getFPS() >= this.FPStability)
        {
            if(mobile)
            {
                this.coords[0] -= (.2f*Math.cos(this.angle)*delta);
                this.coords[1] -= (.2f*Math.sin(this.angle)*delta);
            }
            switch(this.animType)
            {
                case 1:
                    this.ovalAreaEmitting(gc);                
                break;
                case 2:
                    this.fireEmitting(gc);
                break;
                case 3:
                    this.vortexEmitting(gc);
                break;
                case 4:
                    this.risingEmitting(gc);
                break;
                case 5:
                    this.fallingEmitting(gc);
                break;
                case 6:
                    this.rainEmitting(gc);
                break;
                case 7:
                    this.cloudEmitting(gc);
                break;
                case 8:
                    this.laserEmitting(gc);
                break;
                case 9:
                    this.pictureEmitting(gc);
            }

            for(int i = 0 ; i < this.particles.size() ; i++)
            {
                Particle P = this.particles.get(i);
                P.animate(delta);
                 if(P.getScale() <= 0.15
                        || P.getLifetime() <= 0
                        || P.getCoords()[0] >= gc.getWidth()
                        || P.getCoords()[1] >= gc.getHeight()
                        || (this.animType == 2 && P.getCoords()[1] <= P.getTarget()[1])
                        || (this.animType == 5 && P.getCoords()[1] >= P.getTarget()[1])
                        || (this.animType == 8 && P.getCoords()[0] <= P.getTarget()[0]+5 && P.getCoords()[0] >= P.getTarget()[0]-5
                                               && P.getCoords()[1] <= P.getTarget()[1]+5 && P.getCoords()[1] >= P.getTarget()[1]-5))
                {
                    this.particles.remove(P);
                }
            }
            this.timer++;
        }
    }
    
    /**
     * Méthode qui permet de dessiner toutes les particules de l'émetteur.
     * @param Graphics gphc - Objet graphique pour dessiner les particules.
     */
    public void draw(Graphics gphc)
    {
        if(this.model != null)
        {
            for(int i = 0 ; i < this.particles.size() ; i++)
            {
                Particle P = this.particles.get(i);
                this.model.draw(P.getCoords()[0], P.getCoords()[1], P.getScale());
            }
        }
        else
        {
            System.out.println("Error : Picture is null !");
        }
    }
    
    /**
     * Méthode supprimant la référence vers la cible.
     */
    public void setNoTarget()
    {
        this.target = null;
    }

    /**
     * Accesseur aux coordonnées de l'émetteur.
     * @return float[] - Coordonnées de l'émetteur.
     */
    public float[] getCoords()
    {
        return coords;
    }
    
    /**
     * Accesseur à la cible de l'émetteur.
     * @return Personnage - Cible de l'émetteur.
     */
    public Personnage getTarget()
    {
        return this.target;
    }
    
    /**
     * Modificateur de l'angle de direction de l'émetteur.
     * @param double a - Angle de l'émetteur.
     */
    public void setAngle(double a)
    {
        this.angle = a;
    }
    
    /**
     * Accesseur au type d'animation de l'émetteur.
     * @return int - Numéro de l'animation.
     */
    public int getAnimType()
    {
       return this.animType;
    }
    
    /**
     * Accesseur au sort transmis par l'émetteur.
     * @return Sort - Sort appliqué par l'émetteur.
     */
    public Sort getSort()
    {
        return this.sort;
    }
    
    /**
     * Accesseur au chronomètre de l'émetteur. (durée de vie)
     * @return int - Durée de vie de l'émetteur.
     */
    public int getTimer()
    {
        return this.timer;
    }
}
