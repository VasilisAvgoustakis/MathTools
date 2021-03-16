import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.math.BigInteger;

public class mathTools {

    /**Finds the greatest common divisor of 2 integers
     * @param n any integer number
     * @param d any integer number
     * @return  gcd greatest common divisor of the 2 given integers
     * */
    public static int gcd(int n, int d){

        int gcd, q, r, a, b;
        b = n;
        a = d;
        q = b / a;
        r = b - (q * a);

        while(!(r == 0)){
            int tempB = a;
            b = tempB;
            int tempR = r;
            q = b / tempR;
            a = tempR;
            r = b - (q * a);
        }
        gcd = a;
        return gcd;
    }

    /**An efficient way to compute d^(n-1) (mod n)
     *
     * @param d   a new random int d  0 < d < p
     * @param p   n-1
     * @param n   an integer as mod
     * @return
     */
    public static int modExp(int d, int p, int n){
        int b;
        switch(p){
            case 0:
                return 1;
            case 1:
                return d%n;
            default:
                b = modExp(d,p/2,n);
                b = (b*b) % n; // thats where the error probably is
                if(p%2 == 1) b = (b*d) % n;
                return b;
        }
    }

    /**An efficient way to compute d^(n-1) (mod n)
     *
     * @param d   a new random int d  0 < d < p
     * @param p   n-1
     * @param n   an integer as mod
     * @return
     */
    public static BigInteger bigModExp(int d, int p, int n){
        BigInteger b;
        switch(p){
            case 0:
                return new BigInteger(String.valueOf(1));
            case 1:
                return new BigInteger(String.valueOf(d%n));
            default:
                b = bigModExp(d,p/2,n);
                b = b.multiply(b).mod(new BigInteger(String.valueOf(n))); // thats where the error probably is
                if(p%2 == 1) b = b.multiply(b).mod(new BigInteger(String.valueOf(n)));
                return b;
        }
    }

    public static boolean isCharmichael(int n){
        int a, s;
        boolean factorFound = false;
        if(n%2 == 0) return false;
        //else
        s = (int) Math.sqrt(n);
        a = 2;
        while(a < n){
            if(a > s && !factorFound){
                return false;
            }
            if(gcd(a,n) > 1){
                factorFound = true;
            }
            else{
                if(modExp(a,n-1,n) != 1){
                    return false;
                }
            }
            a++;
        }
        return true;
    }

    public static List<Integer> charmichaelRangeList(int n){
        List<Integer> charmichaelRangeList = new LinkedList<Integer>();
        for(int i = 2; i < n; i++){
            if(isCharmichael(i))charmichaelRangeList.add(i);
        }
        System.out.println(charmichaelRangeList.toString());
        return charmichaelRangeList;
    }

    public static void fermatsPrimalityTest() {
        Scanner sc = new Scanner(System.in);
        boolean nFormat = false;
        int n, d, gcd;
        BigInteger dPowN, bigD, bigN, dPowModN;
        int prime = 0;
        int composite = 0;
        int testCount = 100;

        //get an int from user input
        try {
            do {
                System.out.println("Please enter an Integer: ");
                n = sc.nextInt();

                if (n > 1) {
                    System.out.println("You entered (" + n + ") !");
                    nFormat = true;
                } else {
                    System.out.println("The integer must be bigger than 1 !");
                    System.out.println("You entered (" + n + ") !");
                }
            } while (!nFormat);
        }
        //catch wrong character input
        catch(InputMismatchException ex){
            throw new InputMismatchException("The character you entered is not an integer! " +
                    "Please try again...");
        }
        //close Scanner
        sc.close();

        //make n of BigInteger Type
        bigN = new BigInteger(String.valueOf(n));

        //Fermats primality testing loop
        while(testCount > 0) {
            //select a new random int d  0 < d < p every time the loop runs
            d = (int) (Math.random() * (n - 1)) + 1;
            //make d also to BigInteger type
            bigD = new BigInteger(String.valueOf(d));
            //System.out.println("The random number 'd'  0 < d < n is:  " + d);

            //calculate gcd of d and n
            gcd = gcd(d,n);

            //1.Step consider Fermats first condition, if gcd != 1 then n is definitely a composite number
            if(gcd != 1) {
                System.out.println("The number '" + n +
                        "' is a composite number!");
                break;
            }

            //if gcd = 1 then consider we consider the value of d^n−1 mod n.
            if(gcd == 1){
                dPowN = bigD.pow(n-1);
                //System.out.println("d^n-1 = " + dPowN);
                dPowModN = dPowN.mod(bigN);
                //System.out.println("d^n-1 mod n = " + dPowModN);

                //if the value of d^n−1 mod n is not 1 then again n is definitely a composite number
                if(dPowModN.intValue() != 1){
                //if(bigModExp(d, n-1, n).intValue() != 1){
                    System.out.println("The number '" + n +
                            "' is a composite number!");
                    break;
                }

                //else the loops ends when 100 tries have been completed and thta means that n is probably a
                //prime or charmichael number
            }
            testCount--;
        }

        if(testCount == 0) {
            List<Integer>charmichaelNumbers = new LinkedList<Integer>(charmichaelRangeList(n));
            boolean found = false;
            //for(Integer i : charmichaelNumbers){
            //    if(i == n){
            //        found = true;
            //        System.out.println("The number '" + n +
            //                "' is a Charmichael number!");
            //    }
            //}
            if(isCharmichael(n))found = true;
            if(!found)System.out.println("The number '" + n +
                    "' is a prime number!");

        }
    }

    public static void main(String[] args){
       fermatsPrimalityTest();
        //charmichaelRangeList(100000);
        int n;
        //for(n = 2; n < 100000; n ++){
        //    if(isCharmichael(n)) System.out.println(n);
        //}
        boolean b = isCharmichael(41041);
        System.out.println(b);



    }
}
