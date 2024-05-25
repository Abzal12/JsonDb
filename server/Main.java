package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    public static final String ADDRESS = "127.0.0.1";
    public static final int PORT = 23456;

    public static void main(String[] args) {

        String[] fileDB = new String[1000];
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS));
        ) {
            System.out.println("Server started!");
            while (true) {
                try (
                        Socket socket = server.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                    ){
                        String[] receivedData = input.readUTF().split(" ", 3);
                        switch (receivedData[0]) {
                            case "exit" -> {
                                return;
                            }
                            case "get" -> {
                                String cell = fileDB[Integer.parseInt(receivedData[1]) - 1];
                                if (cell == null || (Integer.parseInt(receivedData[1]) < 1 || Integer.parseInt(receivedData[1]) > 100)) {
                                    output.writeUTF("ERROR");
                                } else {
                                    output.writeUTF(fileDB[Integer.parseInt(receivedData[1]) - 1]);
                                }
                            }
                            case "set" -> {
                                if (Integer.parseInt(receivedData[1]) < 1 || Integer.parseInt(receivedData[1]) > 100) {
                                    output.writeUTF("ERROR");
                                } else {
                                    fileDB[Integer.parseInt(receivedData[1]) - 1] = receivedData[2];
                                    output.writeUTF("OK");
                                }
                            }
                            case "delete" -> {
                                if (Integer.parseInt(receivedData[1]) < 1 || Integer.parseInt(receivedData[1]) > 100) {
                                    output.writeUTF("ERROR");
                                } else {
                                    fileDB[Integer.parseInt(receivedData[1]) - 1] = null;
                                    output.writeUTF("OK");
                                }
                            }
                        }
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
