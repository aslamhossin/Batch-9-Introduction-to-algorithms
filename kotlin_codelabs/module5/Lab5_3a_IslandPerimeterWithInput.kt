import java.util.Scanner

fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val m = sc.nextInt()
    val grid = Array(n) { CharArray(m) }
    for (i in 0..<n) {
        grid[i] = sc.next().toCharArray()
    }

    val visited = Array(n) { BooleanArray(m) }
    val dr = intArrayOf(-1, 1, 0, 0)
    val dc = intArrayOf(0, 0, -1, 1)

    fun valid(r: Int, c: Int): Boolean {
        return r in 0..<n && c in 0..<m
    }

    var perimeter = 0

    fun dfs(r: Int, c: Int) {
        visited[r][c] = true
        for (d in dr.indices) {
            val nr = r + dr[d]
            val nc = c + dc[d]
            if (!valid(nr, nc) || grid[nr][nc] == '0') {
                perimeter++
            } else if (grid[nr][nc] == '1' && !visited[nr][nc]) {
                dfs(nr, nc)
            }
        }
    }

    for (i in 0..<n) {
        for (j in 0..<m) {
            if ((grid[i][j] == '1') && !visited[i][j]) {
                dfs(i, j)
            }
        }
    }
    println(perimeter)
}
