package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Server started!");

        String address = "127.0.0.1";
        int port = 23456;
        ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));
        Socket socket = server.accept();
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        String receivedData = input.readUTF();
        System.out.println("Received: " + receivedData);

        String sentData = "A " + receivedData.split("me a ")[1] + " was sent!";
        output.writeUTF(sentData);
        System.out.print("Sent: " + sentData);
    }
}
