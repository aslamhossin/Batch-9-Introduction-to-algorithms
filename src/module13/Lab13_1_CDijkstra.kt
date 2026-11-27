import java.util.PriorityQueue
import java.util.Scanner

/**
 * Module 13 — Dijkstra? (1-Based Shortest Path 1 -> n with Path Printing)
 *
 * Sample Input:
 * 5 6
 * 1 2 2
 * 2 5 5
 * 2 3 4
 * 1 4 1
 * 4 3 3
 * 3 5 1
 *
 * Expected Output:
 * 1 4 3 5
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val e = sc.nextInt()

    val adjList = Array(n + 1) { ArrayList<Pair<Int, Long>>() }
    repeat(e) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        val c = sc.nextLong()
        adjList[a].add(b to c)
        adjList[b].add(a to c)
    }

    val inf = Long.MAX_VALUE
    val dist = LongArray(n + 1) { inf }
    val parent = IntArray(n + 1) { -1 }

    val pq = PriorityQueue(compareBy<Pair<Long, Int>> { it.first }.thenBy { it.second })
    pq.add(0L to 1)
    dist[1] = 0L

    while (pq.isNotEmpty()) {
        val (parDist, parNode) = pq.poll()
        if (parDist > dist[parNode]) continue

        for ((childNode, weight) in adjList[parNode]) {
            if (parDist + weight < dist[childNode]) {
                dist[childNode] = parDist + weight
                parent[childNode] = parNode
                pq.add(dist[childNode] to childNode)
            }
        }
    }

    if (dist[n] == inf) {
        println(-1)
    } else {
        val path = ArrayList<Int>()
        var node = n
        while (node != -1) {
            path.add(node)
            node = parent[node]
        }
        path.reverse()
        println(path.joinToString(" "))
    }
}
