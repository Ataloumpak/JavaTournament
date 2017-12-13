/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javatournament.data;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 *
 * @author pyarg
 */
public class Police {
    /**
     * Accesseur aux polices normal.
     */
    public static final int REGULAR=0;
    /**
     * Accesseur aux polices grasses.
     */
    public static final int BOLD=1;
    /**
     * Accesseur aux polices italiques.
     */
    public static final int ITALIC=2;
    /**
     * Accesseur aux polices grasses et italiques.
     */
    public static final int BOLD_ITALIC=3;
    /**
     * Police AVERIA.
     * Existe en REGULAR, BOLD, ITALIC, et BOLD_ITALIC.
     */
    public static String[] AVERIA=new String[4];
    /**
     * Police AVERIA.
     * Existe en REGULAR, BOLD, ITALIC, et BOLD_ITALIC.
     */
    public static String[] CAVIARDREAM=new String[4];
    
    /**
     * Methode pour générer une UnicodeFont
     * @param ref - Référence de la police.
     * @param size - Taille de la police.
     * @return UnicodeFont
     */
    public static UnicodeFont UNICODEFONT( String ref, int size){
        UnicodeFont tmp=null;
        
        try {
            tmp = new UnicodeFont( ref, size, false, false);
            tmp.addAsciiGlyphs();
            tmp.getEffects().add( new ColorEffect( java.awt.Color.BLACK ) );
            tmp.loadGlyphs();
            return tmp;
        } catch (SlickException ex) {
            Logger.getLogger(Police.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tmp;
    }/**
     * Methode pour générer une UnicodeFont
     * @param ref - Référence de la police.
     * @param size - Taille de la police.
     * @return UnicodeFont
     */
    public static UnicodeFont UNICODEFONT( String ref, int size, java.awt.Color c){
        UnicodeFont tmp=null;
        
        try {
            tmp = new UnicodeFont( ref, size, false, false);
            tmp.addAsciiGlyphs();
            tmp.getEffects().add( new ColorEffect( c ) );
            tmp.loadGlyphs();
            return tmp;
        } catch (SlickException ex) {
            Logger.getLogger(Police.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tmp;
    }
    /**
     * Méthode qui génére toutes les polices.
     */
    public static void POLICE(){
        AVERIA[0]= /*Police.class.getClassLoader().getResource(*/"polices/AveriaSerif-Regular.ttf"/*).getPath()*/;
        AVERIA[1]= /*Police.class.getClassLoader().getResource(*/"polices/AveriaSerif-Bold.ttf"/*).getPath()*/;
        AVERIA[2]= /*Police.class.getClassLoader().getResource(*/"polices/AveriaSerif-Italic.ttf"/*).getPath()*/;
        AVERIA[3]= /*Police.class.getClassLoader().getResource(*/"polices/AveriaSerif-BoldItalic.ttf"/*).getPath()*/;

        CAVIARDREAM[0]= /*Police.class.getClassLoader().getResource(*/"polices/CaviarDreams.ttf"/*).getPath()*/;
        CAVIARDREAM[1]= /*Police.class.getClassLoader().getResource(*/"polices/CaviarDreams_Bold.ttf"/*).getPath()*/;
        CAVIARDREAM[2]= /*Police.class.getClassLoader().getResource(*/"polices/CaviarDreams_Italic.ttf"/*).getPath()*/;
        CAVIARDREAM[3]= /*Police.class.getClassLoader().getResource(*/"polices/CaviarDreams_BoldItalic.ttf"/*).getPath()*/;
    }
    
}
