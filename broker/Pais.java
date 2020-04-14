import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pais extends Thread implements Serializable {

        private String nombre;
        private int poblacion;
        private int peso;
        private int infectados;
        private int recuperados;
        private int port;
        private float vulnerable;
        private float beta;
        private float gamma;
        private Inet4Address dir_ip;
        private Broker broker_mine;
        private HashMap<Integer, Inet4Address> vecinos;

        public Pais(String nombre, String poblacion, String infectados, String pAis, String g, String ip,
                        Map<Integer, Inet4Address> vecinos, int port) throws UnknownHostException {
                this.nombre = nombre;
                this.poblacion = Integer.parseInt(poblacion);
                this.infectados = Integer.parseInt(infectados);
                this.recuperados = 0;
                this.vulnerable = Integer.parseInt(poblacion) - Integer.parseInt(infectados);
                this.beta = 1 - Float.parseFloat(pAis);
                this.gamma = Float.parseFloat(g);
                this.dir_ip = (Inet4Address) Inet4Address.getByName(ip);
                this.peso = 0;
                this.vecinos.putAll(vecinos);
                this.port = port;
        }

        public void setBroker(Broker bro) {
                this.broker_mine = bro;
        }

        public String getNombrePais() {
                return this.nombre;
        }

        public String getIP() {
                return this.dir_ip.getHostAddress();
        }

        public int getPoblacion() {
                return this.poblacion;
        }

        public int getInfectados() {
                return this.infectados;
        }

        public void setPeso(int calculo) {
                this.peso = calculo;
        }

        public void call(Inet4Address dir_destino, int port_destino) throws IOException, ClassNotFoundException {
                Socket client = null;
                try {
                        client = new Socket(dir_destino, port_destino);

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

        public void run() {
                if (infectados == 0) {
                        ServerSocket ListenSocket;
                        try {
                                ListenSocket = new ServerSocket(port);
                                ListenSocket.accept();
                                ListenSocket.close();
                        } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                        }
                }
                infectados = 1;
                System.out.println("XXXXXXXXXXXXXXXXXXX QUE COMIENCE EL JUEGO XXXXXXXXXXXXXXXXXXXXXXXXXXXx");
                while (true) {
                        this.infectados = this.infectados + 1;
                        try {
                                System.out.println(nombre + " " + this.peso * 1000);
                                if (infectados == poblacion * 0.5)
                                        for (Map.Entry<Integer, Inet4Address> entry : vecinos.entrySet()) {
                                                Integer port_destino = entry.getKey();
                                                Inet4Address dir_destino = entry.getValue();
                                                try {
                                                        call(dir_destino, port_destino);
                                                } catch (ClassNotFoundException e) {
                                                        // TODO Auto-generated catch block
                                                        e.printStackTrace();
                                                } catch (IOException e) {
                                                        // TODO Auto-generated catch block
                                                        e.printStackTrace();
                                                }
                                        }
                                sleep(this.peso * 1000);
                        } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                }

        }
}