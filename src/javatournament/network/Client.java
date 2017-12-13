package javatournament.network;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client (Réseau) du jeux.
 * Permet de se connexter et de communiquer avec le serveur.
 */
public class Client {

    private String Nom;                              // Alias du client
    private Socket s;                                // Reference sur le socket
    private PrintStream canalEcriture;               // Flux bufferise pour ecrire vers le serveur
    private Ecouteur ecoute;

    /**
     * Construction d'un client
     * @param host Nom du serveur
     * @param port Port de connexion sur le serveur
     */
    public Client(String host, int port) throws UnknownHostException, IOException{
        s = new Socket(host, port);
        canalEcriture = new PrintStream(s.getOutputStream());
        ecoute = new Ecouteur(s);
        //System.out.println("Client : Connexion sur : " + host + " port : " + s.getPort());
    }

    /**
     * Méthode qui envoie un string au serveur.
     * @param s - String à envoyer.
     */
    public void envoie(String s) {        
        try {
            PrintStream print = new PrintStream(this.s.getOutputStream());
            print.println(s);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Methode qui retourne le flux d'envoie du client.
     * @return OutputStream
     * @throws IOException 
     */
    public OutputStream getOutputStream() throws IOException{
        return this.s.getOutputStream();
    }
    /**
     * Méthode pour arreter le thread d'ecoute
     */
    public void arret() {
        ecoute.interrupt();
    }
}