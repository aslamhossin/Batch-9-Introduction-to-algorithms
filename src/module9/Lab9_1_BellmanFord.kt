import java.util.Scanner

/**
 * Module 9 — Bellman-Ford Single-Source Shortest Path (Supports Negative Weights)
 *
 * Sample Input:
 * 4 4
 * 0 1 4
 * 0 2 5
 * 1 2 -2
 * 2 3 3
 *
 * Expected Output:
 * 0 -> 0
 * 1 -> 4
 * 2 -> 2
 * 3 -> 5
 */
fun main() {
    data class Edge(val a: Int, val b: Int, val c: Int)

    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val e = sc.nextInt()

    val edgeList = ArrayList<Edge>(e)
    repeat(e) {
        edgeList.add(Edge(sc.nextInt(), sc.nextInt(), sc.nextInt()))
    }

    val dist = IntArray(n) { Int.MAX_VALUE }
    dist[0] = 0

    repeat(n - 1) {
        for (ed in edgeList) {
            if (dist[ed.a] != Int.MAX_VALUE && dist[ed.a] + ed.c < dist[ed.b]) {
                dist[ed.b] = dist[ed.a] + ed.c
            }
        }
    }

    for (i in 0..<n) {
        println("$i -> ${dist[i]}")
    }
}
