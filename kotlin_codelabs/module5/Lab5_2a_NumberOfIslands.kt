import java.util.Scanner

/**
 * Module 5 — Codelab 5.2a: Number of Islands (LeetCode #200)
 * রেফারেন্স: Module 5/number_of_islands.cpp এর সম্পূর্ণ Kotlin রূপান্তর।
 *
 * নমুনা Input:
 * 3 3
 * 101
 * 000
 * 101
 *
 * আউটপুট:
 * 4
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

    fun dfs(r: Int, c: Int) {
        visited[r][c] = true

        for (d in 0..<4) {
            val nr = r + dr[d]
            val nc = c + dc[d]

            if (valid(nr, nc) && !visited[nr][nc] && grid[nr][nc] != '0') {
                dfs(nr, nc)
            }
        }
    }

    var islands = 0
    for (i in 0..<n) {
        for (j in 0..<m) {
            if (grid[i][j] == '1' && !visited[i][j]) {
                islands++
                dfs(i, j)
            }
        }
    }

    println(islands)
}
