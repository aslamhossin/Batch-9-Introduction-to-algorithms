import java.util.Scanner

/**
 * Module 5 — Codelab 5.2b: Max Area of Island (LeetCode #695)
 * রেফারেন্স: Module 5/max_area_of_island.cpp এর সম্পূর্ণ Kotlin রূপান্তর।
 *
 * নমুনা Input:
 * 3 4
 * 1100
 * 1001
 * 0011
 *
 * আউটপুট:
 * 3
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

    var currentIslandSize = 0
    fun dfs(r: Int, c: Int) {
        currentIslandSize++
        visited[r][c] = true

        for (d in 0..<4) {
            val nr = r + dr[d]
            val nc = c + dc[d]

            if (valid(nr, nc) && !visited[nr][nc] && grid[nr][nc] != '0') {
                dfs(nr, nc)
            }
        }
    }

    var maxIslandSize = 0
    for (i in 0..<n) {
        for (j in 0..<m) {
            if (grid[i][j] == '1' && !visited[i][j]) {
                dfs(i, j)
                maxIslandSize = maxIslandSize.coerceAtLeast(currentIslandSize)
                currentIslandSize = 0
            }
        }
    }

    println(maxIslandSize)
}
