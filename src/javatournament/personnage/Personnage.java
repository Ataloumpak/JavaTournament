package javatournament.personnage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import javatournament.combat.*;
import javatournament.data.Draw;
import javatournament.data.Son;
import javatournament.emitters.Emitter;
import javatournament.map.Map;
import javatournament.personnage.sorts.Sort;
import javatournament.personnage.sorts.competences.Buff;
import javatournament.personnage.sorts.competences.Competence;
import javatournament.personnage.sorts.competences.Consommable;
import javatournament.personnage.sorts.competences.Soin;
import org.newdawn.slick.*;
import org.newdawn.slick.openal.Audio;

/**
 * Classe abstraite définissant les caractéristiques communes à tout Personnage.
 * @author Quentin, Tony, pyarg
 */
public abstract class Personnage
{

    /**
     * Nombre de personnages
     */
    private static int nbPers;
    /**
     * Numero du personnage
     */
    private int numPers;
    /**
     * Nom du Personnage.
     */
    private String nom;
    /**
     * Cercle d'équipe du Personnage.
     */
    private Image cercle;
    /**
     * Cadre de HUD du Personnage.
     */
    private Image cadre;
    /**
     * Valeur de l'animation du Personnage.
     */
    private int anim;
    /**
     * Points de mouvement que le Personnage peut avoir au maximum.
     */
    private int PMMax;
    /**
     * Points de mouvement actuels du Personnage.
     */
    private int PMCrt;
    /**
     * Energie que le Personnage peut avoir au maximum.
     */
    private int energieMax;
    /**
     * Energie actuelle du Personnage.
     */
    private int energieCrt;
    /**
     * PDV que le Personnage peut avoir au maximum.
     */
    private int PDVMax;
    /**
     * PDV actuels du Personnage.
     */
    private int PDVCrt;
    /**
     * Armure du Personnage.
     */
    private int armure;
    /**
     * Attaque du Personnage.
     */
    private int attaque;
    /**
     * Esquive du Personnage.
     */
    private int esquive;
    /**
     * Tableau de Skin(Draw) du Personnage. en 0 - le skin normal en 1 - le skin
     * bleu en 2 - le skin rouge
     */
    private Image[] skin = new Image[3];
    /**
     * Ligne de position du Personnage.
     */
    private int posX;
    /**
     * Colonne de position du Personnage.
     */
    private int posY;
    /**
     * Description du Personnage
     */
    private static String description = "";
    /**
     * Liste du contenu du sac du personnage
     */
    private ArrayList<Consommable> sac;
    /**
     * Tableau d'nimation personnage en 0 - l'animation normal en 1 -
     * l'animation bleu en 2 - l'animation rouge
     */
    private SkinAnimation[] animPersonnage = new SkinAnimation[3];
    /**
     * Liste des sorts du personnage
     */
    private ArrayList<Sort> listeSort = new ArrayList<>();
    /**
     * Stocke les buffs actifs sur le personnage,qu'ils soient malus ou bonus
     */
    private ArrayList<Buff> buffs = new ArrayList<>();
    /**
     * Son du pas lors d'un déplacement.
     */
    //private static Sound pas;
    /**
     * Emetteurs de particules liÃ©s au Personnage.
     */
    private final ArrayList<Emitter> emitters;
    /**
     * Valeur de tacle du personange, permet de faire passer le tour au personnage ennemis.
     */
    private int tacle;

    /**
     * Constructeur de Personnage.
     * @param nom Nom du personnage
     * @param PMMax Points de mouvement que le Personnage possède au maximum.
     * @param energieMax Energie que le Personnage possède au maximum.
     * @param PDVMax Points de vie que le Personnage possède au maximum.
     * @param armure Armure du Personnage.
     * @param attaque Attaque du Personnage.
     * @param esquive Esquive du Personnage.
     * @param tacle Tacle du personnage.
     */
    public Personnage(String nom, int PMMax, int energieMax, int PDVMax, int armure, int attaque, int esquive, int tacle) {
        this.nom = nom;
        this.PMCrt = PMMax;
        this.PMMax = PMMax;
        this.energieCrt = energieMax;
        this.energieMax = energieMax;
        this.PDVCrt = PDVMax;
        this.PDVMax = PDVMax;
        this.armure = armure;
        this.attaque = attaque;
        this.esquive = esquive;
        this.tacle=tacle;
        this.sac = new ArrayList<>();
        initialiserSac();
        this.posX = 0;
        this.posY = 0;
        // Initialisation son
        /*if(pas==null) {
            pas = Son.PAS;
        }*/
        buffs = new ArrayList<>();
        sac = new ArrayList<>();
        emitters = new ArrayList<>();
    }

    //------------------------------------------------------------------------------------------------------------------//
    /**
     * Numero du personnage
     */
    public int getNumPers() {
        return this.numPers;
    }

    /**
     * Numero du personnage
     */
    public void setNumPers(int num)
    {
        this.numPers = num;
    }

    /**
     * accesseur au nom du personnage
     * @return String - nom du personnage
     */
    public String getNom()
    {
        return nom;
    }

    /**
     * Accesseur au cadre du personnage
     *
     * @return Draw - l'image du cadre
     */
    public Image getCadre()
    {
        return this.cadre;
    }

    /**
     * Accesseur au cercle du personnage
     *
     * @return Draw - l'image du cercle sous le personnage
     */
    public Image getCercle()
    {
        return this.cercle;
    }

    /**
     * accesseur à l'animation du personnage
     *
     * @return int - le numero de l'animation du personnage
     */
    public int getAnim()
    {
        return anim;
    }

    /**
     * Accesseur aux Points de vie actuels du Personnage.
     *
     * @return int - Les points de vie courants
     */
    public int getPDVCrt()
    {
        return PDVCrt;
    }

    /**
     * accesseur aux PDV max du personnage
     *
     * @return int - Les points de vie maximaux
     */
    public int getPDVMax()
    {
        return PDVMax;
    }

    /**
     * accesseur aux PM courants du personnage
     *
     * @return int -les points de mouvements courants
     */
    public int getPMCrt()
    {
        return PMCrt;
    }

    /**
     * accesseur aux PM max du personnage
     *
     * @return int - les points de mouvements Maximums
     */
    public int getPMMax()
    {
        return PMMax;
    }

    /**
     * accesseur à l'armure du personnage
     *
     * @return int - Armure du personnage
     */
    public int getArmure()
    {
        return armure;
    }

    /**
     * accesseur à l'attaque du personnage
     *
     * @return int - Attaque du personnage
     */
    public int getAttaque()
    {
        return attaque;
    }

    /**
     * accesseur à l'energie courante du personnage
     *
     * @return int - Energie courante du personnage
     */
    public int getEnergieCrt()
    {
        return energieCrt;
    }

    /**
     * accesseur à l'energie max du personnage
     *
     * @return int - Energie maximale du personnage
     */
    public int getEnergieMax()
    {
        return energieMax;
    }

    /**
     * accesseur à l'esquive du personnage
     *
     * @return int - Esquive du personnage
     */
    public int getEsquive()
    {
        return esquive;
    }

    /**
     * accesseur à la position en X du personnage
     *
     * @return int -Position horizontale du personnage
     */
    public int getPosX()
    {
        return posX;
    }

    /**
     * accesseur à la position en Y du personnage
     *
     * @return int - Position verticale du personnage
     */
    public int getPosY()
    {
        return posY;
    }

    /**
     * accesseur à la description du personnage
     *
     * @return Sting - Description du personnage
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * accesseur a l'animation du personnage
     *
     * @param int Couleur - la ligne designant la couleur du personnage
     * @return SkinAnimation - animation correspondant a la ligne correspondante
     */
    public SkinAnimation getAnimPersonnage(int couleur)
    {
        return animPersonnage[couleur];
    }

    /**
     * accesseur au skin du personnage
     *
     * @param couleurPersonnage - index de la couleur du skin du personnage
     * @return Draw - skin du personnage de la couleur désiré
     */
    public Image getSkin(int couleurPersonnage)
    {
        return skin[couleurPersonnage];
    }

    /**
     * accesseur aux sorts du personnage
     *
     * @param numeroSort
     * @return Sort - Sort indéxé en numeroSort
     */
    public Sort getSort(int numeroSort)
    {
        return listeSort.get(numeroSort);
    }

    /**
     * Retourne l'objet du sac a la position indiqué
     * @param pos
     * @return 
     */
    public Consommable getObjetSac(int pos)
    {
        return sac.get(pos);
    }
    
    /**
     * Retourne l'index du sort passé en parametre
     * @param S Le sort à rechercher
     * @return L'index du sort
     */
    public int getNumSort(Sort S)
    {
        int nbsort =-1,i=0;
        for (Sort s : listeSort)
        {
            if (!s.equals(S))
            {
                i++;
            } else
            {
                nbsort = i;
            }
        }
        return nbsort;
    }
    
    /**
     * modificateur du nom du personnage
     *
     * @param String nom - Nom du personnage
     */
    public void setNom(String nom)
    {
        this.nom = nom;
    }

    /**
     * modificateur de l'animation du personnage
     *
     * @param int anim - animation du personnage
     */
    public void setAnim(int anim)
    {
        this.anim = anim;
    }

    /**
     * modificateur du nombre de pdv courant du personnage
     *
     * @param int PDVCrt - points de vie courant du personnage
     */
    public void setPDVCrt(int PDVCrt)
    {
        this.PDVCrt = PDVCrt;
    }

    /**
     * modificateur du nombre de points de vie max du personnage
     *
     * @param int PDVMax - points de vie max du personnage
     */
    public void setPDVMax(int PDVMax)
    {
        this.PDVMax = PDVMax;
    }

    /**
     * modificateur du nombre de pm courant du personnage
     *
     * @param int PMCrt - points de mouvement courant du personnage
     */
    public void setPMCrt(int PMCrt)
    {
        this.PMCrt = PMCrt;
    }

    /**
     * modificateur de l'image du cadre
     *
     * @param String cadre - Référence du cadre du personnage
     */
    public void setCadre(String cadre) throws SlickException
    {
        this.cadre = new Image(cadre);
    }

    /**
     * modificateur de l'image du cercme
     *
     * @param String cercle - Référence du cercle du personnage
     */
    public void setCercle(String cercle) throws SlickException
    {
        this.cercle = new Image(cercle);
    }

    /**
     * Modificateur de l'image du cadre
     *
     * @param Image - Image du cadre.
     */
    public void setCadre(Image cadre) throws SlickException
    {
        this.cadre = cadre;
    }

    /**
     * Modificateur de l'image du cercle
     *
     * @param Image - Image du cercle
     */
    public void setCercle(Image cercle) throws SlickException
    {
        this.cercle = cercle;
    }

    /**
     * modificateur du nombre de pm max du personnage
     *
     * @param int PMMax - points de mouvement max du personnage
     */
    public void setPMMax(int PMMax)
    {
        this.PMMax = PMMax;
    }

    /**
     * modificateur de l'armure du personnage
     *
     * @param int armure - armure du personnage
     */
    public void setArmure(int armure)
    {
        this.armure = armure;
    }

    /**
     * modificateur de l'attaque du personnage
     *
     * @param int attaque - attaque du personnage
     */
    public void setAttaque(int attaque)
    {
        this.attaque = attaque;
    }

    /**
     * Modificateur de l'energie courante du personnage
     *
     * @param int energieCrt - energie courante du personnage
     */
    public void setEnergieCrt(int energieCrt)
    {
        this.energieCrt = energieCrt;
    }

    /**
     * Modificateur de l'energie Max du Personnage
     *
     * @param int energieMax - energie max du personnage
     */
    public void setEnergieMax(int energieMax)
    {
        this.energieMax = energieMax;
    }

    /**
     * Modificateur de l'Esquive du personnage
     *
     * @param int esquive - esquive du personnage
     */
    public void setEsquive(int esquive)
    {
        this.esquive = esquive;
    }

    /**
     * Modificateur de la position en ligne du Personnage.
     *
     * @param int posX - position en X du personnage
     */
    public void setPosX(int posX)
    {
        this.posX = posX;
    }

    /**
     * Modificateur de la position en colonne du Personnage.
     *
     * @param int posY - position en Y du personnage
     */
    public void setPosY(int posY)
    {
        this.posY = posY;
    }

    /**
     * Modificateur de la description du Personnage.
     *
     * @param String description - Description du Personnage
     */
    public void setDescription(String description)
    {
        Personnage.description = description;
    }

    /**
     * accesseur a l'animation du personnage @autor pyarg
     *
     * @param numColor - index de la couleur du personnage
     * @param color - référence de l'image de la couleur du personnage
     */
    public void setAnimPersonnageColor(int numColor, String color) throws SlickException
    {
        this.animPersonnage[numColor] = new SkinAnimation(color, 4, 4);
        this.skin[numColor] = this.animPersonnage[numColor].getAnimation(0).getImage(0);
    }

    /**
     * accesseur a l'animation du personnage @autor pyarg
     *
     * @param numColor - index de la couleur du personnage
     * @param color - Draw de la couleur du personnage
     */
    public void setAnimPersonnageColor(int numColor, Image color) throws SlickException
    {
        this.animPersonnage[numColor] = new SkinAnimation(color, 4, 4);
        this.skin[numColor] = this.animPersonnage[numColor].getAnimation(0).getImage(0);
    }

    //------------------------------------------------------------------------------------------------\\
    /**
     * Ajoute un sort a la liste des sorts @autor tony
     *
     * @param String nom - nom du personnage
     * @param int cout - cout du sort en energie
     * @param int puissance - puissance du sort
     * @param int portee - portée du sort
     * @param String urlimage - Référence de l'image
     * @param Compérence comp - competence du sort
     */
    public void addSort(String nom, int cout, int puissance, int portee, String urlimage, Competence... Comp)
    {
        try
        {
            listeSort.add(new Sort(nom, cout, portee, puissance, new Image(urlimage), Comp));
        } catch (Exception e)
        {
            System.err.println("Personnage : addSort : Le sort n'a pas été créé");
        }
    }

    /**
     * Ajoute un sort a la liste des sorts @autor tony
     *
     * @param String nom - nom du personnage
     * @param int cout - cout du sort en energie
     * @param int puissance - puissance du sort
     * @param int portee - portée du sort
     * @param String urlimage - Draw
     * @param Compérence comp - competence du sort
     */
    public void addSort(String nom, int cout, int puissance, int portee, Image image, Competence... Comp)
    {
        try
        {
            listeSort.add(new Sort(nom, cout, portee, puissance, image, Comp));
        } catch (Exception e)
        {
            System.err.println("Personnage : addSort : Le(s) sort(s) n'ont pas été créés");
        }
    }

    /**
     * Méthode pour dessiner un rectanle qui affiche les points de vie ou l'energie du personnage.<br/> 
     * Si pdv est 'true' on affiche les points de vie, sinon on affiche l'energie.
     * @param g Graphics utiliser.
     * @param pdv Booleen pour savoir si on affiche les points de vie ou l'energie.
     * @param X Abscisse où dessiner le rectangle.
     * @param Y Ordonnee où dessiner le rectangle
     * @param largeur Largeur du rectangle.
     * @param hauteur Hauteur du rectangle.
     * @param police - Police utiliser pour dessinner les informations sur la barre.
     */
    public void afficherBarreStats(Graphics g, boolean pdv, int X, int Y, int largeur, int hauteur, UnicodeFont police_blanc, UnicodeFont police_noir) {
        double coef;
        UnicodeFont tmp;
        
        //on calcule le coef en fonction de la caracteristique qu'on veux afficher
        if (pdv) {
            coef = ((double) PDVCrt) / PDVMax; //Pourcentage des points de vie restant.
        } 
        else {
            coef = ((double) energieCrt) / energieMax; //Pourcentage d'energie restant.
        }
        int taillePDV = (int)(coef * largeur)>=0?(int)(coef * largeur):0; //taille de la barre de points de vie
        String affiche; //Message à afficher sur la bar.

        //On affiche un rectangle blanc autour du rectangle de statistiques.
        g.setColor(Color.white);
        g.drawRect(X, Y, largeur, hauteur);
        //rectangle de fond, afficher uniquement si besoin( si le personnage à perdu des points de vie)
        if (coef < 1)
        {
            g.setColor(Color.black);
            g.fillRect(X + taillePDV + 1, Y + 1, largeur - taillePDV - 1, hauteur - 1);
        }
        //rectange de points de vie, on change sa couleur en fonction de la quantite de points de vie restante
        if (pdv) {
            if (coef > 0.5) {
                g.setColor(Color.green);
                tmp = police_noir;
            } 
            else if (coef >= 0.2) {
                g.setColor(Color.orange);
                tmp = police_blanc; 
            } 
            else {
                g.setColor(Color.red);
                tmp = police_blanc;
            }
            affiche = String.valueOf(PDVCrt);
        } 
        else {
            g.setColor(Color.blue);
            affiche = String.valueOf(energieCrt);
            tmp =  police_blanc;
        }
        //affichage de la barre de pdv
        g.fillRect(X + 1, Y + 1, taillePDV - 1, hauteur - 1);
        //affichage des points de vie du perso
        tmp.drawString(X + largeur / 2 - tmp.getWidth(affiche) / 2,
                Y + tmp.getHeight(affiche) / 6,
                affiche);
        //on remet la couleur à noir(couleur par defaut)
        g.setColor(Color.black);
    }

    /**
     * Affiche le HUD d'un personnage
     * @param g la fenetre
     * @param posx position horizontale
     * @param posy position verticale
     * @param tailx longueur du rectangle
     * @param taily largeur du rectangle
     */
    public void afficherstatpersonnage(Graphics g, int posx, int posy, int tailx, int taily, UnicodeFont police_blanc, UnicodeFont police_noir) {
       //Affichage de la barre de vie.
        afficherBarreStats(g, true, posx, posy, tailx, taily, police_blanc, police_noir);
        //Affichage de la barre d'energie.
        afficherBarreStats(g, false, posx, posy + taily, tailx, taily, police_blanc, police_noir);
        //Affichage des PM
        police_blanc.drawString((posx + tailx) - 40, posy + taily + 35, "PM : " + String.valueOf(PMCrt));
        //Affichage du nom du personnages
        police_blanc.drawString(posx + 5, posy + taily + 35, String.valueOf(nom));
        //Affichage du 'PV'       
        if( ((double) this.getPDVCrt() / this.PDVMax) < 0.2)
            police_blanc.drawString(posx + tailx / 10, posy + taily / 5, "PV");
        else
            police_noir.drawString(posx + tailx / 10, posy + taily / 5, "PV");
        //Affichage du 'Energie'
        police_blanc.drawString(posx + tailx / 10, posy + taily + taily / 5, "Energie");
    }
    /**
     * Affiche une fenêtre avec les sors et leurs descriptions.
     * @param g Graphics où afficher la fenetre.
     * @param x Abscisse où dessiner la fenêtre.
     * @param y Ordonnee où dessiner la fenêtre.
     * @param police Police avec laquelle on écrit les textes.
     */
    public void afficherSors(Graphics g, int x, int y, UnicodeFont police) {
        Color oldColor = g.getColor();
        g.setColor( new Color(0, 0, 0, 150) );
        g.fillRect(x, y, 200, 55*(listeSort.size()+1));
        int espace=((55*(listeSort.size()+1))-(40*(listeSort.size()+1)))/(listeSort.size()+2); //espace entre chaque sors
        Draw.dessinerTouche(g, x+5, y+espace, police, Color.green, "A" );
        Draw.EPEE.draw(x+50, y+espace+20-Draw.EPEE.getHeight()/2);
        police.drawString(x+80, y+espace, "Corps à corps" );
        police.drawString(x+80, y+espace+11, "Portée : 1" );
        police.drawString(x+80, y+espace+22, "Coût : 20" );
        police.drawString(x+80, y+espace+33, "Puissance "+this.attaque );
        for( int i=0; i<listeSort.size(); i++) {
            Draw.dessinerTouche(g, x+5, y+(40+espace)*(i+1)+espace, police, Color.green, String.valueOf((i+1)));
            listeSort.get(i).getSkin().draw(x+50, y+20-(listeSort.get(i).getSkin().getHeight()/2)+(40+espace)*(i+1)+espace);
            police.drawString(x+80, y+(40+espace)*(i+1)+espace, listeSort.get(i).getNom() );
            police.drawString(x+80, y+(40+espace)*(i+1)+espace+11, "Portée : "+listeSort.get(i).getPortee() );
            police.drawString(x+80, y+(40+espace)*(i+1)+espace+22, "Coût : "+listeSort.get(i).getCout() );
            police.drawString(x+80, y+(40+espace)*(i+1)+espace+33, "Puissance : "+listeSort.get(i).getPuissance() );
        }
        g.setColor(oldColor);
    }
    /*
     * -- -----------------------------------------------------------------
     * Toutes les méthodes en double ont pour but de simplifier la gestion du réseau.
     * La présence du paramètre dejaEnvoyé signifie, s'il est a vrai, l'information qu'il a deja été envoyé par un client, et que l'on a pas besoin de le renvoyer
     */

    /**
     * Déplace le personnage suivant la direction indiqué
     * @param dep Le mode déplacement est il activé?
     * @param direction La direction vers laquelle bouger le personnage
     * @return si le mouvement est réalisé
     */
    public boolean deplacer(boolean dep, int direction)
    {
        return this.deplacer(dep, direction, false, true);
    }

    /**
     * Déplace le personnage suivant la direction indiqué
     * @param dep Le mode déplacement est il activé?
     * @param direction La direction vers laquelle bouger le personnage
     * @return si le mouvement est réalisé
     */ 
    public boolean deplacer(boolean dep, int direction, boolean dejaEnvoye, boolean tacleesquiver)
    {
        boolean res = false;
        boolean testDeplacement = false;
        int PXc;
        int PYc;
        boolean esq=false;


        switch (direction)
        {
            case 1:
            {
                PYc = getPosY() - Map.tailleCaseMap + PhaseJeu.getPosdepP();
                testDeplacement = getPMCrt() > 0 /*
                         * pm positif
                         */
                        && dep /*
                         * deplacement active
                         */
                        && inContainer(PhaseJeu.getPosdepP(), getPosX(), PYc - 1 - getSkin(0).getHeight())/*
                         * dans la fenetre
                         */
                        && StaticData.map.getCasePersonnage(getPosX(), PYc) == 99/*
                         * case non occupee par un joueur
                         */
                        && StaticData.map.getTypeCase(getPosX() / 30, PYc / 30).isAccess(); /*
                 * case accessible
                 */
                break;
            }
            case 2:
            {
                PYc = getPosY() + Map.tailleCaseMap + PhaseJeu.getPosdepP();
                testDeplacement = getPMCrt() > 0
                        && dep
                        && inContainer(PhaseJeu.getPosdepP(), getPosX(), PYc - 1 - getSkin(0).getHeight())
                        && StaticData.map.getCasePersonnage(getPosX(), PYc) == 99
                        && StaticData.map.getTypeCase(getPosX() / 30, PYc / 30).isAccess();
                break;
            }
            case 3:
            {
                PXc = getPosX() + Map.tailleCaseMap;
                PYc = getPosY() + getSkin(0).getHeight();
                testDeplacement = getPMCrt() > 0
                        && dep
                        && inContainer(PhaseJeu.getPosdepP(), PXc, getPosY() + PhaseJeu.getPosdepP() - 1 - getSkin(0).getHeight())
                        && StaticData.map.getCasePersonnage((PXc), PYc) == 99
                        && StaticData.map.getTypeCase(PXc / 30, PYc / 30).isAccess();
                break;
            }
            case 4:
            {
                PXc = getPosX() - Map.tailleCaseMap;
                PYc = getPosY() + getSkin(0).getHeight();
                testDeplacement = getPMCrt() > 0 /*
                         * pm positif
                         */
                        && dep /*
                         * deplacement active
                         */
                        && inContainer(PhaseJeu.getPosdepP(), PXc, getPosY() + PhaseJeu.getPosdepP() - 1 - getSkin(0).getHeight())
                        && StaticData.map.getCasePersonnage((PXc), (PYc)) == 99
                        && StaticData.map.getTypeCase(PXc / 30, PYc / 30).isAccess();
                break;
            }
        }

        
        if (testDeplacement) {
            if(dejaEnvoye)
            {
                esq=tacleesquiver;
            }
            else
            {
                esq=esquiver();
            }
            
            if(esq) {
                StaticData.map.setCasePersonnage(getPosX(), (getPosY() + getSkin(0).getHeight()), 99);//on libere la case
                switch (direction) {
                    case 1:
                    {
                        setPosY(getPosY() - Map.tailleCaseMap);//calcul de la nouvelle position du personnage
                        setAnim(2);//modifie l'orientation de l'image du personnage
                        break;
                    }
                    case 2:
                    {
                        setPosY(getPosY() + Map.tailleCaseMap);//calcul de la nouvelle position du personnage
                        setAnim(0);//modifie l'orientation de l'image du personnage
                        break;
                    }
                    case 3:
                    {
                        setPosX(getPosX() + Map.tailleCaseMap);//calcul de la nouvelle position du personnage
                        setAnim(3);//modifie l'orientation de l'image du personnage
                        break;
                    }
                    case 4:
                    {
                        setPosX(getPosX() - Map.tailleCaseMap);//calcul de la nouvelle position du personnage
                        setAnim(1);//modifie l'orientation de l'image du personnage
                        break;
                    }
                }
            
            StaticData.map.setCasePersonnage(getPosX(), (getPosY() + getSkin(0).getHeight()), getNumPers());//on libere la case

            setPMCrt(getPMCrt() - 1);//on enleve 1 pm
            //pas.play(); //Bruit de pas
            
            res = true;
            }
            else {
                //PhaseJeu.getLog().ajoutMessage(ListeEquipes.getPersonnageCourant().getNom()+" a été taclé !");
                finTour();
                PhaseJeu.setDeplacement(false);
                res=false;
            }
        }
        if (ListeEquipes.getTypeJeu().equals(TypeJeu.RESEAU) && !dejaEnvoye)// Si on est en réseau, et si on n'a pas déjâ envoyé l'information au réseau
        {
            StaticData.client.envoie("10" + StaticData.transformeInt(StaticData.getIdentifiant()) + "/" + numPers + "/" + "deplacer" + "/" + dep + "/" + direction + "/" + esq);
        }
        
        return res;
    }

    /**
     * Gestion de la fin du tour, dans le cas de l'envoi sur le réseau
     */
    public void finTour()
    {
        finTour(false);
    }

    /**
     * Gestion de la fin du tour d'un personnage,
     * @param dejaEnvoye L'action etait 
     */
    public void finTour(boolean dejaEnvoye)
    {        
        if ( (ListeEquipes.getJoueurCourant())==(numPers/10))
        {
            PMCrt = PMMax;
            energieCrt = energieMax;
            ListeEquipes.passer(); //On change le personage courant

            if (ListeEquipes.getTypeJeu().equals(TypeJeu.RESEAU) && !dejaEnvoye)// Si on est en réseau, et si on n'a pas déjâ envoyé l'information au réseau
            {
                StaticData.client.envoie("10" + StaticData.transformeInt(StaticData.getIdentifiant()) + "/" + numPers + "/" + "finTour");
                
            }
            
        }
        else 
            System.out.println("Personnage : finTour : Tentative de faire passer un tour a l'adversaire prévenue");

    }

    /**
     * Gere le mouvement a la souris (Fonction momentanément suspendu)
     *
     * @param pmtot Le cout en pm du mouvement
     * @param PXc Position d'arrivé horizontale
     * @param PYc Position d'arrivé verticale
     */
    public void mvtSouris(int pmtot, int PXc, int PYc)
    {
        //calcul de la nouvelle position du personnage
        posX = PXc;
        posY = PYc;
        int Ptaille = getSkin(0).getHeight();
        StaticData.map.setCasePersonnage(posX, (posY + Ptaille), 99); //on libere la case
        StaticData.map.setCasePersonnage(posX, (posY + Ptaille), numPers);//on occupe la nouvelle case

        PMCrt = PMCrt - pmtot;//on enleve les pm
        //pas.play(); //Bruit de pas


    }

    /**
     * Permet de savoir si les coordonnées (x,y) pour le déplacement d'un
     * personnage sont valides
     * @param posmaxY position max des ordonnee
     * @param x abscisse
     * @param y ordonnee
     * @return boolean res vrai si les coordonnees (x,y) sont dans le container
     */
    public boolean inContainer(int posmaxY, int x, int y) // permet de savoir si la position demandé est dans l'écran ou non
    {
        boolean res = false;
        if (x > -1 && x < ((StaticData.map.getLargeurMap() * Map.tailleCaseMap)) && y > (0 - posmaxY + getSkin(0).getHeight()) && y < (StaticData.map.getHauteurMap() * Map.tailleCaseMap) - getSkin(0).getHeight())
        {
            res = true;
        }
        return res;
    }

    /**
     * Attaquer un personnage voisin
     * @param posX Position x de la case a attaquer
     * @param posY Position y de la case a attaquer
     */
    public void attaquer(int posX, int posY)
    {
        attaquer(posX, posY, false);
    }

    /**
     * Attaquer un personnage voisin, avec gestion du mode reseau
     * @param posX Position x de la case a attaquer
     * @param posY Position y de la case a attaquer
     * @param dejaEnvoye Si vrai, l'action a déja été envoyé sur le réseau
     */
    public void attaquer(int posX, int posY, boolean dejaEnvoye)
    {
        Map m = StaticData.map;
        Personnage P = ListeEquipes.getPersonnage(StaticData.map.getCasePersonnage(posX, posY));
        Log l = PhaseJeu.getLog();
        int degat;
        if ( (degat = (int) Math.round(this.attaque //Attaque du joueur
                * m.getTypeCase(posX / 30, posY / 30).getBonusAttaque() // Bonus d 'attaque de la case
                * (100.0/(100+P.getArmure())) // Armure de la cible
                * m.getTypeCase(P.getPosX() / 30, P.getPosY() / 30).getBonusArmure())// Bonus d'armure de la case de la cible
             ) > 0 // Si cela inflige des dommages
                && energieCrt >= 20)
        {

            P.PDVCrt = (int) (P.PDVCrt - degat);// on inflige les dommages
            l.ajoutLogDegat(P.getNom(), degat); // ajout des dommages dans le log récapitulatif
            /*
             * try { AfficherEffet(g, P, nom, degat); } catch (SlickException
             * ex) { l.ajoutMessage("Probleme lors de l'affichage du texte
             * flottant"); }
             */
            if (P.PDVCrt < 0)
            {
                P.PDVCrt = 0;
            }
            if (ListeEquipes.getTypeJeu().equals(TypeJeu.RESEAU) && !dejaEnvoye)
            {
                StaticData.client.envoie("10" + StaticData.transformeInt(StaticData.getIdentifiant()) + "/" + numPers + "/" + "attaquer" + "/" + posX + "/" + posY);
            }
            energieCrt -= 20;
        }
    }

    public void lancerSort(int x, int y, Sort S, Log l)
    {
        lancerSort(x, y, S, l, false);
    }

    /**
     * Permet de lancer un sort sur la case [x,y]
     *
     * @param x - Abscisse
     * @param y - OrdonnÃ©e
     * @param S - Sort Ã  lancer
     * @param l - BoÃ®te de logs
     */
    public void lancerSort(int x, int y, Sort S, Log l, boolean dejaEnvoye) {
        int nbsort = getNumSort(S);
        
        if (nbsort == -1)
        {
            System.err.println("Personnage : lancerSort : Sort non trouvé");
            return;
        }
        if (ListeEquipes.getTypeJeu().equals(TypeJeu.RESEAU) && !dejaEnvoye)
        {
            StaticData.client.envoie("10" + StaticData.transformeInt(StaticData.getIdentifiant()) + "/" + numPers + "/" + "lancerSort" + "/" + x + "/" + y + "/" + nbsort);
        }
    }

    public void lancerConsommable(Personnage P, Consommable C)
    {
        lancerConsommable(P, C, false);
    }

    /**
     * Action Utiliser un objet @autor Tony
     *
     * @param P Le personnage ciblé
     * @param C Le consommable utilisé
     */
    public void lancerConsommable(Personnage P, Consommable C, boolean dejaEnvoye)
    {
        int i = 0;
        int nbConso = 0;
        for (Consommable c : sac)
        {
            if (!c.equals(C))
            {
                i++;
            } else
            {
                nbConso = i;
            }
        }
        if (nbConso == 0)
        {
            System.err.println("Personnage : lancerConso : Consommable non trouvé");
            return;
        }

        if (ListeEquipes.getTypeJeu().equals(TypeJeu.RESEAU) && !dejaEnvoye)
        {
            StaticData.client.envoie("10" + StaticData.transformeInt(StaticData.getIdentifiant()) + "/" + numPers + "/" + "lancerConsommable" + "/" + Personnage.nbPers + "/" + nbConso);
        }
        C.utiliser(P);
    }

    /*
     * public void attaquer(Personnage P, String S ) { Consommable c =
     * sac.get(0); c.utiliser(P); }
     */
    /**
     * Initialise le Sac du personnage
     */
    private void initialiserSac()
    {
        sac = new ArrayList<>();
        sac.add(new Consommable("potion", new Soin(), 3, 20));
    }

    /**
     * ajoute le buff B a la liste des buffs (utilisé dans la classe Buff)
     *
     * @param B le buff a ajouter
     */
    public void ajouterBuff(Buff B)
    {
        buffs.add(B);
    }

    /**
     * Passe un tour a tous les buffs, et les supprime si fin de vie de celui-ci
     */
    public void gestionBuff()
    {
        int longueur = buffs.size();
        int i = 0;
        while (i < longueur)
        {
            //System.out.println("Personnage : gestionBuff : il reste  " + Buffs.get(i).getToursRestant() + " tours");
            if (buffs.get(i).passerTour())
            {
                /*
                 * System.out.println("le buff qui modifie: " +
                 * Buffs.get(i).getNomAttribut() + " est en train d'etre géré");
                 */
                buffs.remove(i);
                i--; //vue que le buff est supprimé, le suivant prend sa place, donc on doit contrer l'effet du i++ plus bas.
                longueur--; // car un buff a disparu

            }
            i++;
        }
    }

    /**
     * Gère le début de tour, gere les buffs, puis réinitialise les pm et
     * energies
     */
    public void debutTour()
    {
        if (buffs == null)
        {
            buffs = new ArrayList<>();
        }
        if (sac == null)
        {
            sac = new ArrayList<>();
        }
        gestionBuff();
        PMCrt = PMMax;
        energieCrt = energieMax;
    }

    /**
     * Méthode éxecutant l'action reçu par le web, lors de PhaseJeu
     *
     * @param contraction concatenation de plusieurs indices reçu du
     * web(serveur) a executer : numéro indiquant le type d'action N° joueur
     * N°Perso Position X de la cible Position Y de la cible Nom de la méthode
     * utilisé N° du sort ou du consommable (99 indique une absence de numéro)
     */
    public static void appliquerAction(String contraction) {

//        System.out.println("Action recu : " + contraction);
        // séparation des éléments
        String[] Elements = contraction.split("/");
        //"nomMeth,Type:param1,param2"

        // Recherche du joueur
        int numjoueur = Integer.valueOf(Elements[0].substring(2, 4));
//        System.out.println(numjoueur + " numero du joueur censé jouer");
        Joueur j = ListeEquipes.getJoueur(numjoueur);
//        System.out.println(ListeEquipes.getJoueurCourant() + " numero du joueur actif");


        //Recherche du personnage
        
        Personnage p = ListeEquipes.getPersonnage(Integer.valueOf(Elements[1]));

        int xcible;
        int ycible;
        Method m = null;
        switch (Elements[2])
        {
            case "deplacer":
                boolean mvtPoss = Boolean.valueOf(Elements[3]);
                int direction = Integer.valueOf(Elements[4]);
                boolean tacleesquiver=Boolean.valueOf(Elements[5]);
                //System.out.println("appliquerAction : Valeur tacleesquiver" + tacleesquiver);
                p.deplacer(mvtPoss, direction, true, tacleesquiver);
                break;
            case "finTour":
                p.finTour(true);
                break;
            case "attaquer":
                xcible = Integer.valueOf(Elements[3]);
                ycible = Integer.valueOf(Elements[4]);
                p.attaquer(xcible, ycible, true);
                break;
            case "lancerSort":
                // Recherche case cible
                xcible = Integer.valueOf(Elements[3]);
                //System.out.println("Case cible X :" + xcible);
                ycible = Integer.valueOf(Elements[4]);
                int nbsort = Integer.valueOf(Elements[5]);
                p.lancerSort(xcible, ycible, p.getSort(nbsort), null, true);
                break;
            case "lancerConsommable":
                Personnage cible = ListeEquipes.getPersonnage(Integer.valueOf(Elements[3]));
                int NumConso = Integer.valueOf(Elements[4]);
                p.lancerConsommable(cible, p.getObjetSac(NumConso), true);
                break;
            default:
                System.err.println("Personnage : appliquerAction() -> Le choix réseau n'a pas été reconnu");
        }

    }
    
    /**
     * Méthode pour ajouter une particule au personnage.
     * @param E Particule à ajouter au personnage.
     */
    public void addEmitter(Emitter E) {
        this.emitters.add(E);
    }

    /**
     * Méthode qui retourne la liste des particules du personanges
     * @return ArrayList<Emitter> ArrayList de particules.
     */
    public ArrayList<Emitter> getEmitters() {
        return this.emitters;
    }
    
    /**
     * Méthode pour afficher toutes les particules du personnages
     * @param gphc Graphics où afficher les particules
     */
    public void drawEmitters(Graphics gphc) {
        for (Emitter E : this.emitters) {
            E.draw(gphc);
        }
    }
    /**
     * Méthode pour annimer toutes les particules du personnages
     * @param gc GameContainer du jeu.
     * @param delta Delta de l'update du jeu.
     */
    public void animateEmitters(GameContainer gc, int delta)
    {
        int emitSize = this.emitters.size();
        if (emitSize > 0)
        {
            for (int i = 0; i < emitSize; i++)
            {
                Emitter E = this.emitters.get(i);
                if (!((E.getCoords()[0] <= E.getTarget().getCastingPoint()[0] + 5
                        && E.getCoords()[0] >= E.getTarget().getCastingPoint()[0] - 5
                        && E.getCoords()[1] <= E.getTarget().getCastingPoint()[1] + 5
                        && E.getCoords()[1] >= E.getTarget().getCastingPoint()[1] - 5)
                        || (E.getAnimType() == 5 && E.getTimer() >= gc.getFPS() * 2)
                        || (E.getAnimType() == 3 && E.getTimer() >= gc.getFPS())
                        || (E.getAnimType() == 9 && E.getTimer() >= gc.getFPS() * 2)))
                {
                    E.animate(gc, delta);
                } else
                {
                    E.getSort().effet(E.getTarget(), E.getTarget().getPosX(), E.getTarget().getPosY());
                    emitters.remove(E);
                    i--;
                    emitSize--;
                }
            }
        }
    }
    /**
     * Methode qui crée les coordonnées de lancement des sorts
     * @return float[] Coordonnées de lancement
     */
    public float[] getCastingPoint() {
        return new float[] { this.posX + 25 + this.skin[0].getWidth() / 2, this.posY + 15 + this.skin[0].getHeight() / 2};
    }    
    
    /**
     * Methode pour savoir si on peut esquiver le personnage à côté de nous.
     * @return boolean True si on peut esquiver, False sinon.
     */
    private static boolean esquiver() {
        int x = ListeEquipes.getPersonnageCourant().getPosX();
        int y = ListeEquipes.getPersonnageCourant().getPosY();
        int dodge = ListeEquipes.getPersonnageCourant().getEsquive();
        int tacle = 0;
        int id_bottom = 99;
        int id_left = 99;
        int id_right = 99;
        int id_top = 99;
        
        int id_equipe = ListeEquipes.getPersonnageCourant().getNumPers()/10;
        
        if(x/Map.tailleCaseMap > 0)
            id_left = StaticData.map.getCasePersonnage(x-Map.tailleCaseMap, y+Map.tailleCaseMap);
        if(x/Map.tailleCaseMap < StaticData.map.getLargeurMap())
            id_right = StaticData.map.getCasePersonnage(x+Map.tailleCaseMap, y+Map.tailleCaseMap);
        if(y/Map.tailleCaseMap > 0)
            id_top = StaticData.map.getCasePersonnage(x, y);
        if(y/Map.tailleCaseMap < StaticData.map.getHauteurMap())
            id_bottom = StaticData.map.getCasePersonnage(x, y+2*Map.tailleCaseMap);
        
        if((id_left != 99 && id_equipe != id_left/10))
            tacle += ListeEquipes.getPersonnage(id_left).tacle;
        if((id_right != 99 && id_equipe != id_right/10))
            tacle += ListeEquipes.getPersonnage(id_right).tacle;
        if((id_top != 99 && id_equipe != id_top/10))
            tacle += ListeEquipes.getPersonnage(id_top).tacle;
        if((id_bottom != 99 && id_equipe != id_bottom/10))
            tacle += ListeEquipes.getPersonnage(id_bottom).tacle;
        
        if(tacle != 0) //il y a un personnage adverse a coté
        {
            if(125+dodge-tacle > 0) //si il a une chance de sortir du tacle
                return Math.random() < (100.0/(125+dodge-tacle));
            return false;
        }
        return true;
    }
}