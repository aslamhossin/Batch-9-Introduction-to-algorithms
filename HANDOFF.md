# Handoff Document — Interactive Kotlin DSA Codelab (Module 3 → Module 19)

This document captures the complete context, teaching methodology, verified files, and exact resumption point so another AI assistant can seamlessly continue guiding the learner through this repository.

---

## 1. Goal & Learner Context

- **Objective:** Walk through the C++ algorithms course repository (`Module 3` to `Module 19`) in an **interactive, hands-on Codelab format** using **idiomatic Kotlin**, tailored for **Android Software Engineer interview preparation**.
- **Instruction Language:** **Bengali (বাংলা)** — concise, encouraging, step-by-step explanations.
- **Code & Local Setup:**
  - Learner writes and runs code in **IntelliJ IDEA** (`Main.kt`, Kotlin `2.x` using modern syntax such as `0..<n`, `r in 0..<n`, `ArrayDeque`, `.not()`, `.coerceAtLeast()`).
  - Codelab files are stored under `kotlin_codelabs/moduleX/`.

---

## 2. Mandatory Pedagogical & Formatting Rules

Every AI assistant continuing this repository **MUST** follow these rules:

1. **Problem + Why Data Structure + Steps First (Before Any Code):**
   - **(a) Problem Statement:** Explain the exact problem in simple Bengali with 1–2 small concrete sample Inputs & Expected Outputs.
   - **(b) Why This Data Structure / Algorithm:** Explain *why* a simpler or previous approach fails and why we choose the specific structure:
     - `1D` (`BooleanArray(n)`, `Array(n) { ArrayList<Int>() }`) when each node's address is **1 integer** (`0..n-1`).
     - `2D` (`Array(n) { BooleanArray(m) }`, `Array(n) { CharArray(m) }`) when each cell's address is **2 integers** `(row, col)`.
     - `DFS` for reachability / component counting / area / perimeter vs `BFS` (`ArrayDeque` + `dist[][]`) for unweighted shortest path.
   - **(c) Clear Numbered Steps:** 3–4 short high-level steps in Bengali.

2. **Unambiguous Visuals:**
   - Never mix bare numbers inside grid visuals (e.g., never confuse `0` step distance with `'0'` water or `.` empty floor).
   - Always label cells explicitly with coordinates (`0, 1`) and Bengali tags (`মাটি (1)`, `পানি (0)`, `খালি (.)`, `দেয়াল (#)`, `0 কদম`, `1 কদম`).

3. **Always Provide 100% Full Runnable Kotlin Code + Raw Console Input:**
   - Every example must be a complete runnable file (`import java.util.Scanner` + `fun main() { ... }`) reading from `Scanner(System.`in`)`—never partial LeetCode class snippets alone.
   - Always include a copy-pasteable **Console Input** block and **Expected Output** block so the learner can test directly in IntelliJ IDEA's Run Console.
   - **2D Grid Input Convention:** Always read unspaced grid rows using:
     ```kotlin
     val grid = Array(n) { CharArray(m) }
     for (i in 0..<n) {
         grid[i] = sc.next().toCharArray()
     }
     ```
     *(Do NOT use `sc.next()[0]` or custom stateful token buffers.)*

4. **Micro-Step Hands-On Pacing:**
   - Teach **one concept/problem at a time**.
   - **Atomic 2×2 Hand-Trace First (Zero Overload):** Always start with 1 tiny visual/story example (e.g. 2×2 grid or 3-node graph) traced together interactively in super short Bengali + English chunks BEFORE showing full code; then build the Kotlin code in 2–3 tiny Lego blocks.
   - Invite the learner to write the Kotlin code themselves in IntelliJ `Main.kt` and paste their code for review/debugging (or type `next` when ready to advance).

5. **Git & Privacy Rules:**
   - Verify every `.kt` file before committing:
     ```bash
     kotlinc <path>.kt -include-runtime -d /tmp/check.jar && java -jar /tmp/check.jar && rm -f /tmp/check.jar
     ```
   - Stage, commit to `main`, and `git push origin main` after verifying new codelab files.
   - **Privacy Rule:** Never write any target company or employer names in code, comments, markdown files, or Git commit messages.
   - Never commit `.jar`, `.class`, `.idea/`, or temporary files.

---

## 3. Exact Current State (Where to Resume Right Now)

### Active Spot: **Module 6 — Codelab 6.1: Undirected Graph Cycle Detection (BFS & DFS)** (`Module 6/detect_cycle_in_undirected_graph_using_bfs.cpp`, `Module 6/detect_cycle_in_undirected_graph_using_dfs.cpp`)
- **Module 5 is 100% Complete ✅ (6/6)** — including **Codelab 5.3b (Number of Closed Islands — LeetCode #1254 Accepted ✅)** (`kotlin_codelabs/module5/Lab5_3b_ClosedIslandsWithInput.kt`) and **Codelab 5.4 (Count Sub Islands — LeetCode #1905 Accepted ✅)** (`kotlin_codelabs/module5/Lab5_4_CountSubIslandsWithInput.kt`).
- **What to do next:**
  - When the learner says `next` / ready, introduce **Codelab 6.1** using the atomic tiny-example hand-trace first (why `parent[]` is required in undirected cycle detection), then build Kotlin code step-by-step following all rules in Section 2.

---

## 4. Completed Codelabs & Verified Kotlin Files

### Module 3 — Depth First Search (DFS) & 2D Grid Traversal *(100% Complete ✅)*
- [x] **Codelab 3.1: Graph DFS (`Module 3/dfs.cpp`)**
  - `kotlin_codelabs/module3/Lab3_1_DfsExample.kt`
  - `kotlin_codelabs/module3/Lab3_1_DfsWithInput.kt` *(Learner's solution)*
  - `kotlin_codelabs/module3/Lab3_1_Practice.kt`
- [x] **Codelab 3.2: 2D Grid DFS (`Module 3/dfs_on_2d_grid.cpp`)**
  - `kotlin_codelabs/module3/Lab3_2_GridDfsExample.kt`
  - `kotlin_codelabs/module3/Lab3_2_GridDfsWithInput.kt` *(Learner's solution)*
  - `kotlin_codelabs/module3/Lab3_2_Practice.kt`
- [x] **Codelab 3.3: 2D Grid BFS Shortest Path (`Module 3/bfs_on_2d_grid.cpp`)**
  - `kotlin_codelabs/module3/Lab3_3_GridBfsExample.kt`
  - `kotlin_codelabs/module3/Lab3_3_GridBfsWithInput.kt` *(Learner's solution)*
- [x] **Codelab 3.4: Number of Connected Components (`Module 3/number_of_components.cpp`)**
  - `kotlin_codelabs/module3/Lab3_4_ConnectedComponents.kt` *(Learner's solution)*

### Module 5 — LeetCode Graph & Grid Problem Solving *(100% Complete ✅)*
- [x] **Codelab 5.1: Find if Path Exists in Graph (`Module 5/find_if_path_exists_in_graph.cpp` — LeetCode #1971)**
  - `kotlin_codelabs/module5/Lab5_1_PathExistsExample.kt`
  - `kotlin_codelabs/module5/Lab5_1_PathExistsWithInput.kt` *(Learner's solution)*
- [x] **Codelab 5.2: Number of Islands & Max Area of Island (`Module 5/number_of_islands.cpp`, `Module 5/max_area_of_island.cpp` — LeetCode #200 & #695)**
  - `kotlin_codelabs/module5/Lab5_2a_NumberOfIslands.kt` *(Learner's solution)*
  - `kotlin_codelabs/module5/Lab5_2b_MaxAreaOfIsland.kt` *(Learner's solution)*
- [x] **Codelab 5.3a: Island Perimeter (`Module 5/island_perimeter.cpp` — LeetCode #463)**
  - `kotlin_codelabs/module5/Lab5_3a_IslandPerimeterExample.kt`
  - `kotlin_codelabs/module5/Lab5_3a_IslandPerimeterWithInput.kt` *(Learner's solution)*
- [x] **Codelab 5.3b: Number of Closed Islands (`Module 5/number_of_closed_islands_dfs.cpp`, `Module 5/number_of_closed_islands_bfs.cpp` — LeetCode #1254)**
  - `kotlin_codelabs/module5/Lab5_3b_ClosedIslandsExample.kt` *(Reference verified ✅)*
  - `kotlin_codelabs/module5/Lab5_3b_ClosedIslandsWithInput.kt` *(Learner's solution — LeetCode #1254 Accepted ✅)*
- [x] **Codelab 5.4: Count Sub Islands (`Module 5/count_sub_islands.cpp` — LeetCode #1905)**
  - `kotlin_codelabs/module5/Lab5_4_CountSubIslandsExample.kt` *(Reference verified ✅)*
  - `kotlin_codelabs/module5/Lab5_4_CountSubIslandsWithInput.kt` *(Learner's solution — LeetCode #1905 Accepted ✅)*

---

## 5. Remaining Course Roadmap (Module 6 → Module 19)

- **Module 6 — Cycle Detection:**
  - [ ] **Codelab 6.1:** Undirected Graph Cycle Detection using BFS & DFS (`Module 6/detect_cycle_in_undirected_graph_using_bfs.cpp`, `detect_cycle_in_undirected_graph_using_dfs.cpp` — why `parent[]` is required)
  - [ ] **Codelab 6.2:** Directed Graph Cycle Detection using DFS & BFS (`Module 6/detect_cycle_in_directed_graph_using_dfs.cpp`, `detect_cycle_in_directed_graph_using_bfs.cpp` — why `pathVis[]` / recursion-stack tracking is required)

- **Module 7 — Weighted Graphs & Dijkstra's Algorithm:**
  - [ ] **Codelab 7.1:** Weighted Adjacency List & Kotlin `PriorityQueue` (`Module 7/adj_list_for_weighted_graph.cpp`, `priority_queue_of_pairs.cpp`)
  - [ ] **Codelab 7.2:** Naive vs Optimized Dijkstra (`Module 7/Dijkstra_naive.cpp`, `Dijkstra_optimized.cpp` — why normal BFS Queue fails when edges have unequal weights)

- **Module 9 — Bellman-Ford Algorithm:**
  - [ ] **Codelab 9.1:** Bellman-Ford & Negative Weight Cycle Detection (`Module 9/*.cpp`)

- **Module 10 — Floyd-Warshall Algorithm:**
  - [ ] **Codelab 10.1:** All-Pairs Shortest Path (`Module 10/*.cpp`)

- **Module 11 & Module 13 — Disjoint Set Union (DSU) & Mixed Graph Problems:**
  - [ ] **Codelab 11.1:** DSU `find` (Path Compression), `union` by Size/Rank & Cycle Detection (`Module 11/*.cpp`)
  - [ ] **Codelab 13.1:** Mixed Graph Problem Solving (`Module 13/C_Dijkstra.cpp`, `road_constructions.cpp`, `D_Roads_not_only_in_Berland.cpp`, `sundorban.cpp`)

- **Module 14, Module 15 & Module 17 — Dynamic Programming (DP):**
  - [ ] **Codelab 14.1:** Recursion, Memoization & Bottom-Up Tabulation (`Module 14/*.cpp`)
  - [ ] **Codelab 15.1:** 0-1 Knapsack (`Module 15/*.cpp`)
  - [ ] **Codelab 17.1:** Subset Sum, Count of Subset Sum, Equal Sum Partition, Unbounded Knapsack & Coin Change II (`Module 17/*.cpp`)

- **Module 18 & Module 19 — Divide & Conquer, LCS & Minimum Spanning Tree (MST):**
  - [ ] **Codelab 18.1:** Merge Sort (`Module 18/*.cpp`)
  - [ ] **Codelab 19.1:** Longest Common Subsequence (`Module 19/lcs.cpp`, `printing_lcs.cpp` — practical connection to Android `DiffUtil`) & Kruskal's MST (`Module 19/mst.cpp`)

---

## 6. Quick Starter Prompt for Your Next AI Assistant

Copy and paste this prompt into your new AI tool:

> Read `HANDOFF.md` in the root of this repository carefully. Follow all Bengali teaching, full-runnable `Scanner` Kotlin code, visual clarity, and micro-step rules defined there. Check our active point in Section 3 (`Module 6 — Codelab 6.1: Undirected Graph Cycle Detection`) and let's continue!
