
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class udpClient
{
    public static void main(String args[]) throws IOException
    {
        DatagramSocket ds = new DatagramSocket();
        InetAddress ip = InetAddress.getLocalHost();
        byte buf[] = packetConvert("test.txt");
        while (true)
        {
            System.out.println("starting client");
            DatagramPacket DpSend =
                    new DatagramPacket(buf, buf.length, ip, 8888);
            ds.send(DpSend);
            break;
        }
    }
    static byte[] packetConvert(String args) throws IOException {
        File f = new File(args);
        int len = (int) f.length(); // figure out how big the file is
        System.out.println("length = "+ len);
        FileInputStream in = new FileInputStream(f);

        int offset = 0;
        int bytesRead = 0;
        byte[] data = new byte[1024];
        while ((bytesRead = in.read(data, offset, data.length - offset))
                != -1) {
            offset += bytesRead;
            if (offset >= data.length) {
                break;
            }
        }
        return data;
}}
