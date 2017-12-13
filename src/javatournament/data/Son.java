package javatournament.data;

import java.io.IOException;
import org.newdawn.easyogg.OggClip;
/**
 *
 * @author pyarg
 */

public class Son{
    //public static Sound PAS;
    
    public static OggClip INTRO001;
    public static OggClip MENU_PRINCIPAL001;
    public static OggClip MENU_PRINCIPAL002;
    public static OggClip COMBAT001;
    public static OggClip COMBAT002;
    public static OggClip DEFAITE001;
    public static OggClip CREDITS001;
    
    public static Playlist INTRO;
    public static Playlist MENU_PRINCIPAL;
    public static Playlist COMBAT;
    public static Playlist DEFAITE;
    public static Playlist CREDITS;
    
    /*public static void phaseJeu()
    {
        try
        {
            PAS = new Sound(Son.class.getResource("sons/bruit_pas_1.wav"));
        }
        catch (SlickException ex)
        {
            Logger.getLogger(Son.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
    public static void loadOgg()
    {
        try
        {
            INTRO001 = new OggClip(Son.class.getResourceAsStream("sons/ambiances/la_guilde_des_link.ogg"));
            MENU_PRINCIPAL001 = new OggClip(Son.class.getResourceAsStream("sons/ambiances/amaryllis.ogg"));
            MENU_PRINCIPAL002 = new OggClip(Son.class.getResourceAsStream("sons/ambiances/battle_on_earth.ogg"));
            COMBAT001 = new OggClip(Son.class.getResourceAsStream("sons/ambiances/dream.ogg"));
            COMBAT002 = new OggClip(Son.class.getResourceAsStream("sons/ambiances/running_man.ogg"));
            DEFAITE001 = new OggClip(Son.class.getResourceAsStream("sons/ambiances/somewhere.ogg"));
            CREDITS001 = new OggClip(Son.class.getResourceAsStream("sons/ambiances/genki_desu.ogg"));
            
            INTRO = new Playlist(INTRO001);
            MENU_PRINCIPAL = new Playlist(MENU_PRINCIPAL001, MENU_PRINCIPAL002);
            COMBAT = new Playlist(COMBAT001, COMBAT002);
            DEFAITE = new Playlist(DEFAITE001);
            CREDITS = new Playlist(CREDITS001);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
