import java.util.Scanner

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

    val di = sc.nextInt()
    val dj = sc.nextInt()

    val visited = Array(n) { BooleanArray(m) }
    val dist = Array(n) { IntArray(m) { -1 } }
    val q = ArrayDeque<Pair<Int, Int>>()

    val dr = intArrayOf(-1, 1, 0, 0)
    val dc = intArrayOf(0, 0, -1, 1)

    fun valid(r: Int, c: Int): Boolean {
        return r in 0..<n && c in 0..<m
    }

    q.addLast(Pair(si, sj))
    dist[si][sj] = 0
    visited[si][sj] = true

    while (q.isNotEmpty()) {
        val (r, c) = q.removeFirst()
        for (d in 0..<4) {
            val ni = r + dr[d]
            val nj = c + dc[d]

            if (valid(ni, nj) && !visited[ni][nj] && grid[ni][nj] == '.') {
                visited[ni][nj] = true
                dist[ni][nj] = dist[r][c] + 1
                q.addLast(Pair(ni, nj))
            }
        }
    }

    println(dist[di][dj])
}
