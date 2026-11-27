import java.util.Scanner

fun closedIsland(grid: Array<IntArray>): Int {
    var closedIslands = 0
    val n = grid.size
    val m = grid[0].size
    val visited = Array(n) { BooleanArray(m) }
    val dirs = arrayOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    fun valid(r: Int, c: Int) = r in 0..<n && c in 0..<m

    var isClosed = true

    fun dfs(r: Int, c: Int) {
        visited[r][c] = true
        for ((dr, dc) in dirs) {
            val nr = r + dr
            val nc = c + dc
            if (!valid(nr, nc)) {
                isClosed = false
            } else if (!visited[nr][nc] && grid[nr][nc] == 0) {
                dfs(nr, nc)
            }
        }
    }

    for (r in 0..<n) {
        for (c in 0..<m) {
            if (!visited[r][c] && grid[r][c] == 0) {
                isClosed = true
                dfs(r, c)
                if (isClosed) {
                    closedIslands++
                }
            }
        }
    }
    return closedIslands
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

    println(closedIsland(grid))
}
