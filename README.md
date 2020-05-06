## HW7 Discussion

1. Which hashmap approaches did you try, in what order, and why did you
   choose them? What specific tweaks to HashMap improved or
   made things worse?  Include failed or abandoned attempts if any, and
   why. Summarize all the different ways you developed, evaluated and
   improved your hashmaps.
   
   I tried separate chaining, linear probing, quadratic probing, and double hashing.
   Linear probing was improved by keeping a "tombstone", ie using null key, to indicate the
   position had some elements removed, so that moving to a null position (null entry) would
   be differentiated and thus indicate to the find() function that the key does not exist.
   Otherwise the program does not know if an empty entry indicates nothing has been inserted,
   and has to look through the entire erray.
   Quadratic probing may cause infinite loop, and can be improved by keeping a counter to make
   insert() stop. (I removed this implementation with double hashing as the last test is 
   having problem...)
   Chaining is improved by tracking the size of each linked list and rehash if at a particular
   index the list is too long.
   Failed attempts are with cuckoo: Don't know how to track an element that has kicked out another,
   so I can use this element's index to track the new placement of the original element here. Otherwise,
   find will be O(n).
   Failed attempts with find() in double hashing and quadratic too. Hopefully get them sort out
   during next week after a midterm.....

2. Include all the benchmarking data, results and analysis that contributed to your final
decision on which implementation to use for the search engine.

    chaining:
    Processed urls.txt in 38 ms using 0 kb memory.
    Processed jhu.txt in 91 ms using 0 kb memory.
    Processed joanne.txt in 51 ms using 0 kb memory.
    Processed random164.txt in 3346 ms using 839680 kb memory.
    takes too long to process apache.txt
      
    linear probing:
    Processed urls.txt in 47 ms using 0 kb memory.
    Processed jhu.txt in 124 ms using 0 kb memory.
    Processed joanne.txt in 59 ms using 0 kb memory.
    takes too long to process random164.txt
    takes too long to process apache.txt
    
    I chose chaining because linear probing takes way too much time for large files. 

3. Provide an analysis of your benchmark data and conclusions. Why did
   you choose your final HashMap implementation as the best one? What 
   results were surprising and which were expected?

    I choose the separate chaining implementation as it takes the shortest time. The results
    were surprising because both take too long time to process other files.