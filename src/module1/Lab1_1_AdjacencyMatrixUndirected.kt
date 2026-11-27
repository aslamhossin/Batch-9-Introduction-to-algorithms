import java.util.Scanner

/**
 * Module 1 — Adjacency Matrix (Undirected Graph)
 *
 * Sample Input:
 * 4 3
 * 0 1
 * 1 2
 * 2 3
 *
 * Expected Output:
 * 1 1 0 0
 * 1 1 1 0
 * 0 1 1 1
 * 0 0 1 1
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val e = sc.nextInt()

    val adjMat = Array(n) { IntArray(n) }
    for (i in 0..<n) {
        adjMat[i][i] = 1
    }

    repeat(e) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        adjMat[a][b] = 1
        adjMat[b][a] = 1
    }

    for (i in 0..<n) {
        println(adjMat[i].joinToString(" "))
    }
}
