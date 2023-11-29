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

|Cache  |Block 0|Block 1|Block 2|Block 3|Block 4|Block 5|Block 6|Block 7|
|-------|-------|-------|-------|-------|-------|-------|-------|-------|
|Set 0  |0      |4      |8      |12     |16     |20     |24     |28     |
|Set 1  |1      |5      |9      |13     |17     |21     |25     |29     |
|Set 2  |2      |6      |10     |14     |18     |22     |26     |30     |
|Set 3  |3      |7      |11     |15     |19     |23     |27     |31     |

In this 8-way set associative cache configuration, the initial accesses from 0 to 31 are cache misses as the cache is starting in an unoccupied state. The distribution of blocks across the four sets follows a systematic sequential mapping strategy, where block addresses are consecutively assigned to sets. For instance, block 0 is placed in set 0, block 1 in set 1, and so on, with a cyclical recurrence. Upon reaching full capacity with all 32 memory blocks (ranging from 0 to 31).

|Cache  |Block 0|Block 1|Block 2|Block 3|Block 4|Block 5|Block 6|Block 7|
|-----|-----|-----|-----|-----|-----|-----|-----|-----|
|Set 0|32   |36   |40   |44   |48   |52   |56   |60   |
|Set 1|33   |37   |41   |45   |49  |53   |57   |61   |
|Set 2|34   |38   |42   |46   |50   |54   |58   |62   |
|Set 3|35   |39   |43   |47   |51   |55   |59   |63   |

Upon the completion of the sequence repetitions, the cache remains devoid of any hits, given the absence of repeated memory addresses. The recorded statistics for this test case reveal a total memory access count of 64, with zero cache hits and 64 cache misses. This results in a cache hit rate of 0% and a cache miss rate of 100%. The average memory access time is measured at 11.0 nanoseconds, reflecting the impact of solely cache misses on access latency. The cumulative total memory access time for all accesses during the test is calculated at 1,310,784.0 nanoseconds, emphasizing the importance of addressing the lack of data reuse within the cache. The sequential sequence, while avoiding cache hits, consequently incurs higher memory access times due to the predominant cache misses and their associated latencies.

## b.) Random sequence: Containing 4n blocks

In our next test case, given that the addresses are randomly generated, hit and miss rates, as well as access times will differ from one simulation to the next. Ultimately, this sequence will still take a greater amount of time compared to the first test case as we generate more memory blocks as well.

|Cache  |Block 0|Block 1|Block 2|Block 3|Block 4|Block 5|Block 6|Block 7|
|-----|-----|-----|-----|-----|-----|-----|-----|-----|
|Set 0|88   |108  |36   |4    |84   |120  |76   |20   |
|Set 1|69   |125  |13   |21   |61   |93   |37   |25   |
|Set 2|82   |58   |70   |74   |22   |26   |62   |50   |
|Set 3|103  |15   |87   |59   |51   |79   |67   |39   |

In a cache with a random sequence, the generation of memory addresses is unpredictable. As a result, the sequence in which sets within the cache are populated becomes inherently random. The random nature of the address generation concludes that at any given point during the simulation, some sets may already be in the process of replacing existing blocks with new addresses. This is due to the FIFO (First-In, First-Out) replacement algorithm, which dictates that when a set is full and a new block needs to be inserted, the oldest block in that set is removed to make room for the new one.

Simultaneously, other sets within the cache may not have reached their capacity, and their blocks may still be in the process of being filled. This dynamic situation creates a scenario where the cache is continuously evolving: some sets are in the process of replacing blocks, some are yet to reach full capacity, and others may have a mix of older and newer blocks. The interplay of these factors introduces variability in the cache's state at any given point in time, reflecting the complexity and challenges associated with managing a cache under a randomly generated sequence. 


|Cache  |Block 0|Block 1|Block 2|Block 3|Block 4|Block 5|Block 6|Block 7|
|-----|-----|-----|-----|-----|-----|-----|-----|-----|
|Set 0|28   |96   |124  |56   |60   |8    |88   |44   |
|Set 1|101  |13   |109  |9    |53   |41   |29   |85   |
|Set 2|90   |50   |62   |18   |38   |34   |30   |6    |
|Set 3|67   |23   |27   |43   |75   |83   |11   |15   |

The recorded results for this test case illustrate a total memory access count of 128, with 25 cache hits and 103 cache misses. Consequently, the cache hit rate is calculated at 19.53%, while the cache miss rate is 80.47%. The average memory access time is measured at 9.046875 nanoseconds, reflecting the influence of both cache hits and misses on the overall latency. The cumulative total memory access time for all accesses during the test is calculated at 2,160,743.0 nanoseconds.

This test case underscores the impact of a random sequence on cache performance, introducing variability in hit rates and access times. The inherent unpredictability of randomly generated addresses leads to a substantial number of cache misses, contributing to the elevated average memory access time. Despite the introduction of some hits during initialization, the overall efficiency of the cache is compromised in the face of the random access pattern.


## c.) Mid-repeat blocks: Start at block 0, repeat the sequence in the middle two times up to n-1 blocks, after which continue up to 2n. Then, repeat the sequence four times. 

For the third and final case, we see that the first iteration and second iteration of one sequence repeat the initial addresses almost completely identically and contain addresses that would be less than the number of cache blocks. Consequently, this will lead to an increased amount of hits.

|Cache  |Block 0|Block 1|Block 2|Block 3|Block 4|Block 5|Block 6|Block 7|
|-------|-------|-------|-------|-------|-------|-------|-------|-------|
|Set 0  |0      |4      |8      |12     |16     |20     |24     |28     |
|Set 1  |1      |5      |9      |13     |17     |21     |25     |29     |
|Set 2  |2      |6      |10     |14     |18     |22     |26     |30     |
|Set 3  |3      |7      |11     |15     |19     |23     |27     |31     |

In the 8-way set associative cache configuration, the initial accesses ranging from 0 to 30 result in cache misses as the cache starts in an unoccupied state. However, a notable exception occurs during the mid-repeat condition, where a hit is recorded before all of the sets are full. The distribution of blocks across the four sets follows a distinct pattern. The first iteration of the sequence encompasses blocks 0 to 30, followed by the second iteration from 1 to 30, and finally, the third iteration from 31 to 63. Additionally, the previously mentioned sequence occurs for a total of 4 times. Furthermore, it is important to note that these ranges are also dependent on the number of cache blocks, which in this case is equal to 32 blocks.

|Cache  |Block 0|Block 1|Block 2|Block 3|Block 4|Block 5|Block 6|Block 7|
|-----|-----|-----|-----|-----|-----|-----|-----|-----|
|Set 0|32   |36   |40   |44   |48   |52   |56   |60   |
|Set 1|33   |37   |41   |45   |49   |53   |57   |61   |
|Set 2|34   |38   |42   |46   |50   |54   |58   |62   |
|Set 3|35   |39   |43   |47   |51   |55   |59   |63   |

At the conclusion of the sequence repetitions, the cache state reflects the impact of the mid-repeat pattern. Notably, the system recorded a total of 376 memory accesses, with 120 cache hits and 256 cache misses. This results in a cache hit rate of 31.91% and a cache miss rate of 68.09%. The average memory access time, influenced by the cache hit and miss latencies, is calculated at 7.8085103 nanoseconds. The cumulative total memory access time for all accesses during the test is measured at 5,488,896.0 nanoseconds.

These values underscore the cache's performance in handling the mid-repeat blocks scenario, where the repetitive nature of the sequence and the systematic distribution of initial addresses contribute to a substantial number of cache hits. The cache's ability to retain and reuse data within the repeated pattern is reflected in the higher hit rate and the resulting impact on average and total memory access times.


