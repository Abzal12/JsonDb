package server;

import client.DbPiece;
import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static final String ADDRESS = "127.0.0.1";
    public static final int PORT = 23456;
    public static final String FILENAME_TEST_ENVIRONMENT =
            System.getProperty("user.dir") + "/src/server/data/db.json";
    public static final String FILENAME_LOCAL_ENVIRONMENT =
            System.getProperty("user.dir") + "/JSON Database with Java/task/src/server/data/db.json";

    public static void main(String[] args) throws IOException {

        int poolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        executor.submit(() -> {
            Map<String, String> fileDbAsMap = new HashMap<>();
            try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
                System.out.println("Server started!");
                while (true) {
                    try (
                            Socket socket = server.accept();
                            DataInputStream input = new DataInputStream(socket.getInputStream());
                            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                            FileWriter fw = new FileWriter(FILENAME_TEST_ENVIRONMENT, true);
                            BufferedWriter bw = new BufferedWriter(fw);
                            PrintWriter out = new PrintWriter(bw);
                    ) {
                        String receivedData2 = input.readUTF();
                        out.append("\n").append(receivedData2);
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
        });
        executor.shutdown();
    }
}
