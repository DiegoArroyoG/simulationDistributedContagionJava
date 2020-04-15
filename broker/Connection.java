import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Connection extends Thread{

    Socket clientSocket;
    ObjectInputStream in;
    ObjectOutputStream out;
    Broker broker;

    public Connection(Broker broker) {
        this.broker = broker;
    }

    public void reply(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.in = new ObjectInputStream(this.clientSocket.getInputStream());
            this.out = new ObjectOutputStream(this.clientSocket.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.start();
    }

    public void run() {

        Object read = null;
        try {
            read = in.readObject();
        } catch (ClassNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        if(read instanceof Pais)
        {
            Pais pais = (Pais) read;
            broker.addPais(pais);
            try {
                out.writeObject(true);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else{    
            String[] info=null;
            info = read.toString().split(",");
            if (info[0].equalsIgnoreCase("1")) {
                try {
                    System.out.println("Se conecto uno nuevo con ip " + info[1] + "...");
                    List<String> brokers = new ArrayList<String>();
                    brokers.addAll(broker.getBrokers());
                    brokers.add(broker.getIp());
                    out.writeObject(brokers);
                } catch (Exception e) {
                    System.out.println("Error de entrada/salida." + e.getMessage());
                }
            }
            else if(info[0].equalsIgnoreCase("2")) {
                try {
                    System.out.println("Se esta presentando uno nuevo "+ info[1] + "...");
                    broker.addBroker(info[1]);
                    out.writeObject("Agregado por " + broker.getIp());
                } catch (Exception e) {
                    System.out.println("Error de entrada/salida." + e.getMessage());
                }
            }
            else if(info[0].equalsIgnoreCase("3")){
                
                try {
                    out.writeObject(broker.getCalculo());
                } catch (IOException e) {
                    System.out.println("Error de PESO" + e.getMessage());
                }
            }
        }
    }
}