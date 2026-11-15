import java.util.Scanner

/**
 * Module 3 — Codelab 3.3: 2D Grid BFS Shortest Path
 * রেফারেন্স: Module 3/bfs_on_2d_grid.cpp এর সম্পূর্ণ Kotlin রূপান্তর।
 *
 * নমুনা Input:
 * 3 3
 * ...
 * .#.
 * ...
 * 0 0 0 2
 *
 * আউটপুট:
 * 2
 */
fun main() {
    val sc = Scanner(System.`in`)

    // ধাপ ১: ম্যাপ (grid), শুরুর ঘর (si, sj) ও গন্তব্য (di, dj) ইনপুট নেওয়া
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

    fun valid(r: Int, c: Int): Boolean {
        return r in 0..<n && c in 0..<m
    }

    // ধাপ ২: visited এবং দূরত্ব রাখার ছক dist (শুরুতে সব ঘরে -1)
    val visited = Array(n) { BooleanArray(m) }
    val dist = Array(n) { IntArray(m) { -1 } }
    val dr = intArrayOf(-1, 1, 0, 0)
    val dc = intArrayOf(0, 0, -1, 1)

    // ধাপ ৩: Queue (ArrayDeque) দিয়ে ঢেউয়ের মতো চারপাশে ছড়ানো
    val q = ArrayDeque<Pair<Int, Int>>()
    q.addLast(Pair(si, sj))
    visited[si][sj] = true
    dist[si][sj] = 0

    while (q.isNotEmpty()) {
        val (r, c) = q.removeFirst()

        for (d in 0..<4) {
            val nr = r + dr[d]
            val nc = c + dc[d]

            if (valid(nr, nc) && !visited[nr][nc] && grid[nr][nc] == '.') {
                visited[nr][nc] = true
                dist[nr][nc] = dist[r][c] + 1
                q.addLast(Pair(nr, nc))
            }
        }
    }

    // ধাপ ৪: গন্তব্য ঘরের সবচেয়ে কম কদম প্রিন্ট করা (যেতে না পারলে -1 প্রিন্ট হবে)
    println(dist[di][dj])
}
