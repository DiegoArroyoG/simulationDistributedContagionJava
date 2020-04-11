import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static String[] line;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Inet4Address dir_origen = null , dir_destino = null;
        
        dir_origen =  (Inet4Address) Inet4Address.getByName(args[0]);
        List <Pais> paises_broker = leerFichero(dir_origen);
        
        Broker bro = new Broker(dir_origen, paises_broker);
        for(Pais a : paises_broker){
            a.setBroker(bro);
        }

        if(args.length > 1){
            dir_destino = (Inet4Address) Inet4Address.getByName(args[1]);
            bro.check_in(dir_destino, 1);
        }

        bro.init();      
    }

    private static List<Pais> leerFichero(Inet4Address dir_origen) {
        List <Pais> paises_broker = new ArrayList <Pais> (); 
        try {
            Scanner input = new Scanner(new File("Config.txt"));
            while (input.hasNextLine()) {
                line = input.nextLine().split(",");
                Pais p1 = new Pais(line[0].trim(), line[1].trim(), line[2].trim(), line[3].trim(), line[4].trim(), line[5].trim());
                
                if(p1.getIP().equalsIgnoreCase(dir_origen.getHostAddress())){ 
                    paises_broker.add(p1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paises_broker;
    }
}