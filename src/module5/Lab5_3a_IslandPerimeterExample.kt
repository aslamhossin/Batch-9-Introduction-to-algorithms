import java.util.Scanner

/**
 * Module 5 — Codelab 5.3a: Island Perimeter (LeetCode #463)
 * রেফারেন্স: Module 5/island_perimeter.cpp এর সম্পূর্ণ Kotlin রূপান্তর।
 *
 * নমুনা Input:
 * 4 4
 * 0100
 * 1110
 * 0100
 * 1100
 *
 * আউটপুট:
 * 16
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

    var perimeter = 0

    fun dfs(r: Int, c: Int) {
        visited[r][c] = true

        for (d in 0..<4) {
            val nr = r + dr[d]
            val nc = c + dc[d]

            if (!valid(nr, nc)) {
                // কেস ১: ম্যাপের বাইরে চলে গেছে -> এখানে ১টি সীমানা/বেড়া আছে
                perimeter++
            } else if (grid[nr][nc] == '0') {
                // কেস ২: পাশের ঘরটি পানি -> এখানেও ১টি সীমানা/বেড়া আছে
                perimeter++
            } else if (!visited[nr][nc] && grid[nr][nc] == '1') {
                // কেস ৩: পাশের ঘরটি নতুন মাটি -> বেড়া নেই, ওই মাটিতে DFS চালাও
                dfs(nr, nc)
            }
        }
    }

    for (i in 0..<n) {
        for (j in 0..<m) {
            if (!visited[i][j] && grid[i][j] == '1') {
                dfs(i, j)
            }
        }
    }

    println(perimeter)
}
