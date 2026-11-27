import java.util.Scanner

/**
 * Module 10 — Negative Weighted Cycle Detection using Floyd-Warshall
 *
 * Sample Input:
 * 3 3
 * 0 1 2
 * 1 2 -5
 * 2 0 1
 *
 * Expected Output:
 * Negative weighted cycle detected.
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

    val cycle = (0..<n).any { adjMat[it][it] < 0 }
    if (cycle) {
        println("Negative weighted cycle detected.")
    } else {
        for (i in 0..<n) {
            println(adjMat[i].joinToString(" ") { if (it == inf) "INF" else it.toString() })
        }
    }
}
