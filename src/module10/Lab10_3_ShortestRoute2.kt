import java.util.Scanner

/**
 * Module 10 — Shortest Routes II (1-Based Undirected All-Pairs Shortest Path with Long)
 *
 * Sample Input:
 * 4 3 5
 * 1 2 5
 * 1 3 9
 * 2 3 3
 * 1 2
 * 2 1
 * 1 3
 * 1 4
 * 3 2
 *
 * Expected Output:
 * 5
 * 5
 * 8
 * -1
 * 3
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val e = sc.nextInt()
    val q = sc.nextInt()

    val inf = Long.MAX_VALUE
    val adjMat = Array(n + 1) { i ->
        LongArray(n + 1) { j -> if (i == j) 0L else inf }
    }

    repeat(e) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        val c = sc.nextLong()
        adjMat[a][b] = minOf(adjMat[a][b], c)
        adjMat[b][a] = minOf(adjMat[b][a], c)
    }

    for (k in 1..n) {
        for (i in 1..n) {
            for (j in 1..n) {
                if (adjMat[i][k] != inf && adjMat[k][j] != inf && adjMat[i][k] + adjMat[k][j] < adjMat[i][j]) {
                    adjMat[i][j] = adjMat[i][k] + adjMat[k][j]
                }
            }
        }
    }

    repeat(q) {
        val s = sc.nextInt()
        val d = sc.nextInt()
        println(if (adjMat[s][d] == inf) -1 else adjMat[s][d])
    }
}
