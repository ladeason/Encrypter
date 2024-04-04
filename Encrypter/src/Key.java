import java.util.ArrayList;

public class Key {
    private final int keyValue, modulus;

    public Key(int keyValue, int modulus) {
        this.keyValue = keyValue;
        this.modulus = modulus;
    }

    public ArrayList<Integer> encryptMsg(String msg) {
        ArrayList<Integer> encryptedMsg = new ArrayList<>();
        for (char letter : msg.toCharArray()) {
            encryptedMsg.add(encrypt(letter));
        }
        return encryptedMsg;
    }

    private int encrypt(int num) {
        int enc = 1;
        for (int i = 0; i < keyValue; i++) {
            enc *= num;
            enc %= modulus;
        }
        return enc;
    }

    public String decryptMsg(ArrayList<Integer> encryptedMsg) {
        StringBuilder msg = new StringBuilder();
        for (int num : encryptedMsg) {
            msg.append((char)decrypt(num));
        }
        return msg.toString();
    }

    private int decrypt(int num) {
        int dec = 1;
        for (int i = 0; i < keyValue; i++) {
            dec *= num;
            dec %= modulus;
        }
        return dec;
    }

    @Override
    public String toString() {
        return "(" + keyValue + ", " + modulus + ")";
    }
}
