import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) {
        final String HOST = "35.174.116.4";
        final int PORT = 5000;
        DataInputStream in;
        DataOutputStream out;
        
        try {
            //Socket para conectar con el servidor
            Socket sc = null; 
            sc = new Socket(HOST, PORT);
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            Scanner entrada = new Scanner(System.in);
            String data = null,data2 = null;
            int cont = 0;
                
            System.out.println("BIENVENIDO A LA TRIVIA!!!");
            menu();
            data = entrada.next();
            out.writeUTF(data);
            if(data.equals("2")){
                    cont = 7;
            }
            do{   
                cont++;
                data2 = in.readUTF();
                System.out.println(""+data2);
                data = entrada.next();
                out.writeUTF(data);
            }while(cont < 7);              
            out.close();
            in.close();
            sc.close();
            
        }catch (IOException ex) {
            System.out.println("Exception: " + ex);
        }
    }
    
    public static void menu(){
        System.out.println("1)Jugar");
        System.out.println("2)Salir");
    }    
}
