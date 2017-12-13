package javatournament.data;

import java.io.InputStream;
import java.util.ArrayList;
import javatournament.combat.Joueur;
import javatournament.combat.StaticData;
import javatournament.personnage.SkinAnimation;
import org.newdawn.slick.*;

/**
 *
 * @author pyarg
 */
public class Draw
{
    /**
     * Filtre à appliquer sur les fonds pour l'effet d'éclair
     */
    public static Color filter = new Color(.1f, .1f, .1f, 1.f);
    
    //ATTRIBUTS STATIQUES DES PERSONNNAGES
    public static Image CASEMAP;
    public static Image EPEE;
    public static Image[] CADRE=new Image[2];
    public static Image[] CERCLE=new Image[2];
    /**
     * Tableau des Images du Clerc
     */
    public static Image[] CLERC_SKIN=new Image[3];
    public static Image[] CLERC_SORTS=new Image[4];
    /**
     * Tableau des Images du Guerrier
     */
    public static Image[] GUERRIER_SKIN=new Image[3];
    public static Image[] GUERRIER_SORTS=new Image[4];
    /**
     * Tableau des Images du Mage
     */
    public static Image[] MAGE_SKIN=new Image[3];
    public static Image[] MAGE_SORTS=new Image[4];
    /**
     * Tableau des Images du Voleur
     */
    public static Image[] VOLEUR_SKIN=new Image[3];
    public static Image[] VOLEUR_SORTS=new Image[4];
    
    //ATTRIBUTS STATIQUES DES IMAGE & ANIMATIONS DU MENU PRINCIPALE
    public static Image FOND;
    public static Image FONDMENU;
    public static Image FONDPERSO;
    public static SkinAnimation[] ACCEUIL=new SkinAnimation[4];
    
    //ATTRIBUTS STATIC DE L'IMAGE DU CURSEUR
    private static Image CURSEUR;
    public static Image INFO;
    public static Image[] PHASEJEU=new Image[3];
    public static Image[] MENUCHOIX=new Image[6];
    public static Image MENUEQUIPE;   
    
    //ATTRIBUTS STATIC DES IMAGES DE PARTICULES
    public static Image BLUE_PART;
    public static Image YELLOW_PART;
    public static Image HEAL_PART;
    public static Image SHIELD_PART;
    public static Image DUPUIS_PART;
    public static Image RAIN_PART;
    public static Image UV_PART;
    public static Image BSOD_PART;
    public static Image BISOU_PART;
    public static Image DOOR_PART;
    public static Image CLOUD;
    public static Image SQL_PART;
    public static Image PHISHING_PART;
    public static Image JAILBREAKING_PART;
    public static Image FIREWALL_PART;
    public static Image GRAP_PART;
    public static Image BUBBLE_PART;
    public static Image JOUVENCE_PART;
    
    //ATTRIBUTS STATIC DES IMAGES D'INTRO.
    public static Image LOGO;
    public static Image TITRE;
    
    //ATTRIBUTS DE DESSIN DES MENUS    
    public static final int largeurMenu=600; //largeur d'un champ du menu
    public static final int hauteurMenu=110; //hauteur d'un champ du menu
    public static final int epaisseurMenu=3; //epaisseur de la bordure de selection
    
    /**
     * Genere l'Image de base de la map
     */
    public static boolean imageMap() {
        boolean res=false;
        if( CASEMAP==null ){
            try{
                InputStream is = Draw.class.getResourceAsStream("images/case.png");
                CASEMAP = new Image( is, "caseMap"+StaticData.getIdentifiant(), false );
                res=true;
            }
            catch (Exception e){
                System.err.println("Draw : erreur de génération des images de la map, "+e.getMessage());
            }                
        }
        return res;
    }
    /**
     * Genere les Image du Joueur
     */
    public static void imageJoueur()
    {
        if( CADRE[Joueur.BLEU-1]==null ){
            try{
                CADRE[Joueur.BLEU-1]=new Image( Draw.class.getResourceAsStream("images/hud/fond_perso_bleu.png"), "fond_perso_bleu", false );
                CERCLE[Joueur.BLEU-1]=new Image( Draw.class.getResourceAsStream("images/cercle_bleu.png"), "cercle_bleu", false );
                CADRE[Joueur.ROUGE-1]=new Image( Draw.class.getResourceAsStream("images/hud/fond_perso_rouge.png"), "fond_perso_rouge", false );
                CERCLE[Joueur.ROUGE-1]=new Image( Draw.class.getResourceAsStream("images/cercle_rouge.png"), "cercle_rouge", false );
                EPEE=new Image( Draw.class.getResourceAsStream("images/sort/epee.png"), "epee", false );
            }
            catch (Exception e)        {
                e.getMessage();
            }
        }
    }
    /**
     * Genere les Images du Clerc
     */
    public static void imageClerc()
    {
        if( CLERC_SKIN[0]==null ){
            try{
                CLERC_SKIN[0]=new Image( Draw.class.getResourceAsStream("images/tileset/moine.png"), "moine", false );
                CLERC_SKIN[1]=new Image( Draw.class.getResourceAsStream("images/tileset/moine_bleu.png"), "moine_bleu", false );
                CLERC_SKIN[2]=new Image( Draw.class.getResourceAsStream("images/tileset/moine_rouge.png"), "moine_rouge", false );
                // Génération des sorts
                CLERC_SORTS[0]=new Image( Draw.class.getResourceAsStream("images/sort/dauvou/sousib.png"), "moine_sort_1", false );
                CLERC_SORTS[1]=new Image( Draw.class.getResourceAsStream("images/sort/dauvou/jouvence.png"), "moine_sort_2", false );
                CLERC_SORTS[2]=new Image( Draw.class.getResourceAsStream("images/sort/dauvou/offrande.png"), "moine_sort_3", false );
                CLERC_SORTS[3]=new Image( Draw.class.getResourceAsStream("images/sort/dauvou/effet_dupuis.png"), "moine_sort_4", false );
            }
            catch (Exception e){
                e.getMessage();
            }
        }
    }
    /**
     * On genere les Images du Guerrier
     */
    public static void imageGuerrier() {
        if( GUERRIER_SKIN[0]==null ){
            try{
                // Génération des skins
                GUERRIER_SKIN[0]=new Image( Draw.class.getResourceAsStream("images/tileset/guerrier.png"), "guerrier", false );
                GUERRIER_SKIN[1]=new Image( Draw.class.getResourceAsStream("images/tileset/guerrier_bleu.png"), "guerrier_bleu", false );
                GUERRIER_SKIN[2]=new Image( Draw.class.getResourceAsStream("images/tileset/guerrier_rouge.png"), "guerrier_rouge", false );
                // Génération des sorts
                GUERRIER_SORTS[0]=new Image( Draw.class.getResourceAsStream("images/sort/riou/force.png"), "guerrier_sort_1", false );
                GUERRIER_SORTS[1]=new Image( Draw.class.getResourceAsStream("images/sort/riou/endurcissement.png"), "guerrier_sort_2", false );
                GUERRIER_SORTS[2]=new Image( Draw.class.getResourceAsStream("images/sort/riou/perseverance.png"), "guerrier_sort_3", false );
                GUERRIER_SORTS[3]=new Image( Draw.class.getResourceAsStream("images/sort/riou/cognard.png"), "guerrier_sort_4", false );
            }
            catch (Exception e){
                e.getMessage();
            }
        }
    }
    /**
     * Genere les Images du Mage
     */
    public static void imageMage() {
        if( MAGE_SKIN[0]==null ){
            try{
                // Génération des skins
                MAGE_SKIN[0]=new Image( Draw.class.getResourceAsStream("images/tileset/bibi.png"), "mage", false );
                MAGE_SKIN[1]=new Image( Draw.class.getResourceAsStream("images/tileset/bibi_bleu.png"), "mage_bleu", false );
                MAGE_SKIN[2]=new Image( Draw.class.getResourceAsStream("images/tileset/bibi_rouge.png"), "mage_rouge", false );
                // Génération des sorts
                MAGE_SORTS[0]=new Image( Draw.class.getResourceAsStream("images/sort/zyndac/lumiere_sordide.png"), "mage_sort_1", false );
                MAGE_SORTS[1]=new Image( Draw.class.getResourceAsStream("images/sort/zyndac/eruption_solaire.png"), "mage_sort_2", false );
                MAGE_SORTS[2]=new Image( Draw.class.getResourceAsStream("images/sort/zyndac/eclair.png"), "mage_sort_3", false );
                MAGE_SORTS[3]=new Image( Draw.class.getResourceAsStream("images/sort/zyndac/obscurite.png"), "mage_sort_4", false );
            }
            catch (Exception e){
                e.getMessage();
            }
        }
    }
    /**
     * Genere les Images du Voleur
     */
    public static void imageVoleur() {
        if( VOLEUR_SKIN[0]==null ){
            try{
                // Génération des skins
                VOLEUR_SKIN[0]=new Image( Draw.class.getResourceAsStream("images/tileset/voleur.png"), "voleur", false );
                VOLEUR_SKIN[1]=new Image( Draw.class.getResourceAsStream("images/tileset/voleur_bleu.png"), "voleur_bleu", false );
                VOLEUR_SKIN[2]=new Image( Draw.class.getResourceAsStream("images/tileset/voleur_rouge.png"), "voleur_rouge", false );
                // Génération des sorts
                VOLEUR_SORTS[0]=new Image( Draw.class.getResourceAsStream("images/sort/lervoul/vice.png"), "voleur_sort_1", false );
                VOLEUR_SORTS[1]=new Image( Draw.class.getResourceAsStream("images/sort/lervoul/trouble.png"), "voleur_sort_2", false );
                VOLEUR_SORTS[2]=new Image( Draw.class.getResourceAsStream("images/sort/lervoul/pillage.png"), "voleur_sort_3", false );
                VOLEUR_SORTS[3]=new Image( Draw.class.getResourceAsStream("images/sort/lervoul/sournoiserie.png"), "voleur_sort_4", false );
            }
            catch (Exception e){
                e.getMessage();
            }
        }
    }
    /**
     * Images de l'intro
     */
    public static void imageIntro() {
        if(LOGO==null) {
            try {
                LOGO = new Image(Draw.class.getResourceAsStream("images/logo.png"), "logo", false );
                TITRE = new Image(Draw.class.getResourceAsStream("images/titre.png"), "title", false );
            }
            catch (SlickException e) {
                e.getMessage();
            }
        }
    }
    /**
     * Genere les Images du menu principale 
     */
    public static void ImageMenuPrincpial() {
        if( FOND==null ){
            try{
                FOND=new Image( Draw.class.getResourceAsStream("images/acceuil/start.png"), "start", false );
                FONDMENU=new Image( Draw.class.getResourceAsStream("images/fond_menu.png"), "menu", false );
                FONDPERSO=new Image( Draw.class.getResourceAsStream("images/fond_personnage.png"), "fond_perso", false );
                ACCEUIL[0] = new SkinAnimation( Draw.class.getResourceAsStream("images/acceuil/fontaine.png"), "fontaine", 4, 4);
                ACCEUIL[1] = new SkinAnimation( Draw.class.getResourceAsStream("images/acceuil/forgeron.png"), "forgeron", 4, 4);
                ACCEUIL[2] = new SkinAnimation( Draw.class.getResourceAsStream("images/acceuil/bonus-mario.png"), "bonus-mario", 4, 4);
                ACCEUIL[3] = new SkinAnimation( Draw.class.getResourceAsStream("images/acceuil/voyante.png"), "voyante", 4, 4);
            }
            catch (Exception e){
                e.getMessage();
            }
        }
    }
    /**
     * Methode qui génère et retourne l'image du curseur.
     * @return Image
     */
    public static Image imageCurseur() {
        try{
            CURSEUR=new Image( Draw.class.getResourceAsStream("images/curseur.png"), "curseur", false );
            return CURSEUR;
        }
        catch (Exception e)
        {
            e.getMessage();
            return null;
        }
    }
    /**
     * Genere les Images de PhaseJeu
     */
    public static void phaseJeu() {
        if( PHASEJEU[0]==null ){
            try{
                PHASEJEU[0] =new Image( Draw.class.getResourceAsStream("images/hud/fond_hud_droit.png"), "fond_hud", false);
                PHASEJEU[1] =new Image( Draw.class.getResourceAsStream("images/personnage_courant.png"), "personnage_courant", false);
                PHASEJEU[2] = new Image( Draw.class.getResourceAsStream("images/zone.png"), "zone", false);
            }
            catch (Exception e){
                e.getMessage();
            }
        }
    }
    /**
     * Genere les Images de MenuChoix
     */
    public static void menuChoixPersonnages() {
        if( MENUCHOIX[0]==null ){
            try{
                MENUCHOIX[0] = new Image( Draw.class.getResourceAsStream("images/arcaniste.png"), "arcaniste", false);
                MENUCHOIX[1] = new Image( Draw.class.getResourceAsStream("images/soldat.png"), "soldat", false);
                MENUCHOIX[2] = new Image( Draw.class.getResourceAsStream("images/assassin.png"), "assassins", false);
                MENUCHOIX[3] = new Image( Draw.class.getResourceAsStream("images/clerc.png"), "clerc", false);
                MENUCHOIX[4] = new Image( Draw.class.getResourceAsStream("images/fleche_droite.png"), "fleche_droit", false);
                MENUCHOIX[5] = new Image( Draw.class.getResourceAsStream("images/fleche_gauche.png"), "fleche_gauche", false);
            }
            catch (Exception e){
                e.getMessage();
            }
        }
    }
    /**
     * Genere les Images de MenuEquipe
     */
    public static void menuEquipe() {
        if( MENUEQUIPE==null ){
            try{
                MENUEQUIPE = new Image( Draw.class.getResourceAsStream("images/parchemin_equipe.png"), "parchemin", false);
            }
            catch (Exception e){
                e.getMessage();
            }
        }
    }
    /**
     * Genere les images des particules.
     */
    public static void imageParticule() {
        if(BLUE_PART==null) {
            try {
                BLUE_PART = new Image(Draw.class.getResourceAsStream("images/particles/blue_light.png"), "blue", false);
                YELLOW_PART = new Image(Draw.class.getResourceAsStream("images/particles/yellow_light.png"), "yellow", false);
                HEAL_PART = new Image(Draw.class.getResourceAsStream("images/particles/heal_part_off.png"), "heal", false);
                SHIELD_PART = new Image(Draw.class.getResourceAsStream("images/particles/shining_part_off.png"), "shield", false);
                DUPUIS_PART = new Image(Draw.class.getResourceAsStream("images/particles/dupuis_part_off.png"), "dupuis", false);
                RAIN_PART = new Image(Draw.class.getResourceAsStream("images/particles/rain_off.png"), "rain", false);
                JOUVENCE_PART = new Image(Draw.class.getResourceAsStream("images/particles/jouvence_part_off.png"), "jouvence", false);
                UV_PART = new Image(Draw.class.getResourceAsStream("images/particles/uv_part_off.png"), "uv", false);
                BSOD_PART = new Image(Draw.class.getResourceAsStream("images/particles/bsod.png"), "bsod", false);
                BISOU_PART = new Image(Draw.class.getResourceAsStream("images/particles/lips_part_off.png"), "lips", false);
                DOOR_PART = new Image(Draw.class.getResourceAsStream("images/particles/backdoor.png"), "door", false);
                CLOUD = new Image(Draw.class.getResourceAsStream("images/particles/cloud.png"), "cloud", false);
                SQL_PART = new Image(Draw.class.getResourceAsStream("images/particles/seringue_off.png"), "sql", false);
                FIREWALL_PART = new Image(Draw.class.getResourceAsStream("images/particles/firewall_off.png"), "firewall", false);
                GRAP_PART = new Image(Draw.class.getResourceAsStream("images/particles/grap_off.png"), "grap", false);
                PHISHING_PART = new Image(Draw.class.getResourceAsStream("images/particles/phishing_off.png"), "phishing", false);
                JAILBREAKING_PART = new Image(Draw.class.getResourceAsStream("images/particles/jailbreaking_off.png"), "jailbreaking", false);
                BUBBLE_PART = new Image(Draw.class.getResourceAsStream("images/particles/bubble_part_off.png"), "bubble", false);
                
            }
            catch(Exception e) {
                System.err.println("Draw : impossible de générer les images des particules . "+e.getMessage());
            }
        }
    }
    /**
     * Méthode pour afficher une fenetre d'information.
     * @param gc GameContainer de la fenetre du jeu.
     * @param g Graphics ou afficher la fenetre.
     * @param titre Titre de la fenêtre.
     * @param message Message à afficher dans la fenêtre.
     * @param ok Booleen si on appuie sur "ok".
     */
    public static void fenetre(GameContainer gc, Graphics g, String titre, String message, boolean ok) {
        if( !ok ){           
            Color old = g.getColor();
            UnicodeFont policeTitre = Police.UNICODEFONT(Police.AVERIA[Police.BOLD], 20, java.awt.Color.WHITE);
            UnicodeFont policeMessage = Police.UNICODEFONT(Police.AVERIA[Police.REGULAR], 20, java.awt.Color.BLACK);
            int largeur=400;
            int hauteur=300;
            int abscisse=(gc.getWidth()/2)-(largeur/2);
            int ordonne=(gc.getHeight()/2)-(hauteur/2);
            //fond de la fenetre
            g.setColor( new Color(0, 0, 0, 100));
            g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
            //contour de la fenetre
            g.setColor(Color.darkGray);        
            g.drawRect(abscisse, ordonne, largeur, hauteur);
            //Corps de la fenetre
            g.setColor(Color.lightGray);        
            g.fillRect( abscisse+1, ordonne+1, largeur-1, hauteur-1);
            //bouton "ok"
            g.setColor(Color.white);     
            g.fillRoundRect( abscisse+largeur/4, ordonne+hauteur-hauteur/8, largeur/2, hauteur/10, 10 );
            policeMessage.drawString( abscisse+largeur/2-(policeMessage.getWidth("OK")/2), ordonne+hauteur-hauteur/8+(policeMessage.getHeight("OK")/4), "OK");
            //titre
            g.setColor(Color.black);     
            g.fillRect( abscisse, ordonne, largeur, hauteur/10);
            policeTitre.drawString(abscisse+largeur/2-(policeMessage.getWidth(titre)/2), ordonne+hauteur/20-(policeTitre.getHeight(titre)/2), titre);
            //message

            if( policeMessage.getWidth(message)<largeur )
                policeMessage.drawString(abscisse+(largeur/2-policeMessage.getWidth(message)/2), ordonne+30, message);
            else{
                Boolean fin = true;
                int deb=0;
                ArrayList<String> liste = new ArrayList<>();
                while( fin ){
                    if( message.substring(deb).length()>32 ){
                        liste.add(message.substring(deb, deb+32) );
                    }
                    else if( message.substring(deb).length()<=32 ){
                        liste.add( message.substring(deb) );
                        fin =false;
                    }
                    deb+=32;
                }
                for(int i=0;i<liste.size();i++)
                    policeMessage.drawString(abscisse+(largeur/2-policeMessage.getWidth(liste.get(i))/2), 
                                                ordonne+30+i*(policeMessage.getHeight(liste.get(i))+4), 
                                                liste.get(i));
            }
            g.setColor(old);
        }  
    } 
    
    /**
     * Méthode pour faire un effet d'éclair à l'écran.
     * @param filter Couleur à modifier pour faire l'effet.
     * @param dejaEclair Booleen à modifier pour faire l'effet.
     */
    public static void eclair(boolean dejaEclair) {
        if(Math.random() > 0.999) {
            filter = new Color(1.f, 1.f, 1.f, 1.f);
            dejaEclair = true;
        }

        if( filter.r > 0.1f)
            filter = new Color( filter.r-0.005f, filter.g-0.005f, filter.b-0.005f, filter.a);
        else
            dejaEclair = false;
    }
    
    /**
     * Methode qui dessine sur le menu.<br/>
     * Le menu est centrer au milieux de la fenetre. 
     * @param gc GameContainer de la fenetre.
     * @param g Graphics du jeu.
     * @param police Police des messages à afficher.
     * @param choix Tableau de choix choix fais par le joueur.
     * @param messages Tableau des messages à affiche dans le menu.
     */
    public static void menu( GameContainer gc, Graphics g, UnicodeFont police, boolean[] choix, String ... messages ) {
        if( choix.length>=messages.length) {
            //CALCUL DES VARIABLES
            int abscisse=(gc.getWidth()/2)-(largeurMenu/2);
            int ordonne=(gc.getHeight()-hauteurMenu*messages.length)/(messages.length+1); //espace entre les champs
            Color old=g.getColor(); //on sauvegarde la couleur des graphics

            //AFFICHAGES
            for( int i=0; i<messages.length; i++) {
                g.setColor(Color.white);
                if( choix[i] )
                    g.fillRoundRect(abscisse-epaisseurMenu, ordonne+(hauteurMenu+ordonne)*i-epaisseurMenu, largeurMenu+epaisseurMenu*2, hauteurMenu+epaisseurMenu*2, 20);
                else 
                    g.fillRoundRect(abscisse, ordonne+(hauteurMenu+ordonne)*i, largeurMenu, hauteurMenu, 20);
                
                g.setColor(Color.lightGray);
                g.fillRoundRect(abscisse+epaisseurMenu, ordonne+(hauteurMenu+ordonne)*i+epaisseurMenu, largeurMenu-epaisseurMenu*2, hauteurMenu-epaisseurMenu*2, 20);
                try {
                    police.drawString( abscisse+epaisseurMenu+largeurMenu/2-epaisseurMenu+epaisseurMenu-police.getWidth( messages[i] )/2,
                                    ordonne+(hauteurMenu+ordonne)*i+epaisseurMenu+hauteurMenu/2-epaisseurMenu-police.getHeight( messages[i] )/2,
                                    messages[i] );
                }
                catch(Exception e){
                    System.err.println("Draw : menu() -> erreur lors de l'affichage des textes. "+e.getMessage());
                }
            }//fin for     
            //GESTION DES TOUCHES
            if(gc.getInput().isKeyPressed(Input.KEY_DOWN)) { //si on appuie sur la touche bas.
                if( choix[choix.length-1] ) {
                    choix[choix.length-1]=false;
                    choix[0]=true;
                }
                else {
                    for( int j=choix.length-1;j>=0;j--) {
                        if( choix[j] ) {
                            choix[j]=false;
                            choix[j+1]=true;
                        }

                    }
                }
            }
            else if(gc.getInput().isKeyPressed(Input.KEY_UP)) { //si on appuie sur la touche bas.
                if( choix[0] ){
                    choix[0]=false;
                    choix[choix.length-1]=true;
                }
                else {
                    for( int j=0;j<choix.length;j++) {
                        if( choix[j] ) {
                            choix[j]=false;
                            choix[j-1]=true;
                        }

                    }
                }
            }
            //on réattribut l'ancienne couleur au graphics
            g.setColor(old);
        }
        else {
            System.err.println("Draw : menu() -> le tableau de booleen est trop petit.");
        }
    }
    /**
     * Méthode pour initialiser le tableau de boolean d'un menu.
     * @param choix Tableau de booleen à initialiser.
     * @return boolean[]
     */ 
    public static boolean[] initChoix( boolean[] choix ) {
        for( int p=0; p<choix.length; p++ ){
            if(p==0)
                choix[p]=true;
            else
                choix[p]=false;            
        }
        return choix;
    }
    
    /**
     * Méthode permettant de dessiner une touche.
     * @param graph Graphics où afficher les touches
     * @param x Abscisse de la touche
     * @param y Ordonnee de la touche
     * @param police Police de la touche.
     * @param c Couleur de la touche
     * @param s Text de la touche
     */
    public static void dessinerTouche(Graphics graph, int x, int y, UnicodeFont police, Color c, String s) {
        Color old = graph.getColor(); //on sauvegarde la couleur du graphics
        graph.setColor(Color.black);
        graph.fillRoundRect(x+2, y+2, 40, 40, 10);
        graph.setColor(c);
        graph.fillRoundRect(x, y, 40, 40, 10);
        police.drawString(x+20-police.getWidth(s)/2, y+13, s, Color.black);
        graph.setColor(old);
    }
}
