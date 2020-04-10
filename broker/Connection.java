import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection extends Thread {
    
    DataInputStream in;
    Socket clientSocket;
    Broker broker;

    public Connection (Socket clientSocket, Broker broker) {
       try {
           this.broker = broker;
    	   this.clientSocket = clientSocket; 
    	   in = new DataInputStream(clientSocket.getInputStream()); //Canal de entrada cliente
    	   this.start(); //hilo
	   } catch(IOException e){
		   System.out.println("Connection:"+e.getMessage());
       }
    }

    public void run() {
        String[] info=null;
        
        try {
            info = in.readUTF().split(",");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (info[0].equalsIgnoreCase("1")) {
            try {
                System.out.println("Se conecto uno nuevo con ip" + info[1] + "...");
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                out.writeObject(broker.getBrokers());
                broker.addBroker(info[1]);
                clientSocket.close();
            } catch (Exception e) {
                System.out.println("Error de entrada/salida." + e.getMessage());
            }
        }
        else if(info[0].equalsIgnoreCase("2")) {
            try {
                System.out.println("Se esta presentando uno nuevo"+ info[1] + "...");
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                broker.addBroker(info[1]);
                out.writeObject("Agregado por " + broker.getIp());
                clientSocket.close();
            } catch (Exception e) {
                System.out.println("Error de entrada/salida." + e.getMessage());
            }
        }
    }
}