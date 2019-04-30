package PrimeSieve;

import mpi.*;
import java.util.ArrayList;

/**
* The MpjPrimes class calculates prime numbers from 1 to the N value. They
* are calculated evenly split across the total threads / machines provided by 
* the MPI COMM_WORLD size variables. Once calculated, each thread / machine 
* returns the list of prime numbers and the main calculated the execution time
* and adds the total result up, displaying both.
*
* @author  Stuart Davidson
*/

public class MpjPrimes extends Thread{
    final static int S = 1; // Start of the sieve
    final static int N = 10000; // End point should be N
    static int T, me; // Total number of threads
    
    final static ArrayList<Integer> primes = new ArrayList<>();
    final static int total = 0;
    
    public static void main(String[] args) throws Exception {
        MPI.Init(args);

        long startTime = System.currentTimeMillis();
        
        me = MPI.COMM_WORLD.Rank(); // Position
        T = MPI.COMM_WORLD.Size(); // Threads
        
        int begin = me * (N / T);
        int end = begin + (N / T);

        ArrayList<Integer> prime = new ArrayList<>();

        
        for(int i = begin; i < end; i++){
            // Goes through all numbers between start and end
            if(isNumberPrime(i)){
                prime.add(i);
            }
        }
        
        if (me > 0) {
            ArrayList<Integer> sendBuf = new ArrayList<>(prime); 
            MPI.COMM_WORLD.Send(sendBuf, 0, 1, MPI.DOUBLE, 0, 0);
        } else {
            ArrayList<Integer> recvBuf = new ArrayList<>(prime);
            for (int src = 1; src < T; src++) {
                MPI.COMM_WORLD.Recv(recvBuf, 0, 1, MPI.DOUBLE, src, 0);
                for(int rec = 0; rec < recvBuf.size(); rec++){
                    primes.add(recvBuf.get(rec));
                }
            }
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("Calculated "+ N +" primes across "+ T +" threads in " + (endTime - startTime) + "ms (Found: "+ primes.size() + ")");
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
