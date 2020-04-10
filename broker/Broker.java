import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.List;

import pais.Pais;


public class Broker extends Thread{

        Inet4Address ip;
        int port;
        List<Pais> paises = new ArrayList<Pais>();
        List<String> brokers = new ArrayList<String>();

        public Broker(Inet4Addres ip, int port, List<Pais> paises)
        {
                this.ip = ip;
                this.port = port;
                this.paises = paises;
        }

        public void check_in(Inet4Address register_ip)
        {
                try{
                        Socket client = new Socket(register_ip, 1024);    
                        DataInputStream in = new DataInputStream(s.getInputStream());
                        DataOutputStream out =new DataOutputStream(s.getOutputStream());
                        out.writeUTF(ip.toString()+':'+port);
                        String sBrokers = in.readUTF();

                        for(String broker : sBrokers.split(" ")) brokers.add(broker);
                        
                        for(int i=0; i<brokers.size(); i++)
                        {
                                client = new Socket(brokers.get(i).split(":")[0] , brokers.get(i).split(":")[1]);
                                in = new DataInputStream(s.getInputStream());
                                out =new DataOutputStream(s.getOutputStream());
                                out.writeUTF(ip.toString()+':'+port);
                        }
                        System.out.println("Check in exitoso");
                        for(int i=0; i<brokers.size(); i++) System.out.println(brokers.get(i));

                }catch (UnknownHostException e){
                        System.out.println("Socket:"+e.getMessage());
                }catch (EOFException e){
                        System.out.println("EOF:"+e.getMessage());
                }catch (IOException e){
                        System.out.println("readline:"+e.getMessage());
                }finally {if(s!=null) try { s.close(); }catch (IOException e){
                        System.out.println("close:"+e.getMessage());}
                }
        }

        public void start_listen() {    
		try{
                        ServerSocket server = new ServerSocket(port); //Inicializar socket con el puerto

                        while(true) 
                        {
                                Socket clientSocket = server.accept(); //Esperar en modo escucha al cliente
                                this.start(clientSocket); //Establecer conexion con el socket del cliente(Hostname, Puerto)
                        }
			   
			} catch(IOException e) {
			      System.out.println("Listen socket:"+e.getMessage());
			}
		
        }
        
        public void run() {
                try {
                        clientSocket = aClientSocket; 
                        in = new DataInputStream(clientSocket.getInputStream()); //Canal de entrada cliente
                        out =new DataOutputStream(clientSocket.getOutputStream()); //Canal de salida cliente
                        this.start(); //hilo
                	                                    
                        String data = in.readUTF(); //Datos desde cliente
                        if(data.contains(":") && !data.contains(ip.toString()))
                                brokers.add(data);
                        else System.out.println("Todo bien");;

                } catch (EOFException e){
                        System.out.println("EOF:"+e.getMessage());
                } catch(IOException e){
                        System.out.println("readline:"+e.getMessage());
                }catch(IOException e){
                        System.out.println("Connection:"+e.getMessage()); 
                }finally{
                       try {
                           clientSocket.close(); 
                }catch (IOException e){}}
              } 

}