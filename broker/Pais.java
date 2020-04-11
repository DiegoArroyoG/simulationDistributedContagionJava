import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;

public class Pais extends Thread {
        private String nombre;
        private int poblacion;
        private int totalPoblacion;
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
                this.totalPoblacion = 0;
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

        public void run() {
                while (true) {
                        this.infectados = this.infectados + 1;
                        try {
                                sleep(this.totalPoblacion * 1);
                        } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                }

        }

        public void setPoblacionTotal(int calculo) {
                this.totalPoblacion = calculo;
        }
}