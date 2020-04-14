import java.io.Serializable;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        private Broker broker_mine;
        private HashMap <String,Inet4Address> vecinos;

        public Pais(String nombre, String poblacion, String infectados, String pAis, String g, String ip, Map<String, Inet4Address> vecinos2)
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
                this.vecinos = (HashMap<String, Inet4Address>) vecinos2;
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