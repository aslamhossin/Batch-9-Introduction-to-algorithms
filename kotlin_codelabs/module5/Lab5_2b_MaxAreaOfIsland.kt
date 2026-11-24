import java.util.Scanner
import kotlin.math.max

fun maxAreaOfIsland(grid: Array<IntArray>): Int {
    val row = grid.size
    val column = grid[0].size
    val visited = Array(row) { BooleanArray(column) }
    val directions = arrayOf(0 to -1, 0 to 1, -1 to 0, 1 to 0)

    fun valid(r: Int, c: Int): Boolean {
        return r in 0..<row && c in 0..<column
    }

    var maxAreaOfIsland = 0
    var currentArea = 0
    fun dfs(r: Int, c: Int) {
        visited[r][c] = true
        currentArea++
        for ((dr, dc) in directions) {
            val nr = r + dr
            val nc = c + dc
            if (valid(nr, nc) && !visited[nr][nc] && grid[nr][nc] == 1) {
                dfs(nr, nc)
            }
        }
    }

    for (r in 0..<row) {
        for (c in 0..<column) {
            if (!visited[r][c] && grid[r][c] == 1) {
                currentArea = 0
                dfs(r, c)
                maxAreaOfIsland = max(maxAreaOfIsland, currentArea)
            }
        }
    }
    return maxAreaOfIsland
}

fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val m = sc.nextInt()
    val grid = Array(n) { IntArray(m) }
    for (i in 0..<n) {
        val row = sc.next().toCharArray()
        for (j in 0..<m) {
            grid[i][j] = row[j] - '0'
        }
    }
    println(maxAreaOfIsland(grid))
}
