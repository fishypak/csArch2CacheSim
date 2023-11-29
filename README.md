# CACHE SIMULATION PROJECT

Group 10

Angeles, Frances Justine

Co, Sofia Bianca

Po, Aliexandra Heart

# 8-way BSA + FIFO Algorithm

Our group implemented a code for an 8-way Block Set Associative (BSA) cache with a First-In, First-Out (FIFO) replacement algorithm. Assigned a set size of 8 blocks per set and a total of 32 blocks, our cache configuration results in 4 sets (32 / 8 = 4 sets). 

An 8-way Block Set Associative (BSA) cache with a First-In, First-Out (FIFO) replacement algorithm is a specific configuration of cache memory that strikes a balance between direct mapping and full associative designs. The cache is organized into multiple sets, each capable of holding up to 8 blocks. When the processor seeks data, the cache identifies the set based on the memory address and then checks the 8 blocks within that set.  The FIFO replacement algorithm is applied so that whenever a new block needs to be loaded into a set that is already full, the oldest block in that set, the one that has been present for the longest time, is replaced with the new block. This ensures that the cache operates on a first-come, first-served basis within each set.

# Test Cases (n = no. of cache blocks)

## a.) Sequential sequence: Up to 2n cache block. Repeat the sequence four times.

For the first test case, we see that having sequential memory addresses proves to avoid any hits in the cache memory since no memory addresses are repeated in this sequence. Additionally, due to the smaller amount of blocks being used, overall, this has the lowest memory access time among the 3 predefined test cases.


## b.) Random sequence: Containing 4n blocks

In our next test case, given that the addresses are randomly generated, hit and miss rates, as well as access times will differ from one simulation to the next. Ultimately, this sequence will still take a greater amount of time compared to the first test case as we generate more memory blocks as well.

## c.) Mid-repeat blocks: Start at block 0, repeat the sequence in the middle two times up to n-1 blocks, after which continue up to 2n. Then, repeat the sequence four times. 

For the third and final case, we see that the first sequence and second sequence repeat the initial addresses almost completely identically and contain addresses which would be less than the number of cache blocks. Consequently, this will lead to an increased amount of hits.

