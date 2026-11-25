# Round 1 Programming Deep Dive: High-Frequency Kotlin Patterns

A focused, execution-ready reference for the **five highest-ROI non-graph patterns** in a 60-minute Senior Kotlin programming interview: **Sliding Window**, **Two Pointers**, **Heap / `PriorityQueue`**, **Binary Search**, and **Core Dynamic Programming**.

> **How this fits with the main plan:** Read [`interview_prep/round1_programming_prep.md`](round1_programming_prep.md) for the overall 60-minute interview playbook, 2-week schedule, and 45-problem list. Use this guide when drilling the non-graph patterns in depth—focusing on **invariants, bug-free Kotlin 2.x implementations, edge cases, and senior follow-up trade-offs**.

---

## Quick Pattern Recognition Matrix

Use the first 2–3 minutes of an interview to map problem cues to the right pattern before writing any code:

| Problem Cue | Primary Pattern | Key Watch-Out |
| :--- | :--- | :--- |
| Contiguous subarray/substring with length, distinct-count, or frequency constraint | **Sliding Window** | Fails if the array has **negative numbers** with a target sum → switch to **Prefix Sum + HashMap**. |
| Sorted array (or sortable), pairs/triplets, opposite ends, or in-place compaction | **Two Pointers** | Clarify if mutating/sorting input is allowed; handle duplicates explicitly. |
| "Top K", "K-th largest/smallest", "merge K sorted", streaming median, min rooms/resources | **Heap (`PriorityQueue`)** | Never use `a - b` comparator (overflows `Int`). Use a **min-heap of size `k`** for top-`k` largest. |
| Sorted/rotated input, or "minimize the maximum" / "maximize the minimum" over a monotonic range | **Binary Search** | Guard `mid` and feasibility sums with `Long`; verify post-loop if answer isn't guaranteed. |
| "Number of ways", "min/max cost", overlapping subproblems, choices with/without item reuse | **Dynamic Programming** | State loop direction explicitly: **backward** for 0-1 (use once), **forward** for unbounded (reuse). |

---

## 1. Sliding Window

### When to Use vs. When It Breaks
- **Use Sliding Window** when the problem asks for a **contiguous** subarray or substring and adding/removing elements changes window validity **monotonically** (e.g., character counts, distinct count `<= k`, positive-only subarray sum).
- **When It Breaks:** If the problem asks for *"subarray sum equals `k`"* and numbers can be **negative or zero**, expanding `right` does not monotonically increase the sum and shrinking `left` does not monotonically decrease it. Use **Prefix Sum + Frequency `HashMap`** (`count += seen[prefix - k]`) instead.

### The 3 Window Invariants

| Variant | Goal | Loop Structure | Where to Update `best` |
| :--- | :--- | :--- | :--- |
| **Fixed Size (`k`)** | Max/min/avg over every window of length `k` | Expand `right`; when `right >= k`, remove `nums[right - k]` | When `right >= k - 1` |
| **Variable — Longest** | Longest valid window | Expand `right`; `while (!valid) { remove(left); left++ }` | **After** the `while` loop (window is now valid) |
| **Variable — Shortest** | Shortest valid window | Expand `right`; `while (valid) { updateBest(); remove(left); left++ }` | **Inside** the `while` loop (before shrinking breaks validity) |

### Worked Kotlin 2.x Implementations

#### A. Longest Substring Without Repeating Characters (Variable — Longest, Jump-`left` Optimization)
Instead of shrinking `left` one step at a time, store each character's last-seen index and jump `left` directly past the duplicate.

```kotlin
fun lengthOfLongestSubstring(s: String): Int {
    val lastSeen = HashMap<Char, Int>()
    var left = 0
    var best = 0

    for (right in s.indices) {
        val ch = s[right]
        val prev = lastSeen[ch]
        if (prev != null && prev >= left) {
            // Jump left past the previous occurrence inside the current window
            left = prev + 1
        }
        lastSeen[ch] = right
        best = maxOf(best, right - left + 1)
    }
    return best
}
```
- **Complexity:** `O(n)` time, `O(min(n, charset))` space.
- **Why `prev >= left` matters:** Without it, an old occurrence *before* `left` would move `left` backward and corrupt the window.

#### B. Minimum Window Substring (Variable — Shortest, `formed == required` Invariant)
Maintain a `need` frequency array and a `formed` counter so checking validity is `O(1)` instead of scanning the entire map on every step.

```kotlin
fun minWindow(s: String, t: String): String {
    if (t.isEmpty() || s.length < t.length) return ""

    val need = IntArray(128)
    var required = 0
    for (ch in t) {
        if (need[ch.code] == 0) required++
        need[ch.code]++
    }

    val window = IntArray(128)
    var formed = 0
    var left = 0
    var bestLen = Int.MAX_VALUE
    var bestStart = 0

    for (right in s.indices) {
        val c = s[right].code
        window[c]++
        if (need[c] > 0 && window[c] == need[c]) {
            formed++
        }

        // Shrink as much as possible while the window remains valid
        while (formed == required) {
            val len = right - left + 1
            if (len < bestLen) {
                bestLen = len
                bestStart = left
            }
            val lc = s[left].code
            window[lc]--
            if (need[lc] > 0 && window[lc] < need[lc]) {
                formed--
            }
            left++
        }
    }

    return if (bestLen == Int.MAX_VALUE) "" else s.substring(bestStart, bestStart + bestLen)
}
```
- **Complexity:** `O(|s| + |t|)` time, `O(1)` space (fixed 128 ASCII table).

### Common Pitfalls & Senior Follow-Ups
- **Pitfall:** Checking `window[c] >= need[c]` instead of `==` when incrementing `formed` (causes `formed` to overcount on extra copies of the same char).
- **Edge Cases:** Empty string, `t.length > s.length`, `t` with repeated characters (e.g., `t = "AA"`), no valid window found.
- **Follow-Up (Unicode / Internationalization):** Swap `IntArray(128)` for `HashMap<Char, Int>()` if input can contain arbitrary UTF-16 characters or emojis (or code points via `String.codePointAt` for surrogate pairs).
- **Follow-Up (Streaming):** For a live event stream, store `(timestamp, event)` in an `ArrayDeque` and evict expired entries from the front when `now - deque.first().ts > windowMs`.

---

## 2. Two Pointers

### The 3 Two-Pointer Archetypes

1. **Opposite-End Converging (`left = 0`, `right = n - 1`):** Move the pointer that cannot possibly produce a better result (*Container With Most Water*, *Trapping Rain Water*, sorted *Two Sum II*).
2. **Sort + Deduplication (`i`, `left`, `right`):** Fix one element, run opposite-end pointers on the suffix, and skip adjacent duplicates (*3Sum*, *4Sum*).
3. **Same-Direction Read/Write (`slow`, `fast`):** `fast` scans every element; `slow` writes only kept elements in-place (*Remove Duplicates*, *Move Zeroes*, Dutch National Flag partitioning).

### Worked Kotlin 2.x Implementations

#### A. 3Sum (Sort + Exact Deduplication)
```kotlin
fun threeSum(nums: IntArray): List<List<Int>> {
    val result = ArrayList<List<Int>>()
    if (nums.size < 3) return result

    val sorted = nums.sortedArray() // Avoid mutating caller's array unless permitted
    val n = sorted.size

    for (i in 0..<n - 2) {
        if (sorted[i] > 0) break // Remaining values are all > 0; sum cannot be 0
        if (i > 0 && sorted[i] == sorted[i - 1]) continue // Skip duplicate first element

        var left = i + 1
        var right = n - 1
        while (left < right) {
            val sum = sorted[i].toLong() + sorted[left] + sorted[right]
            when {
                sum == 0L -> {
                    result.add(listOf(sorted[i], sorted[left], sorted[right]))
                    left++
                    right--
                    while (left < right && sorted[left] == sorted[left - 1]) left++
                    while (left < right && sorted[right] == sorted[right + 1]) right--
                }
                sum < 0L -> left++
                else -> right--
            }
        }
    }
    return result
}
```
- **Complexity:** `O(n^2)` time, `O(n)` space for the sorted copy (`O(log n)` if in-place `nums.sort()` is allowed).

#### B. Trapping Rain Water (`O(1)` Space Two-Pointer Invariant)
Water trapped at any index `i` is `min(maxLeft[i], maxRight[i]) - height[i]`. If `leftMax <= rightMax`, the bottleneck at `left` is guaranteed to be `leftMax` regardless of unseen taller bars to the right.

```kotlin
fun trap(height: IntArray): Int {
    if (height.size < 3) return 0

    var left = 0
    var right = height.lastIndex
    var leftMax = 0
    var rightMax = 0
    var water = 0

    while (left < right) {
        if (height[left] <= height[right]) {
            if (height[left] >= leftMax) {
                leftMax = height[left]
            } else {
                water += leftMax - height[left]
            }
            left++
        } else {
            if (height[right] >= rightMax) {
                rightMax = height[right]
            } else {
                water += rightMax - height[right]
            }
            right--
        }
    }
    return water
}
```
- **Complexity:** `O(n)` time, `O(1)` extra space.

### Common Pitfalls & Senior Follow-Ups
- **Pitfall:** Skipping duplicates *before* checking `left < right` (causes `IndexOutOfBoundsException`). Always put `left < right` first in the `while` condition.
- **Pitfall:** Mutating caller-owned input arrays with `nums.sort()` without asking. Mention `nums.sortedArray()` vs. in-place `nums.sort()` as an explicit trade-off.
- **Follow-Up:** Compare Two Pointers vs. Monotonic Stack on *Trapping Rain Water* (Two Pointers computes column-by-column in `O(1)` space; Monotonic Stack computes layer-by-layer in `O(n)` space and handles streaming input cleanly).

---

## 3. Heap / `PriorityQueue`

### Kotlin `PriorityQueue` Rules & Gotchas
1. **Never write `PriorityQueue<Int> { a, b -> a - b }`:** If `a = Int.MIN_VALUE` and `b = 1`, `a - b` overflows to `Int.MAX_VALUE` and silently corrupts heap order. Always use `compareBy`, `compareByDescending`, or `Integer.compare(a, b)` / `a.compareTo(b)`.
2. **Multi-key tie-breaking:** Use `compareBy<Item>({ it.priority }, { it.timestamp })` (or `compareByDescending<Item> { it.freq }.thenBy { it.word }` for Top-K Frequent Words).
3. **Top-`k` Largest uses a Min-Heap of size `k`:** Keeping the *smallest* of the top `k` at the root lets you evict it in `O(log k)` whenever a larger element arrives, giving `O(n log k)` time and `O(k)` space instead of `O(n log n)` and `O(n)`.
4. **Arbitrary removal is `O(n)`:** `pq.remove(x)` scans the backing array linearly. When you need arbitrary deletion (e.g., Sliding Window Median, Dijkstra stale entries), use **lazy deletion** (track invalid counts in a `HashMap` or compare against `dist[u]` and discard stale entries on `poll()`).

### Worked Kotlin 2.x Implementations

#### A. Top K Frequent Elements (Bounded Min-Heap)
```kotlin
import java.util.PriorityQueue

fun topKFrequent(nums: IntArray, k: Int): IntArray {
    val freq = HashMap<Int, Int>()
    for (num in nums) {
        freq[num] = freq.getOrDefault(num, 0) + 1
    }

    // Min-heap ordered by frequency; root is the weakest candidate in the top-k
    val minHeap = PriorityQueue<Map.Entry<Int, Int>>(compareBy { it.value })
    for (entry in freq.entries) {
        minHeap.add(entry)
        if (minHeap.size > k) {
            minHeap.poll()
        }
    }

    val result = IntArray(minHeap.size)
    for (i in result.lastIndex downTo 0) {
        result[i] = minHeap.poll().key
    }
    return result
}
```
- **Complexity:** `O(n log k)` time, `O(n)` space for frequency map + `O(k)` heap.
- **Follow-Up:** Mention **Bucket Sort** (`Array(nums.size + 1) { ArrayList<Int>() }`) to reach `O(n)` time when `k` is close to `n`.

#### B. Meeting Rooms II (Min-Heap of Active End Times)
```kotlin
import java.util.PriorityQueue

fun minMeetingRooms(intervals: Array<IntArray>): Int {
    if (intervals.isEmpty()) return 0

    // Sort meetings by start time
    val sorted = intervals.sortedWith(compareBy({ it[0] }, { it[1] }))
    // Min-heap stores end times of currently occupied rooms
    val endTimes = PriorityQueue<Int>()

    for ((start, end) in sorted) {
        if (endTimes.isNotEmpty() && endTimes.peek() <= start) {
            endTimes.poll() // Reuse the earliest-ending room
        }
        endTimes.add(end)
    }
    return endTimes.size
}
```
- **Complexity:** `O(n log n)` time, `O(n)` space.
- **Edge Case:** Touching intervals `[0, 10]` and `[10, 20]` — clarify whether `end == start` counts as overlapping (`<=` reuses the room; `<` requires a second room).

#### C. Find Median from Data Stream (Two-Heap Invariant)
Maintain two heaps with the invariant:
- `low` (Max-Heap) holds the smaller half.
- `high` (Min-Heap) holds the larger half.
- `low.size == high.size` or `low.size == high.size + 1`.

```kotlin
import java.util.PriorityQueue

class MedianFinder {
    private val low = PriorityQueue<Int>(compareByDescending { it }) // Max-heap
    private val high = PriorityQueue<Int>()                          // Min-heap

    fun addNum(num: Int) {
        low.add(num)
        high.add(low.poll()) // Ensure every element in low <= every element in high
        if (high.size > low.size) {
            low.add(high.poll()) // Restore size invariant
        }
    }

    fun findMedian(): Double {
        return if (low.size > high.size) {
            low.peek().toDouble()
        } else {
            (low.peek().toLong() + high.peek().toLong()) / 2.0
        }
    }
}
```
- **Complexity:** `addNum` is `O(log n)`, `findMedian` is `O(1)`.
- **Why `.toLong()` in `findMedian()`:** Prevents `Int` overflow if both middle elements are near `Int.MAX_VALUE`.

### Common Pitfalls & Senior Follow-Ups
- **Pitfall:** Calling `pq.peek()` or `pq.poll()` without checking `isNotEmpty()` (returns `null` in Java/Kotlin, causing NPE on unboxed `Int`).
- **Follow-Up (K-Way Merge):** For *Merge K Sorted Lists*, push only the head of each non-null list into `PriorityQueue<ListNode>(compareBy { it.`val` })` (`O(k)` heap size, `O(N log k)` total time).
- **Follow-Up (Memory / GC on Mobile):** Boxing primitives in `PriorityQueue<Int>` creates `Integer` allocations. For tight hot paths on Android, mention a primitive array-backed binary heap or bounded ring buffer.

---

## 4. Binary Search

### Eliminating Off-By-One Bugs: The Only 2 Templates You Need

Every binary search reduces to finding a boundary in a monotonic boolean predicate `ok(x)`. Pick the right template and **never mix them**:

| Goal | Predicate Shape | Mid Formula | Branch Update |
| :--- | :--- | :--- | :--- |
| **First True (Lower Bound)** | `F F F [T] T T` | `lo + (hi - lo) / 2` (lower mid) | `if (ok(mid)) hi = mid else lo = mid + 1` |
| **Last True (Upper Bound)** | `T T [T] F F F` | `lo + (hi - lo + 1) / 2` (upper mid) | `if (ok(mid)) lo = mid else hi = mid - 1` |

> **Why Upper Mid (`+ 1`) is mandatory for Last True:** When `lo = 4, hi = 5`, lower mid gives `mid = 4`. If `ok(4)` is `true`, `lo = mid` leaves `lo = 4, hi = 5` unchanged → **infinite loop**. Upper mid gives `mid = 5`, guaranteeing the search space strictly shrinks every iteration.

> **Post-Loop Check:** Both templates converge to `lo == hi`. If the answer is not guaranteed to exist in `[lo, hi]`, check `if (ok(lo)) lo else -1` after the loop.

### Worked Kotlin 2.x Implementations

#### A. Search in Rotated Sorted Array (Identify the Sorted Half)
In a rotated sorted array with distinct values, at least one of `[lo..mid]` or `[mid..hi]` is guaranteed to be strictly sorted. Check which half is sorted, then test whether `target` lies inside that sorted range.

```kotlin
fun searchRotated(nums: IntArray, target: Int): Int {
    var lo = 0
    var hi = nums.lastIndex

    while (lo <= hi) {
        val mid = lo + (hi - lo) / 2
        if (nums[mid] == target) return mid

        if (nums[lo] <= nums[mid]) {
            // Left half [lo..mid] is sorted
            if (target in nums[lo]..<nums[mid]) {
                hi = mid - 1
            } else {
                lo = mid + 1
            }
        } else {
            // Right half [mid..hi] is sorted
            if (target in (nums[mid] + 1)..nums[hi]) {
                lo = mid + 1
            } else {
                hi = mid - 1
            }
        }
    }
    return -1
}
```
- **Complexity:** `O(log n)` time, `O(1)` space.
- **Why `nums[lo] <= nums[mid]` uses `<=`:** When `lo == mid` (a 2-element subarray), `[lo..mid]` is trivially sorted; using `<` would misclassify it as the right half.

#### B. Koko Eating Bananas (Binary Search on Answer Space)
When asked to *"minimize the maximum capacity/speed"* or *"maximize the minimum distance"*, binary search directly over the answer range `[minPossible, maxPossible]` using a linear feasibility check.

```kotlin
fun minEatingSpeed(piles: IntArray, h: Int): Int {
    var lo = 1
    var hi = piles.max()

    fun canFinish(speed: Int): Boolean {
        var hours = 0L // Long prevents overflow when piles are large and speed == 1
        for (pile in piles) {
            hours += (pile.toLong() + speed - 1L) / speed // Exact integer ceiling division
            if (hours > h) return false                   // Early exit
        }
        return true
    }

    while (lo < hi) {
        val mid = lo + (hi - lo) / 2
        if (canFinish(mid)) {
            hi = mid
        } else {
            lo = mid + 1
        }
    }
    return lo
}
```
- **Complexity:** `O(n log(maxPile))` time, `O(1)` space.
- **Same Template Applies To:** *Capacity To Ship Packages Within D Days* (`lo = weights.max()`, `hi = weights.sum()`), *Split Array Largest Sum*, *Aggressive Cows / Magnetic Force Between Two Balls* (Last True template).

### Common Pitfalls & Senior Follow-Ups
- **Pitfall:** Computing `mid = (lo + hi) / 2` (overflows when `lo + hi > Int.MAX_VALUE`). Always use `lo + (hi - lo) / 2`.
- **Pitfall:** Using `Math.ceil(pile.toDouble() / speed)` (floating-point precision loss for large `Long` values). Prefer integer ceiling `(a + b - 1L) / b`.
- **Follow-Up (Duplicates in Rotated Array):** If duplicates exist (`nums[lo] == nums[mid] == nums[hi]`), you cannot tell which half is sorted; shrink both ends (`lo++`, `hi--`), degrading worst-case time to `O(n)`.

---

## 5. Core Dynamic Programming

This section bridges directly into your remaining course modules: **`Module 14`** (Recursion/Memoization/Tabulation), **`Module 15`** (0-1 Knapsack), **`Module 17`** (Subset Sum / Unbounded Knapsack / Coin Change), and **`Module 19`** (LCS).

### The 5-Step Senior DP Narration Protocol
Before coding any DP problem in an interview, state these five points aloud in 60–90 seconds:
1. **State:** Define `dp[i]` (or `dp[i][j]`) in one plain English sentence.
2. **Recurrence:** Write the transition from smaller subproblems.
3. **Base Cases & Sentinel:** Initial values (and `INF` / impossible value that won't overflow).
4. **Iteration Order:** Bottom-up direction so dependencies are already computed.
5. **Space Optimization:** Can `O(n)` drop to `O(1)` rolling variables, or `O(n * m)` drop to `O(m)` 1D array?

### Top-Down Memoization vs. Bottom-Up Tabulation
- **Top-Down (Recursion + Memo):** Faster to derive when state transitions are irregular or sparse (e.g., tree/DAG DP, complex string intervals). Watch out for JVM stack overflow when recursion depth can reach `10^5`.
- **Bottom-Up (Iterative Table):** Preferred for standard 1D/2D DP in interviews—no recursion stack overhead, cache-friendly primitive arrays, and easy `O(1)` / 1D space optimization.

### The 4 High-ROI DP Families

#### Family A: 1D Linear DP — House Robber (`Module 14` bridge)
- **State:** `dp[i]` = max money robbed from houses `0..i`.
- **Recurrence:** `dp[i] = maxOf(dp[i - 1], dp[i - 2] + nums[i])`.
- **Space Optimization:** Only the last two states matter → `O(1)` space.

```kotlin
fun rob(nums: IntArray): Int {
    var prev2 = 0 // dp[i - 2]
    var prev1 = 0 // dp[i - 1]
    for (money in nums) {
        val curr = maxOf(prev1, prev2 + money)
        prev2 = prev1
        prev1 = curr
    }
    return prev1
}
```
- **Complexity:** `O(n)` time, `O(1)` space.

#### Family B: Unbounded Knapsack — Coin Change (`Module 17` bridge)
- **State:** `dp[a]` = minimum coins needed to make amount `a`.
- **Sentinel:** `amount + 1` (strictly greater than any valid answer, never overflows on `+ 1`).
- **Iteration Order:** **Forward** (`a in coin..amount`) because each coin can be reused unlimited times.

```kotlin
fun coinChange(coins: IntArray, amount: Int): Int {
    val inf = amount + 1
    val dp = IntArray(amount + 1) { inf }
    dp[0] = 0

    for (coin in coins) {
        for (a in coin..amount) {
            dp[a] = minOf(dp[a], dp[a - coin] + 1)
        }
    }
    return if (dp[amount] > amount) -1 else dp[amount]
}
```
- **Complexity:** `O(coins.size * amount)` time, `O(amount)` space.
- **Critical Loop-Order Distinction (Counting Ways — *Coin Change II* vs. *Combination Sum IV*):**
  - **Outer `coin`, inner `a in coin..amount`:** Counts **combinations** (order doesn't matter: `1+2` and `2+1` counted once).
  - **Outer `a in 1..amount`, inner `coin`:** Counts **permutations** (order matters: `1+2` and `2+1` counted separately).

#### Family C: 0-1 Knapsack / Subset Sum — Partition Equal Subset Sum (`Module 15` & `Module 17` bridge)
- **State:** `dp[s]` = whether a subset of processed numbers can sum to `s`.
- **Iteration Order:** **Backward** (`s in target downTo num`) so each number is used **at most once**. Iterating forward here would reuse `num` multiple times and turn 0-1 Knapsack into Unbounded Knapsack.

```kotlin
fun canPartition(nums: IntArray): Boolean {
    val total = nums.sum()
    if (total % 2 != 0) return false
    val target = total / 2

    val dp = BooleanArray(target + 1)
    dp[0] = true

    for (num in nums) {
        for (s in target downTo num) {
            if (dp[s - num]) dp[s] = true
        }
        if (dp[target]) return true // Early exit
    }
    return dp[target]
}
```
- **Complexity:** `O(n * target)` time, `O(target)` space.

#### Family D: 2D Sequence DP — Longest Common Subsequence (`Module 19` bridge)
Directly connects to mobile list diffing (e.g., Android `DiffUtil` / Myers diff finds the shortest edit script, the dual of LCS).
- **State:** `dp[i][j]` = LCS length of `text1[0..<i]` and `text2[0..<j]`.
- **Recurrence:**
  - If `text1[i - 1] == text2[j - 1]`: `dp[i][j] = dp[i - 1][j - 1] + 1`
  - Else: `dp[i][j] = maxOf(dp[i - 1][j], dp[i][j - 1])`

```kotlin
fun longestCommonSubsequence(text1: String, text2: String): Int {
    val m = text1.length
    val n = text2.length
    // Space-optimized 1D rolling row: prevDiag holds dp[i - 1][j - 1]
    val dp = IntArray(n + 1)

    for (i in 1..m) {
        var prevDiag = 0
        for (j in 1..n) {
            val savedUp = dp[j] // dp[i - 1][j] before overwrite
            dp[j] = if (text1[i - 1] == text2[j - 1]) {
                prevDiag + 1
            } else {
                maxOf(dp[j], dp[j - 1])
            }
            prevDiag = savedUp
        }
    }
    return dp[n]
}
```
- **Complexity:** `O(m * n)` time, `O(n)` space (swap `text1`/`text2` so `n = min(m, n)` for `O(min(m, n))` space).
- **Note on Path Reconstruction:** If the follow-up asks to *print* the actual LCS string (like `Module 19/printing_lcs.cpp`), keep the full 2D `dp` table so you can backtrack from `(m, n)` to `(0, 0)`.

#### Bonus Bridge (DP + Binary Search): Longest Increasing Subsequence (`O(n log n)`)
Start with the `O(n^2)` DP (`dp[i] = 1 + max(dp[j])` for `j < i, nums[j] < nums[i]`), then optimize to `O(n log n)` using a `tails` array where `tails[len]` is the smallest possible ending value of an increasing subsequence of length `len + 1`.

```kotlin
fun lengthOfLIS(nums: IntArray): Int {
    val tails = IntArray(nums.size)
    var size = 0

    for (x in nums) {
        // First-True binary search: smallest index in [0, size] with tails[idx] >= x
        var lo = 0
        var hi = size
        while (lo < hi) {
            val mid = lo + (hi - lo) / 2
            if (tails[mid] >= x) hi = mid else lo = mid + 1
        }
        tails[lo] = x
        if (lo == size) size++
    }
    return size
}
```
- **Complexity:** `O(n log n)` time, `O(n)` space.
- **Note:** `tails` is *not* necessarily the actual LIS itself, only its length is exact; for non-decreasing subsequences, change `tails[mid] >= x` to `tails[mid] > x`.

---

## 6. High-Frequency Bug-Hunt Checklist (Before Saying "I'm Done")

Run this 60-second mental scan on your code before finishing a problem:
- [ ] **Sliding Window:** Did I update `best` *outside* `while (!valid)` for Longest, and *inside* `while (valid)` for Shortest?
- [ ] **Two Pointers:** Did I check `left < right` *before* array indexing inside duplicate-skipping `while` loops?
- [ ] **Heap:** Did I avoid `a - b` in comparators? Did I guard `peek()` / `poll()` against an empty heap?
- [ ] **Binary Search:** Did I use `lo + (hi - lo) / 2` for First True and `lo + (hi - lo + 1) / 2` for Last True? Did I use `Long` inside feasibility sums?
- [ ] **Dynamic Programming:** Is my 1D knapsack inner loop **backward** for 0-1 (use once) and **forward** for unbounded (reuse)? Is my `INF` safe from `+ 1` overflow?

---

## 7. Focused 5-Session Drill Plan

Run these five 45–60 minute sessions in a plain editor (no IDE autocomplete) while narrating in English:

| Session | Pattern | Target Problems (25 min each) | Repo Codelab Cross-Reference |
| :--- | :--- | :--- | :--- |
| **1** | **Sliding Window** | Longest Substring Without Repeating Characters → Minimum Window Substring | *(New — not in course modules)* |
| **2** | **Two Pointers** | 3Sum → Trapping Rain Water | *(New — not in course modules)* |
| **3** | **Heap / `PriorityQueue`** | Top K Frequent Elements → Meeting Rooms II (or Merge K Sorted Lists) | `Module 7/priority_queue_of_pairs.cpp` |
| **4** | **Binary Search** | Search in Rotated Sorted Array → Koko Eating Bananas | *(New — not in course modules)* |
| **5** | **Core DP** | Coin Change → Partition Equal Subset Sum → Longest Common Subsequence | `Module 14`, `Module 15`, `Module 17`, `Module 19/lcs.cpp` |
