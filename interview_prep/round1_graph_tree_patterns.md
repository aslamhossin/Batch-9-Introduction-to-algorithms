# Round 1 Programming Deep-Dive: Graph & Tree Patterns (Cycle Detection, Topo Sort, Dijkstra)

A focused, interview-ready Kotlin 2.x playbook covering **Trees & BST**, **Cycle Detection**, **Topological Sort**, and **Dijkstra's Algorithm**.

- **Roadmap alignment:** Expands **Week 1, Days 5–7** of [`round1_programming_prep.md`](./round1_programming_prep.md) and bridges your completed `Module 3` & `Module 5` codelabs directly into **`Module 6` (Cycle Detection & Kahn's BFS)** and **`Module 7` (Weighted Graphs & Dijkstra)**.
- **Scope:** Zero repetition of basic DFS/BFS or 2D grid traversal; focuses on the exact invariants, Kotlin 2.x pitfalls, and senior-level follow-ups tested in a 60-minute round.

---

## 1. Pattern Decision Matrix

Use this table in the first 3–5 minutes of an interview to lock onto the right algorithm and articulate *why* simpler alternatives fail.

| Problem Signal | Best Pattern | Core Data Structure | Time / Space | Watch Out For |
| :--- | :--- | :--- | :--- | :--- |
| Validate BST / path constraints from root | **Top-Down Tree DFS** | Recursion (`min..max` bounds) | `O(N)` / `O(H)` | `Int.MIN_VALUE` / `Int.MAX_VALUE` node values → use `Long` bounds |
| Diameter / Max Path Sum / LCA | **Bottom-Up Post-Order DFS** | Recursion returning subtree summary | `O(N)` / `O(H)` | Confusing value returned to parent vs. global answer turning at node |
| Level averages / right-side view / min depth | **Tree Level-Order BFS** | `ArrayDeque<TreeNode>` | `O(N)` / `O(W)` | Snapshot `val size = q.size` before looping over the level |
| Detect loop in **undirected** graph | **Undirected Cycle DFS/BFS** (or DSU) | `visited[]` + `parent` | `O(V + E)` / `O(V)` | Treating the immediate `parent` edge as a cycle |
| Detect loop in **directed** graph | **3-State DFS (`0/1/2`)** or **Kahn's BFS** | `state = IntArray(n)` or `inDegree[]` | `O(V + E)` / `O(V)` | Diamond DAG false positive if you only use `BooleanArray` |
| Task / build / course ordering | **Kahn's Topological Sort (BFS)** | `inDegree[]` + `ArrayDeque<Int>` | `O(V + E)` / `O(V + E)` | Disconnected cycles → always check `processed == n` |
| Shortest path, **unweighted** edges | **Standard BFS** | `ArrayDeque` + `dist[]` | `O(V + E)` / `O(V)` | Marking visited on *pop* instead of *enqueue* (causes duplicates) |
| Shortest path, weights in **`{0, 1}`** | **0-1 BFS** | `ArrayDeque` (`addFirst` / `addLast`) | `O(V + E)` / `O(V)` | Using Dijkstra when 0-1 BFS avoids the `log V` factor |
| Shortest path, **non-negative** weights | **Optimized Dijkstra** | Min-heap `PriorityQueue<State>` | `O((V + E) log V)` / `O(V + E)` | Missing stale-entry skip (`if (d > dist[u]) continue`), `Int` overflow |
| Shortest path, **negative** weights | **Bellman-Ford** *(conceptual)* | Edge list, `V - 1` relaxations | `O(V * E)` / `O(V)` | Dijkstra's greedy finalization breaks with negative edges |

---

## 2. Trees & BST Core Patterns (Day 5)

A tree is a connected, acyclic graph with `N` nodes and `N - 1` edges. Because there is a unique path between any two nodes, you **do not need a `visited[]` array** when traversing downward from the root.

### Three Mental Models That Solve 90% of Tree Questions

1. **Top-Down Propagation:** Pass constraints or accumulated state *down* to children (e.g., Validate BST, Path Sum).
2. **Bottom-Up Post-Order Aggregation:** Compute a summary from `left` and `right` subtrees first, update a global answer if the path *turns* at `node`, and return the single-branch value *up* to the parent (e.g., Diameter, Maximum Path Sum, Balanced Tree, LCA).
3. **Level-Order BFS:** Process the tree layer by layer using `ArrayDeque` and a fixed `levelSize = q.size` snapshot.

### Template 2A: Validate BST (Top-Down Bounds)
```kotlin
class TreeNode(
    var `val`: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null,
)

fun isValidBST(root: TreeNode?): Boolean {
    fun dfs(node: TreeNode?, lo: Long, hi: Long): Boolean {
        if (node == null) return true
        val v = node.`val`.toLong()
        if (v <= lo || v >= hi) return false
        return dfs(node.left, lo, v) && dfs(node.right, v, hi)
    }
    return dfs(root, Long.MIN_VALUE, Long.MAX_VALUE)
}
```
- **Why `Long` bounds?** A single node with `Int.MIN_VALUE` or `Int.MAX_VALUE` fails strict `<` / `>` checks if you use `Int` bounds.
- **Complexity:** `O(N)` time, `O(H)` space (`O(log N)` balanced, `O(N)` skewed).

### Template 2B: Lowest Common Ancestor (Bottom-Up Return Signal)
```kotlin
fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
    if (root == null || root == p || root == q) return root
    val left = lowestCommonAncestor(root.left, p, q)
    val right = lowestCommonAncestor(root.right, p, q)
    return when {
        left != null && right != null -> root // p and q split across subtrees
        left != null -> left
        else -> right
    }
}
```
- **Clarifying question to ask:** *"Are both `p` and `q` guaranteed to exist in the tree?"* If not, track a `foundCount` so you don't return a single found node as the LCA.

### Template 2C: Post-Order Global Best vs. Returned Gain (Max Path Sum / Diameter)
```kotlin
fun maxPathSum(root: TreeNode?): Int {
    var best = Int.MIN_VALUE

    // Returns the max downward path sum starting at `node` (usable by its parent).
    fun gain(node: TreeNode?): Int {
        if (node == null) return 0
        val leftGain = maxOf(0, gain(node.left))   // drop negative subtrees
        val rightGain = maxOf(0, gain(node.right))

        // Path that turns at `node` (cannot extend further up)
        best = maxOf(best, node.`val` + leftGain + rightGain)

        // Single branch returned to parent
        return node.`val` + maxOf(leftGain, rightGain)
    }

    gain(root)
    return best
}
```
- **Senior talking point:** Explicitly name the two quantities aloud: *"The return value is the best single-branch gain extending upward; `best` tracks the best arch path turning through the current node."*

---

## 3. Cycle Detection: Undirected vs. Directed (Module 6 / Day 6)

The single most common interview bug in graph cycle detection is using a plain `visited: BooleanArray` for both undirected and directed graphs.

### Why `visited[]` Alone Fails

1. **Undirected Graph (`u -- v`):**
   - Because each edge is stored in both `adj[u]` and `adj[v]`, moving from `0 -> 1` immediately sees `0` as an already-visited neighbor of `1`.
   - **Fix:** Pass `parent` (DFS) or store `parent[]` (BFS), and skip `if (v == parent) continue`. Any *other* already-visited neighbor proves a cycle.

2. **Directed Graph (`u -> v`) — The Diamond DAG Trap:**
   ```text
     0 ──▶ 1
     │     │
     ▼     ▼
     2 ──▶ 3
   ```
   - DFS visits `0 -> 1 -> 3` (marks `3` visited), backtracks, then visits `0 -> 2 -> 3`.
   - Node `3` is already visited, **but there is no cycle!**
   - **Fix:** Use **3 states** (`IntArray(n)`):
     - `0` = **Unvisited**
     - `1` = **Visiting** (currently on the active recursion path)
     - `2` = **Visited** (all descendants explored, guaranteed cycle-free)
   - A cycle exists **if and only if** you see an edge to a node in state `1` (a *back-edge*).

### Template 3A: Undirected Cycle Detection (DFS & BFS)
```kotlin
// DFS version — O(V + E) time, O(V) space
fun hasUndirectedCycleDfs(n: Int, adj: Array<ArrayList<Int>>): Boolean {
    val visited = BooleanArray(n)

    fun dfs(u: Int, parent: Int): Boolean {
        visited[u] = true
        for (v in adj[u]) {
            if (!visited[v]) {
                if (dfs(v, u)) return true
            } else if (v != parent) {
                return true // back-edge to a non-parent visited node
            }
        }
        return false
    }

    for (i in 0..<n) {
        if (!visited[i] && dfs(i, -1)) return true
    }
    return false
}

// BFS version — avoids recursion stack overflow on deep chains
fun hasUndirectedCycleBfs(n: Int, adj: Array<ArrayList<Int>>): Boolean {
    val visited = BooleanArray(n)
    val parent = IntArray(n) { -1 }
    val q = ArrayDeque<Int>()

    for (start in 0..<n) {
        if (visited[start]) continue
        visited[start] = true
        q.addLast(start)

        while (q.isNotEmpty()) {
            val u = q.removeFirst()
            for (v in adj[u]) {
                if (!visited[v]) {
                    visited[v] = true
                    parent[v] = u
                    q.addLast(v)
                } else if (v != parent[u]) {
                    return true
                }
            }
        }
    }
    return false
}
```

### Template 3B: Directed Cycle Detection (3-State DFS)
```kotlin
fun hasDirectedCycle(n: Int, adj: Array<ArrayList<Int>>): Boolean {
    // 0 = unvisited, 1 = visiting (on current path), 2 = done
    val state = IntArray(n)

    fun dfs(u: Int): Boolean {
        state[u] = 1
        for (v in adj[u]) {
            when (state[v]) {
                1 -> return true            // back-edge -> cycle!
                0 -> if (dfs(v)) return true
                // 2 -> already verified safe, skip
            }
        }
        state[u] = 2
        return false
    }

    for (i in 0..<n) {
        if (state[i] == 0 && dfs(i)) return true
    }
    return false
}
```
- **Edge cases to always check:** Disconnected components (outer `for (i in 0..<n)` loop), self-loops (`u -> u` hits `state[u] == 1` immediately), and `n == 0` / empty edge list.
- **When to mention DSU (Module 11):** For undirected graphs arriving as an edge stream (like *Redundant Connection*), Disjoint Set Union detects a cycle in `O(E * α(V))` without even building an adjacency list.

---

## 4. Topological Sort: Kahn's BFS & Extensions (Module 6 / Day 6)

A **Topological Sort** orders vertices of a Directed Acyclic Graph (DAG) so that for every directed edge `u -> v`, `u` appears before `v`.

### Why Kahn's Algorithm (BFS) Is the Interview Default
- **Iterative:** Zero risk of `StackOverflowError` on `10^5`-node dependency chains.
- **Built-in cycle detection:** Nodes stuck in a cycle (or downstream of a cycle) never reach `inDegree == 0`, so `processedCount < n` iff a cycle exists.
- **Easy follow-up extensions:**
  - *Minimum parallel batches / semesters?* Process level-by-level with `val waveSize = q.size`.
  - *Lexicographically smallest valid order?* Replace `ArrayDeque<Int>()` with a min-heap `PriorityQueue<Int>()`.
  - *Unique topological order?* Check `if (q.size > 1) return false` on every iteration.

### Template 4A: Kahn's Algorithm (Standard + Cycle Detection)
```kotlin
// Prerequisite pair [course, prereq] means prereq -> course
fun findOrder(numCourses: Int, prerequisites: Array<IntArray>): IntArray {
    val adj = Array(numCourses) { ArrayList<Int>() }
    val inDegree = IntArray(numCourses)

    for (edge in prerequisites) {
        val course = edge[0]
        val prereq = edge[1]
        adj[prereq].add(course)
        inDegree[course]++
    }

    val q = ArrayDeque<Int>()
    for (i in 0..<numCourses) {
        if (inDegree[i] == 0) q.addLast(i)
    }

    val order = IntArray(numCourses)
    var idx = 0
    while (q.isNotEmpty()) {
        val u = q.removeFirst()
        order[idx++] = u
        for (v in adj[u]) {
            if (--inDegree[v] == 0) {
                q.addLast(v)
            }
        }
    }

    return if (idx == numCourses) order else intArrayOf()
}
```

### Template 4B: Parallel Execution Waves (Common Senior Follow-Up)
*"Given tasks and dependencies, return the tasks grouped into parallel batches (or the minimum number of rounds to finish all tasks; return `-1` if impossible)."*

```kotlin
fun parallelExecutionWaves(n: Int, edges: Array<IntArray>): List<List<Int>> {
    val adj = Array(n) { ArrayList<Int>() }
    val inDegree = IntArray(n)
    for ((u, v) in edges) {
        adj[u].add(v)
        inDegree[v]++
    }

    val q = ArrayDeque<Int>()
    for (i in 0..<n) if (inDegree[i] == 0) q.addLast(i)

    val waves = mutableListOf<List<Int>>()
    var processed = 0

    while (q.isNotEmpty()) {
        val waveSize = q.size
        val wave = ArrayList<Int>(waveSize)
        repeat(waveSize) {
            val u = q.removeFirst()
            wave.add(u)
            processed++
            for (v in adj[u]) {
                if (--inDegree[v] == 0) q.addLast(v)
            }
        }
        waves.add(wave)
    }

    return if (processed == n) waves else emptyList() // empty => cycle
}
```
- **Edge-direction sanity check:** Always verify aloud whether `[a, b]` means `a -> b` or `b -> a` (LeetCode's *Course Schedule* uses `[course, prereq]`, i.e., `b -> a`!).
- **Duplicate edges:** If the input can contain duplicate edges `(u, v)`, deduplicate with a `HashSet` per node (or increment/decrement `inDegree` consistently—Kahn's actually still works if `adj[u]` has duplicates and `inDegree[v]` counts each duplicate, *unless* you deduplicate only one of the two).

---

## 5. Weighted Shortest Path: Optimized Dijkstra (Module 7 / Day 7)

### Why Standard BFS Fails on Weighted Graphs
Standard BFS expands nodes in order of **hop count**, not **total path weight**:
```text
0 ──(10)──▶ 2
│           ▲
└──(1)─▶ 1 ─┴─(2)
```
BFS reaches `2` in 1 hop (cost `10`) and finalizes it before discovering `0 -> 1 -> 2` (cost `3`).

**Dijkstra's Invariant:** Always expand the unfinalized node with the **smallest known distance** using a min-heap (`PriorityQueue`). Because all edge weights are **non-negative (`w >= 0`)**, no future path through another unvisited node can ever produce a smaller distance to the popped node.

### Three Critical Upgrades Over the Repo's C++ `Module 7/Dijkstra_optimized.cpp`
When writing Dijkstra in a Kotlin interview, make sure you include all three:
1. **Stale-entry guard:** `if (d > dist[u]) continue` right after `pq.poll()`. Because `PriorityQueue` has no `O(log V)` `decreaseKey`, we push a new `(v, nd)` entry on every relaxation; skipping stale entries keeps time at `O((V + E) log V)`.
2. **`Long` distance array + safe `INF`:** Use `LongArray(n) { Long.MAX_VALUE / 4 }` so `d + w` never overflows `Int.MAX_VALUE`.
3. **Overflow-safe comparator:** Use `PriorityQueue<State>(compareBy { it.dist })`, never `PriorityQueue { a, b -> (a.dist - b.dist).toInt() }`.

### Template 5A: Optimized Dijkstra with Optional Path Reconstruction
```kotlin
import java.util.PriorityQueue

data class Edge(val to: Int, val weight: Int)
data class State(val node: Int, val dist: Long)

const val INF = Long.MAX_VALUE / 4

fun dijkstra(
    n: Int,
    adj: Array<ArrayList<Edge>>,
    src: Int,
): Pair<LongArray, IntArray> {
    val dist = LongArray(n) { INF }
    val parent = IntArray(n) { -1 }
    val pq = PriorityQueue<State>(compareBy { it.dist })

    dist[src] = 0L
    pq.add(State(src, 0L))

    while (pq.isNotEmpty()) {
        val (u, d) = pq.poll()
        if (d > dist[u]) continue // crucial: skip stale heap entry

        for ((v, w) in adj[u]) {
            val nd = d + w
            if (nd < dist[v]) {
                dist[v] = nd
                parent[v] = u
                pq.add(State(v, nd))
            }
        }
    }
    return dist to parent
}

fun reconstructPath(target: Int, parent: IntArray): List<Int> {
    val path = ArrayList<Int>()
    var cur = target
    while (cur != -1) {
        path.add(cur)
        cur = parent[cur]
    }
    path.reverse()
    return path
}
```
- **Early exit optimization:** If you only need the shortest distance to a single `target` (not all nodes), you can `if (u == target) return d` immediately after the stale-entry check!

### Template 5B: State-Space Dijkstra (2D Grid / Constrained Path)
Many Senior interview Dijkstra problems are disguised as **2D grid** or **constrained-state** problems (e.g., *Path With Minimum Effort*, *Swim in Rising Water*, *Cheapest Flights Within K Stops*).

Key rule: **Your `dist` table must match the state dimensions.**
- On a 2D grid: `dist = Array(rows) { IntArray(cols) { Int.MAX_VALUE } }`, state is `(r, c, cost)`.
- With a resource limit `k` (e.g., stops/fuel/coupons): `dist = Array(n) { LongArray(k + 1) { INF } }`, state is `(node, usedK, cost)`.

```kotlin
// Example: Path With Minimum Effort (minimax Dijkstra on a 2D grid)
data class Cell(val r: Int, val c: Int, val effort: Int)

fun minimumEffortPath(heights: Array<IntArray>): Int {
    val rows = heights.size
    val cols = heights[0].size
    val dist = Array(rows) { IntArray(cols) { Int.MAX_VALUE } }
    val pq = PriorityQueue<Cell>(compareBy { it.effort })
    val dirs = arrayOf(intArrayOf(1, 0), intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(0, -1))

    dist[0][0] = 0
    pq.add(Cell(0, 0, 0))

    while (pq.isNotEmpty()) {
        val (r, c, eff) = pq.poll()
        if (eff > dist[r][c]) continue
        if (r == rows - 1 && c == cols - 1) return eff // early exit

        for ((dr, dc) in dirs) {
            val nr = r + dr
            val nc = c + dc
            if (nr !in 0..<rows || nc !in 0..<cols) continue
            val step = kotlin.math.abs(heights[nr][nc] - heights[r][c])
            val nextEff = maxOf(eff, step)
            if (nextEff < dist[nr][nc]) {
                dist[nr][nc] = nextEff
                pq.add(Cell(nr, nc, nextEff))
            }
        }
    }
    return 0
}
```

### Shortest-Path Trade-Off Cheat Sheet (For Verbal Follow-Ups)
- **All weights = 1** → Standard BFS (`O(V + E)`).
- **Weights ∈ `{0, 1}`** → 0-1 BFS with `ArrayDeque`: push `0`-cost neighbors to front (`addFirst`), `1`-cost neighbors to back (`addLast`) (`O(V + E)`).
- **Weights ≥ 0** → Optimized Dijkstra (`O((V + E) log V)`).
- **Negative weights possible** → Bellman-Ford (`O(V * E)`); run a `V`-th relaxation pass to detect negative cycles.
- **DAG with any weights (even negative)** → Topological Sort + DP relaxation in topo order (`O(V + E)` — faster than Dijkstra and handles negative weights!).

---

## 6. Focused Practice Drills (12 High-ROI Problems)

Work through these in order during Days 5–7. Time-box each Medium to **20–25 minutes** including dry-run verification.

| # | Problem | Pattern | Target | Must-Check Edge Case |
| :---: | :--- | :--- | :---: | :--- |
| 1 | **Validate Binary Search Tree** (LC 98) | Tree Top-Down DFS | 15m | Root value `Int.MIN_VALUE` or `Int.MAX_VALUE`; duplicate values are invalid |
| 2 | **Lowest Common Ancestor of a Binary Tree** (LC 236) | Tree Bottom-Up DFS | 20m | One target node is an ancestor of the other |
| 3 | **Diameter of Binary Tree** (LC 543) | Tree Post-Order Global Max | 15m | Longest path does not pass through the root; single-node tree (`0`) |
| 4 | **Binary Tree Maximum Path Sum** (LC 124) | Tree Post-Order Global Max | 25m | All node values are negative (init `best = Int.MIN_VALUE`, not `0`) |
| 5 | **Graph Valid Tree** (LC 261) | Undirected Cycle + Connectivity | 20m | Must verify *both* no cycle AND all `n` nodes connected (`edges.size == n - 1`) |
| 6 | **Course Schedule** (LC 207) | Directed Cycle (3-State DFS & Kahn's) | 20m | Disconnected components; self-dependency `[0, 0]` |
| 7 | **Course Schedule II** (LC 210) | Kahn's Topological Sort | 20m | Empty prerequisites (`0..<n` in any order); cycle returns `intArrayOf()` |
| 8 | **Parallel Courses** (LC 1136) / **Minimum Height Trees** (LC 310) | Level-by-Level Kahn's BFS | 25m | Cycle detection via `processed == n` |
| 9 | **Alien Dictionary** (LC 269) | Implicit Graph + Kahn's Topo Sort | 30m | Invalid prefix order (`"abc"` before `"ab"` → return `""`); isolated letters |
| 10 | **Network Delay Time** (LC 743) | Optimized Dijkstra | 20m | 1-indexed nodes (`1..n`); unreachable node returns `-1` |
| 11 | **Path With Minimum Effort** (LC 1631) | 2D Grid State-Space Dijkstra | 25m | `1x1` grid (`0` effort); early exit on reaching `(rows - 1, cols - 1)` |
| 12 | **Cheapest Flights Within K Stops** (LC 787) | Constrained Dijkstra / Bellman-Ford | 25m | Pruning by `(node, stops)`—a costlier path with fewer stops may win later |

### Bonus Senior Practical Coding Drill (Format B Bridge)
Practice this 45-minute multi-part prompt to connect these patterns to real-world Mobile Foundation coding rounds:
- **Part 1:** Build a `TaskScheduler` that registers tasks (`id`, `durationMs`, `dependencies`) and returns a valid sequential execution order, or throws/returns an error if a circular dependency exists *(Kahn's BFS)*.
- **Part 2:** Return the tasks grouped into **parallel batches** and compute the **minimum total wall-clock time** assuming unlimited concurrency *(Topo Sort + DAG longest-path DP: `finish[v] = max(finish[v], finish[u] + duration[v])`)*.
- **Part 3:** Add a method `getBlockingCycle(): List<String>` that returns the exact cycle of task IDs when validation fails *(3-State DFS with `parent` map)*.

---

## 7. Pre-Submission 5-Minute Mental Checklist

Before declaring your graph or tree solution finished in an interview, dry-run these 5 checks aloud:

1. **Boundaries & Indexing:** Are nodes `0`-indexed (`0..<n`) or `1`-indexed (`1..n`)? Did I handle `n == 0`, `n == 1`, `root == null`, and empty `edges`?
2. **Graph Direction & Disconnection:** Did I build `adj[u].add(v)` in the right direction? If the graph can be disconnected, does my outer loop iterate `0..<n`?
3. **Cycle & Visited Invariant:**
   - Undirected → ignored `parent`?
   - Directed → used 3-state `0/1/2` (not boolean)?
   - Kahn's → checked `processed == n`?
4. **Overflow & Comparator Safety:**
   - BST bounds / Dijkstra distances → `Long`?
   - `PriorityQueue` → `compareBy { it.dist }` (never subtraction)?
   - Dijkstra → included `if (d > dist[u]) continue`?
5. **Complexity Stated Clearly:** Always state both Time and Space in terms of `V` and `E` (or `N` and `H` for trees), including adjacency list storage and recursion stack depth.
