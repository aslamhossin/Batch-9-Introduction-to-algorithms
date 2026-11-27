import java.util.Scanner

/**
 * Module 3 — Codelab 3.2: 2D Grid DFS (Kotlin)
 * রেফারেন্স: Module 3/dfs_on_2d_grid.cpp এর সম্পূর্ণ Kotlin রূপান্তর।
 *
 * নমুনা Input:
 * 3 4
 * ....
 * .#..
 * ..#.
 * 0 0
 *
 * (এখানে n = 3 সারি, m = 4 কলাম, এবং শুরু হবে (0, 0) ঘর থেকে;
 *  '.' মানে খালি ঘর আর '#' মানে দেয়াল।)
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt() // সারি সংখ্যা (Row)
    val m = sc.nextInt() // কলাম সংখ্যা (Column)

    // ধাপ ১: 2D Grid ইনপুট নেওয়া
    val grid = Array(n) { CharArray(m) }
    for (i in 0 until n) {
        grid[i] = sc.next().toCharArray()
    }

    // শুরুর ঘরের অবস্থান (Start Row, Start Column)
    val si = sc.nextInt()
    val sj = sc.nextInt()

    // ধাপ ২: 2D Visited Array এবং ৪টি দিক (উপরে, নিচে, বামে, ডানে)
    val vis = Array(n) { BooleanArray(m) }
    val dr = intArrayOf(-1, 1, 0, 0) // সারির পরিবর্তন: উপরে(-1), নিচে(+1), বামে(0), ডানে(0)
    val dc = intArrayOf(0, 0, -1, 1) // কলামের পরিবর্তন: উপরে(0), নিচে(0), বামে(-1), ডানে(+1)

    // গ্রিডের সীমানার ভেতরে আছে কি না চেক করার ফাংশন
    fun valid(r: Int, c: Int): Boolean {
        return r in 0 until n && c in 0 until m
    }

    // ধাপ ৩: 2D Grid DFS ফাংশন
    fun dfs(r: Int, c: Int) {
        println("$r $c")
        vis[r][c] = true

        // ৪ দিকে যাওয়ার চেষ্টা (উপরে, নিচে, বামে, ডানে)
        for (d in 0 until 4) {
            val nr = r + dr[d]
            val nc = c + dc[d]

            if (valid(nr, nc) && !vis[nr][nc] && grid[nr][nc] != '#') {
                dfs(nr, nc)
            }
        }
    }

    dfs(si, sj)
}
