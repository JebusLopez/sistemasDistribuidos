package trivia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class OperacionesServidor extends Thread{
    
    Socket sc;
    int contador;
    String data, mensaje;
    DataInputStream in;
    DataOutputStream out;
    Logger logger;
    FileHandler fh;
    SimpleFormatter formater;
    
    OperacionesServidor(Socket sc, int contador) {
        this.sc = sc;
        this.contador = contador;
    }
    
    public void run(){
        try {
            convertir();
        } catch (IOException ex) {
            Logger.getLogger(OperacionesServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void convertir() throws IOException {
        System.out.println("Hilo de atencion numero: " + contador);
        System.out.println("Operaciones Iniciadas " + getName() + " " + sc.getInetAddress());
        in = new DataInputStream(sc.getInputStream());
        out = new DataOutputStream(sc.getOutputStream());
        logger = Logger.getLogger("Entradas");
        fh = new FileHandler("respuestas.txt");
        formater = new SimpleFormatter();
        fh.setFormatter(formater);
        logger.addHandler(fh);
        String[] mix = {"ktscoe","nertinte","cxnoieno","rotpeu","ppi-tc"};
        String[] org = {"socket","internet","conexion","puerto","tcp-ip"};
        Date date = new Date();
        DateFormat dFormat = new SimpleDateFormat("HH:mm");
        DateFormat hFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        while(true){
            
            data = in.readUTF();
            
            if(data.equals("1")){
            
                String q = "";
                String d = "\n";
                int aciertos = 0;
                String info = "";
                
                for(int i= 0; i<5; i++){
                    q = "Que palabra se forma con la siguiente cadena: "+mix[i];
                    out.writeUTF(info+q);
                    data = in.readUTF();
                    if(data.equals(org[i])){
                        aciertos ++;
                        info = "IP: "+sc.getRemoteSocketAddress()+" Correcto,"+ data +" "+hFormat.format(date)+" "+dFormat.format(date)+" Aciertos:"+aciertos+"\n";
                        d += info;
                    }
                    else{
                    
                        info = "IP: "+sc.getRemoteSocketAddress()+" Incorrecto," + data +" "+hFormat.format(date)+" "+dFormat.format(date)+ " Aciertos: "+aciertos+"\n";
                        d += info;
                    }
                }
                
                q = "Nombre del jugador?: ";
                out.writeUTF(q);
                data = in.readUTF();
                info = "IP: "+sc.getRemoteSocketAddress()+" Nombre del Jugador: " + data + " Total: "+aciertos;
                d+= info;
                System.out.println("Inprimiendo info: " + d.toString());
                logger.info(d.toString());
                out.writeUTF("Total: "+aciertos);
            }
            else{
                out.writeUTF("Adios!!!");
            }
        }
    }
}
