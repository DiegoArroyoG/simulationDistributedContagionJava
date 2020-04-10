

public class Broker{

    public Broker(ip, port, register_ip, paises)
        def __init__(self, ip, port, register_ip, paises):
        self.ip = ip
        self.port = port
        self.brokers = []
        self.register_ip = register_ip
        self.paises = paises

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