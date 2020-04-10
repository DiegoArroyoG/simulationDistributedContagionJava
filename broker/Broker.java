import java.net.Inet4Address;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.UnknownHostException;

public class Broker extends Thread {

        Inet4Address ip;
        List<Pais> paises = new ArrayList<Pais>();
        List<String> brokers = new ArrayList<String>();

        public Broker(Inet4Address ip, List<Pais> paises) {
                this.ip = ip;
                this.paises = paises;
        }

        public void check_in(Inet4Address register_ip) throws IOException, ClassNotFoundException {
                Socket client = null;
                try {
                        client = new Socket(register_ip, 7777);
                        ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                        DataOutputStream out = new DataOutputStream(client.getOutputStream());
                        out.writeUTF("1," + ip.getHostAddress());
                        this.brokers = (List<String>) in.readObject();

                        for (String broker : brokers) {
                                System.out.println("------------------>" + broker);
                        }
                        
                        for (int i = 0; i < brokers.size(); i++) 
                        { 
                                client = new Socket(Inet4Address.getByName(brokers.get(i)), 7777); 
                                in = new ObjectInputStream(client.getInputStream());
                                out = new DataOutputStream(client.getOutputStream()); 
                                out.writeUTF("2," + ip.getHostAddress()); 
                                System.out.println((String)in.readObject());

                        } 
                        System.out.println("Check in exitoso"); 
                        for(int i = 0; i <brokers.size(); i++) System.out.println(brokers.get(i));
                        

                } catch (UnknownHostException e) {
                        System.out.println("Socket:" + e.getMessage());
                } catch (EOFException e) {
                        System.out.println("EOF:" + e.getMessage());
                } catch (IOException e) {
                        System.out.println("readline:" + e.getMessage());
                } finally {
                        if (client != null)
                                try {
                                        client.close();
                                } catch (IOException e) {
                                        System.out.println("close:" + e.getMessage());
                                }
                }
        }

        public void start_listen()
        {
                try {
                        while (true) 
                        {
                                ServerSocket ListenSocket = new ServerSocket(7777);
                                System.out.println("Esperando conexión...");
                                Socket clientSocket = ListenSocket.accept();
                                System.out.println("Se conecto un broker...");
                             
                                new Connection(this).reply(clientSocket);
                           
                                ListenSocket.close();
                        }
                    } catch (Exception e) {
                        System.out.println("Error de entrada/salida BROKER." + e.getMessage());
                    }
        }

        public String getIp()
        {
                return ip.getHostAddress();
        }

        public List<String> getBrokers() {
                return this.brokers;
        }

        public void addBroker(String broker)
        {
                this.brokers.add(broker);
        }

        public void init() {
                this.start();
                start_listen();
        }

        public void run() {
                System.out.println("ESTOY LEYENDO A MIS PAISES");
                try {
                        sleep(1000);
                } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }       

}