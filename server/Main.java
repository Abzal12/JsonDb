package server;

import client.DbPiece;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static final String ADDRESS = "127.0.0.1";
    public static final int PORT = 23456;

    public static void main(String[] args) {

        String[] fileDB = new String[1000];
        Map<String, String> fileDbAsMap = new HashMap<>();
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS));
        ) {
            System.out.println("Server started!");
            while (true) {
                try (
                    Socket socket = server.accept();
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                    ) {
                        String receivedData2 = input.readUTF();
                        DbPiece dbPiece = new Gson().fromJson(receivedData2, DbPiece.class);

                        Response errorObj = new Response("ERROR", "No such key", null);
                        switch (dbPiece.getType()) {
                            case "exit" -> {
                                output.writeUTF(new Gson().toJson(new Response("OK", null, null)));
                                return;
                            }
                            case "get" -> {
                                String cell = fileDbAsMap.get(dbPiece.getKey());
                                if (cell == null)
                                {
                                    output.writeUTF(new Gson().toJson(errorObj));
                                }
                                else
                                {
                                    output.writeUTF(new Gson().toJson(new Response("OK", null, fileDbAsMap.get(dbPiece.getKey()))));
                                }
                            }
                            case "set" -> {
                                fileDbAsMap.put(dbPiece.getKey(), dbPiece.getValue());
                                output.writeUTF(new Gson().toJson(new Response("OK", null, null)));
                            }
                            case "delete" -> {
                                if (fileDbAsMap.get(dbPiece.getKey()) == null)
                                {
                                    output.writeUTF(new Gson().toJson(errorObj));
                                }
                                else
                                {
                                    fileDbAsMap.remove(dbPiece.getKey());
                                    output.writeUTF(new Gson().toJson(new Response("OK", null, null)));
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
