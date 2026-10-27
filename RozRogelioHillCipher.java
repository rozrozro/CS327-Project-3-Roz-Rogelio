
import java.util.Arrays;

public class AbdullaRozHillCipher {
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
