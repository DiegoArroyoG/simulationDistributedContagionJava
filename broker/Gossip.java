import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Gossip extends Thread {

    Socket clientSocket;
    DataInputStream in;
    DataOutputStream out;
    Pais pais;

    public Gossip(Pais pais) {
        this.pais = pais;
    }

    public void reply(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.in = new DataInputStream(this.clientSocket.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.start();
    }

    public void run() {

        String read = null;
        try {
            read = in.readUTF();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String[] info = null;
        info = read.toString().split(",");
        if (info[0].equalsIgnoreCase("1")) {
            pais.setInfectados(1);
            System.out.println(pais.getNombrePais());
        } else if (info[0].equalsIgnoreCase("2")) {
            System.out.println("Cambio de direccion Ip de pais...");
            try {
                pais.changeIpVecino(info[1], Integer.parseInt(info[2]));
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }
}