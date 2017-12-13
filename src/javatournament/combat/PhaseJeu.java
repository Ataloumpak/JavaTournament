package javatournament.combat;

import javatournament.JavaTournament;
import javatournament.data.Draw;
import javatournament.data.Police;
import javatournament.data.Son;
import javatournament.map.Map;
import javatournament.personnage.Personnage;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PhaseJeu extends BasicGameState {

    /**
     * Identifiant de la state.
     */
    private int stateID = -1;
    /**
     * Curseur de selection sur la carte.
     */
    private Curseur cursor;
    /**
     * Permet de savoir si le joueur est en phase d'attaque ou non.
     */
    private static boolean attaque = false;
    /**
     * Permet de savoir si le joueur est en phase de lancement de sort ou non.
     */
    private static boolean sort = false;
    /**
     * Permet de savoir si le joueur est en phase de deplacement ou non.
     */
    private static boolean deplacement = false;
    /**
     * Affichage de la confirmation pour quitter le combat.
     */
    private boolean echap = false;
    /**
     * En réseau, indique si le joueur a l'autorisation de jouer
     */
    private boolean jouer = false;
    /**
     * Permet de gerer l'image affin qu'elle ne deborde pas du cadre
     */
    private static int posdepP = 55;
    /**
     * Cadre d'information apparaissant a droite
     */
    private Image hud_droit;
    /**
     * Image permetant de distinguer le personnage jouant
     */
    private Image fondPersonnageCourant;
    /**
     * Abscisse du cadre d'information.
     */
    private int posXHud = 800;
    /**
     * Numéro du sort utilise.
     */
    private int numSort = 1;
    /**
     * Personnage jouant.
     */
    private Personnage persoCourant;
    /**
     * Affichage texte dans le Hud.
     */
    private static Log log;
    /**
     * Police d'ecriture dans le menu pour quitter.
     */
    private UnicodeFont policeEchappe;
    /**
     * Police d'ecriture dans le Hud
     */
    private UnicodeFont policeHUD;
    /**
     * Police d'affichage des points de vie.
     */
    private UnicodeFont policePDV;
    /**
     * Police d'affichage des caractéristiques en noir;
     */
    private UnicodeFont policeCarac_noir;
    /**
     * Police d'affichage des caractéristiques en blanc
     */
    private UnicodeFont policeCarac_blanc;
    /*
     * Zone de saisie du texte dans le HUD.
     */
    private TextField text;
    /**
     * True si on a eu une erreur de deconnexion avec le serveur, false sinon.
     */
    public static boolean erreur=false;

    /**
     * Constructeur de la state
     * @param stateID Identifiant
     */
    public PhaseJeu(int stateID) {
        this.stateID = stateID;
    }

    /**
     * Getter de l'identifiant de la state
     * @return 
     */
    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
        Draw.phaseJeu();
        //Son.phaseJeu();
        Draw.imageParticule();
        hud_droit = Draw.PHASEJEU[0];
        fondPersonnageCourant = Draw.PHASEJEU[1];
        this.cursor = new Curseur(Map.centrageXMap, Map.centrageYMap + Map.tailleCaseMap);

        //POLICE DU MENU ECHAPE
        policeEchappe = Police.UNICODEFONT(Police.AVERIA[Police.BOLD], 20);
        log = new Log();

        //POLICE DU HUD
        policeHUD = Police.UNICODEFONT(Police.AVERIA[Police.REGULAR], 16, java.awt.Color.white);
        policePDV = Police.UNICODEFONT( Police.AVERIA[Police.BOLD_ITALIC], 10, java.awt.Color.white);
        policeCarac_noir = Police.UNICODEFONT( Police.AVERIA[Police.BOLD], 12, java.awt.Color.black);
        policeCarac_blanc = Police.UNICODEFONT( Police.AVERIA[Police.BOLD], 12, java.awt.Color.white);
        text = new TextField(container, policeHUD, posXHud + 3, 577, 244, 20);
        text.setMaxLength(100);//Nombre de caractères maximum dans le champs texte
    }

    @Override
    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {

        //On charge le fond de la Map
        if(StaticData.fond==null)
            StaticData.fond = new Image(Draw.class.getResourceAsStream("images/fond_map/"+StaticData.map.getNom()), "fond_map", false);
        //On affiche le fond.
        StaticData.fond.draw(0,0);

        //AFFICHE LA MAP
        for (int i = 0; i < StaticData.map.getLargeurMap(); i++) {
            for (int j = 0; j < StaticData.map.getHauteurMap(); j++) {
                g.drawImage(StaticData.map.getTypeCase(i, j).getSkin(), (i * Map.tailleCaseMap) + Map.centrageXMap, (j * Map.tailleCaseMap) + Map.centrageYMap);
            }
        }
        //FIN AFFICHAGE MAP

        //AFFICHAGE DE LA SURBRILLANCE DU PERSONNAGE COURANT
        if(persoCourant == null)
            persoCourant = ListeEquipes.getPersonnageCourant();
        g.drawImage(fondPersonnageCourant, persoCourant.getPosX() + Map.centrageXMap, persoCourant.getPosY() + Map.tailleCaseMap + Map.centrageYMap);

        //AFFICHAGE DU CURSEUR ET DE LA POSITION DU PERSONNAGES
        if (!PhaseJeu.deplacement) {
            g.drawImage(this.cursor.getImg(), this.cursor.getPosX() + Map.centrageXMap, this.cursor.getPosY());
        }
        //Méthode de mise en surbrillance des cases ciblables par le personnages courant.
        verifCase(g, StaticData.map);
        //AFFICHAGE DU HUD, DE CES INFORMATIONS, DES PERSONNAGES ET DE LA VERIFICATION DE LEURS SIGNE VIE
        hud_droit.draw(posXHud, 0); //Affichage de l'image de fond
        if(ListeEquipes.getTypeJeu().equals(TypeJeu.LOCAL))
            policeHUD.drawString(posXHud+125-policeHUD.getWidth(ListeEquipes.getJoueur(ListeEquipes.getJoueurCourant()).getNom())/2, 10, ListeEquipes.getJoueur(ListeEquipes.getJoueurCourant()).getNom());
        else if(ListeEquipes.isJoueurExiste(StaticData.getIdentifiant()))
            policeHUD.drawString(posXHud+125-policeHUD.getWidth(ListeEquipes.getJoueur(StaticData.getIdentifiant()).getNom())/2, 10, ListeEquipes.getJoueur(StaticData.getIdentifiant()).getNom());
        persoCourant.afficherstatpersonnage(g, posXHud + 15, 40, 220, 20, policeCarac_blanc, policeCarac_noir);
        //si on appuie sur Tab, on affiche une fenetre avec les sors et leurs descriptions.
        if( container.getInput().isKeyDown(Input.KEY_TAB)) {
            persoCourant.afficherSors(g, posXHud-200, 0, policeCarac_blanc);                            
        }
        
        //Affichage ligne de séparation
        g.setColor(Color.white);
        g.drawLine(posXHud + 20, 120, posXHud+230, 120);
        
        try {
        //AFFICHAGE DES SORTS DU PERSONNAGE
            policeHUD.drawString(posXHud + 20, 150, "Sorts :");
            for (int numHUDSort = 0; numHUDSort < 4; numHUDSort++) {
                if( numHUDSort==numSort)
                    g.setColor(Color.red);
                else
                    g.setColor(Color.gray);
                    
                g.fillRoundRect(posXHud + 88 + Map.tailleCaseMap * numHUDSort, 145, 24, 24, 1);
                g.drawImage(persoCourant.getSort(numHUDSort).getSkin(), posXHud + 90 + Map.tailleCaseMap * numHUDSort, 147);
            }
        } catch (Exception e) {
            System.err.println("PhaseJeu : erreur OpenGL. "+e.getMessage());
        }
        //FIN AFFICHAGE DES SORTS DU PERSONNAGE

        //Affichage ligne de séparation
        g.setColor(Color.white);
        g.drawLine(posXHud + 20, 200, posXHud+230, 200);

        //AFFICHAGE DES PERSONNAGES DANS LE HUD & SUR LA MAP & VERFICATION SIGNE VIE       
        for (int a = 0; a < ListeEquipes.getNombrePersonnageMax(); a++)
        {
            for (int b = 0; b < ListeEquipes.nbrEquipes(); b++)
            {
                //
                int persoHudX = ((a * 2) < 4) ? posXHud + Map.tailleCaseMap + ((a * 2 + b) * 55) - 1 : posXHud + Map.tailleCaseMap + (((a * 2 + b) - 4) * 55) - 1;
                int persoHudY = ((a * 2) < 4) ? 230 : 230 + 55;
                int indJoueur = ListeEquipes.getJoueurCourant() + b == ListeEquipes.nbrEquipes() ? 0 : ListeEquipes.getJoueurCourant() + b;

                //Si le joueur possède des personnages et si l'indice 'a' est inferieur à son nombre de personnages.
                if( !ListeEquipes.getJoueur(indJoueur).isEmpty() && a<ListeEquipes.getJoueur(indJoueur).nbrPersonnage() )
                {
                    //Si le personnage a au plus 0 point de vie
                    Personnage persoCrt = ListeEquipes.getJoueur(indJoueur).getPersonnage(a);
                    if (persoCrt.getPDVCrt() <= 0) 
                    {
                        //On ajoute son nom aux log en temps que mort.
                        log.ajoutLogMort(persoCrt.getNom());
                        //On modifie la case ou il se situe à 99 (vide).
                        StaticData.map.setCasePersonnage(persoCrt.getPosX(), persoCrt.getPosY() + Map.tailleCaseMap, 99);
                        //On le supprime.
                        ListeEquipes.remove(indJoueur, a);
                    } 
                    else if (persoCrt != null)
                    {
                        //AFFICHAGE DANS LE HUD
                        g.drawImage(ListeEquipes.getJoueur(indJoueur).getCadre(), persoHudX, persoHudY);
                        g.drawImage(persoCrt.getSkin(ListeEquipes.getJoueur(indJoueur).getCouleur()), persoHudX, persoHudY);
                        //AFFICHAGE SUR LA MAP
                        //affichage du cercle du personnage
                        g.drawImage(ListeEquipes.getJoueur(indJoueur).getCercle(), persoCrt.getPosX() + Map.centrageXMap, persoCrt.getPosY() + Map.tailleCaseMap + Map.centrageYMap);
                        //affichage du skin du personnage
                        g.drawAnimation(persoCrt.getAnimPersonnage(ListeEquipes.getJoueur(indJoueur).getCouleur()).getAnimation(persoCrt.getAnim()),
                                persoCrt.getPosX() + Map.centrageXMap,
                                persoCrt.getPosY() + Map.centrageYMap);
                        //si on appuie sur la touche alt gauche
                        //on affiche la barre de points de vie minature au dessus de chaque personnage
                        if( container.getInput().isKeyDown(Input.KEY_LALT) ) {
                            persoCrt.afficherBarreStats(g, true, persoCrt.getPosX()+Map.centrageXMap+( persoCrt.getSkin(0).getWidth()-40 )/2,
                                                    persoCrt.getPosY(), 40, 15, policePDV, policeCarac_noir);
                        }
                    }
                }
                else
                {
                    ListeEquipes.getJoueur(indJoueur).defeat();
                }
            }
        }//FIN AFFICHAGE DU HUD ET DE CES INFORMATIONS
        //FIN AFFICHAGE DU HUD, DE CES INFORMATIONS, DES PERSONNAGES ET DE LA VERIFICATION DE LEURS SIGNE VIE

        //AFFICHAGE DES LOGS
        log.afficheLogs( policeHUD, 805, 355);
        g.setColor(Color.lightGray);
        g.drawRect( posXHud+2, 355, 246, 243);
        g.setColor(Color.white);
        g.fillRect( posXHud+3, 577, 244, 20);
        text.render(container, g);
        //AFFICHAGE DES TEXTES D'ACTIONS & CIBLES
        if (attaque) {
            g.drawString("Mode Attaque activé", posXHud + 45, 332);
        } 
        else if (PhaseJeu.deplacement) {
            g.drawString("Mode Déplacement activé", posXHud + 30, 332);
        }
        else if (PhaseJeu.sort) {
            g.drawString("Mode Sortilège activé", posXHud + 30, 332);
        }
        //FIN AFFICHAGE DES TEXTES D'ACTIONS & CIBLES


        //AFFICHAGE DE LA CONFIRMATION DE QUITTER LA PARTIE
        if( this.echap && ListeEquipes.nbrEquipes()>1 ) { //si on veux quitter et qu'on a pas gagner
            information(container, g, "Voulez vous vraiment quitter le combat ?", "Si oui, appuyez sur la touche Entrée.", "Sinon, appuyez sur la touche Echap.");
        }
        //FIN AFFICHAGE DE LA CONFIRMATION DE QUITTER LA PARTIE
        for(Personnage p : ListeEquipes.getPersonnages())
                p.drawEmitters(g);        

        //on verifie si il y à un gagnant.
        if( ListeEquipes.getWinner() != null )
        {
            container.getInput().clearKeyPressedRecord();
            String enjoy="Félicitations!";
            String type;
            //si c'est en locale
            if(ListeEquipes.getTypeJeu().equals(TypeJeu.LOCAL))
                type="Le joueur '"+ListeEquipes.getWinner()+"' a gagné la partie !";
            //si c'est en reseau et que l'id du joueur est le miens.
            else
            {
                if( ListeEquipes.getJoueur(0).getIdentifiant()==StaticData.getIdentifiant() )
                {
                    type="Vous avez gagné la partie.";
                }
                //sinon
                else
                {
                    enjoy="Dommage!";
                    type="Vous avez perdu la partie.";
                }
            }
            information(container, g, enjoy, type, "Appuyez sur Entrée pour quitter.");
            this.echap=true;
        }
        //Si le serveur à été perdu.
        if(erreur) {
            if(StaticData.serveur==null)
                information(container, g, "Serveur perdu!", "Vous avez été déconnecté de la partie.", "Appuyez sur Entrée","pour revenir au menu principal.");
            else if(StaticData.serveur!=null)
                information(container, g, "Client perdu!", "Le client est déconnecté.", "Appuyer sur Entrée","pour revenir au menu principal.");
            this.echap=true;
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
        if(persoCourant == null)
        {
            persoCourant = ListeEquipes.getPersonnageCourant();
        }

        int PX = persoCourant.getPosX();
        int PY = persoCourant.getPosY();
        int Ptaille = persoCourant.getSkin(0).getHeight();
        int CX = this.cursor.getPosX();
        int CY = this.cursor.getPosY();
        
        for(Personnage p : ListeEquipes.getPersonnages())
        {
            p.animateEmitters(container, delta);
        }
        if (this.echap) //Si le menu pour quitter le combat est ouvert
        {
            if (container.getInput().isKeyPressed(Input.KEY_ENTER))
            {
                this.echap = false;
                log.clear();
                Son.COMBAT.stop();
                Son.MENU_PRINCIPAL.play();
                sbg.enterState(JavaTournament.menuPrincipalState);
            } else if (container.getInput().isKeyPressed(Input.KEY_ESCAPE) && ListeEquipes.nbrEquipes()>1)
            {
                this.echap = false;
            }
        } //CHAMP TEXTE
        else if (!text.hasFocus() && container.getInput().isKeyPressed(Input.KEY_T))
        {
            text.setFocus(true);
        } else if (text.hasFocus() && container.getInput().isKeyPressed(Input.KEY_ENTER))
        {
            if (!text.getText().equals("")) {
                if(ListeEquipes.getTypeJeu().equals(TypeJeu.LOCAL))
                    log.ajoutMessage(ListeEquipes.getJoueur(ListeEquipes.getJoueurCourant()).getNom() + " : " + text.getText());
                //si le jeu est en reseau, on envoie le message
                else if(ListeEquipes.getTypeJeu().equals(TypeJeu.RESEAU)) {
                    String message =ListeEquipes.getJoueur(StaticData.getIdentifiant()).getNom() + " : " + text.getText();
                    log.ajoutMessage(message);
                    StaticData.client.envoie("11"+StaticData.transformeInt(StaticData.getIdentifiant())+message);
                }
            }
            razText(container, text);
        } else if (text.hasFocus() && container.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            razText(container, text);
        }
        //FIN CHAMP TEXTE

        // si on est en local, aucune raison de brider les actions
        if (ListeEquipes.getTypeJeu().equals(TypeJeu.LOCAL) && !jouer) {
            jouer = true;
        }

        // Si le réseau est activé, il faut vérifier si le joueur a le droit de jouer
        if (ListeEquipes.getTypeJeu().equals(TypeJeu.RESEAU)) {
            if (ListeEquipes.getJoueurCourant() == StaticData.getIdentifiant()) {// si c'est son tour
                if (!jouer) // on le laisse jouer
                    jouer = !jouer;
            } else { // sinon
                if (jouer) // on lui retire le droit
                    jouer = false;
            }
        }

        if (!text.hasFocus() && jouer) { //Si le champs texte n'est pas activé et le joueur a le droit de bouger
            if (attaque
                    && container.getInput().isKeyPressed(Input.KEY_ENTER)
                    && StaticData.map.getCiblable(CX / Map.tailleCaseMap, CY / Map.tailleCaseMap) != 0
                    && StaticData.map.getCasePersonnage(CX, CY) / 10 != ListeEquipes.getJoueurCourant()) // Lance l'attaque
            {
                if (StaticData.map.getCasePersonnage(CX, CY) != 99)// si il y a un joueur sur la case
                    persoCourant.attaquer(CX, CY);
                attaque = false;
                fondAttaque(1, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
            }
            if (container.getInput().isKeyPressed(Input.KEY_SPACE))
            {
                if (attaque)
                {
                    attaque = false;
                    fondAttaque(1, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                }
                if(PhaseJeu.sort)
                {
                    PhaseJeu.sort = false;
                    fondAttaque(persoCourant.getSort(numSort).getPortee(), PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                }
                PhaseJeu.deplacement = false;
                this.numSort = 0;
                persoCourant.finTour();
                persoCourant = ListeEquipes.getPersonnageCourant();
                curseurPerso( ListeEquipes.getPersonnageCourant().getPosX(), ListeEquipes.getPersonnageCourant().getPosY());
                
            }//Les MOUVEMENTS
            else if (container.getInput().isKeyPressed(Input.KEY_UP)) {
                if (!persoCourant.deplacer(deplacement, 1))
                {
                    if (!deplacement && inContainer(this.cursor, Map.tailleCaseMap, CX, CY - Map.tailleCaseMap))
                    {
                        this.cursor.setPosY(CY - Map.tailleCaseMap);
                    }
                }
            } else if (container.getInput().isKeyPressed(Input.KEY_DOWN)) {
                if (!persoCourant.deplacer(deplacement, 2)) {
                    if (!deplacement && inContainer(this.cursor, Map.tailleCaseMap, CX, CY + Map.tailleCaseMap)) {
                        this.cursor.setPosY(CY + Map.tailleCaseMap);
                    }
                }
            } else if (container.getInput().isKeyPressed(Input.KEY_RIGHT))
            {
                if (!persoCourant.deplacer(deplacement, 3))
                {
                    if (!deplacement && inContainer(this.cursor, Map.tailleCaseMap, CX + Map.tailleCaseMap, CY))
                    {
                        this.cursor.setPosX(CX + Map.tailleCaseMap);
                    }
                }
            } else if (container.getInput().isKeyPressed(Input.KEY_LEFT))
            {
                if (!persoCourant.deplacer(deplacement, 4))
                {
                    if (!deplacement && inContainer(this.cursor, Map.tailleCaseMap, CX - Map.tailleCaseMap, CY))
                    {
                        this.cursor.setPosX(CX - Map.tailleCaseMap);
                    }
                }
            } /*
             * ----------- Mvt souris ----------------------------------------
             * 
             */
            /*
            else if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON))
            {
                int MX = container.getInput().getMouseX(); // +5 => ???   -Map.tailleCaseMap => taille de la premiere case
                int MY = container.getInput().getMouseY() - posdepP - Ptaille + 2 * Map.tailleCaseMap; //-posdepP => on retire la position minimal du personnage     -Ptaille  => soustraction de la taille du pers  +Map.tailleCaseMap => ajout de la taille de la case
                //System.out.println(MX);
                //System.out.println(MY);
                //System.out.println(PX);
                //System.out.println(PY);
                int difY = PX / Map.tailleCaseMap - MX / Map.tailleCaseMap;
                int difX = PY / Map.tailleCaseMap - MY / Map.tailleCaseMap;
                int pmtot = Math.abs(difX) + Math.abs(difY);
                int PYc = PY - difX * Map.tailleCaseMap;
                int PXc = PX - difY * Map.tailleCaseMap;
                if (pmtot <= persoCourant.getPMCrt()
                         //assez de pm pour le deplacement
                         
                        && deplacement
                         // deplacement active
                         
                        && persoCourant.inContainer(posdepP, PXc, PYc)
                        && StaticData.map.getCasePersonnage((PXc), (PYc + Ptaille)) == 99
                        && StaticData.map.getTypeCase(PXc / Map.tailleCaseMap // -1
                         , (PYc) / Map.tailleCaseMap // +1
                         ).isAccess())
                {
                    persoCourant.mvtSouris(pmtot, PXc, PYc);
                }

            }*/
            // Demande de confirmation pour quitter le jeu
            else if (container.getInput().isKeyPressed(Input.KEY_ESCAPE) && ListeEquipes.getTypeJeu().equals(TypeJeu.LOCAL)) {
                echap = true;
            } // Passage en mode deplacement
            else if (container.getInput().isKeyPressed(Input.KEY_M))
            {
                if(attaque)
                {
                    attaque = false;
                    fondAttaque(1, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                }
                if(PhaseJeu.sort)
                {
                    PhaseJeu.sort = false;
                    fondAttaque(persoCourant.getSort(numSort).getPortee(), PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                }
                PhaseJeu.deplacement = !PhaseJeu.deplacement;
                if (!deplacement)
                {
                    curseurPerso(ListeEquipes.getPersonnageCourant().getPosX(), ListeEquipes.getPersonnageCourant().getPosY());
                }
            } //Mouvement a la souris
            /*
            else if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON))
            {
                int MX = container.getInput().getMouseX() + 5 - Map.tailleCaseMap; // +5 => ??? -Map.tailleCaseMap => taille de la premiere case
                int MY = container.getInput().getMouseY() - posdepP - Ptaille + Map.tailleCaseMap; //-posdepP => on retire la position minimal du personnage -Ptaille => soustraction de la taille du pers +Map.tailleCaseMap => ajout de la taille de la case
                //System.out.println(MY);
                //System.out.println(PY);
                int difY = PX / Map.tailleCaseMap - MX / Map.tailleCaseMap;
                int difX = PY / Map.tailleCaseMap - MY / Map.tailleCaseMap;
                int pmtot = Math.abs(difX) + Math.abs(difY);
                int PYc = PY - difX * Map.tailleCaseMap;
                int PXc = PX - difY * Map.tailleCaseMap;
                if (pmtot <= persoCourant.getPMCrt() 
                         // assez de pm pour le deplacement
                         
                        && deplacement 
                         // deplacement active
                          && persoCourant.inContainer(posdepP, PXc, PYc)
                        && StaticData.map.getCasePersonnage((PXc), (PYc + Ptaille)) == 99
                        && StaticData.map.getTypeCase(PXc / Map.tailleCaseMap, (PYc) / Map.tailleCaseMap
                         //+1
                         ).isAccess())
                {
                    persoCourant.mvtSouris(pmtot, PXc, PYc);
                }
            }*/ // Passage en mode attaque
            else if (container.getInput().isKeyPressed(Input.KEY_A))
            {
                if(PhaseJeu.sort)
                {
                    PhaseJeu.sort = false;
                    fondAttaque(persoCourant.getSort(numSort).getPortee(), PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                }
                PhaseJeu.attaque = !PhaseJeu.attaque;
                fondAttaque(1, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                if(deplacement)
                {
                    PhaseJeu.deplacement = false;
                    curseurPerso(ListeEquipes.getPersonnageCourant().getPosX(), ListeEquipes.getPersonnageCourant().getPosY());
                }
            } //CHANGEMENT DE LA VARIABLE D'AFFICHAGE DES CARACS DES SORTS DANS LE HUD
            else if(container.getInput().isKeyPressed(Input.KEY_S) && PhaseJeu.sort)
            {
                if(StaticData.map.getCiblable(CX / Map.tailleCaseMap, CY / Map.tailleCaseMap) == 1)
                {
                    PhaseJeu.sort = false;
                    persoCourant.lancerSort(CX, CY, persoCourant.getSort(numSort), log);
                    fondAttaque(persoCourant.getSort(numSort).getPortee(), PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                }
                else
                    log.ajoutMessage("Hors de portée !");
            }
            else if (container.getInput().isKeyPressed(Input.KEY_1) || container.getInput().isKeyPressed(Input.KEY_NUMPAD1))
            {
                if(PhaseJeu.attaque)
                    fondAttaque(1, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                PhaseJeu.attaque = false;
                PhaseJeu.deplacement = false;
                if(numSort == 0)
                {
                    PhaseJeu.sort = !PhaseJeu.sort;
                    fondAttaque(persoCourant.getSort(numSort).getPortee(), PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                }
                if(numSort != 0 && PhaseJeu.sort)
                {
                    PhaseJeu.sort = false;
                    fondAttaque(persoCourant.getSort(numSort).getPortee(), PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                    PhaseJeu.sort = true;
                    fondAttaque(persoCourant.getSort(0).getPortee(), PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                }
                numSort = 0;
            }
            else if (container.getInput().isKeyPressed(Input.KEY_2) || container.getInput().isKeyPressed(Input.KEY_NUMPAD2))
            {
                if(PhaseJeu.attaque)
                    fondAttaque(1, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                PhaseJeu.attaque = false;
                PhaseJeu.deplacement = false;
                if(numSort == 1)
                {
                    PhaseJeu.sort = !PhaseJeu.sort;
                    fondAttaque(persoCourant.getSort(numSort).getPortee(), PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                }
                if(numSort != 1 && PhaseJeu.sort)
                {
                    PhaseJeu.sort = false;
                    fondAttaque(persoCourant.getSort(numSort).getPortee(), PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                    PhaseJeu.sort = true;
                    fondAttaque(persoCourant.getSort(1).getPortee(), PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                }
                numSort = 1;
            }
            else if (container.getInput().isKeyPressed(Input.KEY_3) || container.getInput().isKeyPressed(Input.KEY_NUMPAD3))
            {
                if(PhaseJeu.attaque)
                    fondAttaque(1, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                PhaseJeu.attaque = false;
                PhaseJeu.deplacement = false;
                if(numSort == 2)
                {
                    PhaseJeu.sort = !PhaseJeu.sort;
                    fondAttaque(persoCourant.getSort(numSort).getPortee(), PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                }
                if(numSort != 2 && PhaseJeu.sort)
                {
                    PhaseJeu.sort = false;
                    fondAttaque(persoCourant.getSort(numSort).getPortee(), PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                    PhaseJeu.sort = true;
                    fondAttaque(persoCourant.getSort(2).getPortee(), PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                }
                numSort = 2;
            }
            else if (container.getInput().isKeyPressed(Input.KEY_4) || container.getInput().isKeyPressed(Input.KEY_NUMPAD4))
            {
                if(PhaseJeu.attaque)
                    fondAttaque(1, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                PhaseJeu.attaque = false;
                PhaseJeu.deplacement = false;
                if(numSort == 3)
                {
                    PhaseJeu.sort = !PhaseJeu.sort;
                    fondAttaque(persoCourant.getSort(numSort).getPortee(), PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                }
                if(numSort != 3 && PhaseJeu.sort)
                {
                    PhaseJeu.sort = false;
                    fondAttaque(persoCourant.getSort(numSort).getPortee(), PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                    PhaseJeu.sort = true;
                    fondAttaque(persoCourant.getSort(3).getPortee(), PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap, PX / Map.tailleCaseMap, (PY + Ptaille) / Map.tailleCaseMap);
                }
                numSort = 3;
            }
            //FIN CHANGEMENT DE LA VARIABLE D'AFFICHAGE DES CARACS DES SORTS DANS LE HUD
            //FIN UTILISATION DES TOUHES
        }//Fin du if(!TextFocus && jouer)
        container.getInput().clearKeyPressedRecord();
    }

    /**
     * Permet de savoir si les coordonnee (x,y) pour le déplacement du curseur
     * sont valide
     * @param Curseur cursor curseur courant
     * @param int posmaxY position max des ordonnee
     * @param int x abcisse
     * @param int y ordonnee
     * @return boolean res vrai si la position du curseur est dans l'écran
     */
    public boolean inContainer(Curseur cursor, int posmaxY, int x, int y) // permet de savoir si la position demandé est dans l'écran ou non
    {
        boolean res = false;
        if (x > -1 && x < (StaticData.map.getLargeurMap() * Map.tailleCaseMap) && y > (Map.tailleCaseMap - posmaxY + cursor.getImg().getHeight()) && y < ((StaticData.map.getHauteurMap()) * Map.tailleCaseMap))
        {
            res = true;
        }
        return res;
    }

    /**
     * Permet de rendre ciblable les cases aux alentours du joueur a une portee donnee
     * @param Graphics g outil de dessin
     * @param int portee portee du sort ou de l'attaque
     * @param int x abscisse courante (initialement celle du joueur)
     * @param int y ordonnee courante (initiallement celle du joueur)
     * @param int xini abscisse initiale (celle du joueur)
     * @param int yini ordonnee initiale (celle du joueur)
     * @throws SlickException
     */
    public void fondAttaque(int portee, int x, int y, int xini, int yini)
    {
        if ((x - 1) > -1 && StaticData.map.getTypeCase(x - 1, y).isAccess() && (x - 1) != xini && portee > 0)
        {
            StaticData.map.setCiblable(x - 1, y);
            fondAttaque(portee - 1, x - 1, y, xini, yini);
        }
        if ((y - 1) > -1 && StaticData.map.getTypeCase(x, y - 1).isAccess() && (y - 1) != yini && portee > 0)
        {
            StaticData.map.setCiblable(x, y - 1);
            fondAttaque(portee - 1, x, y - 1, xini, yini);
        }
        if ((x + 1) < StaticData.map.getLargeurMap() && StaticData.map.getTypeCase(x + 1, y).isAccess() && (x + 1) != xini && portee > 0)
        {
            StaticData.map.setCiblable(x + 1, y);
            fondAttaque(portee - 1, x + 1, y, xini, yini);
        }
        if ((y + 1) < StaticData.map.getHauteurMap() && StaticData.map.getTypeCase(x, y + 1).isAccess() && (y + 1) != yini && portee > 0)
        {
            StaticData.map.setCiblable(x, y + 1);
            fondAttaque(portee - 1, x, y + 1, xini, yini);
        }
    }

    /**
     * Verifie toute les case et affiche celles qui sont ciblable
     * @param Graphics g outil de dessin
     * @param Map M carte dans laquel on va dessiner
     * @throws SlickException
     */
    public void verifCase(Graphics g, Map M) throws SlickException
    {
        for (int i = 0; i < M.getLargeurMap(); i++)
        {
            for (int j = 0; j < M.getHauteurMap(); j++)
            {
                if (M.getCiblable(i, j) == 1)
                {
                    g.drawImage(Draw.PHASEJEU[2], i * Map.tailleCaseMap + Map.centrageXMap, j * Map.tailleCaseMap + Map.centrageYMap);
                }
            }
        }
    }
    
    /**
     * Methode qui positionne le curseur sur le personnage courant.
     * @param x - Abscisse du Personnage.
     * @param y - Ordonnée du Personnage.
     */
    private void curseurPerso(int x, int y)
    {
        //MODIFICATION DE LA POSITION DU CURSEUR EN FONCTION DE LA POSITION DE PERSONNAGE COURANT
        this.cursor.setPosX(x + Map.centrageXMap - Map.tailleCaseMap);
        this.cursor.setPosY(y + Map.tailleCaseMap + Map.centrageYMap);
        //FIN MODIFICATION DE LA POSITION DU CURSEUR EN FONCTION DE LA POSITION DE PERSONNAGE COURANT
    }

    /**
     * Méthode pour savoir si le personnage courant attaque. Retourne true si
     * oui, false sinon.
     * @return boolean
     */
    public static boolean getAttaque()
    {
        return attaque;
    }
    
    /**
     * Méthode pour savoir si le personnage courant est en mode sortilège.
     * @return boolean - Vrai : Activé / Faux : Désactivé
     */
    public static boolean getSort()
    {
        return sort;
    }

    /**
     * Méthode pour remetre à zero le champs texte
     * @param GameContainer - GameContainer qui contient les touches utilisées.
     * @param TextField - champ text à modifier.
     * @param boolean - booleen de focus sur le champs text
     */
    private void razText(GameContainer c, TextField t)
    {
        if (t.hasFocus())
        {
            t.setText("");
            t.setFocus(false);
            c.getInput().clearKeyPressedRecord();
        }
    }

    /**
     * Methode qui retourne la position de depart d'un personnage.
     * @return int
     */
    public static int getPosdepP() {
        return posdepP;
    }
    /**
     * Méthode pour modifier le booleen de deplacement
     * @param dep Valeur du deplacement
     */
    public static void setDeplacement(boolean dep) {
        deplacement = dep;
    }
    
    /*
     * Methode qui retourne les log du jeu. 
     */
    public static Log getLog() {
        return log;
    }
    
    /**
     * Methode affichant une fenetre et un message à l'écran.
     */
    private void information( GameContainer gc, Graphics g, String ... messages) {
        if(messages.length>0) {
            int largeur=600;
            int hauteur=messages.length*40+policeEchappe.getHeight(messages[0]);
            int x=gc.getWidth()/2-largeur/2;
            int y=gc.getHeight()/2-hauteur/2;

            //on noircit le fond de la fenetre
            g.setColor(new Color(0, 0, 0, 80));
            g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
            //on affiche un rectangle blanc
            g.setColor(Color.white);
            g.fillRoundRect( x, y, largeur, hauteur, 15);
            //texte à afficher dans se rectangle.
            try {
                for( int i=0; i<messages.length;i++)
                    policeEchappe.drawString( x+largeur/2-policeEchappe.getWidth(messages[i])/2, y+policeEchappe.getHeight(messages[i])+40*i, messages[i], Color.black);
            } catch (Exception e) {
                System.err.println("PhaseJeu : information() -> erreur OpenGL. "+e.getMessage());                
            }
            g.setColor(Color.black);
        }
    }
}
