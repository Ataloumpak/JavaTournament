package javatournament.personnage;

import javatournament.combat.ListeEquipes;
import javatournament.combat.Log;
import javatournament.combat.PhaseJeu;
import javatournament.combat.StaticData;
import javatournament.data.Draw;
import javatournament.emitters.Emitter;
import javatournament.map.Map;
import javatournament.personnage.sorts.Sort;
import javatournament.personnage.sorts.competences.Buff;
import javatournament.personnage.sorts.competences.Degat;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Quentin
 */
public class Mage extends Personnage
{
    /**
     * Description de la classe de Personnage (Mage).
     */
    public static String description;
    
    /**
     * Constructeur du personnage Mage
     * @throws SlickException 
     */
    public Mage() throws SlickException
    {
        super("Zyndac", 3, 70, 100, 30, 60, 120, 60);
        Draw.imageMage();
        super.setAnimPersonnageColor(0, Draw.MAGE_SKIN[0]);
        super.setAnimPersonnageColor(1, Draw.MAGE_SKIN[1]);
        super.setAnimPersonnageColor(2, Draw.MAGE_SKIN[2]);
        Mage.description = "Enfants du Soleil, les Zyndac utilise la lumière et le feu pour assouvir leurs soif de pouvoirs. On racconte que les Zyndac sont arrivés sur terres avec une météorite. Leur pouvoirs de contrôle de la lumière ont permis d'éclairé l'humanité pendant ses périodes les plus sombres.";
        
        super.addSort("Lumière sordide", 30, 15, 4, Draw.MAGE_SORTS[0], new Degat());// capacité d'attaque
        super.addSort("Rayon U.V.", 50, 20, 6, Draw.MAGE_SORTS[1], new Degat(),new Buff("Armure", 2));// capacité d'attaque
        super.addSort("Eclair",30, 10, 5, Draw.MAGE_SORTS[2], new Degat());// capacité d'attaque
        super.addSort("B.S.O.D.", 40, 30, 2, Draw.MAGE_SORTS[3], new Degat());// capacité d'attaque
    }
    /**
     * Methode pour lancer un sorts
     * @param x Abscisse de l'ennemi
     * @param y Ordonne de l'ennemi
     * @param S Sors à lancer
     * @param l Logs où afficher les messages
     */
    @Override
    public void lancerSort(int x, int y, Sort S, Log l)
    {
        lancerSort( x, y, S, l, false);
    }
    
    /**
     * Methode pour lancer un sorts
     * @param x Abscisse de l'ennemis
     * @param y Ordonnee de l'ennemi
     * @param S Sors à lancer
     * @param l Log ou afficher les messages 
     */
    @Override
    public void lancerSort(int x, int y, Sort S, Log l, boolean envoye)
    {
        if( l==null )
            l=PhaseJeu.getLog();

        int id = StaticData.map.getCasePersonnage(x, y);
        Personnage P = null;
        if(id != 99)
           P = ListeEquipes.getPersonnage(id);
        if(P != null)
        {
            if(this.getEnergieCrt()-S.getCout() >= 0)
            {
                this.setEnergieCrt(this.getEnergieCrt()-S.getCout());
                l.ajoutMessage(this.getNom()+" : '"+S.getNom()+"'");
                switch(S.getNom())
                {
                    case "Lumière sordide":
                    {
                        this.getEmitters().add(new Emitter(this.getCastingPoint()[0], this.getCastingPoint()[1], 200, 75, 0, 1.f, P, S, Emitter.OVAL_AREA_EMITTING, 1, true, false, Draw.JOUVENCE_PART));
                    } 
                    break;
                    case "Rayon U.V.":
                    {
                        Emitter E = new Emitter(this.getCastingPoint()[0], this.getCastingPoint()[1], 500, 10000, 0, 5, P, S, Emitter.LASER_EMITTING, 1, true, false, Draw.UV_PART);
                        E.setAngle(Math.atan2(this.getCastingPoint()[1]-P.getCastingPoint()[1], this.getCastingPoint()[0]-P.getCastingPoint()[0]));
                        this.getEmitters().add(E);
                    }
                    break;
                    case "Eclair":
                    {
                        Emitter E = new Emitter(P.getCastingPoint()[0], P.getCastingPoint()[1]-100, 350, 1000, 0, 2, P, S, Emitter.FALLING_EMITTING, 1, false, false, Draw.YELLOW_PART);
                        E.setAngle(Math.atan2(this.getCastingPoint()[1]-P.getCastingPoint()[1], this.getCastingPoint()[0]-P.getCastingPoint()[0]));
                        this.getEmitters().add(E);
                    }
                    break;
                    case "B.S.O.D.":
                    {
                        this.getEmitters().add(new Emitter(0, 0, 1, 10000, 0, 0, P, S, Emitter.PICTURE_EMITTING, 1, false, false, Draw.BSOD_PART));
                    }
                    break;
                    default:
                    {
                        System.err.println("Mage : Sort non implémenté !");
                        return;
                    }

                }                
                super.lancerSort(x, y, S, l, envoye);
            }
            else
                l.ajoutMessage("Pas assez d'énergie !");
        }
        else
            l.ajoutMessage("Cible incorrecte !");
    }
}
