package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static final String ADDRESS = "127.0.0.1";
    public static final int PORT = 23456;
    static String FILE_REQUEST_PATH_TEST_ENVIRONMENT = System.getProperty("user.dir") + "/src/client/data/";
    static String FILE_REQUEST_PATH_LOCAL_ENVIRONMENT = System.getProperty("user.dir") + "/JSON Database with Java/task/src/client/data/";

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

            DbPiece dbPiece = null;

            if (args1.getInputFromFile() == null) {
                 dbPiece= new DbPiece(
                        args1.getRequestType(),
                        args1.getKey(),
                        args1.getValue()
                );
            } else {
                String inputFromFile = new String(Files.readAllBytes(
                        Paths.get(FILE_REQUEST_PATH_TEST_ENVIRONMENT + args1.getInputFromFile()))
                );
                dbPiece = new Gson().fromJson(inputFromFile, DbPiece.class);
            }

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
