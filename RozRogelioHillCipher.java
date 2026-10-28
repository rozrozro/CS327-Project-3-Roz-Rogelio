
import java.util.Arrays;
public class RozRogelioHillCipher {
        //do not touch pls  VVV
    private static final int MOD = 26; //26 letters in alphabet
    

    //make method called mod, generates floor mod
    public static int mod(int value) {
        return Math.floorMod(value, MOD);
    }

    //extended euclidian 
    //gets gcd of int a & int b 
    public static int[] xgcd(int a, int b) {
        if (b == 0) { //if b = 0 create int array containing numbers a, 1, 0
            return new int[]{a, 1, 0}; //i.e. xgcd(5,0), {5, 1, 0} 
        }

        int[] result = xgcd(b, a % b); //create array integer variable named result
            //xgcd calls same method again
            //(b, a % b) provides new values for a & b
        int gcd = result[0];
        int x = result[2];
        int y = result[1] - (a / b) * result[2];

        return new int[]{gcd, x, y};
    }

        
    // 2.a) Calculate the decryption key from a given encryption key.
    //      Where 'ecryptedKey' is a 2x2 maxtrix-based encryption key.
    public static int[][] findDecryptionKey(int[][] encryptionKey) {
        // (a , b)
        // (c , d)
        int a = encryptionKey[0][0];
        int b = encryptionKey[0][1];
        int c = encryptionKey[1][0];
        int d = encryptionKey[1][1];

        // Find the determinant = (a*d - b*c) mod 26
        int determinant = mod((a * d) - (b * c));

        int[] multInv = xgcd(determinant, 26);

        if (multInv[0] != 1) {
            throw new IllegalArgumentException("gcd != 1");
        }

        int detInv = mod(multInv[1]);
        detInv = (detInv + 26) % 26;

        // construct decryption key:
        //          ( d , -b)
        // detInv * (-c ,  a) mod 26
        int[][] decKey = new int[2][2];
        decKey[0][0] = ( d * detInv) % 26;
        decKey[0][1] = (-b * detInv) % 26;
        decKey[1][0] = (-c * detInv) % 26;
        decKey[1][1] = ( a * detInv) % 26;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                decKey[i][j] = (decKey[i][j] + 26) % 26;
            }
        }

        return decKey;
    }

        
    // 2.b) Encrypt a multiple-letter plaintext message with a given 2x2 encryption key
public static int[] encrypt(int[] plaintext, int[][] encryptionKey) {

    //add X if plaintext has odd number of letters
    if (plaintext.length % 2 != 0) {
        plaintext = Arrays.copyOf(plaintext, plaintext.length + 1);
        plaintext[plaintext.length - 1] = 23;
    }

    //make array to store encrypted message
    int[] ciphertext = new int[plaintext.length];

    //two letters at a time
    for (int i = 0; i < plaintext.length; i += 2) {

        int p1 = plaintext[i];
        int p2 = plaintext[i + 1];

        //plaintext * encryption key
        ciphertext[i] = mod(
            encryptionKey[0][0] * p1 +
            encryptionKey[0][1] * p2
        );

        ciphertext[i + 1] = mod(
            encryptionKey[1][0] * p1 +
            encryptionKey[1][1] * p2
        );
    }

    return ciphertext;
}

        
    // 2.c) Decrypt a multiple-letter ciphertext with a given 2x2 decryption key
public static int[] decrypt(int[] ciphertext, int[][] decryptionKey) {
    int[] plaintext = new int[ciphertext.length];

    //two letters at a time
    for (int i = 0; i < ciphertext.length; i += 2) {

        int c1 = ciphertext[i];
        int c2 = ciphertext[i + 1];
        //ciphertext * decryption key
        plaintext[i] = mod(
            decryptionKey[0][0] * c1 +
            decryptionKey[0][1] * c2
        );
        plaintext[i + 1] = mod( decryptionKey[1][0] * c1 + decryptionKey[1][1] * c2
        );
    }

    return plaintext;

}

        //test Hill Cipher for CS327 Project 3
    public static void main(String[] args) {

        //given encryption key
        int[][] encryptionKey = {
            {16, 7},
            {9, 14}
        };

        //2.a) find and print decryption key
        int[][] decryptionKey = findDecryptionKey(encryptionKey);
        System.out.println("Decryption Key:");
        System.out.println(Arrays.toString(decryptionKey[0]));
        System.out.println(Arrays.toString(decryptionKey[1]));

        //2.b) encrypt JMUCSISCOOL
        String message = "JMUCSISCOOL";
        //add X if message has odd number of letters
        //encrypt() will handle the padding
        int[] plaintext = new int[message.length()];
        for (int i = 0; i < message.length(); i++) {
            plaintext[i] = message.charAt(i) - 'A';
        }
        int[] ciphertext = encrypt(plaintext, encryptionKey);
        String encryptedMessage = "";
        for (int i = 0; i < ciphertext.length; i++) {
            encryptedMessage += (char)(ciphertext[i] + 'A');
        }

        System.out.println("\nEncrypted JMUCSISCOOL:");
        System.out.println(encryptedMessage);

        //2.c) decrypt MQGVGQSMJI
        String encrypted = "MQGVGQSMJI";
        int[] cipherNumbers = new int[encrypted.length()];
        for (int i = 0; i < encrypted.length(); i++) {
            cipherNumbers[i] = encrypted.charAt(i) - 'A';
        }
        int[] decrypted = decrypt(cipherNumbers, decryptionKey);

        String decryptedMessage = "";
        for (int i = 0; i < decrypted.length; i++) {
            decryptedMessage += (char)(decrypted[i] + 'A');
        }

        System.out.println("\nDecrypted MQGVGQSMJI:");
        System.out.println(decryptedMessage);
    }


}
