import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Pais extends Thread implements Serializable{

        private String nombre;
        private int poblacion;
        private int peso;
        private int infectados;
        private int recuperados;
        private float vulnerable;
        private float beta;
        private float gamma;
        private Inet4Address dir_ip;
        private List<Pais> vecinos;
        private Broker broker_mine;

        public Pais(String nombre, String poblacion, String infectados, String pAis, String g, String ip)
                        throws UnknownHostException {
                this.nombre = nombre;
                this.poblacion = Integer.parseInt(poblacion);
                this.infectados = Integer.parseInt(infectados);
                this.recuperados = 0;
                this.vulnerable = Integer.parseInt(poblacion) - Integer.parseInt(infectados);
                this.beta = 1 - Float.parseFloat(pAis);
                this.gamma = Float.parseFloat(g);
                this.dir_ip = (Inet4Address) Inet4Address.getByName(ip);
                this.peso = 0;
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

        public void call(Inet4Address dir_destino, int valor) throws IOException, ClassNotFoundException
        {
                Socket client = null;
                try {
                        client = new Socket(dir_destino, 8888);
                        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                        out.writeObject("Me contagie");

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
                while (true) {
                        this.infectados = this.infectados + 1;
                        try {
                                System.out.println(nombre + " " +this.peso*1000);
                                sleep(this.peso * 1000);
                        } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                }

        }
}