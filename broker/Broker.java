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

        public int call(Inet4Address dir_destino, int valor) throws IOException, ClassNotFoundException {
                Socket client = null;
                try {
                        client = new Socket(dir_destino, 7777);
                        ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                        if (valor == 1) {
                                out.writeObject("1," + ip.getHostAddress());
                                this.brokers = (List<String>) in.readObject();

                                for (String broker : brokers) {
                                        System.out.println("------------------>" + broker);
                                }

                                for (int i = 0; i < brokers.size(); i++) {
                                        client = new Socket(Inet4Address.getByName(brokers.get(i)), 7777);
                                        in = new ObjectInputStream(client.getInputStream());
                                        out = new ObjectOutputStream(client.getOutputStream());
                                        out.writeObject("2," + ip.getHostAddress());
                                        System.out.println((String) in.readObject());

                                }
                                System.out.println("Check in exitoso");
                                for (int i = 0; i < brokers.size(); i++)
                                        System.out.println(brokers.get(i));
                                return -1;
                        } else if (valor == 2) {
                                out.writeObject("3," + ip.getHostAddress());
                                return (int) in.readObject();
                        }
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
                return -1;
        }

        public void start_listen() {
                try {
                        while (true) {
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

        public String getIp() {
                return ip.getHostAddress();
        }

        public List<String> getBrokers() {
                return this.brokers;
        }

        public void addBroker(String broker) {
                this.brokers.add(broker);
        }

        public void addPais(Pais pais)
        {
                System.out.println("llego el pais "+ pais.getNombrePais() +" con "+ pais.getInfectados() +" infectados");
                paises.add(pais);
                paises.get(paises.size()-1).start();
        }

        public int getCalculo() {
                /**int x = 0;
                for (Pais p : this.paises) {
                        x = x + p.getPoblacion();
                }
                return x;**/
                return paises.size();
        }

        public void init() {
                this.start();
                start_listen();
        }

        public void run() {
                int calculo;
                try {
                        int peso;
                        Inet4Address dir_destino;
                        calculo = getCalculo();
                        for (Pais p : paises) {
                                p.setPeso(calculo);
                        }
                        for (Pais p : paises) {
                                p.start();
                        }
                        while (true) {

                                for (String b : brokers) {
                                        dir_destino = (Inet4Address) Inet4Address.getByName(b);
                                        peso = this.call(dir_destino, 2);
                                        if((peso-paises.size())/2>0)
                                        {
                                                for(int i=0; i<(peso-paises.size())/2;i++)
                                                {
                                                        Socket client = new Socket(dir_destino, 7777);
                                                        ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                                                        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                                                        out.writeObject(paises.get(i));
                                                        System.out.println((String) in.readObject());
                                                        if((boolean) in.readObject())
                                                        {
                                                                System.out.println(paises.get(i).getNombrePais()+" con "+paises.get(i).getInfectados()+"infectados, enviado a " +dir_destino.getHostAddress());
                                                                paises.remove(i);
                                                        }
                                                        client.close();
                                                }
                                        }
                                        sleep(500);
                                }
                                calculo = getCalculo();

                                for (Pais p : paises) {
                                        p.setPeso(calculo);
                                }
                        }

                } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

}