import java.util.Scanner

/**
 * Module 3 — Codelab 3.2 (Input থেকে 2D Grid + DFS Count)
 *
 * নমুনা Input:
 * 3 3
 * ..#
 * ###
 * ...
 * 0 0
 *
 * আউটপুট:
 * 0, 0
 * 0, 1
 * 2
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val m = sc.nextInt()

    val grid = Array(n) { CharArray(m) }
    for (i in 0..<n) {
        grid[i] = sc.next().toCharArray()
    }

    val si = sc.nextInt()
    val sj = sc.nextInt()

    fun valid(r: Int, c: Int): Boolean {
        return r in 0..<n && c in 0..<m
    }

    val visited = Array(n) { BooleanArray(m) }
    val dr = intArrayOf(-1, 1, 0, 0)
    val dc = intArrayOf(0, 0, -1, 1)

    var count = 0
    fun dfs(r: Int, c: Int) {
        visited[r][c] = true
        count++
        println("$r, $c")

        for (d in 0..<4) {
            val nr = r + dr[d]
            val nc = c + dc[d]

            if (valid(nr, nc) && !visited[nr][nc] && grid[nr][nc] != '#') {
                dfs(nr, nc)
            }
        }
    }

    if (valid(si, sj) && grid[si][sj] != '#') {
        dfs(si, sj)
    }
    println(count)
}
