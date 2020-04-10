import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection extends Thread {
    private String tipo; // 1 es para Start_Listen
    private Broker bro;

    DataInputStream in;
    ObjectOutputStream out;
    Socket clientSocket;
    Broker broker;

    public Connection (Socket clientSocket, Broker broker) {
       try {
           this.broker = broker;
    	   this.clientSocket = clientSocket; 
    	   in = new DataInputStream(clientSocket.getInputStream()); //Canal de entrada cliente
    	   out =new ObjectOutputStream(clientSocket.getOutputStream()); //Canal de salida cliente
    	   this.start(); //hilo
	   } catch(IOException e){
		   System.out.println("Connection:"+e.getMessage());
       }
    }

    public void run() {
        String type=null;
        
        try {
            type = in.readUTF();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (type.equalsIgnoreCase("1")) {
            try {
                while (true) {
                    System.out.println("Se conecto uno...");
                    out.writeObject(broker.getBrokers());
                    //String[] mensajeRecibido = in.readUTF().split(":");
                    //broker.brokers.add(mensajeRecibido[0].trim());
                    //clientSocket.close();
                }
            } catch (Exception e) {
                System.out.println("Error de entrada/salida." + e.getMessage());
            }
        }
    }
}