import java.util.Scanner

fun islandPerimeter(grid: Array<IntArray>): Int {
    val n = grid.size
    val m = grid[0].size

    val visited = Array(n) { BooleanArray(m) }
    fun valid(r: Int, c: Int): Boolean {
        return r in 0..<n && c in 0..<m
    }

    val dirs = arrayOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
    var perimeter = 0
    fun dfs(r: Int, c: Int) {
        visited[r][c] = true
        for ((dr, dc) in dirs) {
            val nr = r + dr
            val nc = c + dc
            if (!valid(nr, nc) || grid[nr][nc] == 0) {
                perimeter++
            } else if (!visited[nr][nc]) {
                dfs(nr, nc)
            }
        }
    }

    for (i in 0..<n) {
        for (j in 0..<m) {
            if (!visited[i][j] && grid[i][j] == 1) {
                dfs(i, j)
            }
        }
    }

    return perimeter
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
    println(islandPerimeter(grid))
}
