package trivia;

import CadenaMayusMinus.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    
    public static void main(String[] args){
        ServerSocket servidor = null;
        Socket sc = null;
        int PUERTO = 5000;
        OperacionesServidor os = null;
        
        try {
           //Creamos el socket del servidor
           servidor = new ServerSocket(PUERTO);
           System.out.println("Servidor iniciado...");
           int contador = 0;
            //Escuchando peticiones
            while (true) {
                //Espero a que conecte un ciente
                sc = servidor.accept();
                contador++;
                os = new OperacionesServidor(sc,contador);
                os.start();
            }
        }
        catch(IOException e){}
    }
}
