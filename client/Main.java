package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

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

            DbPiece dbPiece = new DbPiece(
                    args1.getRequestType(),
                    args1.getKey(),
                    args1.getValue()
            );

            String dbPiece1 = new Gson().toJson(dbPiece);
            output.writeUTF(dbPiece1);
            System.out.println("Sent: " + dbPiece1);

            String receivedData = input.readUTF();
            System.out.println("Received: " + receivedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
