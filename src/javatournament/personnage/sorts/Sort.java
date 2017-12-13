/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javatournament.personnage.sorts;
import java.util.ArrayList;
import java.util.Arrays;
import javatournament.personnage.Personnage;
import javatournament.personnage.sorts.competences.Competence;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Passage du Bug T détécté.
 * @author Quentin
 */
public class Sort
{
    /**
     * Coût du sort.
     */
    private int cout;
    
    /**
     * Nom du sort.
     */
    private String nom;
    
    /**
     * Puissance du sort.
     */
    private int puissance;
    
    /**
     * Portée du sort (distance maximal).
     */
    private int portee;
    
    /**
     * Liste des effets du sort.
     */
    private ArrayList<Competence> comps;
    
    /**
     * Icône du sort.
     */
    private Image skin;
    
    /**
     * Constructeur de Sort.
     * @param nom - Nom du Sort.
     * @param cout - Coût du Sort en Energie.
     * @param portee - Portée du Sort.
     * @param puissance - Puissance du Sort.
     * @param skin - Image du Sort.
     * @param Comps liste de Competence définis pour le Sort.
     * @throws SlickException 
     */
    public Sort(String nom, int cout, int portee, int puissance, Image skin, Competence... Comps) throws SlickException
    {
        this.cout = cout;
        this.nom = nom;
        this.portee = portee;
        this.comps = new ArrayList<>();
        this.comps.addAll(Arrays.asList(Comps));
        this.skin=skin;
        this.puissance=puissance;
    }

    /**
     * Accesseur au coût du Sort.
     * @return - Coût du Sort en Energie.
     */
    public int getCout()
    {
        return cout;
    }

    /**
     * Modificateur du coût du Sort.
     * @param cout - Nouveau coût du Sort en Energie.
     */
    public void setCout(int cout)
    {
        this.cout = cout;
    }

    /**
     * Accesseur au nom du Sort.
     * @return - Nom du Sort.
     */
    public String getNom()
    {
        return nom;
    }

    /**
     * Modificateur du nom du Sort.
     * @param nom - Nouveau nom du Sort.
     */
    public void setNom(String nom)
    {
        this.nom = nom;
    }

    /**
     * Accesseur à la portée du Sort.
     * @return - Portée du Sort.
     */
    public int getPortee()
    {
        return portee;
    }

    /**
     * Modificateur de la portée du Sort.
     * @param portee - Nouvelle portée du Sort.
     */
    public void setPortee(int portee)
    {
        this.portee = portee;
    }

    /**
     * Accesseur à la puissance du Sort.
     * @return - Puissance du Sort.
     */
    public int getPuissance()
    {
        return puissance;
    }

    /**
     * Modificateur de la puissance du Sort.
     * @param puissance - Nouvelle puissance du Sort.
     */
    public void setPuissance(int puissance)
    {
        this.puissance = puissance;
    }

    /**
     * Accesseur à l'Image du Sort.
     * @return - Image du Sort.
     */
    public Image getSkin()
    {
        return skin;
    }

    /**
     * Modificateur de l'Image du Sort.
     * @param skin - Nouvelle Image du Sort.
     * @throws SlickException
     */
    public void setSkin(String skin) throws SlickException 
    {
        this.skin = new Image(skin);
    }

    /**
     * Surchage de la méthode equals, permet de comparé le nom de deux sorts.
     * @param o - Sort de comparaison.
     * @return - True si leur nom est identique, false sinon.
     */
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof Sort)
        {
            Sort s = (Sort) o;
            if(s.nom.equals(this.nom))
                return true;
        }
        return false;
    }
    
    /**
     * Accesseur à la liste de Compétences (Effets) du Sort.
     * @return - Compétences du Sort.
     */
    public ArrayList<Competence> getComps()
    {
        return this.comps;
    }

    /**
     * Modificateur de la liste de Compétences (Effets) du Sort.
     * @param Comp - Nouvelle liste de Compétences du Sort.
     */
    public void setComps(ArrayList<Competence> comps)
    {
        this.comps = comps;
    }

    /**
     * Méthode permettant d'ajouter les effets du Sort sur le Personnage.
     * @param P - Personnage cible des effets du Sort.
     */
    public void effet(Personnage P, int x, int y)
    {
        for(Competence compet : comps )
        {
            compet.effet(P, puissance);
        }
    }
    
}
