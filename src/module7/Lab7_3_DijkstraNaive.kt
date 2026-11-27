import java.util.Scanner

/**
 * Module 7 — Naive Dijkstra (Queue-based Shortest Path on Weighted Graph)
 *
 * Sample Input:
 * 4 4
 * 0 1 4
 * 0 2 1
 * 2 1 2
 * 1 3 1
 *
 * Expected Output:
 * 0 -> 0
 * 1 -> 3
 * 2 -> 1
 * 3 -> 4
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val e = sc.nextInt()

    val adjList = Array(n) { ArrayList<Pair<Int, Int>>() }
    repeat(e) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        val c = sc.nextInt()
        adjList[a].add(b to c)
        adjList[b].add(a to c)
    }

    val dist = IntArray(n) { Int.MAX_VALUE }

    fun dijkstra(src: Int) {
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.addLast(src to 0)
        dist[src] = 0

        while (queue.isNotEmpty()) {
            val (parNode, parDist) = queue.removeFirst()
            for ((childNode, edgeWeight) in adjList[parNode]) {
                if (parDist + edgeWeight < dist[childNode]) {
                    dist[childNode] = parDist + edgeWeight
                    queue.addLast(childNode to dist[childNode])
                }
            }
        }
    }

    dijkstra(0)
    for (i in 0..<n) {
        println("$i -> ${dist[i]}")
    }
}
