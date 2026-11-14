# Bonus Task

Group: SE-2539

Full name: Aktanbi Kusman


# Aho-Corasick Algorithm

This repository has a simple Java implementation of the Aho-Corasick algorithm and compares it with the KMP algorithm.

## Files in Project
* `Node.java` - node of the Trie tree.
* `AhoCorasick.java` - main algorithm (build trie, failure links, and search).
* `KMP.java` - KMP algorithm for comparison.
* `Main.java` - runs tests and measures execution time.

## Test Results
I ran each test 5 times using `System.nanoTime()` and calculated the average time in milliseconds (ms).

| Patterns | Text Length | Aho-Corasick (ms) | KMP (ms) |
| :--- | :--- | :--- | :--- |
| 10 | 1,000 | 16.0893 | 4.8342 |
| 10 | 10,000 | 1.0066 | 5.1305 |
| 10 | 100,000 | 1.3362 | 5.4371 |
| 100 | 1,000 | 0.4603 | 3.8634 |
| 100 | 10,000 | 0.3345 | 4.7471 |
| 100 | 100,000 | 1.2765 | 62.7813 |
| 1,000 | 1,000 | 1.0170 | 5.1055 |
| 1,000 | 10,000 | 1.1335 | 52.8227 |
| 1,000 | 100,000 | 3.2657 | 589.5652 |

*(Note: The first test for Aho-Corasick took 16 ms because Java VM was warming up at the start of the program).*

## How It Works
1. **Trie:** We put all patterns into one prefix tree. If words start with the same letters, they share the same nodes.
2. **Failure Links:** We build them using BFS. If the next letter in the text does not match, the failure link jumps to the longest suffix that is also in the Trie. So we do not need to go back in the text.
3. **Output Links:** Each node keeps the IDs of finished patterns from its failure link. This helps find patterns inside other patterns at the same time.

## Complexity Analysis
* $N$ = length of text
* $M$ = total length of all patterns
* $K$ = number of patterns
* $Z$ = number of matches found

* **Time Complexity:** $O(M + N + Z)$. Building the tree and links takes $O(M)$, and reading the text takes $O(N + Z)$.
* **Space Complexity:** $O(M \times 26)$ because every node has an array of size 26 for English letters.

## Aho-Corasick vs KMP
* **Aho-Corasick** reads the text only **one time** for all patterns. When we have 1,000 patterns and 100,000 text length, it takes only ~3.26 ms. The main minus is that it uses more memory for the tree.
* **KMP** searches for one pattern at a time, so it reads the text $K$ times ($O(K \times N + M)$). It uses very little memory, but when there are many patterns, it becomes very slow (~589.56 ms).

## Conclusion
From the test results, we can see that Aho-Corasick is much better when we need to find many patterns at the same time. It scans the text only once, so the time stays very small (around 3.26 ms for 1,000 patterns and 100,000 characters). KMP is okay for a small number of patterns, but for 1,000 patterns it becomes almost 180 times slower (589.56 ms) because it reads the text again for every single pattern.