
import java.util.Arrays;

public class AbdullaRozCruzRogelioHillCipher {
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
        int determinant = ((a*d) - (b*c)) % 26;
        determinant = (determinant + 26) % 26;

        // use xcgd to find the modular multiplicative inverse
        int[] multInv = xgcd(determinant, 26);
        if (multInv[2] != 1) {
            throw new IllegalArgumentException("gcd != 1");
        }

        int detInv = multInv[0];
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

        
    // 2.b) Ecrypt a multiple-letter plaintext message with a given 2x2 encryption key
    public static int[] encrypt(int[] plaintext, int[][] encryptionKey) {
        return  null;
    }

        
    // 2.c) Decrypt a multiple-letter ciphertext with a given 2x2 decryption key
    public static int[] decrypt(int[] ciphertext, int[][] decryptionKey) {
        return  null;
    }
        
}
