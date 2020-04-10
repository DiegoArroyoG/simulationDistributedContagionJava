import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;

public class Pais {
    private String nombre;
    private int poblacion;
    private int infectados;
    private int recuperados;
    private int vulnerable;
    private int beta;
    private int gamma;
    private Inet4Address dir_ip;
    private List<Pais> vecinos;
    private Broker broker_mine;

    public Pais(String nombre, String poblacion, String infectados, String pAis, String g, String ip)
            throws UnknownHostException {
        this.poblacion = Integer.parseInt(poblacion);
        this.infectados = Integer.parseInt(infectados);
        this.recuperados = 0;
        this.vulnerable = Integer.parseInt(poblacion) - Integer.parseInt(infectados);
        this.beta = 1 - Integer.parseInt(pAis);
        this.gamma = Integer.parseInt(g);
        this.dir_ip = (Inet4Address) Inet4Address.getByName(ip);
	}

	public void setBroker(Broker bro) {
        this.broker_mine = bro;
	}
}