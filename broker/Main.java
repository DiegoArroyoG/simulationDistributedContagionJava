import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main extends Thread {

    private static String[] line;
    private static String[] args1;
    public static Inet4Address dir_origen;
    private static List<Pais> paises_broker;
    public static List<String> paises;
    public static  Broker bro;

    public static void main(String[] args) throws UnknownHostException{
    		args1 = args;
    		
           GUIDistribuidos.main();
    
    }

    static List<Pais> leerFichero(Inet4Address dir_origen) {
        String[] line2;
        paises_broker = new ArrayList <Pais> (); 
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
    
    public static List<Pais> getPaises_Broker(){
    	return paises_broker;
    }
    
   /* public static void init() throws IOException, ClassNotFoundException {
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
        Inet4Address dir_destino = null;
   
        List <Pais> paises_broker = leerFichero(dir_origen);
        
        bro = new Broker(dir_origen, paises_broker);
        for(Pais a : paises_broker){
            a.setBroker(bro);
        }

        if(args1.length > 1){
            dir_destino = (Inet4Address) Inet4Address.getByName(args1[1]);
            bro.call(dir_destino, 1);
        }

        
    }*/
    
    public static void iniBroker(String origen, String destino) throws ClassNotFoundException, IOException {
    	System.out.println(Inet4Address.getLocalHost().getHostAddress());
        Inet4Address dir_destino = null;
        dir_origen = (Inet4Address) Inet4Address.getByName(origen);
        List <Pais> paises_broker = leerFichero(dir_origen);
        
        bro = new Broker(dir_origen, paises_broker);
        for(Pais a : paises_broker){
            a.setBroker(bro);
        }

        if(!destino.equals("")){
            dir_destino = (Inet4Address) Inet4Address.getByName(args1[1]);
            bro.call(dir_destino, 1);
        }
					
					bro.init();
			
    }
}