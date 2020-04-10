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
        List <Pais> paises_broker = leerFichero();
        
        dir_origen =  (Inet4Address) Inet4Address.getLocalHost();
        Broker bro = new Broker(dir_origen, paises_broker);
        for(Pais a : paises_broker){
            a.setBroker(bro);
        }
        
        if(args.length > 0){
            dir_destino = (Inet4Address) Inet4Address.getByName(args[0]);
            bro.check_in(dir_destino);
        }

        bro.start();        
    }

    private static List<Pais> leerFichero() {
        List <Pais> paises_broker = new ArrayList <Pais> (); 
        try {
            Scanner input = new Scanner(new File("Config.txt"));
            while (input.hasNextLine()) {
                line = input.nextLine().split(",");
                Pais p1 = new Pais(line[0].trim(), line[1].trim(), line[2].trim(), line[3].trim(), line[4].trim(), line[5].trim());
                paises_broker.add(p1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paises_broker;
    }
}