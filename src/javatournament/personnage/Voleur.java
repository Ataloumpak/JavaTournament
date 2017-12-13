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
import javatournament.personnage.sorts.competences.Soin;
import org.newdawn.slick.SlickException;

/**
 *
 * @author pyarg & Baptiste
 */
public class Voleur extends Personnage
{
    /**
     * Description de la classe de Personnage (Voleur).
     */
    public static String description;
    
    /**
     * Constructeur du personnage Voleur
     * @throws SlickException 
     */
    public Voleur() throws SlickException
    {
        super("Lervoul", 4, 70, 100, 40, 55, 140, 80);
        try{
            Draw.imageVoleur();
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        super.setAnimPersonnageColor(0,Draw.VOLEUR_SKIN[0]);
        super.setAnimPersonnageColor(1,Draw.VOLEUR_SKIN[1]);
        super.setAnimPersonnageColor(2,Draw.VOLEUR_SKIN[2]);
        
        super.addSort("Backdoor", 30, 5, 3, Draw.VOLEUR_SORTS[0], new Soin());// capacité d'attaque
        super.addSort("Injection de SQL", 30, -15, 1, Draw.VOLEUR_SORTS[1], new Buff("Armure", 2));// capacité de malus
        super.addSort("Phishing", 30, 20, 2, Draw.VOLEUR_SORTS[2], new Degat());// capacité d'attaque
        super.addSort("Jailbreaking", 15, 15, 1, Draw.VOLEUR_SORTS[3], new Degat());// capacité d'attaque
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
        if(this.getEnergieCrt()-S.getCout() >= 0)
        {
            if(P != null)
            {
                switch(S.getNom())
                {
                    case "Injection de SQL":
                    {
                        l.ajoutMessage(this.getNom()+" : '"+S.getNom()+"'");
                        this.setEnergieCrt(this.getEnergieCrt()-S.getCout());
                        this.getEmitters().add(new Emitter(P.getCastingPoint()[0], P.getCastingPoint()[1], 1, 15000, 0, 1.f, P, S, Emitter.PICTURE_EMITTING, 1, false, true, Draw.SQL_PART));
                        super.lancerSort(x, y, S, l, envoye);
                    }
                    break;
                    case "Phishing":
                    {
                        l.ajoutMessage(this.getNom()+" : '"+S.getNom()+"'");
                        this.setEnergieCrt(this.getEnergieCrt()-S.getCout());
                        this.getEmitters().add(new Emitter(P.getCastingPoint()[0], P.getCastingPoint()[1], 1, 15000, 0, 1.f, P, S, Emitter.PICTURE_EMITTING, 1, false, true, Draw.PHISHING_PART));
                        super.lancerSort(x, y, S, l, envoye);
                    }
                    break;
                    case "Jailbreaking":
                    {
                        l.ajoutMessage(this.getNom()+" : '"+S.getNom()+"'");
                        this.setEnergieCrt(this.getEnergieCrt()-S.getCout());
                        this.getEmitters().add(new Emitter(P.getCastingPoint()[0], P.getCastingPoint()[1], 1, 15000, 0, 1.f, P, S, Emitter.PICTURE_EMITTING, 1, false, true, Draw.JAILBREAKING_PART));
                        super.lancerSort(x, y, S, l, envoye);
                    }
                    break;
                    default:
                    {
                        l.ajoutMessage("Cible incorrecte !");
                    }
                }
            }
            else
            {
                switch(S.getNom())
                {
                    case "Backdoor":
                    {
                        l.ajoutMessage(this.getNom()+" : '"+S.getNom()+"'");
                        this.setEnergieCrt(this.getEnergieCrt()-S.getCout());
                        StaticData.map.placerPersonnage(this, x, y);
                        this.getEmitters().add(new Emitter(x+25+14, y-80, 100, 75, 12, 3.f, this, S, Emitter.RISING_EMITTING, 1, true, false, Draw.DOOR_PART));
                        super.lancerSort(x, y, S, l, envoye);
                    }
                    break;
                    default:
                    {
                        l.ajoutMessage("Cible incorrecte !");
                    }
                }
            }
        }
        else
            l.ajoutMessage("Pas assez d'énergie !");
    }
}
