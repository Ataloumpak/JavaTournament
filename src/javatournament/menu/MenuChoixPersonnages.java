package javatournament.menu;

import java.io.IOException;
import javatournament.JavaTournament;
import javatournament.combat.Joueur;
import javatournament.combat.ListeEquipes;
import javatournament.combat.StaticData;
import javatournament.combat.TypeJeu;
import javatournament.data.Draw;
import javatournament.data.Police;
import javatournament.map.Map;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Menu qui permet de choisir les personnages de chaque joueur
 * @author Quentin, pyarg
 */
public class MenuChoixPersonnages extends BasicGameState {

    /**
     * Identifiant de la State.
     */
    private int stateID = -1;

    /**
     * Musique d'ambiance du Choix des Personnages.
     */
    private Music musique;

    /**
     * Identifiant de la classe (de Personnage).
     */
    private static int id = 0;

    /**
     * Variable permettant la répartition des Personnages entre les 2 Equipes.
     */
    private boolean repartition = true;

    /**
     * Position en largeur du premier perso de chaque equipe.
     */
    private int posXequipe = 9*Map.tailleCaseMap;

    /**
     * Position en hauteur de l'Equipe1
     */
    private int posYequipe1 = 1*Map.tailleCaseMap+Map.centrageXMap;

    /**
     * Position en hauteur de l'Equipe2
     */
    private int posYequipe2 = 14*Map.tailleCaseMap+Map.centrageXMap;

    /**
     * Distance entre l'origine du premier perso d'une equipe et l'origine de son suivant.
     */
    private int ecartPerso = 2*Map.tailleCaseMap;

    /**
     * Nombre de personnage Maximum dans chaque equipes.
     */
    private int nbrPersoJoueur[];
    /**
     * Police des textes
     */
    private UnicodeFont police;
    /**
     * Police du joueur bleu
     */
    private UnicodeFont policeBleu;
    /**
     * Police du joueur rouge
     */
    private UnicodeFont policeRouge;
    /**
     * Indice du joueur courant.
     */
    private int indiceJoueur=0;

    /**
     * Constructeur de MenuChoix.
     * @param stateID - Identifiant de la state.
     */
    public MenuChoixPersonnages( int stateID ) {
       this.stateID = stateID;
    }

    /**
     * Accesseur à l'identifiant de la state.
     * @return - Identifiant de la state.
     */
    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
        Draw.menuChoixPersonnages();  
    }

    @Override
    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException
    {
        actualiserNombrePersonnages();
        g.drawImage( Draw.FONDMENU, 0, 0 );
        
        if(this.police==null)
            this.police = Police.UNICODEFONT( Police.AVERIA[Police.BOLD], 16);
        if(this.policeBleu==null)
            this.policeBleu = Police.UNICODEFONT( Police.AVERIA[Police.BOLD], 16, java.awt.Color.blue);
        if(this.policeRouge==null)
            this.policeRouge = Police.UNICODEFONT( Police.AVERIA[Police.BOLD], 16, java.awt.Color.red);
        
        int nbPersoJ;
        //Si le jeu n'est pas en locale.
        if( ListeEquipes.getTypeJeu().equals(TypeJeu.LOCAL) ){
            //on alloue a nbPersoJ le nombre de personnage du joueur 0 ou 1 en fonction de la repartition.
            nbPersoJ = this.repartition? nbrPersoJoueur[0] :nbrPersoJoueur[1];
            //affichage du texte avec une couleur differente en fonction de la repartition
        }
        else //si on est en reseau
            nbPersoJ=nbrPersoJoueur[StaticData.getIdentifiant()];
        try{
            //on affiche le nom du joueur.
            if( ListeEquipes.getJoueur(indiceJoueur).getCouleur()==1 ) 
                policeBleu.drawString(50, 80, ListeEquipes.getJoueur(indiceJoueur).getNom());
            else 
                policeRouge.drawString(50, 80, ListeEquipes.getJoueur( indiceJoueur ).getNom());   
            //si le joueur à des personnages
            if( ListeEquipes.getJoueur(indiceJoueur).nbrPersonnage()>0 )
                police.drawString( 20, 120, "Vos personnages :");
            else                
                police.drawString( 20, 120, "Vous n'avez aucun personnage.");
            
            String s;
            
            if( nbPersoJ>1 )
                s="Il vous reste "+String.valueOf( nbPersoJ )+" personnages à choisir.";
            else            
                s="Il ne vous reste plus qu'un personnage à choisir.";
            //On affiche s au milieu de l'écran
            police.drawString( (container.getWidth()/2)-(police.getWidth(s)/2), 10, s);
            
            if( ListeEquipes.getTypeJeu().equals(TypeJeu.LOCAL) ){
                police.drawString( 10, 550, "Appuyer sur la touche Espace");
                police.drawString( 30, 570, "pour valider votre équipe.");
            }
            
            police.drawString( 840, 550, "Appuyez sur la touche Entrée");            
            police.drawString( 770, 570, "pour sélectionner un personnage.");  
        }catch(Exception e){
            System.err.println("MenuChoixPersonnages : erreur OpenGL. "+e.getMessage());
        }

        g.drawImage(Draw.MENUCHOIX[4], 722, 250);
        g.drawImage(Draw.MENUCHOIX[5], 195, 250);
        if(MenuChoixPersonnages.id == Joueur.MAGE){
            this.dessinerPortrait("Mage", Draw.MENUCHOIX[0], 100, 70, 3, 60, 30, 20, g, container);
        }
        else if(MenuChoixPersonnages.id == Joueur.GUERRIER){
            this.dessinerPortrait("Guerrier", Draw.MENUCHOIX[1], 100, 80, 2, 80, 90, 10, g, container);
        }
        else if(MenuChoixPersonnages.id == Joueur.VOLEUR){
            this.dessinerPortrait("Voleur", Draw.MENUCHOIX[2], 100, 50, 4, 60, 60, 40, g, container);
        }
        else if(MenuChoixPersonnages.id == Joueur.CLERC){
            this.dessinerPortrait("Clerc", Draw.MENUCHOIX[3], 100, 60, 2, 40, 30, 20, g, container);
        }
        
        //Affichage des personnages du joueur
        for( int j=0; j<ListeEquipes.getJoueur(indiceJoueur).nbrPersonnage(); j++){
            g.drawImage( ListeEquipes.getJoueur(indiceJoueur).getCadre(), 50, 151+(j*70) );
            g.drawAnimation( ListeEquipes.getJoueur(indiceJoueur).getPersonnage(j).getAnimPersonnage(0).getAnimation(0), 51 , 150+(j*70) );
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
        if( nbrPersoJoueur==null ){ //Si nbrPersoJoueur n'a pas encore été initialiser
            //on initialisre nbrPersoJoueur en tableau du nombre de joueur
            nbrPersoJoueur=new int[ ListeEquipes.nbrEquipes() ];
            //on alloue à chaque case le nombre de personnage maximum du joueur.
            for(int y=0; y<nbrPersoJoueur.length;y++)
                nbrPersoJoueur[y]=ListeEquipes.getNombrePersonnageMax();
        }
        //LORSQUE ON APPUIE SUR UNE TOUCHE
        if(container.getInput().isKeyPressed(Input.KEY_RIGHT)) { //on fais défiler les personnages vers la droite.
            MenuChoixPersonnages.id++;
            if(MenuChoixPersonnages.id == 4)
                MenuChoixPersonnages.id = 0;
        }
        else if(container.getInput().isKeyPressed(Input.KEY_LEFT)) { //on fais défiler les personnages vers la gauche.
            MenuChoixPersonnages.id--;
            if(MenuChoixPersonnages.id == -1)
                MenuChoixPersonnages.id = 3;
        }
        else if(container.getInput().isKeyPressed(Input.KEY_ESCAPE)){ //si on appuis sur Echape, on retourne sur la page principale.
            sbg.enterState(JavaTournament.menuPrincipalState);
        }
        else if(container.getInput().isKeyPressed(Input.KEY_ENTER)) { //si on appuie sur entrée.
            //Si le jeu est en locale
            if( ListeEquipes.getTypeJeu().equals(TypeJeu.LOCAL) ){
                int posYequipe = indiceJoueur==0? posYequipe1 : posYequipe2; //On change la position de l'équipe en fonction de la repartition.
                addPersonnage( MenuChoixPersonnages.id, posXequipe, posYequipe); //on ajoute le personnage au joueur.
                this.repartition = !this.repartition; //on inverse la repartition
                indiceJoueur= this.repartition? 0 : 1; //indice du joueur qui doit selectionner un personnage
                //si le joueur 1 n'a plus de personnage à choisir.
                if( nbrPersoJoueur[1]==0 ) //on switch vers menuEquipe.
                    sbg.enterState(JavaTournament.menuEquipe);
            }
            //Si le jeu est en réseau
            else if( ListeEquipes.getTypeJeu().equals(TypeJeu.RESEAU) ) {
                int posYequipe = StaticData.getIdentifiant()==0 ? posYequipe1 : posYequipe2; //on change le position de l'équipe en fonction de l'identifiant du joueur
                addPersonnage( MenuChoixPersonnages.id, posXequipe, posYequipe); //on ajoute le personnage au joueur.
                //Si le joueur n'a plus de personnage à choisir, on switch vers menuEquipe.
                if( nbrPersoJoueur[this.indiceJoueur]==0 ){
                    try {
                        ListeEquipes.getJoueur(StaticData.getIdentifiant()).envoieJoueur(StaticData.client.getOutputStream());
                    } catch (IOException ex) {
                        System.out.println("MenuChoixPersonnages : impossible d'envoyer le joueur au serveur.");
                    }
                    this.police=null;
                    this.policeBleu=null;
                    this.policeRouge=null;
                    sbg.enterState(JavaTournament.menuEquipe);
                }
            }            
        }
        else if( ListeEquipes.getTypeJeu().equals(TypeJeu.LOCAL) //si on est en local(
                && container.getInput().isKeyPressed(Input.KEY_SPACE) && this.repartition //Si on appuie sur espace, et que les 2 équipes ont le même nombre de joueur.
                && !ListeEquipes.getJoueur(0).isEmpty() && !ListeEquipes.getJoueur(1).isEmpty() //si les 2 équipes sont vides
                && ListeEquipes.getTypeJeu().equals( TypeJeu.LOCAL) ) { //Et que le jeu se déroule en locale
            this.police=null;
            this.policeBleu=null;
            this.policeRouge=null;
            sbg.enterState(JavaTournament.menuEquipe);
        }
    }

    /**
     * Méthode permettant de dessiner le portrait de la classe du Personnage.
     * @param nom - Nom du Personnage.
     * @param img - Portrait du Personnage.
     * @param pdv - Points de Vie du Personnage.
     * @param nrj - Points d'Energie du Personnage.
     * @param pm - Points de Mouvement du Personnage.
     * @param atk - Attaque du Personnage.
     * @param armor - Armure du Personnage.
     * @param dodge  - Esquive du Personnage.
     */
    public void dessinerPortrait(String nom, Image img, int pdv, int nrj, int pm, int atk, int armor, int dodge, Graphics g, GameContainer c ) {
        String hp = String.valueOf(pdv);
        String ep = String.valueOf(nrj);
        String movep = String.valueOf(pm);
        String attaque = String.valueOf(atk);
        String armure = String.valueOf(armor);
        String esq = String.valueOf(dodge);
        
        Color old = g.getColor(); //on sauvegarde la couleur
        g.setColor(Color.black);
        g.drawImage(img, (c.getWidth()/2)-img.getWidth()/2, (c.getHeight()/2)-img.getHeight()/2);
        g.drawString(nom, (c.getWidth()/2)-img.getWidth()/2+75, (c.getHeight()/2)-img.getHeight()/2+35);
        g.drawString("Vie : "+hp, (c.getWidth()/2)-img.getWidth()/2+45, (c.getHeight()/2)-img.getHeight()/2+335);
        g.drawString("Energie : "+ep, (c.getWidth()/2)-img.getWidth()/2+45, (c.getHeight()/2)-img.getHeight()/2+355);
        g.drawString("Mouvement : "+movep, (c.getWidth()/2)-img.getWidth()/2+45, (c.getHeight()/2)-img.getHeight()/2+375);
        g.drawString("Attaque : "+attaque, (c.getWidth()/2)-img.getWidth()/2+45, (c.getHeight()/2)-img.getHeight()/2+395);
        g.drawString("Armure : "+armure, (c.getWidth()/2)-img.getWidth()/2+45, (c.getHeight()/2)-img.getHeight()/2+415);
        g.drawString("Esquive : "+esq, (c.getWidth()/2)-img.getWidth()/2+45, (c.getHeight()/2)-img.getHeight()/2+435);
        g.setColor(old);
    }
    
    /**
     * Methode pour actualiser le nombre de personnages de chaque Joueur ainsi que son indice.
     */
    private void actualiserNombrePersonnages(){
        //si le jeu est sur un reseau distant
        if( ListeEquipes.getTypeJeu().equals(TypeJeu.RESEAU) )
        {
            this.indiceJoueur=StaticData.getIdentifiant();
        }
        for(int i=0; i<ListeEquipes.nbrEquipes(); i++)
        {
            nbrPersoJoueur[i]=ListeEquipes.getNombrePersonnageMax()-ListeEquipes.getJoueur(i).nbrPersonnage();
        }
    }
    /**
     * Méthode qui ajoute un personnage
     * @param idPerso - Numero du personnage voulu
     * @param indiceJoueur - Indice du joueur.
     * @param posX - Abscisse du personnage
     * @param posY - Ordonnée du personnage.
     * @throws SlickException 
     */
    private void addPersonnage(int idPerso, int posX, int posY) throws SlickException{
        int identifiant = indiceJoueur*10+ListeEquipes.getJoueur(indiceJoueur).nbrPersonnage();
        int x = posX+(ecartPerso*ListeEquipes.getJoueur(indiceJoueur).nbrPersonnage());
        ListeEquipes.getJoueur(indiceJoueur).ajoutPersonnage( idPerso, identifiant, x, posY );
        actualiserNombrePersonnages();
    }
}

