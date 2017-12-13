package javatournament.combat;

import java.util.LinkedList;
import java.util.ListIterator;
import org.newdawn.slick.UnicodeFont;

/**
 * Classe qui contient les logs du jeu.
 * @author pyarg
 */
public class Log {
    /**
     * Nombre maximum de logs qu'on stock.
     */
    private static int nbrMax=100;
    /**
     * Nombre de logs maximum afficher dans le jeu.
     */
    private static int nbrAffichage=11;
    /**
     * Largeur de l'affichage des logs (En nombre de caractères)
     */
    private static int largeurAffichage=30;
    
    /*
     * Liste des logs
     */
    private LinkedList<String> listeLog = new LinkedList<String>();
    
    /*
     * Récupérer la taille de la liste des logs
     */
    public int size()
    {
        return listeLog.size();
    }
    
    /*
     * Modificateur des logs, ajouts de l'attaque qu'un personnage sur un autre
     * @param str - Phrase de log
     */
    public void ajoutLogDegat(String nomP, int degat)
    {
        listeLog.add(nomP+" subit "+degat+" dommages.");
        testNombreLog();
    }
    
    /*
     * Modificateur des logs, ajouts de l'attaque qu'un personnage sur un autre
     * @param str - Phrase de log
     */
    public void ajoutLogSoin(String nomP, int soin)
    {
        listeLog.add(nomP+" a été soigné de "+soin+".");
        testNombreLog();
    }
    
    /*
     * Modificateur des logs, ajouts d'un décès
     * @param str - Phrase de log
     */
    public void ajoutLogMort(String nomP1)
    {
        listeLog.add(nomP1+" est mort!");
        testNombreLog();
    }
    
    /*
     * Modificateur des logs, ajouts d'un décès
     * @param message Message à afficher.
     */
    public void ajoutMessage(String message)
    {
        if( message.length()<largeurAffichage )
            listeLog.add(message);
        else{
            boolean fin = true;
            int deb=0;
            while( fin ){
                if( message.substring(deb).length()>largeurAffichage ){
                    listeLog.add(message.substring(deb, deb+largeurAffichage) );
                }
                else if( message.substring(deb).length()<=largeurAffichage ){
                    listeLog.add( message.substring(deb) );
                    fin =false;
                }
                deb+=largeurAffichage;
            }
        }
        testNombreLog();
    }
     /**
     * Accesseur au log indexé i.
     * @param index - Index du log.
     * @return - Le log indexé i.
     */
    public String getLog(int index)
    {
        return listeLog.get(index);
    }
    /**
     * Méthode qui supprime le premiers log crées au dessus d'une certaine limite de logs.
     */
    private void testNombreLog()
    {
        if( listeLog.size()==(nbrMax+1) )
            listeLog.removeFirst();
    }
    
    /**
     * Méthode qui affiche les logs
     * @param font - Police avec laquelle on écrit les logs
     * @param X - Position des logs.
     * @param Y - Position des logs.
     */
    public void afficheLogs(UnicodeFont font, int X, int Y){
        ListIterator<String> it = (listeLog.size()>nbrAffichage ? listeLog.listIterator(listeLog.size() - nbrAffichage) : listeLog.listIterator(0));
        int l = -20;
        while (it.hasNext()) { 
            font.drawString( X,  Y+(l+=20), it.next());
        }
    }
    
    /**
     * Méthode pour afficher les logs dans la console
     */
    public void logConsole(){
        for( String tmp : listeLog ){
            System.out.println(tmp);
        }
    }
    /**
     * Méthode pour vider les logs
     */
    public void clear(){
        listeLog.clear();
    }
}
