import java.net.Inet4Address;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
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
                System.out.println(register_ip.toString());
                try {
                        client = new Socket(register_ip, 7777);
                        ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                        DataOutputStream out = new DataOutputStream(client.getOutputStream());
                        out.writeUTF(ip.toString() + ':' + 7777);
                        List<String> sBrokers = (List<String>) in.readObject();

                        for (String broker : sBrokers) {
                                brokers.add(broker);
                                System.out.println("------------------>" + broker);
                        }
                        /*
                         * for (int i = 0; i < brokers.size(); i++) { client = new
                         * Socket(Inet4Address.getByName(brokers.get(i).split(":")[0]),
                         * Integer.parseInt(brokers.get(i).split(":")[1])); in = new
                         * DataInputStream(client.getInputStream()); out = new
                         * DataOutputStream(client.getOutputStream()); out.writeUTF(ip.toString() + ':'
                         * + port); } System.out.println("Check in exitoso"); for (int i = 0; i <
                         * brokers.size(); i++) System.out.println(brokers.get(i));
                         */

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

        public void start_listen() {
                try {
                        System.out.println("Esperando conexion con otros brokers...");
                        ServerSocket listenSocket = new ServerSocket(7777); // Inicializar socket con el puerto

                        while (true) {
                                Socket clientSocket = listenSocket.accept(); // Esperar en modo escucha al cliente
                                new Connection(clientSocket, this); // Establecer conexion con el socket del
                                                                    // cliente(Hostname, Puerto)
                        }

                } catch (IOException e) {
                        System.out.println("Listen socket:" + e.getMessage());
                }

        }

        public List<String> getBrokers() {
                return this.brokers;
        }

        public void start() {
                this.run();
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