package PrimeSieve;

import java.util.ArrayList;

/**
* The SequentialPrime class calculates prime numbers from 1 to the N value. They
* are calculated on one thread & on one machine. Once calculated, the total time
* taken is calculated and returned along with total number of prime numbers found.
*
* @author  Stuart Davidson
*/

public class SequentialPrimes {
    final static int S = 1; // Start of the sieve
    final static int N = 10000; // End point should be N

    
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        ArrayList<Integer> primes = new ArrayList<>(); // Captured prime list

        
        for(int i = S; i < N; i++){
            // Goes through all numbers between start and end
            if(isNumberPrime(i)){
                primes.add(i);
            }
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("Calculated "+ N +" primes in " + (endTime - startTime) + "ms (Found: "+ primes.size() + ")");
    }
    
    public static boolean isNumberPrime(int number) {  
        if(number <= 1) return false;
        for(int i = 2; i <= Math.sqrt(number); ++i)
        {
            if(number % i == 0) return false;
        }
        
        return true;  
   }  
}
