import java.net.ServerSocket;
import java.net.Socket;

public class GossipListen extends Thread {

    Pais pais;

    public GossipListen(Pais pais){
        this.pais = pais;
    }

    public void run()
    {
        try {
            while (true) {
                    ServerSocket ListenSocket = new ServerSocket(pais.getPort());
                    Socket clientSocket = ListenSocket.accept();

                    new Gossip(pais).reply(clientSocket);

                    ListenSocket.close();
            }
    } catch (Exception e) {
            System.out.println("Error de entrada/salida PAIS." + e.getMessage());
    }
    }
}