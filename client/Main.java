package client;

import com.beust.jcommander.JCommander;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static final String ADDRESS = "127.0.0.1";
    public static final int PORT = 23456;

    public static void main(String[] args) throws IOException {

        try (

                Socket socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            ) {
            System.out.println("Client started!");
            Args args1 = new Args();
            JCommander.newBuilder()
                    .addObject(args1)
                    .build()
                    .parse(args);

            String sentData = String.format("%s %d %s",
                    args1.getRequestType(), args1.getCellIndex(), args1.getValue());
            output.writeUTF(sentData);
            sentData = sentData
                    .replace("null", "")
                    .replace("0", "")
                    .trim();
            System.out.println("Sent: " + sentData);

            if (!sentData.equals("exit")) {
                String receivedData = input.readUTF();
                System.out.println("Received: " + receivedData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
