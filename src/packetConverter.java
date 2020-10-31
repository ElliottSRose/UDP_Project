import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class packetConverter {
    public static void main(String[] args) throws IOException {
                    File f = new File("test.txt");
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
                    String str = new String(data, 0, offset, "UTF-8");
//                    System.out.write(data);
//                    System.out.println("str= " + str);
                }

            }
