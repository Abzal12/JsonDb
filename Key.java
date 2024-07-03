package encryptdecrypt;

public class Key {

    public static String encryptUsingAscii(String text, int shift) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            sb.append((char) (text.charAt(i) + shift));
        }
        return sb.toString();
    }

    public static String decryptUsingAscii(String encryptedText, int shift) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encryptedText.length(); i++) {
            sb.append((char) (encryptedText.charAt(i) - shift));
        }
        return sb.toString();
    }

    public static String encryptUsingAlphabet(String text, int shift) {
        StringBuilder sb = new StringBuilder();
        boolean isFoundInAlphabet;
        for (int i = 0; i < text.length(); i++) {
            isFoundInAlphabet = false;
            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            for (int j = 0; j < alphabet.length(); j++) {
                if (text.charAt(i) == alphabet.charAt(j)) {
                    sb.append(alphabet.charAt((j + shift)% alphabet.length()));
                    isFoundInAlphabet = true;
                    break;
                }
            }
            if (!isFoundInAlphabet) {
                sb.append(text.charAt(i));
            }
        }
        return sb.toString();
    }

    public static String makeTextCipher(String text) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char currentCh = text.charAt(i);
            if (currentCh >= 'a' && currentCh <= 'z')
            {
                char cipherCh = (char) ('z' - (currentCh - 'a'));
                result.append(cipherCh);
            }
            else
            {
                result.append(currentCh);
            }
        }
        return result.toString();
    }
}
