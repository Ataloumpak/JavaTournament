/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javatournament.personnage.sorts.competences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javatournament.personnage.Personnage;

/**
 *
 * @author tony
 */
public class Buff implements Competence
{
    /**
     * Nom de l'attribut boosté
     */
    private String nomAttribut;
    
    /**
     * Valeur de l'attribut
     */
    private int valeur;
    
    /**
     * nombre de tour restant avant la fin du buff
     */
    private int toursRestant;
    
    /**
     * Personnage sur lequelle on applique le buff
     */
    private Personnage p;
    
    /**
     * Accesseur sur le nom de l'attribut
     * @return nomAttribut nom de l'attribut du buff
     */
    public String getNomAttribut()
    {
        return nomAttribut;
    }
    
    /**
     * Modificateur du nom de l'atrribut
     * @param nomAttribut nom du nouvel attribut du buff
     */
    public void setNomAttribut(String nomAttribut)
    {
        this.nomAttribut = nomAttribut;
    }

    /**
     * Accesseur sur le nombre de tours restant pour le buff
     * @return int - Nombre de tours restant pour le buff
     */
    public int getToursRestant()
    {
        return toursRestant;
    }

    /**
     * Modificateur du nombre de tours restant
     * @param toursRestant  - nombre de tours restant
     */
    public void setToursRestant(int toursRestant)
    {
        this.toursRestant = toursRestant;
    }

    /**
     * Accesseur sur la valeur du buff
     * @return int valeur valeur du buff
     */
    public int getValeur()
    {
        return valeur;
    }

    /**
     * Modificateur sur la valeur du buff
     * @param valeur - nouvelle valeur du buff
     */
    public void setValeur(int valeur)
    {
        this.valeur = valeur;
    }

    /**
     * Constructeur de Buff
     * @param nomatribut nom de l'attribut du buff
     * @param nbtours nombre de tours où le buff fait effet
     */
    public Buff(String nomatribut, int nbtours)
    {
        nomAttribut = nomatribut;
        toursRestant = nbtours;
    }
    /**
     * Applique l'effet du buff sur un personnage suivant sa puissance
     * @param p - Personnage su lequelle on fais l'effet
     * @param puissance - Puissance de l'effets
     */
    @Override
    public void effet(Personnage p, int puissance)
    {
        try
        {
            this.valeur = puissance;
            this.p = p;
            Method m = p.getClass().getMethod("set" + nomAttribut, Integer.TYPE);
            Method g = p.getClass().getMethod("get" + nomAttribut);
            p.ajouterBuff(this);
            m.invoke(p, (Integer) g.invoke(p) + valeur);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            System.out.println("L'application du buff a échoué.");
        }
    }

    /**
     * Annule l'effet buff sur un personnage
     * @param p - Le personnage sur lequel s'applique le buff
     */
    public void stopEffet()
    {
        try
        {
            Method m = p.getClass().getMethod("set" + nomAttribut, Integer.TYPE);
            Method g = p.getClass().getMethod("get" + nomAttribut);
            m.invoke(p, (Integer) g.invoke(p) - valeur);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            System.out.println(this.nomAttribut+" ("+this.valeur+") a disparu");
        }
    }

    /**
     * Retire un tour au buff
     * @return boolean - true si le buff n'a plus de durée, false sinon
     */
    public boolean passerTour()
    {
        toursRestant--;
        boolean test = false;
        if (toursRestant <= 0)
        {
            test = true;
            stopEffet();

            System.out.println("Le buff a été annulé\n");
        }
        return test;
    }
}
