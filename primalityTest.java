import java.util.Scanner;

public class primalityTest {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testInt;
        boolean intFormat = false;
        int aRandom;
        int gcd, q, r, a, b;
        final int TEST_COUNT = 100;

        //get an int from user input and check >1
        do {
            System.out.println("Please enter an Integer: ");
            testInt = sc.nextInt();

            if (testInt > 1) {
                System.out.println("You entered (" + testInt + ") !");
                intFormat = true;
            } else {
                System.out.println("The integer must be bigger than 1 !");
                System.out.println("You entered (" + testInt + ") !");
            }
        } while(!intFormat);


        sc.close();

        //select a random int a  0 < a < p
        aRandom = (int) (Math.random() * (testInt - 1)) + 1;
        //System.out.println("The random number 'a'  0 < a < testInt is:  " + aRandom);

        //find gcd for testInt and aRandom
        b = testInt;
        a = aRandom;
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
        //System.out.println("gcd(" + testInt + ", " + aRandom + ") = " + gcd);
        // check first condition of Fermats test
        if(gcd != 1) System.out.println("The number '" + testInt +
                                       "' is a composite number!");

        //check 2nd condition of Fermat's test
        if(gcd == 1){
            int aPowered = (int) Math.pow(aRandom, testInt - 1);
            int modTest = aPowered % testInt;

            if(modTest != 1) System.out.println("The number '" + testInt +
                                                "' is a composite number!");

            if(modTest == 1){
                System.out.println("The number '" + testInt +
                        "' is either prime or Charmichael number!");
            }
        }

    }
}
