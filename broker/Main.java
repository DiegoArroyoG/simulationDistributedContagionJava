import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static String[] line;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
        Inet4Address dir_origen = null , dir_destino = null;
        
        dir_origen =  (Inet4Address) Inet4Address.getByName(args[0]);
        List <Pais> paises_broker = leerFichero(dir_origen);
        
        Broker bro = new Broker(dir_origen, paises_broker);
        for(Pais a : paises_broker){
            a.setBroker(bro);
        }

        if(args.length > 1){
            dir_destino = (Inet4Address) Inet4Address.getByName(args[1]);
            bro.call(dir_destino, 1);
        }

        bro.init();      
    }

    private static List<Pais> leerFichero(Inet4Address dir_origen) {
        String[] line2;
        List <Pais> paises_broker = new ArrayList <Pais> (); 
        try {
            Scanner input = new Scanner(new File("Config.txt"));
            while (input.hasNextLine()) {
                line = input.nextLine().split(",");
                HashMap <Integer, Inet4Address> vecinos = new HashMap<Integer, Inet4Address>();
                for(int p = 6; p < line.length - 1; p++){
                    line2 = line[p].split("-");
                    vecinos.put(Integer.parseInt(line2[0].trim()), (Inet4Address) Inet4Address.getByName(line2[1].trim()));
                }
                Pais p1 = new Pais(line[0].trim(), line[1].trim(), line[2].trim(), line[3].trim(), line[4].trim(), line[5].trim(), vecinos, Integer.parseInt(line[line.length-1].trim()));
                
                if(p1.getIP().equalsIgnoreCase(dir_origen.getHostAddress())){ 
                    paises_broker.add(p1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paises_broker;
    }