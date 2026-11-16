import java.util.Scanner

/**
 * Module 5 — Codelab 5.3b: Number of Closed Islands (LeetCode #1254)
 * রেফারেন্স: Module 5/number_of_closed_islands_dfs.cpp এর সম্পূর্ণ Kotlin রূপান্তর।
 *
 * বিশেষ নোট: এই প্রবলেমে '0' = মাটি (Land) এবং '1' = পানি (Water)!
 *
 * নমুনা Input:
 * 4 5
 * 11111
 * 10010
 * 11110
 * 11111
 *
 * আউটপুট:
 * 1
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val m = sc.nextInt()

    val grid = Array(n) { CharArray(m) }
    for (i in 0..<n) {
        grid[i] = sc.next().toCharArray()
    }

    fun valid(r: Int, c: Int): Boolean {
        return r in 0..<n && c in 0..<m
    }

    val visited = Array(n) { BooleanArray(m) }
    val dr = intArrayOf(-1, 1, 0, 0)
    val dc = intArrayOf(0, 0, -1, 1)

    // একটি দ্বীপ চারপাশে পানি দিয়ে বন্দী আছে কি না তা ট্র্যাক করার ফ্ল্যাগ
    var isClosed = true

    fun dfs(r: Int, c: Int) {
        visited[r][c] = true

        for (d in dr.indices) {
            val nr = r + dr[d]
            val nc = c + dc[d]

            if (!valid(nr, nc)) {
                // ম্যাপের বাইরে চলে গেছে মানে এই দ্বীপটি বর্ডার ছুঁয়েছে -> বন্দী নয়!
                isClosed = false
            } else if (!visited[nr][nc] && grid[nr][nc] == '0') {
                // নতুন মাটি ('0') পেলে পুরো দ্বীপটা মার্ক করতে DFS চালিয়ে যাব
                dfs(nr, nc)
            }
        }
    }

    var closedIslands = 0
    for (i in 0..<n) {
        for (j in 0..<m) {
            if (!visited[i][j] && grid[i][j] == '0') {
                isClosed = true // নতুন দ্বীপ শুরু করার আগে ধরে নিই এটি বন্দী (true)
                dfs(i, j)
                if (isClosed) {
                    closedIslands++
                }
            }
        }
    }

    println(closedIslands)
}
