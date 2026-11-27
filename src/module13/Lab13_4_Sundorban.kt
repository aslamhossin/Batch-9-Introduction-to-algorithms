import java.util.Scanner

/**
 * Module 13 — Sundorban (2D Grid BFS Shortest Path from 'S' to 'E', avoiding 'T')
 *
 * Sample Input:
 * 5
 * S.T..
 * .T...
 * ...T.
 * .T.E.
 * .....
 *
 * Expected Output:
 * 6
 */
fun main() {
    val sc = Scanner(System.`in`)
    val dirs = arrayOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    while (sc.hasNextInt()) {
        val n = sc.nextInt()
        val grid = Array(n) { CharArray(n) }
        var si = 0
        var sj = 0
        var di = 0
        var dj = 0

        for (i in 0..<n) {
            val row = sc.next().toCharArray()
            grid[i] = row
            for (j in 0..<n) {
                if (row[j] == 'S') {
                    si = i
                    sj = j
                } else if (row[j] == 'E') {
                    di = i
                    dj = j
                }
            }
        }

        val visited = Array(n) { BooleanArray(n) }
        val level = Array(n) { IntArray(n) { -1 } }

        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.addLast(si to sj)
        visited[si][sj] = true
        level[si][sj] = 0

        while (queue.isNotEmpty()) {
            val (r, c) = queue.removeFirst()
            for ((dr, dc) in dirs) {
                val nr = r + dr
                val nc = c + dc
                if (nr in 0..<n && nc in 0..<n && !visited[nr][nc] && grid[nr][nc] != 'T') {
                    visited[nr][nc] = true
                    level[nr][nc] = level[r][c] + 1
                    queue.addLast(nr to nc)
                }
            }
        }

        println(level[di][dj])
    }
}
