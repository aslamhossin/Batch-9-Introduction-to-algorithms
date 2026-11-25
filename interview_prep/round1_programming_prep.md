# 1st Round Programming Interview Prep Plan (Kotlin DSA & 60-Minute Coding)

A focused, execution-ready preparation plan for the **60-minute Programming interview** in Round 1 (Senior Software Engineer — Mobile Foundation), built directly on top of your existing Kotlin DSA progress in this repository.

---

## 1. Current Baseline & Gap Analysis

### What You Already Have Solidified
Based on your completed codelabs in `kotlin_codelabs/` (`Module 3` and `Module 5`):
- **Graph & 2D Grid DFS/BFS:** Graph traversal, 2D grid navigation, unweighted shortest path, connected components, islands, perimeter, closed islands, and sub-islands.
- **Idiomatic Kotlin 2.x Fluency:** Clean use of `0..<n`, destructured direction arrays, nested helper functions (`valid`, `dfs`), and primitive arrays (`IntArray`, `BooleanArray`).

### Highest-Priority Gaps for a 60-Minute Senior Coding Round
1. **Non-Graph Core Patterns (Not Covered in the Course Modules):** Most 60-minute coding rounds heavily test **Arrays/Hashing, Sliding Window, Two Pointers, Monotonic Stack, Binary Search, Trees/BST, Heaps, Intervals, and Practical Data Structure Design** (e.g., LRU Cache, Trie, Rate Limiter, Dependency Resolver).
2. **Remaining High-ROI Graph & DP Modules:** You are currently at `Module 6` (Cycle Detection). Cycle Detection + Topological Sort (`Module 6`), Dijkstra + `PriorityQueue` (`Module 7`), Union-Find (`Module 11`), and core Dynamic Programming (`Module 14–17`) have high interview ROI, while some later modules are lower priority.
3. **60-Minute Interview Execution Under Time Pressure:** Practicing without IDE autocomplete, narrating trade-offs clearly in English, hand-tracing edge cases before running, and handling multi-part follow-ups cleanly.

---

## 2. How to Triage the Remaining Repo Modules (`Module 6` → `Module 19`)

Do not treat all remaining course modules equally before Round 1. Use this priority map alongside your `HANDOFF.md` codelabs:

| Priority | Repo Module | Topics | Interview ROI & Action |
| :--- | :--- | :--- | :--- |
| **Must Master** | **Module 6** | Undirected & Directed Cycle Detection, Kahn's BFS (Topological Sort) | **Very High.** Directly maps to build/dependency graphs, course/task scheduling, and deadlock detection. Complete both Codelabs 6.1 & 6.2. |
| **Must Master** | **Module 7** | Weighted Adjacency List, `PriorityQueue`, Optimized Dijkstra | **High.** Essential for weighted shortest paths and mastering Kotlin's `PriorityQueue` comparator patterns. |
| **Must Master** | **Module 11** | Disjoint Set Union (Path Compression + Union by Size/Rank) | **High.** Fast, clean alternative to DFS for dynamic connectivity, grouping, and cycle detection. |
| **Must Master** | **Module 14, 15, 17** | Memoization, Bottom-Up DP, 0-1 Knapsack, Coin Change / Subset Sum | **High.** Focus on state definition, transitions, and space optimization (1D DP first, then 2D). |
| **Good to Know** | **Module 18 & 19 (LCS)** | Merge Sort, Longest Common Subsequence | **Medium.** Merge Sort is useful for inversion/divide-and-conquer patterns; LCS is a classic 2D DP pattern with direct relevance to mobile list diffing (`DiffUtil`). |
| **Conceptual Only** | **Module 9, 10, 13, 19 (MST)** | Bellman-Ford, Floyd-Warshall, Competitive Mixed Problems, Kruskal's MST | **Low for Round 1.** Know when to mention them (negative weights → Bellman-Ford; small `V <= 400` all-pairs → Floyd-Warshall; minimum wiring cost → Kruskal's + DSU), but defer full codelabs until after Round 1. |

---

## 3. The 60-Minute Programming Interview Playbook

Senior-level 60-minute programming rounds typically take one of two formats:
- **Format A (Algorithmic):** 1 Medium + 1 Medium/Hard problem (or 1 problem with 2–3 escalating follow-ups).
- **Format B (Practical / Stateful Coding):** A multi-part real-world problem (e.g., in-memory cache with TTL, task/dependency runner, rate limiter, event aggregator, or log parser) where requirements evolve across 3–4 stages.

### Time Budget Breakdown

| Window | Phase | What to Do |
| :--- | :--- | :--- |
| **0–5 min** | **Clarify & Scope** | Restate the problem in your own words. Ask about input size/bounds, empty/null inputs, duplicates, negative numbers, ordering, mutability, and expected return values on invalid input. |
| **5–15 min** | **Design & Align** | Start with the obvious brute-force baseline (1 sentence + complexity), then derive the optimal approach. Walk through a tiny 3–4 element example verbally. State Time & Space complexity (including recursion stack). **Get explicit alignment before coding.** |
| **15–40 min** | **Clean Implementation** | Write idiomatic, modular Kotlin. Extract small helpers (`isValid`, `buildGraph`, `evict`) so the main flow reads like prose. Narrate *why* you are writing each block, not just reading syntax aloud. |
| **40–50 min** | **Verify & Edge Cases** | Dry-run your code line by line on: (1) the normal example, (2) empty / single-element input, (3) boundary values (all same, already sorted, disconnected graph, cycles, `Int` overflow). Fix bugs calmly and explain the root cause. |
| **50–60 min** | **Follow-Ups & Scaling** | Handle extensions (memory limits, streaming input, thread safety, persistence, or cleaner API ergonomics). Save 3–5 minutes for thoughtful questions if the interviewer opens the floor. |

### Senior-Level Signals Interviewers Look For
- **Proactive Trade-Offs:** Explicitly compare approaches (e.g., *"DFS uses `O(V)` stack space and can overflow on skewed graphs of 10^5 nodes; iterative BFS/stack avoids stack overflow"*).
- **Clean Data Modeling:** Use `data class` or clear helper types instead of cryptic `Triple<Int, Int, Int>` when representing domain state.
- **Defensive Boundaries:** Check empty collections before indexing (`if (grid.isEmpty() || grid[0].isEmpty()) return 0`), guard against integer overflow using `Long`, and avoid mutating caller-owned inputs unless permitted.
- **Calm Debugging:** When a dry-run reveals a bug, treat it as a win—state what failed, why it failed, and verify the fix across all branches.

### Useful English Narration Phrases
- **Clarifying:** *"Before I jump into a solution, let me verify a few constraints: can the input be empty, and can values be negative or exceed 32-bit integer bounds?"*
- **Aligning:** *"A brute-force approach would check all pairs in `O(n^2)`. By maintaining a hash map / sliding window, we can bring this down to `O(n)` time and `O(k)` space. Does that direction sound good before I start coding?"*
- **Structuring:** *"I'll separate the traversal helper from the main loop so the invariants stay easy to verify."*
- **Testing:** *"Let me trace this with a small edge case—say a single-node graph and a cycle—before considering it done."*

---

## 4. Two-Week Study Roadmap (With 1-Week Fast-Track Option)

Aim for **2–3 hours/day**: 15 min template review, 90 min timed coding (2–3 problems), 30 min post-mortem & mistake log.

> **1-Week Fast-Track:** If your interview is ~7 days away, compress Days 1–10 into 5 days (2 problems per pattern instead of 4, prioritizing **Must-Do** items below) and keep Days 6–7 exclusively for timed 60-minute mocks + practical coding drills.

### Week 1: Core Patterns & High-ROI Graphs

- **Day 1 — Arrays, Strings & Hash Maps**
  - Focus: Frequency maps, prefix sums, in-place array manipulation, grouping/anagrams.
  - Goal: Write zero-bug `O(n)` map/prefix-sum solutions in under 15 minutes.
- **Day 2 — Two Pointers & Sliding Window**
  - Focus: Fixed vs. variable sliding window, shrink-while-invalid invariant, opposite-end pointers.
  - Goal: Internalize the exact `left` / `right` expansion-contraction loop without off-by-one errors.
- **Day 3 — Stacks, Queues & Monotonic Stack**
  - Focus: Valid parentheses/parsing, Min Stack, Next Greater Element, Daily Temperatures, Largest Rectangle / Trapping Rain Water.
  - Goal: Use `ArrayDeque` cleanly for both stack and queue operations.
- **Day 4 — Binary Search (Standard & Binary Search on Answer)**
  - Focus: First/last occurrence, rotated sorted array, and monotonic predicate search (`Koko Eating Bananas`, `Capacity to Ship Packages`).
  - Goal: Pick one bug-free binary search invariant (`left < right` with `mid = left + (right - left) / 2`) and stick to it every time.
- **Day 5 — Trees & BST**
  - Focus: Recursive DFS (return-value vs. top-down state), level-order BFS, Lowest Common Ancestor, Validate BST, Serialize/Deserialize.
  - Goal: Comfortably reason about subtree return values and `O(h)` stack space.
- **Day 6 — Repo Module 6: Cycle Detection & Topological Sort**
  - Focus: Undirected cycle detection (`parent[]`), directed cycle detection (`0/1/2` states or `pathVis[]`), and **Kahn's Algorithm (BFS in-degree)** for Topological Sort.
  - Goal: Solve dependency-ordering problems (`Course Schedule I & II`, `Alien Dictionary` concept) cold.
- **Day 7 — Repo Module 7 & 11: Heaps, Dijkstra & Union-Find (DSU)**
  - Focus: Kotlin `PriorityQueue` (min/max heap, custom comparators), Top-K, K-way merge, Optimized Dijkstra, and DSU with Path Compression + Union by Size.
  - Goal: Know instantly when to pick BFS vs. Dijkstra vs. DSU.

### Week 2: Intervals, Backtracking, Core DP, Practical Coding & Mocks

- **Day 8 — Intervals & Greedy**
  - Focus: Sort-by-start vs. sort-by-end, merging intervals, meeting rooms (min-heap or sweep line), jump game / task scheduling.
- **Day 9 — Backtracking & Recursion**
  - Focus: Subsets, Permutations, Combination Sum, Word Search (2D grid backtracking with in-place marking + restore).
- **Day 10 — Repo Modules 14, 15, 17 & 19: High-ROI Dynamic Programming**
  - Focus: 1D DP (`Climbing Stairs`, `House Robber`, `Coin Change`, `Word Break`, `Longest Increasing Subsequence`) and 2D DP (`Unique Paths`, `0-1 Knapsack`, `Longest Common Subsequence`).
  - Goal: Always articulate `(1) DP state, (2) recurrence, (3) base cases, (4) iteration order, (5) space optimization`.
- **Day 11 — Practical / Stateful Design Coding (Senior Focus)**
  - Focus: `LRU Cache`, `Trie (Prefix Tree)`, `Time-Based Key-Value Store`, `Design Hit Counter / Sliding Window Rate Limiter`, `In-Memory File System / Event Scheduler`.
  - Goal: Practice clean class/API design, helper decomposition, and extending your code when a new requirement is added mid-session.
- **Day 12 — Timed Mock #1 (Algorithmic, 60 min)**
  - Setup: Plain text editor or LeetCode without autocomplete + 60-minute timer. Speak aloud in English the entire time.
  - Format: 1 Medium (20 min) + 1 Medium/Hard (35 min) + 5 min self-review.
- **Day 13 — Timed Mock #2 (Practical / Multi-Part, 60 min)**
  - Setup: Implement an LRU Cache with TTL support, or a Task/Dependency Executor with cycle detection and parallel-batch ordering, adding a requirement every 15 minutes.
- **Day 14 — Weakness Patching & Light Review**
  - Re-solve 3–4 problems from your Mistake Log from scratch without looking at notes.
  - Review the Kotlin Cheat Sheet (Section 6) and Time Budget (Section 3). Stop studying early and rest well.

---

## 5. Curated High-ROI Problem List (45 Problems)

Prioritize **Must-Do** items first. Aim to solve Medium problems in **20–25 minutes** including dry-run testing.

### A. Arrays, Hashing & Prefix Sum
1. **Two Sum** (Easy) — Hash map lookup invariant.
2. **Group Anagrams** (Medium, *Must-Do*) — Frequency key vs. sorted key trade-off.
3. **Product of Array Except Self** (Medium, *Must-Do*) — Prefix/suffix without division, `O(1)` extra space.
4. **Subarray Sum Equals K** (Medium, *Must-Do*) — Prefix sum + frequency map (handles negatives where sliding window fails).
5. **Longest Consecutive Sequence** (Medium) — `HashSet`, start counting only when `num - 1` is absent.

### B. Two Pointers & Sliding Window
6. **Container With Most Water** (Medium) — Greedy opposite-end pointer contraction.
7. **3Sum** (Medium) — Sorting + deduplication with two pointers.
8. **Longest Substring Without Repeating Characters** (Medium, *Must-Do*) — Variable window with last-seen index map.
9. **Longest Repeating Character Replacement** (Medium) — Window validity via `windowLen - maxFreq <= k`.
10. **Minimum Window Substring** (Hard, *Must-Do*) — Classic two-map shrink-to-minimum window.

### C. Stack, Queue & Monotonic Stack
11. **Valid Parentheses** (Easy) — `ArrayDeque` stack baseline.
12. **Min Stack** (Medium) — Auxiliary min tracking in `O(1)`.
13. **Daily Temperatures** (Medium, *Must-Do*) — Monotonic decreasing stack of indices.
14. **Evaluate Reverse Polish Notation** or **Basic Calculator II** (Medium) — Expression parsing (frequent in practical rounds).
15. **Trapping Rain Water** (Hard) — Two-pointer `O(1)` space vs. monotonic stack.

### D. Binary Search
16. **Search in Rotated Sorted Array** (Medium, *Must-Do*) — Identify which half is sorted.
17. **Find Minimum in Rotated Sorted Array** (Medium) — `left < right` boundary convergence.
18. **Koko Eating Bananas** (Medium, *Must-Do*) — Binary search on answer space with `Long` overflow guard.
19. **Capacity To Ship Packages Within D Days** (Medium) — Monotonic feasibility predicate.

### E. Linked Lists
20. **Reverse Linked List** (Easy) — Iterative pointer rewiring.
21. **Merge Two Sorted Lists** (Easy) — Dummy head pattern.
22. **Linked List Cycle II** (Medium) — Floyd's fast/slow pointers.
23. **Reorder List** (Medium) — Find middle + reverse second half + merge.

### F. Trees & BST
24. **Binary Tree Level Order Traversal** (Medium) — BFS with `ArrayDeque` and level-size loop.
25. **Validate Binary Search Tree** (Medium, *Must-Do*) — Pass `Long.MIN_VALUE..Long.MAX_VALUE` bounds down.
26. **Lowest Common Ancestor of a Binary Tree** (Medium, *Must-Do*) — Post-order return value propagation.
27. **Diameter of Binary Tree** (Easy/Medium) — Height vs. global max path through node.
28. **Binary Tree Maximum Path Sum** (Hard) — Post-order gain (`maxOf(0, childGain)`).
29. **Serialize and Deserialize Binary Tree** (Hard) — Pre-order or BFS encoding (great Senior communication problem).

### G. Graphs (Building on Your Completed Modules 3 & 5)
30. **Rotting Oranges** (Medium, *Must-Do*) — Multi-source BFS on 2D grid.
31. **Clone Graph** (Medium) — DFS/BFS with `oldNode -> newNode` map.
32. **Course Schedule I & II** (Medium, *Must-Do*, *Module 6*) — Directed cycle detection + Kahn's Topological Sort.
33. **Network Delay Time** (Medium, *Must-Do*, *Module 7*) — Optimized Dijkstra with `PriorityQueue`.
34. **Redundant Connection** (Medium, *Must-Do*, *Module 11*) — Union-Find cycle detection.
35. **Number of Provinces / Accounts Merge** (Medium, *Module 11*) — Union-Find grouping on real-world entities.
36. **Word Ladder** (Hard) — Implicit graph BFS shortest path.

### H. Heaps, Intervals & Backtracking
37. **Top K Frequent Elements** (Medium, *Must-Do*) — Min-heap of size `k` (`O(n log k)`) vs. bucket sort (`O(n)`).
38. **Merge K Sorted Lists** (Hard, *Must-Do*) — Min-heap K-way merge.
39. **Merge Intervals** (Medium, *Must-Do*) — Sort by start time, merge overlapping ranges.
40. **Meeting Rooms II** (Medium, *Must-Do*) — Min-heap of end times (or sweep line).
41. **Combination Sum** & **Word Search** (Medium) — Backtracking with pruning and state restoration.

### I. Dynamic Programming & Practical Data Structures
42. **House Robber** & **Coin Change** (Medium, *Must-Do*, *Module 14/17*) — 1D DP staples.
43. **Longest Common Subsequence** (Medium, *Module 19*) — Classic 2D DP (connects to Android `DiffUtil`).
44. **LRU Cache** (Medium, *Must-Do*) — HashMap + Doubly Linked List (`O(1)` get/put) — know how to code from scratch, not just `LinkedHashMap`.
45. **Implement Trie (Prefix Tree)** & **Time Based Key-Value Store** (Medium, *Must-Do*) — Practical API + binary search/tree design.

---

## 6. Idiomatic Kotlin 2.x Interview Cheat Sheet

### Standard Library Quick Reference

| Need | Preferred Kotlin Idiom | Avoid / Pitfall |
| :--- | :--- | :--- |
| **Queue (BFS)** | `val q = ArrayDeque<Int>()` → `addLast(x)` / `removeFirst()` | `LinkedList` (extra node allocations) |
| **Stack** | `val st = ArrayDeque<Int>()` → `addLast(x)` / `removeLast()` / `last()` | `java.util.Stack` (synchronized legacy class) |
| **Min-Heap** | `PriorityQueue<Int>()` or `PriorityQueue<Node>(compareBy { it.dist })` | `a - b` comparator (can overflow `Int`!) |
| **Max-Heap** | `PriorityQueue<Int>(compareByDescending { it })` | Negating `Int.MIN_VALUE` (overflows!) |
| **Frequency Map** | `map[k] = map.getOrDefault(k, 0) + 1` or `map.merge(k, 1, Int::plus)` | Repeated `containsKey` + `!!` |
| **Adjacency List** | `val adj = Array(n) { ArrayList<Int>() }` | `MutableList` of `MutableList` when `0..<n` fits `Array` |
| **String Building** | `val sb = StringBuilder()` → `sb.append(...)` → `sb.toString()` | `str += ch` inside a loop (`O(n^2)`) |
| **Ordered Map/Set** | `java.util.TreeMap<Int, Int>()` (`floorKey`, `ceilingKey`) | Sorting a list on every insert |
| **Infinity Constant** | `const val INF = Int.MAX_VALUE / 2` (or `Long.MAX_VALUE / 4`) | `Int.MAX_VALUE + weight` (silently overflows negative!) |

### Copy-Ready Core Templates (Memorize the Invariants)

#### 1. Binary Search (First True / Lower Bound)
```kotlin
// Finds the smallest index/value in [lo, hi] where ok(mid) is true.
var lo = minPossible
var hi = maxPossible
while (lo < hi) {
    val mid = lo + (hi - lo) / 2
    if (ok(mid)) {
        hi = mid
    } else {
        lo = mid + 1
    }
}
return lo
```

#### 2. Sliding Window (Variable Length)
```kotlin
var left = 0
var best = 0
for (right in s.indices) {
    // 1. Add s[right] to window state
    while (!isValid()) {
        // 2. Remove s[left] from window state
        left++
    }
    // 3. Update answer with valid window [left..right]
    best = maxOf(best, right - left + 1)
}
```

#### 3. Topological Sort — Kahn's BFS (Module 6)
```kotlin
fun topoSort(n: Int, edges: Array<IntArray>): IntArray {
    val adj = Array(n) { ArrayList<Int>() }
    val inDegree = IntArray(n)
    for ((u, v) in edges) {
        adj[u].add(v)
        inDegree[v]++
    }
    val q = ArrayDeque<Int>()
    for (i in 0..<n) if (inDegree[i] == 0) q.addLast(i)

    val order = IntArray(n)
    var idx = 0
    while (q.isNotEmpty()) {
        val u = q.removeFirst()
        order[idx++] = u
        for (v in adj[u]) {
            if (--inDegree[v] == 0) q.addLast(v)
        }
    }
    return if (idx == n) order else intArrayOf() // empty => cycle detected
}
```

#### 4. Optimized Dijkstra (Module 7)
```kotlin
data class Edge(val to: Int, val weight: Int)
data class State(val node: Int, val dist: Long)

fun dijkstra(n: Int, adj: Array<ArrayList<Edge>>, src: Int): LongArray {
    val dist = LongArray(n) { Long.MAX_VALUE / 4 }
    val pq = PriorityQueue<State>(compareBy { it.dist })
    dist[src] = 0L
    pq.add(State(src, 0L))

    while (pq.isNotEmpty()) {
        val (u, d) = pq.poll()
        if (d > dist[u]) continue // stale heap entry
        for ((v, w) in adj[u]) {
            val nd = d + w
            if (nd < dist[v]) {
                dist[v] = nd
                pq.add(State(v, nd))
            }
        }
    }
    return dist
}
```

#### 5. Disjoint Set Union / Union-Find (Module 11)
```kotlin
class DSU(n: Int) {
    private val parent = IntArray(n) { it }
    private val size = IntArray(n) { 1 }

    fun find(x: Int): Int {
        var root = x
        while (parent[root] != root) root = parent[root]
        var cur = x
        while (parent[cur] != root) {
            val next = parent[cur]
            parent[cur] = root
            cur = next
        }
        return root
    }

    fun union(a: Int, b: Int): Boolean {
        var ra = find(a)
        var rb = find(b)
        if (ra == rb) return false // already connected (cycle)
        if (size[ra] < size[rb]) {
            val tmp = ra; ra = rb; rb = tmp
        }
        parent[rb] = ra
        size[ra] += size[rb]
        return true
    }
}
```

---

## 7. Practical / Multi-Part Coding Checklist (Senior Mobile Foundation)

If your 60-minute round leans toward a practical coding exercise instead of pure LeetCode:
1. **Define clean contracts first:** Write clear function signatures and small `data class` models before implementing internals.
2. **Design for the obvious next requirement:** Don't over-engineer upfront, but keep state encapsulated inside a class so adding a new method (e.g., `evictExpired(now)`, `getStats()`, `cancel(id)`) does not require rewriting everything.
3. **Avoid hidden global state:** Pass clocks/dependencies (`now: Long` or `() -> Long`) as parameters so the logic is deterministic and easy to unit-test.
4. **Be ready to discuss mobile-relevant follow-ups:**
   - Memory footprint (primitive arrays vs. boxed objects, bounded caches, eviction policies).
   - Thread safety (immutable snapshots, `synchronized` / `ReentrantLock` / `ConcurrentHashMap`, or coroutine `Mutex` / single-threaded dispatcher).
   - Large inputs on constrained devices (streaming/chunking instead of loading everything into memory).

---

## 8. Practice Tracker & Mistake Log Template

Copy this table into your local notes (or append below as you practice) and fill it out after **every** timed problem:

| Date | Problem | Pattern | Time | Solved Solo? | Root Cause of Any Bug / Slowdown | Rule to Remember Next Time |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| YYYY-MM-DD | e.g. Course Schedule II | Topo Sort (BFS) | 22m | Yes | Forgot `idx == n` cycle check | Always compare output count with `n` |

### Final 48-Hour Checklist
- [ ] Can write BFS, DFS, Kahn's Topological Sort, Dijkstra, DSU, Binary Search, Sliding Window, and LRU Cache in Kotlin from memory without syntax lookup.
- [ ] Completed at least **2 full 60-minute timed mocks** speaking aloud in English in a plain editor (no IDE autocomplete).
- [ ] Re-solved the top 3–5 problems from your Mistake Log cleanly.
- [ ] Tested microphone, camera, screen sharing, and a clean coding environment (font size readable over video call).
