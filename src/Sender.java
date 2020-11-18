import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Sender {

    private static int windowSize = 4;
    private static int currentSeqNum = 1;
    private static int totalPacketsSent = 0;
    private static long start = 0;
    private static long end = 0;
    private static int max = 4094;// subtracted 2 to fit seqNum and lastPacket identifier
    private static int userNum;
    private static int testNum;
    private static int packetLoss;
    private static int startIndex;
    private static int endIndex;
    private static byte[] data;
    private static int lostSeqNum;

    static int packetLossSim() {
        //user inputs number 0-99
        Scanner reader = new Scanner(System.in);
        do {
            System.out.println("Please enter a number from 0-99:");
            userNum = Integer.parseInt(reader.nextLine());
            if (userNum < 0 || userNum > 99) {
                System.out.println("Invalid. Please enter a number from 0-99: \n");
                testNum = -1;   //wrong input = loop again
            } else if (userNum >= 0 && userNum <= 99) {
                return userNum;
            }
        } while (userNum >= 0 && userNum <= 99 || testNum == -1);
        return userNum;
    }

    static byte[] checkIfLastPacket(byte[] totalBytes, int endIndex, int currentSeqNum){
        byte[] num = new byte[2];
        num[0] = (byte) currentSeqNum;
        if (totalBytes.length < endIndex){
            num[1] = (byte) -1;
        }else{
            num[1] = (byte) 0;
        }   return num;
    }

    static byte[] setupPacket(int max, int currentSeqNum, byte[] totalBytes) {
        startIndex = max * currentSeqNum;
        endIndex = startIndex + max;
        data = new byte[4094];
        if (endIndex> totalBytes.length){
            endIndex = totalBytes.length;
        }
        data = Arrays.copyOfRange(totalBytes, startIndex, endIndex); //get bytes for the current packet from totalBytes
        byte[] seqNum = checkIfLastPacket(totalBytes, endIndex, currentSeqNum);
        byte[] destination = new byte[data.length + seqNum.length];
        System.arraycopy(seqNum, 0, destination, 0, seqNum.length);
        System.arraycopy(data, 0, destination, seqNum.length, data.length);
        return destination;
    }

    static int goBackN(int lostSeqNum){
//        each step adds one because we are 1 based indexing
        if (lostSeqNum%4==0){
            return lostSeqNum-4 +1;
        }else{
            return lostSeqNum - (lostSeqNum%4)+1;
        }
    }

    public static void main(String args[]) throws IOException {
        byte[] totalBytes = Files.readAllBytes(Paths.get("COSC635_P2_DataSent.txt")); //convert entire file to bytes
        try {//SETUP OF CONNECTION AND FILE
            DatagramSocket ds = new DatagramSocket();
            ds.setSoTimeout(3000); //arbitrary milliseconds
            userNum = packetLossSim();//user inputs number 0-99
            start = System.nanoTime(); //start the timer
            while (true) {//BEGIN SENDING DATA
                int eachRoundCompare = currentSeqNum + windowSize;
                while (currentSeqNum < eachRoundCompare) {  //CHECK IF WINDOW SIZE HAS BEEN SENT
                    byte[] destination = setupPacket(max, currentSeqNum, totalBytes);
                    DatagramPacket pkt = new DatagramPacket(destination, destination.length, InetAddress.getLocalHost(), 8888);
                    int pseudoNum = new Random(System.currentTimeMillis()).nextInt(99); //pseudonumber generated using random seed set to current system time
                    System.out.println("Pseudonum is " + pseudoNum);

                    if (pseudoNum < userNum) { // SIMULATE LOSS ELSE SEND
                        System.out.println("Packet being lost = " + currentSeqNum);
                        ++packetLoss; //keep count of total packet losses
                        lostSeqNum = currentSeqNum;
                        ++currentSeqNum;
                        ++totalPacketsSent;
                    } else { //SEND PACKETS
                        ds.send(pkt);
                        System.out.println("currentSeqNum sent " + currentSeqNum);
                        ++currentSeqNum;
                        ++totalPacketsSent;
                    }if (currentSeqNum == eachRoundCompare) { //if end of round check for ack
                        try {
                            System.out.println("Waiting for ack after sending packet number " + (currentSeqNum-1));
                            byte[] ackBytes = new byte[1024] ; //arbitrary number for ACK bytes
                            DatagramPacket ack = new DatagramPacket(ackBytes, ackBytes.length); //create new Datagram packet for ACK coming in -- need to fill in parameters
                            ds.receive(ack);
                            System.out.println("ack received " + ack.getData());
                        } catch (SocketTimeoutException e) {
                            System.out.println("Timeout error, packet " + lostSeqNum+ " lost");
                            currentSeqNum = goBackN(lostSeqNum);
                            System.out.println("The refreshed currentSeqNum after loss is "+ currentSeqNum);
                            }
                        }
                    if (totalBytes.length < currentSeqNum * 4094) {
                        System.out.println("Packets sent: " + totalPacketsSent);
                        System.out.println("Lost packets: " + packetLoss);
                        return;
                    }
                    }
                }
            }
        finally {
            end = System.nanoTime(); //end the timer
            System.out.println("Elapsed time: " + (end - start));
            System.out.println("Goodbye!");
        }
    }
};

