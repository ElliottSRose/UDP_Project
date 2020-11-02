import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

public class udpServer {
    public static void main(String[] args) throws IOException {
//        File destfile = new File("hello.txt");
//        FileOutputStream fos = new FileOutputStream(destfile);
//        BufferedOutputStream bos = new BufferedOutputStream(fos);

        DatagramSocket ds = new DatagramSocket(8888);// open port to listen
        byte[] receive = new byte[4096];
        ByteBuffer buff = ByteBuffer.wrap(receive);
        DatagramPacket DpReceive = null;

        while (true) {
            System.out.println("Server is awaiting packets...");
            DpReceive = new DatagramPacket(receive, receive.length); // create appropriate sized data packet
            ds.receive(DpReceive);// retrieve data
            String msg = new String(DpReceive.getData(), DpReceive.getOffset(), DpReceive.getLength());// to format the bytes back into strings
            System.out.println("Unmodified packet recieved = " + DpReceive.getData());
            System.out.println("Recieved text = " + msg);
//            bos.write(msg);// We can add this later so we don't need to continue rewriting

            buff.clear();
            buff.rewind(); //reset buffer
//            break;// If we want the server to close, we will have to trigger it with the packet headers
        }
    }
}
