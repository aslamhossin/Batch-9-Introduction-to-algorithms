/**
 * Module 3 — Codelab 3.2 Practice Problem (নিজে সমাধান করুন!)
 *
 * সমস্যা (2D Grid Reachable Rooms Count):
 * আপনাকে `n x m` সাইজের একটি 2D ম্যাপ (`grid`) দেওয়া আছে:
 *   - '.' মানে খালি ঘর (যাওয়া যাবে)
 *   - '#' মানে দেয়াল (যাওয়া যাবে না)
 *
 * আপনি `(startRow, startCol)` ঘর থেকে যাত্রা শুরু করলে উপরে, নিচে, বামে ও ডানে হেঁটে
 * শুরুর ঘরসহ মোট **কতগুলো** খালি ঘরে ('.') পৌঁছাতে পারবেন — সেই সংখ্যাটি রিটার্ন করুন।
 */

fun countReachableCells(
    grid: Array<CharArray>,
    startRow: Int,
    startCol: Int
): Int {
    val n = grid.size
    val m = grid[0].size
    val vis = Array(n) { BooleanArray(m) }

    // ৪টি দিক: উপরে, নিচে, বামে, ডানে
    val dr = intArrayOf(-1, 1, 0, 0)
    val dc = intArrayOf(0, 0, -1, 1)

    fun valid(r: Int, c: Int): Boolean {
        return r in 0 until n && c in 0 until m
    }

    if (grid[startRow][startCol] == '#') return 0

    var count = 0

    fun dfs(r: Int, c: Int) {
        vis[r][c] = true
        count++

        for (d in 0..<4) {
            val nr = r + dr[d]
            val nc = c + dc[d]
            if (valid(nr, nc) && !vis[nr][nc] && grid[nr][nc] != '#') {
                dfs(nr, nc)
            }
        }
    }

    dfs(startRow, startCol)
    return count
}

fun main() {
    val grid1 = arrayOf(
        "..#".toCharArray(),
        ".#.".toCharArray(),
        "...".toCharArray()
    )
    val test1 = countReachableCells(grid1, startRow = 0, startCol = 0)
    println("Test 1 -> আপনার উত্তর: $test1 | সঠিক উত্তর: 7 | ${if (test1 == 7) "PASS ✅" else "TRY AGAIN ❌"}")

    val grid2 = arrayOf(
        ".#.".toCharArray(),
        "###".toCharArray(),
        "...".toCharArray()
    )
    val test2 = countReachableCells(grid2, startRow = 0, startCol = 0)
    println("Test 2 -> আপনার উত্তর: $test2 | সঠিক উত্তর: 1 | ${if (test2 == 1) "PASS ✅" else "TRY AGAIN ❌"}")

    val grid3 = arrayOf(
        "..".toCharArray(),
        "..".toCharArray()
    )
    val test3 = countReachableCells(grid3, startRow = 0, startCol = 0)
    println("Test 3 -> আপনার উত্তর: $test3 | সঠিক উত্তর: 4 | ${if (test3 == 4) "PASS ✅" else "TRY AGAIN ❌"}")
}
