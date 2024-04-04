public class KeySet {
    private final Key publicKey, privateKey;

    public KeySet(int p, int q) {
        int n = p * q;
        int phi = (p - 1) * (q - 1);

        int e = 2;
        while (e < phi) {
            if (gcd(e, phi) == 1) {
                break;
            }
            e++;
        }

        int d = 1;
        while (d < phi) {
            if ((d * e) % phi == 1) {
                break;
            }
            d++;
        }

        publicKey = new Key(e, n);
        privateKey = new Key(d, n);
    }

    public Key getPublicKey() {
        return publicKey;
    }

    public Key getPrivateKey() {
        return privateKey;
    }
    private int gcd(int a, int b) {
        if (a == 0) {
            return b;
        }
        return gcd(b % a, a);
    }
}
