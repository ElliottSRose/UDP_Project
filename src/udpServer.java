import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class udpServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(8888);// open port to listen
        byte[] receive = new byte[65535];
        DatagramPacket DpReceive = null;
        while (true) {
            System.out.println("starting server...");
            DpReceive = new DatagramPacket(receive, receive.length); // create appropriate sized data packet
            ds.receive(DpReceive);// retrieve data
            System.out.println("recieved = " + DpReceive);
            receive = new byte[65535];// reset the buffer
            break;
        }
    }
}
