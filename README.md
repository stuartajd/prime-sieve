# Prime Sieve
Implementation of a Prime Number Sieve using multi-threading &amp; MPJ to benchmark - Distributed Systems and Parallel Programming

## SequentialPrime
The SequentialPrime class calculates prime numbers from 1 to the N value. They are calculated on one thread & on one machine. Once calculated, the total time taken is calculated and returned along with total number of prime numbers found.

## ThreadedPrime
The ThreadedPrime class calculates prime numbers from 1 to the N value. They are calculated evenly split across T threads. Once calculated, each thread returns the list of prime numbers and the main calculated the execution time and adds the total result up, displaying both.

## MpjPrime
The MpjPrimes class calculates prime numbers from 1 to the N value. They are calculated evenly split across the total threads / machines provided by the MPI COMM_WORLD size variables. Once calculated, each thread / machine returns the list of prime numbers and the main calculated the execution time and adds the total result up, displaying both.