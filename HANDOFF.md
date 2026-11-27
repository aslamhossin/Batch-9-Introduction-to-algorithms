# Handoff Document — Interactive Kotlin DSA Codelab (Module 3 → Module 19)

This document captures the complete context, teaching methodology, verified files, and exact resumption point so another AI assistant can seamlessly continue guiding the learner through this repository.

---

## 1. Goal & Learner Context

- **Objective:** Walk through the C++ algorithms course repository (`Module 3` to `Module 19`) in an **interactive, hands-on Codelab format** using **idiomatic Kotlin**, tailored for **Android Software Engineer interview preparation**.
- **Instruction Language:** **Bengali (বাংলা)** — concise, encouraging, step-by-step explanations.
- **Code & Local Setup:**
  - Learner writes and runs code in **IntelliJ IDEA** (`Main.kt`, Kotlin `2.x` using modern syntax such as `0..<n`, `r in 0..<n`, `ArrayDeque`, `.not()`, `.coerceAtLeast()`).
  - Codelab files are stored under `src/moduleX/`.

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

### Active Spot: **Round 1 Interview Prep — Open GitHub Issues (`#9`–`#22`, `#24`–`#56`) + Interactive Kotlin Practice**
- **100% Course C++ → Kotlin Conversion Complete ✅:** All modules (`Module 1` through `Module 19`) have been converted to standalone, `kotlinc`-verified Kotlin 2.x implementations under `src/module1/` through `src/module19/`, and all legacy C++ `Module *` directories have been retired.
- **Completed LeetCode Track Issues:** `#23` (`LC #20 Valid Parentheses`), `#33` (`LC #739 Daily Temperatures`), plus Module 5 LeetCode solutions (`#1971`, `#200`, `#695`, `#463`, `#1254`, `#1905`).
- **What to do next:**
  - Continue solving open GitHub Issues (`#24`–`#56` LeetCode track and `#9`–`#22` Programming & Mobile System Design track) using the atomic tiny-example hand-trace first, then building clean Kotlin 2.x code step-by-step and closing each verified issue.

---

## 4. Completed Codelabs & Verified Kotlin Files

### Module 3 — Depth First Search (DFS) & 2D Grid Traversal *(100% Complete ✅)*
- [x] **Codelab 3.1: Graph DFS (`Module 3/dfs.cpp`)**
  - `src/module3/Lab3_1_DfsExample.kt`
  - `src/module3/Lab3_1_DfsWithInput.kt` *(Learner's solution)*
  - `src/module3/Lab3_1_Practice.kt`
- [x] **Codelab 3.2: 2D Grid DFS (`Module 3/dfs_on_2d_grid.cpp`)**
  - `src/module3/Lab3_2_GridDfsExample.kt`
  - `src/module3/Lab3_2_GridDfsWithInput.kt` *(Learner's solution)*
  - `src/module3/Lab3_2_Practice.kt`
- [x] **Codelab 3.3: 2D Grid BFS Shortest Path (`Module 3/bfs_on_2d_grid.cpp`)**
  - `src/module3/Lab3_3_GridBfsExample.kt`
  - `src/module3/Lab3_3_GridBfsWithInput.kt` *(Learner's solution)*
- [x] **Codelab 3.4: Number of Connected Components (`Module 3/number_of_components.cpp`)**
  - `src/module3/Lab3_4_ConnectedComponents.kt` *(Learner's solution)*

### Module 5 — LeetCode Graph & Grid Problem Solving *(100% Complete ✅)*
- [x] **Codelab 5.1: Find if Path Exists in Graph (`Module 5/find_if_path_exists_in_graph.cpp` — LeetCode #1971)**
  - `src/module5/Lab5_1_PathExistsExample.kt`
  - `src/module5/Lab5_1_PathExistsWithInput.kt` *(Learner's solution)*
- [x] **Codelab 5.2: Number of Islands & Max Area of Island (`Module 5/number_of_islands.cpp`, `Module 5/max_area_of_island.cpp` — LeetCode #200 & #695)**
  - `src/module5/Lab5_2a_NumberOfIslands.kt` *(Learner's solution)*
  - `src/module5/Lab5_2b_MaxAreaOfIsland.kt` *(Learner's solution)*
- [x] **Codelab 5.3a: Island Perimeter (`Module 5/island_perimeter.cpp` — LeetCode #463)**
  - `src/module5/Lab5_3a_IslandPerimeterExample.kt`
  - `src/module5/Lab5_3a_IslandPerimeterWithInput.kt` *(Learner's solution)*
- [x] **Codelab 5.3b: Number of Closed Islands (`Module 5/number_of_closed_islands_dfs.cpp`, `Module 5/number_of_closed_islands_bfs.cpp` — LeetCode #1254)**
  - `src/module5/Lab5_3b_ClosedIslandsExample.kt` *(Reference verified ✅)*
  - `src/module5/Lab5_3b_ClosedIslandsWithInput.kt` *(Learner's solution — LeetCode #1254 Accepted ✅)*
- [x] **Codelab 5.4: Count Sub Islands (`Module 5/count_sub_islands.cpp` — LeetCode #1905)**
  - `src/module5/Lab5_4_CountSubIslandsExample.kt` *(Reference verified ✅)*
  - `src/module5/Lab5_4_CountSubIslandsWithInput.kt` *(Learner's solution — LeetCode #1905 Accepted ✅)*

---

## 5. Full Course Kotlin Conversion Inventory (`Module 1` → `Module 19` — 100% Verified ✅)

- [x] **Module 1 — Graph Representations** (`src/module1/`, 5 files: Adjacency Matrix, Adjacency List, Edge List)
- [x] **Module 2 — BFS Traversal & Shortest Path** (`src/module2/`, 4 files: BFS, Reachability, Distance, Path Printing)
- [x] **Module 3 — DFS & 2D Grid Traversal** (`src/module3/`, 9 files)
- [x] **Module 5 — LeetCode Graph & Grid Problems** (`src/module5/`, 10 files)
- [x] **Module 6 — Cycle Detection** (`src/module6/`, 4 files: Undirected BFS/DFS, Directed DFS `pathVisited` & Kahn's BFS)
- [x] **Module 7 — Weighted Graphs & Dijkstra's Algorithm** (`src/module7/`, 4 files: Weighted Adj List, PriorityQueue Pairs, Naive & Optimized Dijkstra)
- [x] **Module 9 — Bellman-Ford Algorithm** (`src/module9/`, 2 files: Bellman-Ford, Negative Weight Cycle Detection)
- [x] **Module 10 — Floyd-Warshall Algorithm** (`src/module10/`, 3 files: Floyd-Warshall, Negative Cycle, Shortest Routes II)
- [x] **Module 11 — Disjoint Set Union (DSU)** (`src/module11/`, 5 files: Find Loop/Recursion/Path Compression, Union by Size, Cycle Detection)
- [x] **Module 13 — Mixed Graph Problem Solving** (`src/module13/`, 4 files: C_Dijkstra, Road Construction, Roads Not Only in Berland, Sundorban)
- [x] **Module 14 — Recursion & Basic DP** (`src/module14/`, 6 files: Recursion, Factorial, Fibonacci Naive/Memo/Bottom-Up, Climbing Stairs)
- [x] **Module 15 — 0-1 Knapsack** (`src/module15/`, 3 files: Recursive, DP Memoization, U_Knapsack)
- [x] **Module 17 — Subset Sum & Unbounded Knapsack** (`src/module17/`, 6 files: Subset Sum, Count Subset Sum, Equal Sum Partition, Unbounded Knapsack, Coin Change II)
- [x] **Module 18 — Divide & Conquer (Merge Sort)** (`src/module18/`, 3 files: Divide, Conquer, Merge Sort)
- [x] **Module 19 — LCS & Kruskal's MST** (`src/module19/`, 3 files: LCS Length, Printing LCS, Kruskal's MST)
- [x] **LeetCode Interview Track** (`src/leetcode/`, verified solutions pushed as issues are closed)

---

## 6. Quick Starter Prompt for Your Next AI Assistant

Copy and paste this prompt into your new AI tool:

> Read `HANDOFF.md` in the root of this repository carefully. Follow all Bengali teaching, atomic 2×2 hand-trace first, full-runnable `Scanner` Kotlin code, visual clarity, and micro-step rules defined there. Check our active point in Section 3 (open GitHub Issues `#9`–`#22`, `#24`–`#56`) and let's continue!
