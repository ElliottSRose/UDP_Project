import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class packetConverter {
    public static void main(String[] args) throws IOException {
                    File f = new File("test.txt");
                    FileInputStream in = new FileInputStream(f);

                    int offset = 0;
                    int bytesRead = 0;

//                    Queue<byte[]> q = new LinkedList<>();
                    while(true) {
                        byte[] data = new byte[1024];
                        bytesRead = in.read(data, offset, data.length);
                        offset += bytesRead;

                        String str = new String(data, 0, offset, "UTF-8");
                        System.out.println("str= " + str);
                        int byte_len = data.length; // figure out how big the file is
                        System.out.println("data length = " + byte_len);
                        System.out.println("offset = " + offset);
                        System.out.println("bytesread = " + bytesRead);
//                        q.add(data);
//                        q.peek();
                        if(bytesRead <1024) {
                            break;
                        }
                        in.close();
                    }
                }

            }

//                                while ((bytesRead = in.read(data, offset, data.length - offset))
//                            != -1) {
//                        offset += bytesRead;
//                        if (offset >= data.length) {
//                            break;
//                        }
//                    }