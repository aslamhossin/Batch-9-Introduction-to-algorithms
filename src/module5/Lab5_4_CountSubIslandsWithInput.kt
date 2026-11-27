import java.util.Scanner

fun countSubIslands(grid1: Array<IntArray>, grid2: Array<IntArray>): Int {
    val n = grid1.size
    val m = grid1[0].size
    val dirs = arrayOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
    val visited = Array(n) { BooleanArray(m) }

    fun valid(r: Int, c: Int): Boolean {
        return r in 0..<n && c in 0..<m
    }

    var isSubIsland = true

    fun dfs(r: Int, c: Int) {
        visited[r][c] = true
        if (grid1[r][c] == 0) {
            isSubIsland = false
        }
        for ((dr, dc) in dirs) {
            val nr = r + dr
            val nc = c + dc
            if (valid(nr, nc) && grid2[nr][nc] == 1 && !visited[nr][nc]) {
                dfs(nr, nc)
            }
        }
    }

    var subIslands = 0
    for (r in 0..<n) {
        for (c in 0..<m) {
            if (!visited[r][c] && grid2[r][c] == 1) {
                isSubIsland = true
                dfs(r, c)
                if (isSubIsland) {
                    subIslands++
                }
            }
        }
    }
    return subIslands
}

fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val m = sc.nextInt()

    val grid1 = Array(n) { IntArray(m) }
    for (i in 0..<n) {
        val row = sc.next().toCharArray()
        for (j in 0..<m) {
            grid1[i][j] = row[j] - '0'
        }
    }

    val grid2 = Array(n) { IntArray(m) }
    for (i in 0..<n) {
        val row = sc.next().toCharArray()
        for (j in 0..<m) {
            grid2[i][j] = row[j] - '0'
        }
    }

    println(countSubIslands(grid1, grid2))
}
