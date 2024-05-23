package server;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String[] fileDB = new String[100];

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String[] userCmd = scanner.nextLine().split(" ", 3);

            switch (userCmd[0]) {
                case "get" -> {
                    String cell = fileDB[Integer.parseInt(userCmd[1]) - 1];
                    if (cell == null || (Integer.parseInt(userCmd[1]) < 1 || Integer.parseInt(userCmd[1]) > 100)) {
                        System.out.println("ERROR");
                    } else {
                        System.out.println(fileDB[Integer.parseInt(userCmd[1]) - 1]);
                    }
                }
                case "set" -> {
                    if (Integer.parseInt(userCmd[1]) < 1 || Integer.parseInt(userCmd[1]) > 100) {
                        System.out.println("ERROR");
                    } else {
                        fileDB[Integer.parseInt(userCmd[1]) - 1] = userCmd[2];
                        System.out.println("OK");
                    }
                }
                case "delete" -> {
                    if (Integer.parseInt(userCmd[1]) < 1 || Integer.parseInt(userCmd[1]) > 100) {
                        System.out.println("ERROR");
                    } else {
                        fileDB[Integer.parseInt(userCmd[1]) - 1] = null;
                        System.out.println("OK");
                    }
                }
                case "exit" -> {
                    return;
                }
            }
        }
    }
}
