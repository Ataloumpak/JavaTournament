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
 * @author pyarg
 */
public class Guerrier extends Personnage
{
    /**
     * Description de la classe de Personnage (Guerrier).
     */
    public static String description;
    
    /**
     * Constructeur du personnage Guerrier
     * @throws SlickException 
     */
    public Guerrier() throws SlickException
    {
        super("Riou", 2, 80, 100, 90, 80, 100, 140);
        try{
            Draw.imageGuerrier();
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        super.setAnimPersonnageColor(0,Draw.GUERRIER_SKIN[0]);
        super.setAnimPersonnageColor(1,Draw.GUERRIER_SKIN[1]);
        super.setAnimPersonnageColor(2,Draw.GUERRIER_SKIN[2]);
        
        super.addSort("Brute Forcing", 100, 40, 1, Draw.GUERRIER_SORTS[0], new Degat());// capacité d'attaque
        super.addSort("Caféine pétillante", 30, 20, 2, Draw.GUERRIER_SORTS[1], new Buff("Armure", 1),new Buff("Attaque", 1));// capacité buff
        super.addSort("Persevérance", 20, 1, 1, Draw.GUERRIER_SORTS[2], new Buff("PMMax", 2));// capacité d'attaque
        super.addSort("Cogne-Hack", 30, 20, 2, Draw.GUERRIER_SORTS[3], new Degat());// capacité d'attaque
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
                    case "Brute Forcing":
                    {
                        this.getEmitters().add(new Emitter(P.getCastingPoint()[0]+1-S.getSkin().getWidth()/2, P.getCastingPoint()[1]-20, 5, 200, 0, 10.f, P, S, Emitter.VORTEX_EMITTING, 1, false, false, Draw.GRAP_PART));
                    }
                    break;
                    case "Caféine pétillante":
                    {
                        this.getEmitters().add(new Emitter(P.getCastingPoint()[0]+1-S.getSkin().getWidth()/2, P.getCastingPoint()[1]-100, 25, 200, 15, 10.f, P, S, Emitter.VORTEX_EMITTING, 1, true, false, Draw.BUBBLE_PART));
                    }
                    break;
                    case "Persevérance":
                    {
                        this.getEmitters().add(new Emitter(P.getCastingPoint()[0]+1-S.getSkin().getWidth()/2, P.getCastingPoint()[1]-100, 25, 200, 15, 10.f, P, S, Emitter.VORTEX_EMITTING, 1, true, false, Draw.SHIELD_PART));
                    }
                    break;
                    case "Cogne-Hack":
                    {
                        this.getEmitters().add(new Emitter(P.getCastingPoint()[0]+1-S.getSkin().getWidth()/2, P.getCastingPoint()[1], 1, 4000, 15, 10.f, P, S, Emitter.PICTURE_EMITTING, 1, false, false, Draw.JAILBREAKING_PART));
                    }
                    break;
                    default:
                    {
                        System.err.println("Guerrier : Sort non implémenté !");
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
