import java.util.Scanner

fun numIslands(grid: Array<CharArray>): Int {
    val n = grid.size
    val m = grid[0].size

    fun valid(r: Int, c: Int): Boolean {
        return r in 0..<n && c in 0..<m
    }

    val dirs = arrayOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
    val visited = Array(n) { BooleanArray(m) }

    fun dfs(r: Int, c: Int) {
        visited[r][c] = true
        for ((dr, dc) in dirs) {
            val nr = r + dr
            val nc = c + dc
            if (valid(nr, nc) && !visited[nr][nc] && grid[nr][nc] == '1') {
                dfs(nr, nc)
            }
        }
    }

    var counts = 0
    for (r in 0..<n) {
        for (c in 0..<m) {
            if (!visited[r][c] && grid[r][c] == '1') {
                dfs(r, c)
                counts++
            }
        }
    }

    return counts
}

fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val m = sc.nextInt()
    val grid = Array(n) { sc.next().toCharArray() }
    println(numIslands(grid))
}
