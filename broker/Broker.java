import java.net.Inet4Address;
import java.net.InetAddress;

import pais.Pais;


public class Broker{

        Inet4Address ip;
        Inet4Address register_ip;
        int port;
        List<Pais> paises = new ArrayList<Pais>();
        List<String> brokers = new 

        public Broker(Inet4Addres ip, int port, Inet4Addres register_ip, List<Pais> paises)
        {
                this.ip = ip;
                this.port = port;
                this.register_ip = register_ip;
                this.paises = paises;
        }

        public void check_in()
        {
                try{
                        s = new Socket(register_ip, 1024);    
                        DataInputStream in = new DataInputStream(s.getInputStream());
                        DataOutputStream out =new DataOutputStream(s.getOutputStream());
                        out.writeUTF(ip.toString()+':'+port);
                        String brokers = in.readUTF();
                        System.out.println("Leí: "+ data) ; 
                     }catch (UnknownHostException e){
                          System.out.println("Socket:"+e.getMessage());
                     }catch (EOFException e){
                          System.out.println("EOF:"+e.getMessage());
                     }catch (IOException e){
                          System.out.println("readline:"+e.getMessage());
                     }finally {if(s!=null) try { s.close(); }catch (IOException e){
                          System.out.println("close:"+e.getMessage());}}
                    }
        }

    def check_in(self):
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            client.connect((self.register_ip, 1024))
            client.send((str(self.ip)+":"+str(self.port)).encode())
    brokers = client.recv(4096)
    self.brokers = brokers.decode().split()
        for i in range (len(self.brokers)):
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            client.connect((self.brokers[i].split(':')[0], int(self.brokers[i].split(':')[1])))
            client.send((str(self.ip) + ":" + str(self.port)).encode())
    print("Check in exitoso" + (' '.join([str(broker) for broker in self.brokers])))

    def append_new(self, broker_to_append):
            self.brokers.append(broker_to_append)
    print("[*] Nuevo broker añadido" + broker_to_append)

    def connector(self, socket_broker):
    msg = socket_broker.recv(1024).decode()
        if ':' in msg:
            self.append_new(msg)
            else:
    print("[*] Mensaje " + msg)
            socket_broker.send("Recibido")
                    socket_broker.close()

    def start_listen(self):
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            server.bind((self.ip, self.port))
            server.listen(5)
    print("Broker has started")
        while True:
    socket_broker, ip_port = server.accept()
    print("[***] Broker intentado conectarse")
    connect = threading.Thread(target=self.connector, args=(socket_broker,))
            connect.start()

    def getPaises(self):

    print(self.paises[0].check_in())

}