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
    final static int numbers = 100000000; // End point should be N
    
    static int threads, position; // Total number of threads
        
    final static ArrayList<Integer> primes = new ArrayList<>();;    

    public static void main(String[] args) throws Exception {
        MPI.Init(args);

        long startTime = System.currentTimeMillis();
        
        position = MPI.COMM_WORLD.Rank(); // Position [0 / 1]
        threads = MPI.COMM_WORLD.Size(); // Threads [2]
        
        int begin = position * (numbers / threads);
        int end = begin + (numbers / threads);
        
        ArrayList<Integer> temporaryStorage = new ArrayList<>();
        for(int i = begin; i < end; i++){
            if(isNumberPrime(i)){
                temporaryStorage.add(i);
            }
        }
       
        // Send back the length the storage currently is
        if(position > 0){
            int[] sendBuf = new int[]{temporaryStorage.size()};
            MPI.COMM_WORLD.Send(sendBuf, 0, 1, MPI.INT, 0, 1);
            
            for(int i = 0; i < temporaryStorage.size(); i++){
                int[] send = new int[]{temporaryStorage.get(i)};
                MPI.COMM_WORLD.Send(send, 0, 1, MPI.INT, 0, 0);
            }
        } else {
            for(int i = 0; i < temporaryStorage.size(); i++){
                primes.add(temporaryStorage.get(i));
            }
            
            int[] recvBuf = new int[1];
            for (int src = 1; src < threads; src++) {
                MPI.COMM_WORLD.Recv(recvBuf, 0, 1, MPI.INT, src, 1);
                
                for (int ind = 1; ind <= recvBuf[0]; ind++) {
                    int[] recv = new int[1];
                    MPI.COMM_WORLD.Recv(recv, 0, 1, MPI.INT, src, 0);
                    primes.add(recv[0]);
                }
            }
        }
               
        long endTime = System.currentTimeMillis();   
         
        if(position == 0){
            System.out.println("Calculated "+ numbers +" primes across "+ threads +" threads in " + (endTime - startTime) + "ms (Found: "+ primes.size() + ")");
        }
        
        MPI.Finalize();
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