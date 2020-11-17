// Java program to fill a subarray array with
// given value.
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test
{
//    public static void main(String[] args) throws IOException {
//        byte[] totalBytes = Files.readAllBytes(Paths.get("COSC635_P2_DataSent.txt"));
////        byte[] seqNum = {10};
////        byte[] destination = new byte[totalBytes.length + seqNum.length];
////        System.arraycopy(seqNum, 0, destination, 0, seqNum.length);
////        System.arraycopy(totalBytes, 0, destination, seqNum.length, totalBytes.length);
//        String s = new String(totalBytes, StandardCharsets.UTF_8);
//        System.out.println(s);
////        System.out.println(destination[0]);
////        String tester = "1hello";
////        System.out.println("before " +Integer.parseInt(tester.substring(0,1)));
////        tester = tester.concat("jojojo");
////        System.out.println("after " +tester);
//    }
//}



    public static void main(String[] args) {
            try {
            File file = new File("COSC635_P2_DataSent.txt");
            System.out.println(file.length());
            } catch (Exception e) {
            }
            }};

