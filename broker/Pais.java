import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
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
        private HashMap<Integer, Inet4Address> vecinos = new HashMap<Integer, Inet4Address>();
        private boolean hilo = true;

        public Pais(String nombre, String poblacion, String infectados, String pAis, String g, String ip,
                        HashMap<Integer, Inet4Address> vecinos, int port) throws UnknownHostException {
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

        public void setIP(Inet4Address ip) {
                this.dir_ip = ip;
                for (Map.Entry<Integer, Inet4Address> entry : vecinos.entrySet()) {
                        Integer port_destino = entry.getKey();
                        Inet4Address dir_destino = entry.getValue();
                        try {
                                call(dir_destino, port_destino, ip.getHostAddress());
                        } catch (ClassNotFoundException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                }
        }

        public void setInfectados(int infectados) {
                this.infectados = this.infectados + infectados;
        }

        public String getNombrePais() {
                return this.nombre;
        }

        public String getIP() {
                return this.dir_ip.getHostAddress();
        }

        public int getPort()
        {
                return this.port;
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

        public void setHilo(boolean hilo)
        {
                this.hilo = hilo;
        }

        public void changeIpVecino(String ip2c, Integer port2c) throws UnknownHostException {
                vecinos.put(port2c, (Inet4Address) Inet4Address.getByName(ip2c));
        }

        public void call(Inet4Address dir_destino, int port_destino) throws IOException, ClassNotFoundException {
                Socket client = null;
                try {
                        client = new Socket(dir_destino, port_destino);
                        DataOutputStream out = new DataOutputStream(client.getOutputStream());
                        out.writeUTF("1,");

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

        public void call(Inet4Address dir_destino, int port_destino, String nueva_ip) throws IOException, ClassNotFoundException {
                Socket client = null;
                try {
                        client = new Socket(dir_destino, port_destino);
                        DataOutputStream out = new DataOutputStream(client.getOutputStream());
                        out.writeUTF("2," + nueva_ip + ","+ port);
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
                
        }

        public void init() {
                new GossipListen(this).start();
                this.start();
        }

        public void run() {

                while (hilo) {
                        if(infectados!=0){        
                                this.infectados = this.infectados + 1;
                                try {
                                        System.out.println(nombre + " " + this.peso * 1000+ " "+ infectados);
                                        if (infectados == poblacion * 0.5)
                                                for (Map.Entry<Integer, Inet4Address> entry : vecinos.entrySet()) {
                                                        Integer port_destino = entry.getKey();
                                                        Inet4Address dir_destino = entry.getValue();
                                                        try {
                                                                System.out.print(nombre + " contagio a ");
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
}