import java.util.Scanner

/**
 * Module 10 — Floyd-Warshall All-Pairs Shortest Path
 *
 * Sample Input:
 * 4 4
 * 0 1 5
 * 1 2 3
 * 2 3 1
 * 0 3 10
 *
 * Expected Output:
 * 0 5 8 9
 * INF 0 3 4
 * INF INF 0 1
 * INF INF INF 0
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val e = sc.nextInt()

    val inf = Int.MAX_VALUE
    val adjMat = Array(n) { i ->
        IntArray(n) { j -> if (i == j) 0 else inf }
    }

    repeat(e) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        val c = sc.nextInt()
        adjMat[a][b] = c
    }

    for (k in 0..<n) {
        for (i in 0..<n) {
            for (j in 0..<n) {
                if (adjMat[i][k] != inf && adjMat[k][j] != inf && adjMat[i][k] + adjMat[k][j] < adjMat[i][j]) {
                    adjMat[i][j] = adjMat[i][k] + adjMat[k][j]
                }
            }
        }
    }

    for (i in 0..<n) {
        println(adjMat[i].joinToString(" ") { if (it == inf) "INF" else it.toString() })
    }
}
