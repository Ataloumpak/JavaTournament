/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javatournament.data;

import java.util.ArrayList;
import java.util.Arrays;
import org.newdawn.easyogg.OggClip;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

/**
 *
 * @author Quentin
 */
public class Playlist
{
    /**
     * Liste des morceaux.
     */
    private ArrayList<OggClip> clipsList;
    
    /**
     * Piste en cours de lecture.
     */
    private int index;
    
    /**
     * Playlist en cours de lecture.
     */
    private boolean played;
    
    /**
     * Volume de la playlist.
     */
    private float volume;
    
    /**
     * Constructeur par défaut de PlayList.
     */
    public Playlist()
    {
        this.clipsList = new ArrayList<>();
        this.index = 0;
        this.played = false;
        this.volume = 1.0f;
    }
    
    /**
     * Constructeur de PlayList permettant d'ajouter des morceaux.
     * @param OggClip clips - Liste des morceaux à ajouter à la PlayList.
     */
    public Playlist(OggClip... clips)
    {
        this.clipsList = new ArrayList<>();
        this.clipsList.addAll(Arrays.asList(clips));
        this.index = 0;
        this.volume = 1.0f;
        this.played = false;
    }
    
    /**
     * Méthode permettant d'ajouter un morceau à la PlayList.
     * @param OggClip O - Morceau à ajouter.
     */
    private void addClip(OggClip O)
    {
        this.clipsList.add(O);
    }
    
    /**
     * Méthode permettant de lancer la lecture de la PlayList.
     */
    public void play()
    {
        OggClip clipCourant = this.clipsList.get(this.index);
        clipCourant.setGain(this.volume);
        clipCourant.play();
        this.played = true;
    }
    
    /**
     * Méthode permettant de lire une PlayList à partir d'un certain morceau.
     * @param int i - Index du morceau à jouer.
     */
    public void playFrom(int i)
    {
        this.index = i;
        this.play();
    }
    
    /**
     * Méthode permettant d'arrêter la lecture de la PlayList.
     */
    public void stop()
    {
        this.played = false;
        this.clipsList.get(this.index).stop();
    }
    
    /**
     * Méthode permettant d'augmenter le volume de lecture.
     * @param OggClip clipCourant - Morceau actuellement joué.
     */
    public void augmenterVolume(OggClip clipCourant)
    {
        if(this.volume < 0.95f)
            this.volume += 0.05f;
        else
            this.volume = 1.0f;
        
        clipCourant.setGain(this.volume);
    }
    
    /**
     * Méthode permettant de baisser le volume de lecture.
     * @param OggClip clipCourant - Morceau actuellement joué.
     */
    public void baisserVolume(OggClip clipCourant)
    {
        if(this.volume > 0.05f)
            this.volume -= 0.05f;
        else
            this.volume = 0.0f;
        
        clipCourant.setGain(this.volume);
    }
    
    /**
     * Méthode gèrant la PlayList : lecture, arrêt, gestion du volume et
     * changement automatique de la piste.
     * @param GameContainer gc - Conteneur du jeu.
     */
    public void gestion(GameContainer gc)
    {
        if(this.played)
        {
            OggClip clipCourant = this.clipsList.get(this.index);
            int size = this.clipsList.size();

            if(gc.getInput().isKeyPressed(Input.KEY_INSERT))
                this.play();
            else if(gc.getInput().isKeyPressed(Input.KEY_DELETE))
                this.stop();
            else if(gc.getInput().isKeyPressed(Input.KEY_ADD))
                this.augmenterVolume(clipCourant);
            else if(gc.getInput().isKeyPressed(Input.KEY_SUBTRACT))
                this.baisserVolume(clipCourant);

            if(clipCourant.stopped() && this.index < size)
            {
                this.index = (this.index+1)%size;
                clipCourant = this.clipsList.get(this.index);
                clipCourant.setGain(this.volume);
                clipCourant.play();
            }
        }
    }
}
