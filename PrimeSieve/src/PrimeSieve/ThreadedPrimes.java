package PrimeSieve;

import java.util.ArrayList;

/**
* The ThreadedPrime class calculates prime numbers from 1 to the N value. They
* are calculated evenly split across T threads. Once calculated, each thread 
* returns the list of prime numbers and the main calculated the execution time
* and adds the total result up, displaying both.
*
* @author  Stuart Davidson
*/

public class ThreadedPrimes extends Thread{
    final static int S = 1; // Start of the sieve
    final static int N = 10000; // End point should be N
    final static int T = 2; // Total number of threads
    
    final static ArrayList<ArrayList<Integer>> primes = new ArrayList<>();
    final static int total = 0;
    
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();

        // Create my threads
        ThreadedPrimes[] threads = new ThreadedPrimes[T];
        for (int me = 0; me < T; me++) {
            threads[me] = new ThreadedPrimes(me);
            threads[me].start();
        }

        for (int me = 0; me < T; me++) {
            threads[me].join();
        }
        
        ArrayList<Integer> primeList = new ArrayList<>();
        for(ArrayList list : primes){
            primeList.addAll(list);
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("Calculated "+ N +" primes across "+ T +" threads in " + (endTime - startTime) + "ms (Found: "+ primeList.size() + ")");
    }
    
    int me;
    public ThreadedPrimes(int me) {
        this.me = me;
    }

public void run(){
    int begin = me * (N / T);
    int end = begin + (N / T);

    ArrayList<Integer> prime = new ArrayList<>();

    for(int i = begin; i < end; i++){
        // Goes through all numbers between start and end
        if(isNumberPrime(i)){
            prime.add(i);
        }
    }

    primes.add(prime);
}

    public static boolean isNumberPrime(int number) {  
        if(number <= 1) return false;
        for(int i = 2; i <= Math.sqrt(number); ++i)
        {
            if(number % i == 0) return false;
        }
        
        return true;  
   }  
    
    
    public static int[] combine(int[] a, int[] b){
        int length = a.length + b.length;
        int[] result = new int[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}
