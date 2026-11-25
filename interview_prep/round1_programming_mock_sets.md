# 60-Minute Timed Mock Coding Sets & Walkthrough Checklist (Kotlin)

A hands-on mock practice companion to [`round1_programming_prep.md`](./round1_programming_prep.md). Use this guide to execute **Day 12 (Format A: Algorithmic Pair)** and **Day 13 (Format B: Practical Multi-Part Stateful Coding)** under realistic 60-minute interview conditions.

> **Spoiler Guard:** Every reference solution is tucked inside a collapsible section. Read only the **Prompt** before starting your 60-minute timer, and wait until the timer stops before expanding the solution or checklist answers.

---

## 1. Mock Setup & Ground Rules

1. **Environment:** Use a plain text editor or scratch Kotlin file with **autocomplete disabled** (no AI suggestions, no auto-imports).
2. **Timer:** Set a hard **60-minute countdown** and follow the pacing in [`round1_programming_prep.md` Section 3](./round1_programming_prep.md#3-the-60-minute-programming-interview-playbook).
3. **Narrate in English:** Speak out loud for the entire session—state assumptions, compare trade-offs, explain *why* each helper exists, and narrate your dry-run line by line.
4. **Post-Mock Debrief (5 min):** Score yourself using Section 5 and log any bugs or slowdowns in your Mistake Log (`round1_programming_prep.md` Section 8).

---

## 2. Live 60-Minute Walkthrough Checklist

Keep this checklist visible during every mock until the rhythm becomes automatic.

### Phase 1: Clarify & Scope (0–5 min)
- [ ] Restated the problem in 1–2 sentences to confirm understanding.
- [ ] Checked **input size & bounds** (e.g., `n` up to `10^5`? Can sums or counts overflow 32-bit `Int`?).
- [ ] Checked **empty / null / zero-capacity** inputs and expected return value on invalid input.
- [ ] Checked **duplicates, negative numbers, self-loops, and ordering** requirements.
- [ ] Checked **mutability** (can we modify the input array/list in place, or treat it as read-only?).

### Phase 2: Design & Align (5–15 min)
- [ ] Stated the obvious brute-force baseline in 1 sentence with its Time & Space complexity.
- [ ] Identified the bottleneck and derived the optimal data structure / invariant.
- [ ] Walked through a tiny **3–4 element example** verbally to verify the invariant.
- [ ] Stated final **Time & Space complexity** (including recursion stack or auxiliary maps).
- [ ] Asked for explicit alignment before coding (*"Does this approach look good before I start writing Kotlin?"*).

### Phase 3: Clean Kotlin Implementation (15–40 min)
- [ ] Defined clean function signatures / `data class` models first (avoid cryptic `Triple` / `Pair` soup for domain state).
- [ ] Extracted small helpers (`evictLru`, `isExpired`, `moveToFront`) so the main flow reads like prose.
- [ ] Used idiomatic Kotlin 2.x (`0..<n`, `ArrayDeque`, `PriorityQueue(compareBy { ... })`, `Long` overflow guards).
- [ ] Narrated *intent* behind each block instead of reading syntax aloud.

### Phase 4: Dry-Run & Edge Cases (40–50 min)
- [ ] Traced the code **line by line** on the main sample input (tracking variables explicitly, not guessing).
- [ ] Verified **empty / single-element** input.
- [ ] Verified **boundary cases** (all zeros/same values, negative numbers, integer overflow, disconnected graph, cycles, zero capacity, expired entries).
- [ ] If a bug appeared during dry-run, stated the root cause clearly and re-verified the fix across all branches.

### Phase 5: Follow-Ups & Scaling (50–60 min)
- [ ] Addressed scaling constraints (memory footprint, streaming input, thread safety, deterministic testing via injected clocks).
- [ ] Stated trade-offs between alternative designs clearly without over-engineering the core solution.

---

## 3. Mock Set 1 — Format A: Algorithmic Pair (60 Minutes)

**Target Pacing:** Problem 1A (`20 min`) + Problem 1B (`35 min`) + Self-Review (`5 min`).

---

### Problem 1A (Warm-Up Medium — 20 min): Count Subarrays with Target Sum

#### Prompt
Given an integer array `nums` and a target integer `target`, return the total number of **contiguous** subarrays whose elements sum to `target`.

- **Example 1:** `nums = [1, 1, 1]`, `target = 2` → Output: `2` (`[1, 1]` at indices `0..1` and `1..2`)
- **Example 2:** `nums = [1, -1, 0]`, `target = 0` → Output: `3` (`[1, -1]`, `[0]`, `[1, -1, 0]`)

#### Clarifying Questions to Ask Before Coding
1. Can `nums` contain **negative numbers or zeros**? *(Crucial: if negatives exist, two-pointer sliding window fails because expanding/shrinking is no longer monotonic.)*
2. What is the maximum length `n` and value range of `nums[i]`? *(If `n = 10^5` and `|nums[i]| <= 10^9`, both running prefix sums and the subarray count `n * (n + 1) / 2` can overflow 32-bit `Int` → use `Long`.)*
3. Can `nums` be empty? *(Return `0L`.)*
4. Must subarrays be non-empty? *(Yes, standard contiguous subarrays have length `>= 1`.)*

#### Edge Cases Checklist
- Empty array `[]` with `target = 0` → `0L` (do not accidentally count the seeded `freq[0L] = 1L`).
- All zeros `[0, 0, 0]` with `target = 0` → `6L` (`3 * 4 / 2`).
- Single element matching `target` vs. not matching `target`.
- Negative numbers canceling out to `0` (`[1, -1, 0]`).
- Large values causing 32-bit `Int` overflow (`[Int.MAX_VALUE, Int.MAX_VALUE]`, `target = 2L * Int.MAX_VALUE`).

#### Complexity
- **Time:** `O(n)` — single pass with `O(1)` average hash map lookup/update.
- **Space:** `O(n)` — up to `n + 1` distinct prefix sums stored in the map.
- **Follow-Up (Positive-Only Input + `O(1)` Space):** If all elements are strictly positive (`nums[i] > 0`), a two-pointer sliding window solves this in `O(n)` time and `O(1)` extra space.

<details>
<summary><strong>Reference Solution — Problem 1A (Kotlin 2.x)</strong></summary>

```kotlin
fun countSubarraysWithSum(nums: IntArray, target: Long): Long {
    // Maps prefixSum -> how many times this prefix sum has been seen so far.
    // Seed with 0L -> 1L so subarrays starting at index 0 are counted.
    val freq = HashMap<Long, Long>()
    freq[0L] = 1L

    var prefixSum = 0L
    var count = 0L
    for (num in nums) {
        prefixSum += num
        // If (prefixSum - target) appeared k times earlier,
        // there are k valid subarrays ending at the current index.
        count += freq.getOrDefault(prefixSum - target, 0L)
        freq[prefixSum] = freq.getOrDefault(prefixSum, 0L) + 1L
    }
    return count
}
```

</details>

---

### Problem 1B (Core Medium/Hard — 35 min): Module Build Order & Parallel Stages

#### Prompt
You have `n` build modules labeled `0` to `n - 1` and a list of directed dependencies `dependencies: List<Pair<Int, Int>>`, where a pair `(module, prereq)` means `prereq` must finish building before `module` can start.

- **Part 1:** Return a `List<List<Int>>` representing the **minimum number of sequential build stages** needed to build all `n` modules, assuming unlimited parallelism within each stage. If no valid build order exists (circular dependency), return an empty list.
- **Part 2 (Follow-Up):** Now assume only **one** worker builds modules sequentially, and whenever multiple modules are ready, you must build the one with the **smallest module ID** first. Return the build order as an `IntArray` (or an empty `IntArray` if a cycle exists).

- **Example:** `n = 4`, `dependencies = [(1, 0), (2, 0), (3, 1), (3, 2)]`
  - Part 1 Output: `[[0], [1, 2], [3]]` (3 stages)
  - Part 2 Output: `[0, 1, 2, 3]`

#### Clarifying Questions to Ask Before Coding
1. Can `dependencies` contain **duplicate pairs** like `(1, 0)` twice? *(If yes, deduplicating with `HashSet` per node keeps `inDegree` accurate and avoids redundant edge traversal.)*
2. Can there be **self-dependencies** like `(0, 0)`? *(That is a cycle of length 1; return empty.)*
3. Can the graph be **disconnected** (independent modules with zero dependencies)? *(Yes—all zero-in-degree modules start in Stage 0.)*
4. Does the order of modules *within* a single parallel stage matter in Part 1? *(Usually no; clarify if sorted order per stage is expected.)*
5. Can `n` be `0`? *(Return empty list / empty `IntArray`.)*

#### Edge Cases Checklist
- `n = 0` → `emptyList()` / `intArrayOf()`.
- `dependencies` is empty (`n = 3`) → single stage `[[0, 1, 2]]`.
- Simple 2-node cycle `(0, 1), (1, 0)` → empty result.
- Self-loop `(0, 0)` → empty result.
- Disconnected graph where one component is valid and another has a cycle → `processed < n` catches it and returns empty.
- Duplicate dependency edges `(1, 0), (1, 0)` → must not double-increment `inDegree[1]`.

#### Complexity
- **Part 1 (Kahn's BFS by Level):**
  - **Time:** `O(V + E)` where `V = n` and `E = dependencies.size`.
  - **Space:** `O(V + E)` for adjacency sets, `inDegree`, queue, and output.
- **Part 2 (Lexicographically Smallest via Min-Heap):**
  - **Time:** `O((V + E) log V)` — each node is pushed/popped from `PriorityQueue` once (`V log V`), plus edge relaxations (`E`).
  - **Space:** `O(V + E)`.

<details>
<summary><strong>Reference Solution — Problem 1B (Kotlin 2.x)</strong></summary>

```kotlin
import java.util.PriorityQueue

// Part 1: Minimum sequential build stages (parallel batches)
fun buildStages(n: Int, dependencies: List<Pair<Int, Int>>): List<List<Int>> {
    if (n <= 0) return emptyList()

    // Deduplicate edges so duplicate dependencies never inflate inDegree.
    val adj = Array(n) { HashSet<Int>() }
    val inDegree = IntArray(n)

    for ((module, prereq) in dependencies) {
        if (adj[prereq].add(module)) {
            inDegree[module]++
        }
    }

    val queue = ArrayDeque<Int>()
    for (i in 0..<n) {
        if (inDegree[i] == 0) queue.addLast(i)
    }

    val stages = ArrayList<List<Int>>()
    var processed = 0

    while (queue.isNotEmpty()) {
        val stageSize = queue.size
        val stage = ArrayList<Int>(stageSize)
        repeat(stageSize) {
            val u = queue.removeFirst()
            stage.add(u)
            processed++
            for (v in adj[u]) {
                if (--inDegree[v] == 0) {
                    queue.addLast(v)
                }
            }
        }
        stages.add(stage)
    }

    return if (processed == n) stages else emptyList()
}

// Part 2: Single-worker lexicographically smallest build order
fun lexSmallestBuildOrder(n: Int, dependencies: List<Pair<Int, Int>>): IntArray {
    if (n <= 0) return intArrayOf()

    val adj = Array(n) { HashSet<Int>() }
    val inDegree = IntArray(n)

    for ((module, prereq) in dependencies) {
        if (adj[prereq].add(module)) {
            inDegree[module]++
        }
    }

    val minHeap = PriorityQueue<Int>()
    for (i in 0..<n) {
        if (inDegree[i] == 0) minHeap.add(i)
    }

    val order = IntArray(n)
    var idx = 0
    while (minHeap.isNotEmpty()) {
        val u = minHeap.poll()
        order[idx++] = u
        for (v in adj[u]) {
            if (--inDegree[v] == 0) {
                minHeap.add(v)
            }
        }
    }

    return if (idx == n) order else intArrayOf()
}
```

</details>

---

## 4. Mock Set 2 — Format B: Practical Multi-Part Stateful Coding (60 Minutes)

**Target Pacing:** 4 progressive stages × 15 minutes each. Do **not** read Stage 2 before implementing Stage 1—treat each stage as a live requirement change from the interviewer.

---

### Problem 2: In-Memory LRU Cache with TTL, Resize & Observability

#### Stage 1 (0–15 min): Core `O(1)` LRU Cache
Implement a generic `TtlLruCache<K, V>(initialCapacity: Int)` supporting:
- `get(key: K): V?` — returns the value and marks the entry as most recently used (MRU), or `null` if absent.
- `put(key: K, value: V)` — inserts or updates the key as MRU. If capacity is exceeded, evicts the least recently used (LRU) entry.
- **Constraint:** Implement the doubly linked list + `HashMap` yourself (do not delegate to `LinkedHashMap`), keeping `get` and `put` strictly `O(1)`.

#### Stage 2 (15–30 min): Per-Entry TTL & Deterministic Clock
Extend `TtlLruCache` to support optional time-to-live:
- Accept an injected clock `clock: () -> Long = System::currentTimeMillis` in the constructor so tests are deterministic.
- Update `put(key: K, value: V, ttlMs: Long? = null)`:
  - `ttlMs == null` means the entry does not expire.
  - `ttlMs <= 0L` means the entry is already expired (remove any existing entry for `key` and do not store).
- `get(key)` must lazily expire stale entries (`now >= expiresAt`), remove them, and return `null`.
- Add `evictExpired(): Int` to actively purge all expired entries and return how many were removed.

#### Stage 3 (30–45 min): Dynamic Resize & Cache Stats
Add operational controls:
- `resize(newCapacity: Int)`: updates the capacity (throw `IllegalArgumentException` if `< 0`). If the current size exceeds `newCapacity`, immediately evict LRU entries until `size <= newCapacity`.
- `stats(): CacheStats`: returns an immutable snapshot `data class CacheStats(val hits: Long, val misses: Long, val evictions: Long, val expirations: Long)`.

#### Stage 4 (45–60 min): Senior Follow-Up Discussion (Verbal — No Code Required)
Be ready to answer these cleanly in 2–3 minutes each:
1. **Capacity full + expired entries exist:** In `put`, evicting the LRU tail is `O(1)`, even if another entry is already expired. How would you prefer evicting expired entries first? *(Trade-off: maintain a min-heap / `TreeMap` of `(expiresAt, key)` for `O(log n)` expiration checks vs. periodic background sweep vs. `O(1)` lazy eviction.)*
2. **Thread safety on Android / JVM:** How would you make this safe under concurrent coroutines or threads? *(Single-threaded coroutine dispatcher, `Mutex`, or `ReentrantLock`—note that `get()` mutates the linked list so a `ReadWriteLock` does not allow concurrent reads.)*
3. **Memory-bounded mobile cache:** What changes if capacity is measured in **bytes** (e.g., bitmaps) instead of entry count? *(Track `weightOf(key, value)` and `currentBytes`, reject entries larger than `maxBytes`, and evict in a `while (currentBytes > maxBytes)` loop.)*

#### Clarifying Questions to Ask Before Coding
1. Is `initialCapacity == 0` allowed? *(If yes, `put` is a no-op and `get` always misses; negative capacity throws `IllegalArgumentException`.)*
2. On `get(key)` of an expired entry, should it count as both an **expiration** and a **miss**? *(Yes—the caller got a cache miss caused by expiration.)*
3. Does updating an existing key via `put` refresh its **recency** and **TTL**? *(Yes, both move to MRU and replace `expiresAt`.)*
4. Is expiration boundary inclusive (`now >= expiresAt`) or strict (`now > expiresAt`)? *(State your choice explicitly: `now >= expiresAt`.)*

#### Edge Cases Checklist
- `initialCapacity = 0` → `put` does not crash or store anything; `size == 0`.
- `initialCapacity < 0` or `resize(-1)` → throws `IllegalArgumentException`.
- Updating an existing key when cache is **at capacity** → must **not** trigger an eviction.
- `put(key, value, ttlMs = 0L)` on an **existing** key → removes the existing key rather than leaving stale data behind.
- `get(key)` at the exact timestamp `now == expiresAt` → treated as expired.
- `resize(0)` on a populated cache → evicts all entries cleanly and updates `evictions`.

#### Complexity
- `get(key)`: **`O(1)`** time.
- `put(key, value, ttlMs)`: **`O(1)`** time.
- `evictExpired()`: **`O(n)`** time, **`O(1)`** extra space.
- `resize(newCapacity)`: **`O(max(0, size - newCapacity))`** time.
- `stats()`: **`O(1)`** time.
- **Space:** **`O(capacity)`** for the `HashMap` and doubly linked list nodes.

<details>
<summary><strong>Reference Solution — Problem 2 (Kotlin 2.x, Stages 1–3 Complete)</strong></summary>

```kotlin
data class CacheStats(
    val hits: Long,
    val misses: Long,
    val evictions: Long,
    val expirations: Long
)

class TtlLruCache<K, V>(
    initialCapacity: Int,
    private val clock: () -> Long = System::currentTimeMillis
) {
    private class Node<K, V>(
        val key: K?,
        var value: V?,
        var expiresAt: Long? = null,
        var prev: Node<K, V>? = null,
        var next: Node<K, V>? = null
    )

    private var capacity: Int = initialCapacity.also {
        require(it >= 0) { "Capacity must be non-negative" }
    }

    private val map = HashMap<K, Node<K, V>>()
    private val head = Node<K, V>(null, null) // Most recently used sentinel
    private val tail = Node<K, V>(null, null) // Least recently used sentinel

    private var hits = 0L
    private var misses = 0L
    private var evictions = 0L
    private var expirations = 0L

    init {
        head.next = tail
        tail.prev = head
    }

    val size: Int
        get() = map.size

    fun get(key: K): V? {
        val node = map[key]
        if (node == null) {
            misses++
            return null
        }
        val now = clock()
        if (isExpired(node, now)) {
            removeNode(node)
            map.remove(key)
            expirations++
            misses++
            return null
        }
        moveToFront(node)
        hits++
        return node.value
    }

    fun put(key: K, value: V, ttlMs: Long? = null) {
        if (ttlMs != null && ttlMs <= 0L) {
            map.remove(key)?.let { removeNode(it) }
            return
        }
        if (capacity == 0) return

        val expiresAt = ttlMs?.let { clock() + it }
        val existing = map[key]
        if (existing != null) {
            existing.value = value
            existing.expiresAt = expiresAt
            moveToFront(existing)
            return
        }

        val node = Node(key, value, expiresAt)
        map[key] = node
        addToFront(node)

        if (map.size > capacity) {
            evictLru()
        }
    }

    fun evictExpired(): Int {
        val now = clock()
        var removed = 0
        var cur = tail.prev
        while (cur != null && cur !== head) {
            val prev = cur.prev
            if (isExpired(cur, now)) {
                removeNode(cur)
                cur.key?.let { map.remove(it) }
                expirations++
                removed++
            }
            cur = prev
        }
        return removed
    }

    fun resize(newCapacity: Int) {
        require(newCapacity >= 0) { "Capacity must be non-negative" }
        capacity = newCapacity
        while (map.size > capacity) {
            evictLru()
        }
    }

    fun stats(): CacheStats = CacheStats(
        hits = hits,
        misses = misses,
        evictions = evictions,
        expirations = expirations
    )

    private fun isExpired(node: Node<K, V>, now: Long): Boolean {
        val exp = node.expiresAt ?: return false
        return now >= exp
    }

    private fun addToFront(node: Node<K, V>) {
        val first = head.next!!
        node.prev = head
        node.next = first
        head.next = node
        first.prev = node
    }

    private fun removeNode(node: Node<K, V>) {
        val p = node.prev!!
        val n = node.next!!
        p.next = n
        n.prev = p
        node.prev = null
        node.next = null
    }

    private fun moveToFront(node: Node<K, V>) {
        removeNode(node)
        addToFront(node)
    }

    private fun evictLru() {
        val lru = tail.prev ?: return
        if (lru === head) return
        removeNode(lru)
        lru.key?.let { map.remove(it) }
        evictions++
    }
}
```

</details>

---

## 5. Post-Mock Self-Scorecard (Complete Within 5 Minutes)

Rate each dimension from **1 (Needs Work)** to **5 (Strong Senior Signal)**. Any score **<= 3** becomes your top focus for Day 14 Weakness Patching.

| Dimension | Score (1–5) | What a "5" Looks Like |
| :--- | :---: | :--- |
| **Clarification & Problem Framing** | ` / 5` | Caught traps early (negatives, duplicates, cycles, overflow, zero capacity) in under 5 minutes. |
| **Algorithm & Data Structure Design** | ` / 5` | Derived optimal approach cleanly from brute force; stated exact Time & Space complexity without hedging. |
| **Kotlin Code Quality** | ` / 5` | Modular helpers, idiomatic Kotlin 2.x, clean invariants, zero hacky patches when requirements evolved. |
| **Dry-Run & Bug Detection** | ` / 5` | Found and fixed any off-by-one or edge-case bug calmly during manual trace before finishing. |
| **Communication & Pacing** | ` / 5` | Spoke clearly in English throughout; hit time checkpoints without rushing the final 10 minutes. |
