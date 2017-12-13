package javatournament.personnage;

import javatournament.combat.ListeEquipes;
import javatournament.combat.Log;
import javatournament.combat.PhaseJeu;
import javatournament.combat.StaticData;
import javatournament.data.Draw;
import javatournament.emitters.Emitter;
import javatournament.personnage.sorts.Sort;
import javatournament.personnage.sorts.competences.Buff;
import javatournament.personnage.sorts.competences.Soin;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Romain
 */
public class Clerc extends Personnage
{
    /**
     * Description de la classe de Personnage (Clerc).
     */
    public static String description;
    
    /**
     * Constructeur du Personnage Clerc
     * @throws SlickException 
     */
    public Clerc() throws SlickException
    {
        super("Dauvou", 3, 60, 100, 30, 40, 110, 50);
        try{
            Draw.imageClerc();
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        super.setAnimPersonnageColor(0, Draw.CLERC_SKIN[0]); 
        super.setAnimPersonnageColor(1, Draw.CLERC_SKIN[1]);
        super.setAnimPersonnageColor(2, Draw.CLERC_SKIN[2] );
        
        super.addSort("Sousib", 25, 12, 3, Draw.CLERC_SORTS[0], new Soin()); // Sort de soin
        super.addSort("Restauration système", 35, 5, 1, Draw.CLERC_SORTS[1], new Buff("PDVCrt", 3));// Régénère la santé de la cible
        super.addSort("Pare-feu", 30, 10, 1, Draw.CLERC_SORTS[2], new Buff("Armure", 2));// boost d'armure (+10)
        super.addSort("Effet du Puyx", 60, -25, 2, Draw.CLERC_SORTS[3], new Buff("Armure", 2), new Buff("Attaque", 2));//gros debuff
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
                    case "Sousib":
                        this.getEmitters().add(new Emitter(this.getCastingPoint()[0], this.getCastingPoint()[1], 200, 75, 0, 1.f, P, S, Emitter.VORTEX_EMITTING, 1, true, false, Draw.BISOU_PART));
                    break;
                    case "Restauration système":
                        this.getEmitters().add(new Emitter(P.getCastingPoint()[0]+1-S.getSkin().getWidth()/2, P.getCastingPoint()[1]-100, 75, 200, 0, 10.f, P, S, Emitter.VORTEX_EMITTING, 1, true, false, Draw.HEAL_PART));
                    break;
                    case "Pare-feu":
                        this.getEmitters().add(new Emitter(P.getCastingPoint()[0]+1-S.getSkin().getWidth()/2, P.getCastingPoint()[1]-100, 75, 200, 0, 10.f, P, S, Emitter.VORTEX_EMITTING, 1, true, false, Draw.FIREWALL_PART));
                    break;
                    case "Effet du Puyx":
                        this.getEmitters().add(new Emitter(this.getCastingPoint()[0], this.getCastingPoint()[1], 200, 75, 0, 1.f, P, S, Emitter.OVAL_AREA_EMITTING, 1, true, false, Draw.DUPUIS_PART));
                    break;
                    default:
                    {
                        System.err.println("Clerc : Sort non implémenté !");
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
