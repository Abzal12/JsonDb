package encryptdecrypt;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String targetOperation = scanner.nextLine();
        String inputText = scanner.nextLine();
        int shift = scanner.nextInt();

        if (targetOperation.equals("enc")) {
            String encryptedText = Key.encryptUsingAscii(inputText, shift);
            System.out.println(encryptedText);
        } else if (targetOperation.equals("dec")) {
            String decryptedText = Key.decryptUsingAscii(inputText, shift);
            System.out.println(decryptedText);
        }
    }
}
