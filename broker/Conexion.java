import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Conexion extends Thread {
    private String tipo; // 1 es para Start_Listen
    private Broker bro;

    public Conexion(String string, Broker bro) {
        this.tipo = string;
        this.bro = bro;
    }

    public void run() {
        if (this.tipo.equalsIgnoreCase("1")) {
            try {
                while (true) {
                    ServerSocket sc = new ServerSocket(1024);
                    Socket so = new Socket();
                    System.out.println("Esperando conexión...");
                    so = sc.accept();
                    System.out.println("Se conecto uno...");
                    DataInputStream entrada = new DataInputStream(so.getInputStream());
                    ObjectOutputStream salida = new ObjectOutputStream(so.getOutputStream());
                    salida.writeObject(bro.getBrokers());
                    String[] mensajeRecibido = entrada.readUTF().split(":");
                    bro.brokers.add(mensajeRecibido[0].trim());
                    sc.close();
                }
            } catch (Exception e) {
                System.out.println("Error de entrada/salida." + e.getMessage());
            }
        }
    }
}