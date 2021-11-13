package MathTools;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Numbers;

public class MathTools {

    /**Finds the greatest common divisor of 2 integers
     * @param n any integer number
     * @param d any integer number
     * @return  gcd greatest common divisor of the 2 given integers of type int
     * */
    public static int gcd(int n, int d){

        int gcd, q, r, a, b;
        b = n;
        a = d;
        q = b / a;
        r = b - (q * a);

        while(!(r == 0)){
            b = a;
            int tempR = r;
            q = b / tempR;
            a = tempR;
            r = b - (q * a);
        }
        gcd = a;
        return gcd;
    }

    /**Returns true if 'n' is a Carmichael number.
     * 'n' must be a composite number that satisfies the relation (b^n-1 ≡ 1 mod n for all 'b' co-prime of 'n'
     * @param n an integer
     * @return returns true if 'n' is Carmichael number
     */
    public static boolean isCarmichael(int n){
        int a, s;
        boolean factorFound = false;
        if(n%2 == 0) return false;
        //else
        s = (int) Math.sqrt(n);
        a = 2;
        while(a < n){
            //System.out.println(n);
            if(a > s && !factorFound){
                return false;
            }
            if(gcd(a,n) > 1){
                factorFound = true;
            }
            else{
                BigInteger bigA = new BigInteger(String.valueOf(a));
                if(!bigA.modPow(new BigInteger(String.valueOf(n-1)),new BigInteger(String.valueOf(n)))
                        .equals(new BigInteger(String.valueOf(1)))){
                    return false;
                }
            }
            a++;
        }
        return true;
    }

    /**Returns a LinkedList of all Carmichael numbers up to 'n'
     *
     * @param n a number of type int
     * @return a LinkedList of Integer Objects
     */
    public static LinkedList<Integer> carmichaelNumList(int n){
        LinkedList<Integer> list = new LinkedList<>();
        for(int i = 2; i < n; i++){
            if(isCarmichael(i))list.add(i);
        }
        return list;
    }


    /**An efficient Fermat's primality test for integer n.
     * It tests for primality 100 times each time using a new 'd' where 0 &lt;= d &lt;= n
     * @param n an number of type int
     * @return  a boolean value as to whether n is a prime number or not
     */
    public static boolean isPrime(int n){
        int  d, gcd;
        BigInteger dPowN, bigD, bigN, dPowModN;
        int testCount = 100;

        //make n of BigInteger Type
        bigN = new BigInteger(String.valueOf(n));

        //Fermat's primality testing loop
        while(testCount > 0) {
            //select a new random int d  0 < d < p every time the loop runs
            d = (int) (Math.random() * (n - 1)) + 1;
            //make d also to BigInteger type for calculation compatibility
            bigD = new BigInteger(String.valueOf(d));

            //calculate gcd of d and n
            gcd = gcd(d,n);

            //1.Step consider Fermat's first condition, if gcd != 1 then n is definitely a composite number
            if(gcd != 1) {
                return false;
            }

            //if gcd = 1 then we consider the value of d^n−1 mod n.
            else{
                dPowN = bigD.pow(bigN.intValue()-1);
                dPowModN = dPowN.mod(bigN);

                //if the value of d^n−1 mod n is not 1 then again n is definitely a composite number
                if(dPowModN.intValue() != 1){
                    return false;
                }
                //else the loops ends when 100 tries have been completed and that means that n is probably a
                //prime or Carmichael number
            }
            testCount--;
        }

        if(testCount == 0) {
            // check in n is a carmichael number and return false if it is
            return !isCarmichael(bigN.intValue());
        }
        //else its a prime
        return true;
    }

    /**Performs Fermat's primality test for an integer acquired through user input
     * This static method runs independently as a small program.
     */
    public static void fermatPrimalityTest() {
        Scanner sc = new Scanner(System.in);
        boolean nFormat = false;
        int n, d, gcd;
        BigInteger dPowN, bigD, bigN, dPowModN;
        int testCount = 100;

        //get an int from user input and catch corresponding exceptions
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

        //Fermat's primality testing loop
        while(testCount > 0) {
            //select a new random int d  0 < d < p every time the loop runs
            d = (int) (Math.random() * (n - 1)) + 1;
            //make d also to BigInteger type
            bigD = new BigInteger(String.valueOf(d));

            //calculate gcd of d and n
            gcd = gcd(d,n);

            //1.Step consider Fermat's first condition, if gcd != 1 then n is definitely a composite number
            if(gcd != 1) {
                System.out.println("The number '" + n +
                        "' is a composite number!");
                break;
            }

            //if gcd = 1 then consider we consider the value of d^n−1 mod n.
            else{
                dPowN = bigD.pow(n-1);
                dPowModN = dPowN.mod(bigN);

                //if the value of d^n−1 mod n is not 1 then again n is definitely a composite number
                if(dPowModN.intValue() != 1){
                    System.out.println("The number '" + n +
                            "' is a composite number!");
                    break;
                }

                //else the loops ends when 100 tries have been completed and that means that n is probably a
                //prime or Carmichael number
            }
            testCount--;
        }

        if(testCount == 0) {
            boolean found = false;
            if(isCarmichael(n))found = true;
            if(!found)System.out.println("The number '" + n +
                    "' is a prime number!");

        }
    }

    /**Returns the results of Encrypted: (z^e mod M) from public key (e,M) and Decrypted: (z^d mod M) from private key(d,M).
     * While testing RSA Encryption on paper I noticed that when the calculated values for e or d where a few digits long
     * my calculator couldn't use them as exponents to calculate (z^e) for the encryption or (z^d) for the decryption of some character (z).
     * So I wrote this method that can do that and so help me understand the final encryption and decryption step
     * by applying the (z^e mod M) and (z^d mod M) of the RSA Encryption method.
     *
     * @param charInput  a number corresponding to the character to be encrypted/ decrypted
     * @param e          the 'e' from (z^e mod M)
     * @param d          the 'd' from (z^d mod M)
     * @param M          the 'M' from (z^d mod M) or (z^d mod M)
     */
    public static void rsaKeysTest(BigInteger charInput, int e, int d,
                                   BigInteger M){

        BigInteger cipher = charInput.pow(e).mod(M);
        BigInteger decipher = cipher.pow(d).mod(M);
        System.out.println("Encrypted Input is: "+ cipher + " " +
                "Decrypted is : " + decipher + " Input was : " + charInput);
    }

    /**Prints a linked list containing all prime factors of an Integer.
     * An efficient way to get the prime factors of any integer up to 9223372036854775807
     * @param n an integer of type long (for integers larger than 2147483647,
     *          please put an 'L' at the end of any input integer &gt; 2147483647 i.e.(9223372036854775807L)
     */
    public static void getPrimeFactorsOf(long n){
        boolean factorized = false;
        long num = n;
        int divisor = 2;
        LinkedList<Integer>primeFactors = new LinkedList<>();

        while(!factorized){
            long remainder = num % divisor;
            if(remainder == 0){
                primeFactors.add(divisor);
                num = num / divisor;
                if(num == 1)factorized = true;
            }
            else{
                divisor++;
            }
        }
        System.out.println(primeFactors.toString());
    }







    /**
     * Returns true if a matrix is homogeneous, meaning has the same number of columns in each of its rows.
     * @param matrix a 2D Array of Reference Type 
     *  extends Number
     * @return boolean 
     */
    public static < E extends Number > boolean matrixHomogenity(E[][]matrix){

        ArrayList<Integer> numberOfElementsPerColumn = new ArrayList<Integer>();

        for(int r = 0; r < matrix.length; r++){
            numberOfElementsPerColumn.add(matrix[r].length);
            if (numberOfElementsPerColumn.size() > 1){
                int e1 = numberOfElementsPerColumn.get(0);
                for(int i = 1; i < numberOfElementsPerColumn.size(); i++ ){
                    if(numberOfElementsPerColumn.get(i) != e1){
                        return false;
                    } 
                }
            } 
        }
        return true;
    }
    
    /**
     * Return true if the two matrices are of same size/type and thus matrix addition can be performed.
     * @param matrix1 a 2D Array of Reference Type <E> extends Number
     * @param matrix2 a 2D Array of Reference Type <E> extends Number
     * @return boolean
     */
    public static < E extends Number > boolean matrixSizeEquality(E[][]matrix1, E[][]matrix2){
        
        if(matrixHomogenity(matrix1) && matrixHomogenity(matrix2)){
            if(matrix1.length == matrix2.length){
                //Since we made sure by checking for homogeneity above that each of the matrices has
                //the same number of columns in each of its rows and also that the 2 matrices have the
                //same number of rows it is enough if we just compare the number of columns in the first
                //rows of each matrix.
                if(matrix1[0].length == matrix2[0].length)return true;
                else return false;
            }else return false;
            
        }else return false;
    }



    public static ArrayList<ArrayList<Double>> addMatrices(Double[][] matrix1, Double[][]matrix2, String symbol)throws Exception{
        //compare sizes, matrices must have the same size for addition to be possible
        if(matrixSizeEquality(matrix1, matrix2)){
            ArrayList<ArrayList<Double>> resultMatrix = new ArrayList<ArrayList<Double>>();
            ArrayList<Double> currentRow = new ArrayList<Double>();

            for(int r = 0; r < matrix1.length; r++){
                for(int c = 0; c < matrix1[r].length; c++){
                    if(symbol.equals("+")) currentRow.add(Double.sum(matrix1[r][c], matrix2[r][c]));
                    else if (symbol.equals("-")) currentRow.add(Double.sum(matrix1[r][c], -matrix2[r][c]));
                }
                //System.out.println(currentRow.toString());
                resultMatrix.add(new ArrayList<Double>(currentRow));
;               currentRow.clear();
            }
            //System.out.println(resultMatrix.get(1).toString());
            return resultMatrix;

        }else throw new Exception("Matrices are not of the same size/type!!!");
    
    }

    public static void main(String[] args){

        Double[][]matrix1 = {
            new Double[]{1.0, 1.0},
            new Double[]{1.0, 2.0}
        };

        Double[][]matrix2 = {
            new Double[]{1.0, 1.0},
            new Double[]{1.0, 3.0}
        };

        String[][]strMatrix = {
            new String[]{"1", "2"},
            new String[]{"1", "2"}
        };

        //addMatrices(matrix1, matrix2);
        //System.out.println(matrixHomogenity(strMatrix));
        
        //System.out.println(matrixSizeEquality(matrix1, matrix2));
        try{
            System.out.println(addMatrices(matrix1, matrix2, "-").toString());
        }catch (Exception e){
            e.printStackTrace();
        }        

    }
}

    