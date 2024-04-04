import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the Encrypter\n");

        boolean isValid = false;

        while (!isValid) {
            System.out.println("Select an option: ");
            System.out.println("0 - Example");
            System.out.println("1 - Generate keys");
            System.out.println("2 - Encrypt message");
            System.out.println("3 - Decrypt message");
            System.out.println("4 - Quit");

            int input = sc.nextInt();
            sc.nextLine();

            if (input == 0) {
                example();
            }
            else if (input == 1) {
                generateKeys();
            }
            else if (input == 2) {
                encryptMsg();
            }
            else if (input == 3) {
                decryptMsg();
            }
            else if (input == 4) {
                isValid = true;
            }
            else {
                System.out.println("Invalid input, try again\n");
            }
        }
    }

    private static void example() {
        System.out.println("Alice and Bob want to communicate, here's the steps that they follow: \n");

        System.out.println("Step 1: Alice and Bob generate keys separately on their own devices." +
                "\nAlice uses p = 11 and q = 13 and Bob uses p = 17 and q = 19.");
        KeySet aliceKeySet = new KeySet(11, 13);
        Key alicePublicKey = aliceKeySet.getPublicKey();
        Key alicePrivateKey = aliceKeySet.getPrivateKey();
        System.out.println("Alice's public key: " + alicePublicKey);
        System.out.println("Alice's private key: " + alicePrivateKey);

        KeySet bobKeySet = new KeySet(17, 19);
        Key bobPublicKey = bobKeySet.getPublicKey();
        Key bobPrivateKey = bobKeySet.getPrivateKey();
        System.out.println("Bob's public key: " + bobPublicKey);
        System.out.println("Bob's private key: " + bobPrivateKey + "\n");

        System.out.println("Step 2: Alice wants to send a message to Bob. She encrypts her message using BOB'S PUBLIC key.");
        String aliceMsg = "Can you read this message, Bob?";
        System.out.println("Alice's message: \n" + aliceMsg);
        System.out.println("Encrypted: ");
        ArrayList<Integer> aliceEncryptedMsg = bobPublicKey.encryptMsg(aliceMsg);
        for (int num : aliceEncryptedMsg) {
            System.out.print(num + " ");
        }
        System.out.println("\n");

        System.out.println("Step 3: Bob receives the encrypted message from Alice and uses his own PRIVATE key to decrypt it.");
        String aliceDecryptedMsg = bobPrivateKey.decryptMsg(aliceEncryptedMsg);
        System.out.println("Decrypted message: \n" + aliceDecryptedMsg + "\n");

        System.out.println("Step 4: Bob is ready to reply to Alice. He encrypts his message with ALICE'S PUBLIC key.");
        String bobMsg = "Yes, Alice, I can read it.";
        System.out.println("Bob's message: \n" + bobMsg);
        System.out.println("Encrypted: ");
        ArrayList<Integer> bobEncryptedMsg = alicePublicKey.encryptMsg(bobMsg);
        for (int num : bobEncryptedMsg) {
            System.out.print(num + " ");
        }
        System.out.println("\n");

        System.out.println("Step 5: Alice receives the encrypted message from Bob and uses her own PRIVATE key to decrypt it.");
        String bobDecryptedMsg = alicePrivateKey.decryptMsg(bobEncryptedMsg);
        System.out.println("Decrypted message: \n" + bobDecryptedMsg + "\n");
    }
    private static void generateKeys() {
        System.out.println("Enter two prime numbers, p and q: " +
                "\n(note: p != q and p * q > 126 (ascii))");
        int p = sc.nextInt(), q = sc.nextInt();
        sc.nextLine();

        System.out.println("Generated Keys:");
        KeySet keySet = new KeySet(p, q);
        Key publicKey = keySet.getPublicKey();
        Key privateKey = keySet.getPrivateKey();
        System.out.println("Public key: " + publicKey);
        System.out.println("Private key: " + privateKey + "\n");
    }
    private static void encryptMsg() throws IOException {
        System.out.println("Enter recipient's public key: (_, _)");
        Key publicKey = new Key(sc.nextInt(), sc.nextInt());
        sc.nextLine();

        FileReader fr = new FileReader("plaintext.txt");
        BufferedReader br = new BufferedReader(fr);
        FileWriter fw = new FileWriter("ciphertext.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        String line;

        while ((line = br.readLine()) != null) {
            ArrayList<Integer> encryptedMsg = publicKey.encryptMsg(line);
            for (int num : encryptedMsg) {
                bw.write(num + " ");
            }
            bw.newLine();
        }
        bw.close();
        System.out.println("Encrypted message successfully\n");
    }

    private static void decryptMsg() throws IOException {
        System.out.println("Enter private key: (_, _)");
        Key privateKey = new Key(sc.nextInt(), sc.nextInt());
        sc.nextLine();

        FileReader fr = new FileReader("ciphertext.txt");
        BufferedReader br = new BufferedReader(fr);
        FileWriter fw = new FileWriter("plaintext.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        String line;

        while ((line = br.readLine()) != null) {
            if (!line.isBlank()) {
                String[] lineSplit = line.split(" ", 0);

                ArrayList<Integer> encryptedMsg = new ArrayList<>();
                for (String str : lineSplit) {
                    encryptedMsg.add(Integer.parseInt(str));
                }

                String decryptedMsg = privateKey.decryptMsg(encryptedMsg);
                bw.write(decryptedMsg);
            }
            bw.newLine();
        }
        bw.close();
        System.out.println("Decrypted message successfully\n");
    }
}
