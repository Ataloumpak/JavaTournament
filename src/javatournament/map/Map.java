/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javatournament.map;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javatournament.combat.PhaseJeu;
import javatournament.combat.StaticData;
import javatournament.personnage.Personnage;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.newdawn.slick.SlickException;

/**
 *
 * @author quentin
 */
public class Map { 

/**
     * Tableau 3D de la Map
     */
    private int[][][] tabMap;
    /**
     * Liste des textures
     */
    private ArrayList<TypeCase> listeCase = new ArrayList<TypeCase>();
    /**
     * Hauteur de la  Map
     */
    private final int hauteurMap = 19;
    /**
     * Largeur de la Map
     */
    private final int largeurMap = 25;
    /**
     * taille de chaque case de la Map (largeur et hauteur)
     */
    public final static int tailleCaseMap = 30; //taille de chaque case de la Map (largeur et hauteur)
    /**
     * valeur de centrage en largeur pour afficher au centre de sa zone la Map
     */
    public final static int centrageYMap = 15; //valeur de centrage en largeur pour afficher au centre de sa zone la Map
    /**
     * valeur de centrage en hauteur pour afficher au centre de sa zone la Map
     */
    public final static int centrageXMap = 30; //valeur de centrage en hauteur pour afficher au centre de sa zone la Map
    
//ATTRIBUTS STATIC POUR LES TYPES DE CASES
    /**
     * Attributs d'accession à la case Herbe
     */
    public final static int HERBE=0;
    /**
     * Attributs d'accession à la case Sable
     */
    public final static int SABLE=1;
    /**
     * Attributs d'accession à la case Ronce
     */
    public final static int PLANTE=2;
    /**
     * Attributs d'accession à la case Glace
     */
    public final static int GLACE=3;
    /**
     * Attributs d'accession à la case Rocher
     */
    public final static int ROCHER=4
    /**
     * Attributs d'accession à la case Magma
     */;
    public final static int LAVE=5;
    /**
     * Attributs d'accession à la case Eau
     */
    public final static int EAU=6;
    /**
     * Attributs d'accession à la case Neige
     */
    public final static int NEIGE=7;
    /**
     * Attributs d'accession à la case Invisible
     */
    public final static int INVISIBLE=8;   
    /**
     * nom du fond de la Map
     */
    private String nom;
    
    static Document document;
    
    static Element racine;
    
    /**
     * Constructeur de la classe Map. 
     * Construit une Map en fonction d'un fichier XML
     * @throws SlickException
     */
    public Map(String S) throws SlickException
    {
        initCase();
        //Initialisation du tableau 3D
        tabMap = new int[largeurMap+1][hauteurMap+1][3];
        
        //Transforme le fichier XML en JDom et initialise l'element Racine
        this.init(S);
        
        //Initialisation par defaut de la map
        for(int i = 0; i < largeurMap;i++)
        {
            for(int j =0; j < hauteurMap; j++)
            {
                //Pour toutes les cases
                this.tabMap[i][j][1] = 0; //on alloue dans la deuxieme case du 3D le nom d'accession du nom du personnage
                this.tabMap[i][j][2] = 99;
                if( j==0 )
                    this.tabMap[i][j][0] = Map.INVISIBLE;                    
                else 
                    this.tabMap[i][j][0] = Map.NEIGE; 
            }
        }

        
        //On crée une List contenant tous les noeuds de l'Element racine
        List list = racine.getChildren();
        
        //On crée un Iterator sur notre liste
        Iterator t = list.iterator();

        //On parcours la liste
        while(t.hasNext())
        {
            //On recrée l'Element courant à chaque tour de boucle afin de
            //créer la Map en fonction de la balise Xml
            Element courant = (Element)t.next();

            //Si c'est un type de case
            if(!courant.getName().equals("fond"))
            {
                this.tabMap[Integer.valueOf(courant.getAttributeValue("x"))][Integer.valueOf(courant.getAttributeValue("y"))][1] = 0; //on alloue dans la deuxieme case du 3D le nom d'accession du nom du personnage
                this.tabMap[Integer.valueOf(courant.getAttributeValue("x"))][Integer.valueOf(courant.getAttributeValue("y"))][2] = 99;

                this.tabMap[Integer.valueOf(courant.getAttributeValue("x"))][0][0] = Map.INVISIBLE;

                if(courant.getName().equals("herbe"))
                    this.tabMap[Integer.valueOf(courant.getAttributeValue("x"))][Integer.valueOf(courant.getAttributeValue("y"))][0] = Map.HERBE;
                if(courant.getName().equals("sable"))
                    this.tabMap[Integer.valueOf(courant.getAttributeValue("x"))][Integer.valueOf(courant.getAttributeValue("y"))][0] = Map.SABLE;
                if(courant.getName().equals("plante"))
                    this.tabMap[Integer.valueOf(courant.getAttributeValue("x"))][Integer.valueOf(courant.getAttributeValue("y"))][0] = Map.PLANTE;
                if(courant.getName().equals("glace"))
                    this.tabMap[Integer.valueOf(courant.getAttributeValue("x"))][Integer.valueOf(courant.getAttributeValue("y"))][0] = Map.GLACE;
                if(courant.getName().equals("pierre"))
                    this.tabMap[Integer.valueOf(courant.getAttributeValue("x"))][Integer.valueOf(courant.getAttributeValue("y"))][0] = Map.ROCHER;
                if(courant.getName().equals("lave"))
                    this.tabMap[Integer.valueOf(courant.getAttributeValue("x"))][Integer.valueOf(courant.getAttributeValue("y"))][0] = Map.LAVE;
                if(courant.getName().equals("eau"))
                    this.tabMap[Integer.valueOf(courant.getAttributeValue("x"))][Integer.valueOf(courant.getAttributeValue("y"))][0] = Map.EAU;
                if(courant.getName().equals("neige"))
                    this.tabMap[Integer.valueOf(courant.getAttributeValue("x"))][Integer.valueOf(courant.getAttributeValue("y"))][0] = Map.NEIGE;
                if(courant.getName().equals("invisible"))
                    this.tabMap[Integer.valueOf(courant.getAttributeValue("x"))][Integer.valueOf(courant.getAttributeValue("y"))][0] = Map.INVISIBLE;
            }
            else {//Si c'est une image de fond
                this.nom = courant.getAttributeValue("nom");
            }
        }
    }
    
    /**
     * Constructeur d'une Map invisible.
     */
    public Map() throws SlickException{        
        initCase();    
        //Initialisation du tableau 3D
        tabMap = new int[largeurMap][hauteurMap][3];
        this.nom = "";
        for(int i = 0; i < largeurMap;i++)
        {
            for(int j =0; j < hauteurMap; j++)
            {
                //Pour toutes les cases
                this.tabMap[i][j][0] = Map.INVISIBLE;
                this.tabMap[i][j][1] = 0; //on alloue dans la deuxieme case du 3D le nom d'accession du nom du personnage
                this.tabMap[i][j][2] = 99;
            }
        }
    }
    
    /**
     * Retourne l'identifiant du fond de la map.
     * @return int
     */
    public String getNom() {
        return this.nom;
    }
    
    /**
     * Fonction qui transforme le fichier XML en une arborescence JDom
     * et on crée l'element racine du document
     * @param S 
     */
    public void init(String S)
    {
        //On crée une instance de SAXBuilder
        SAXBuilder sxb = new SAXBuilder();
        try
        {
            //On crée un nouveau document JDOM avec en argument le fichier XML
            document = sxb.build(new File(S));
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        //On initialise un nouvel élément racine avec l'élément racine du document.
        racine = document.getRootElement();
    }
    
    /**
     * Construction de la liste des textures
     * @throws SlickException
     */
    public final void initCase() throws SlickException
    {
        /* Ajout de tout les types de case à la liste */
        listeCase.add(new Herbe());
        listeCase.add(new Sable());
        listeCase.add(new Ronce());
        listeCase.add(new Glace());
        listeCase.add(new Rocher());
        listeCase.add(new Magma());
        listeCase.add(new Eau());
        listeCase.add(new Neige());
        listeCase.add(new Invisible());
    }
    /**
     * Accesseur de la Map
     * @return
     */
    public int[][][] getMap() {
        return tabMap;
    }
    /**
     * Accesseur de la case
     * @param i
     * @return
     */
    public TypeCase getCase(int i) {
        return listeCase.get(i);
    }
    /**
     * Accesseur du type de case
     * @param x - Coordonné de la largeur dans la fenetre de la case
     * @param y - Coordonné de la hauteur dans la fenetre de la case
     * @return
     */
    public TypeCase getTypeCase(int x, int y) {
        return listeCase.get(tabMap[x][y][0]);
    }
    /**
     * Accesseur de la hauteur de la Map
     * @return
     */
    public int getHauteurMap() {
        return this.hauteurMap;
    }
    /**
     * Accesseur de la largeur de la Map
     * @return
     */
    public int getLargeurMap() {
        return this.largeurMap;
    }
    /**
     * Accesseur, si la cible est ciblable ou non
     * @param x
     * @param y
     * @return
     */
    public int getCiblable(int x, int y)
    {
        return tabMap[x][y][1];
    }

    /**
     * Accesseur, si la case possède un personnage
     * @param x
     * @param y
     * @return
     */
    public int getCasePersonnage(int x, int y)
    {
        return tabMap[x/Map.tailleCaseMap][y/Map.tailleCaseMap][2];
    }

    /**
     * Modificateur, si la case possède un personnage
     * @param y
     * @param x
     * @param numListPerso
     */
    public void setCasePersonnage(int x, int y, int idPerso) {
        this.tabMap[x/Map.tailleCaseMap][y/Map.tailleCaseMap][2] = idPerso; //on alloue dans la deuxieme case du 3D le nom d'accession du nom du personnage
    }

     /**
     * Modificateur, si la cible est ciblable ou non
     * @param x
     * @param y
     */
    public void setCiblable(int x, int y)
    {
        if(PhaseJeu.getAttaque() || PhaseJeu.getSort())
        {
            tabMap[x][y][1]=1;
        }
        else
        {
            tabMap[x][y][1]=0;
        }
    }
    
    /**
     * Affiche dans la console la positions des Personnages de la map
     */
    public void afficherPersonnagesConsole()
    {
        System.out.println( "Positions des Personnages sur la map :");
        for(int j=0;j<=hauteurMap;j++){
            for(int i=0;i<=largeurMap;i++)
            {
                if( i==0 && j==0 )
                   System.out.print("   ");
                else if( i==0 ){
                    if( j<11)
                        System.out.print(" "+(j-1)+" ");
                    else
                        System.out.print( (j-1)+" ");
                }
                else if( j==0 ){
                    if( i<11)
                        System.out.print(" "+(i-1)+" ");
                    else
                        System.out.print((i-1)+" ");
                }
                else if( tabMap[i-1][j-1][2]==99 )
                   System.out.print("-- ");
                else if( tabMap[i-1][j-1][2]<10 )
                   System.out.print("0"+tabMap[i-1][j-1][2]+ " ");
                else
                   System.out.print(tabMap[i-1][j-1][2]+ " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    /**
     * Affiche dans la console la positions des Personnages de la map
     */
    public void afficherTypeCaseConsole()
    {
        System.out.println( "Types des cases de la map :");
        for(int j=0;j<=hauteurMap;j++){
            for(int i=0;i<=largeurMap;i++){
                if( i==0 && j==0 )
                   System.out.print("   ");
                else if( i==0 ){
                    if( j<11)
                        System.out.print(" "+(j-1)+" ");
                    else
                        System.out.print( (j-1)+" ");
                }
                else if( j==0 ){
                    if( i<11)
                        System.out.print(" "+(i-1)+" ");
                    else
                        System.out.print((i-1)+" ");
                }
                else 
                    System.out.print(" "+tabMap[i-1][j-1][0]+ " ");
            }
            System.out.println("");
        }
    }
    
    /**
     * Affiche dans la console la positions des Personnages de la map
     */
    public void afficherCasesCiblablesConsole()
    {
        System.out.println( "Cases ciblables de la map :");
        for(int j=0;j<=hauteurMap;j++){
            for(int i=0;i<=largeurMap;i++){
                if( i==0 && j==0 )
                   System.out.print("   ");
                else if( i==0 ){
                    if( j<11)
                        System.out.print(" "+(j-1)+" ");
                    else
                        System.out.print( (j-1)+" ");
                }
                else if( j==0 ){
                    if( i<11)
                        System.out.print(" "+(i-1)+" ");
                    else
                        System.out.print((i-1)+" ");
                }
                else if(tabMap[i-1][j-1][1]==1)
                    System.out.print("C"+tabMap[i][j][1]+" ");
                else 
                    System.out.print("-- ");
            }
            System.out.println("");
        }
    }
    
    /**
     * Méthode pour envoyer une map aux clients du serveur.
     * @param  out - flux sur lequel envoyer la map.
     */
    public void envoieMap(OutputStream out){
        PrintStream print = new PrintStream(out);     
        String x,y,type;
        for(int i=0; i < largeurMap;i++) {
            //ecriture correcte de l'abscisse
            x= StaticData.transformeInt(i);
            for(int j=0; j < hauteurMap; j++) {
                //ecriture correcte de l'ordonnée
                y= StaticData.transformeInt(j);
                //ecriture correcte du type d-e la case
                if( this.tabMap[i][j][0]<10 )
                    type="0"+this.tabMap[i][j][0];
                else
                    type=String.valueOf(this.tabMap[i][j][0]);
                //envoie
                print.println("01"+StaticData.transformeInt(StaticData.getIdentifiant())+x+y+type+nom);               
            }
        }
    }
    
    /**
     * Méthode qui modifie une case à partir d'une chaine de caractère.<br/>
     * Utile lors de la reception de la map du serveur.
     * @param s - String à traiter.
     */
    public void setCase( String s ){        
        if( s.startsWith("01") && s.endsWith(".png") ){
            int x = Integer.parseInt(s.substring(4, 6));
            int y = Integer.parseInt(s.substring(6, 8));
            int type = Integer.parseInt(s.substring(8,10));
            String idFond = s.substring(10);
            
            //si le fond n'est pas definie.
            if(!nom.equals(idFond))
                nom=idFond;
            //si x et y sont valident.
            if( x<largeurMap && y<hauteurMap )
                tabMap[x][y][0]=type;
        }
        else
            System.err.println("Map : String de recomposition invalide. "+s);
    }
    /**
     * Méthode pour déplacer un personnage.
     * @param P Personnage à déplacer.
     * @param x Abscisse où le déplacer.
     * @param y Ordonnée où le déplacer.
     */
    public void placerPersonnage(Personnage P, int x, int y) {
        if(this.getCasePersonnage(x, y) == 99) {
            this.setCasePersonnage(P.getPosX(), P.getPosY()+P.getSkin(0).getHeight(), 99);
            this.setCasePersonnage(x, y, P.getNumPers());
            P.setPosX(x);
            P.setPosY(y-P.getSkin(0).getHeight()+3);
        }
        else
            System.err.println("Map : deplacer personnage "+P.getNumPers()+" en ["+x+","+y+"]. ");
    }
}
