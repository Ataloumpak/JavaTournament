package javatournament.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

class Connexion extends Thread
{
    protected Socket client;
    protected Serveur serveur;
    protected BufferedReader in;
    protected PrintStream out;
    protected Nettoyeur nettoyeur;
    
    public Connexion(Socket client_soc, Nettoyeur n, Serveur s)
    {
        client=client_soc;
        nettoyeur=n;
        serveur=s;
        try
        {
            in=new BufferedReader(new InputStreamReader(client.getInputStream()));
            out=new PrintStream(client.getOutputStream());
        }
        catch (IOException e)
        {
            try { 
                client.close(); 
            }
            catch (IOException e1) {
                System.err.println("Connexion : IOException : "+e1.getMessage());
            }
            
            System.err.println("Connexion : IOException : "+e.getMessage());
            return;
        }
        this.start();
    }
    
    public void run()
    {
        String ligne;
        boolean nonFini=true;
        try
        {
            while(nonFini)
            {
                ligne=in.readLine();
                if ( ligne == null )
                {
                     nonFini=false;
                     //System.out.println("Connexion : Fermeture intempestive du flux");
                }
                else
                {
                    synchronized(serveur.connexions)
                    {
                         for(Connexion c:serveur.connexions){
                             c.out.println(ligne);
                         }                             
                    }
                    nonFini=!ligne.endsWith("FIN");
                }
            }
        }
        catch (IOException e)
        {}
        finally
        {
            try
            {client.close();}
            catch (IOException e)
            {};
            //System.out.println("Connexion : Fin de connexion...");
            synchronized(nettoyeur)
            {nettoyeur.notify();}
        }
    }
    
    /**
     * Méthode pour arreter le thread de connexion.
     */
    public void arret(){
        try {
            client.close();
        } catch (IOException ex) {
            System.err.println("Connexion : impossible d'arreter le socket du client.");
        }
        this.interrupt();
    }
}
