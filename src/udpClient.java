
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class udpClient
{
    public static void main(String args[]) throws IOException
    {
        DatagramSocket ds = new DatagramSocket();
        InetAddress ip = InetAddress.getLocalHost();
        byte[] data = new byte[4096];
        ByteBuffer buf = ByteBuffer.wrap(data);

        DatagramPacket pkt;
        File file = new File("test.txt");
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        int bytesRead = 0;

        while(true){
            buf.clear();
            bytesRead = bis.read(data);
            if(bytesRead==-1) {
                break;
            }
            buf.rewind();
            pkt = new DatagramPacket(data,4096, ip,8888);
            ds.send(pkt);
        }
        bis.close();
        fis.close();
    }
}