import java.util.Scanner

/**
 * Module 9 — Negative Weighted Cycle Detection using Bellman-Ford
 *
 * Sample Input:
 * 3 3
 * 0 1 2
 * 1 2 -5
 * 2 0 1
 *
 * Expected Output:
 * Negative weighted cycle detected
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

    var cycle = false
    for (ed in edgeList) {
        if (dist[ed.a] != Int.MAX_VALUE && dist[ed.a] + ed.c < dist[ed.b]) {
            cycle = true
            break
        }
    }

    if (cycle) {
        println("Negative weighted cycle detected")
    } else {
        for (i in 0..<n) {
            println("$i -> ${dist[i]}")
        }
    }
}
