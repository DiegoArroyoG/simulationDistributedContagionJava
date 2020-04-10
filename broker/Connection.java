import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Connection extends Thread {
    
    DataInputStream in;
    ObjectOutputStream out;
    Broker broker;

    public Connection (Broker broker) {
        this.broker = broker;
    }

    public void reply(DataInputStream in, ObjectOutputStream out)
    {
        this.in = in;
        this.out = out;
        this.start();
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
                System.out.println("Se conecto uno nuevo con ip " + info[1] + "...");
                List<String> brokers = broker.getBrokers();
                brokers.add(broker.getIp());
                out.writeObject(brokers);
                broker.addBroker(info[1]);
            } catch (Exception e) {
                System.out.println("Error de entrada/salida." + e.getMessage());
            }
        }
        else if(info[0].equalsIgnoreCase("2")) {
            try {
                System.out.println("Se esta presentando uno nuevo"+ info[1] + "...");
                broker.addBroker(info[1]);
                out.writeObject("Agregado por " + broker.getIp());
            } catch (Exception e) {
                System.out.println("Error de entrada/salida." + e.getMessage());
            }
        }
    }
}