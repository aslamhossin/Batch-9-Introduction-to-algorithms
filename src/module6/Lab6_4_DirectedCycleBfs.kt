import java.util.Scanner

/**
 * Module 6 — Directed Graph Cycle Detection using BFS (Kahn's Topological Sort)
 *
 * Sample Input:
 * 3 3
 * 0 1
 * 1 2
 * 2 0
 *
 * Expected Output:
 * Cycle Detected
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val e = sc.nextInt()

    val adjList = Array(n) { ArrayList<Int>() }
    val inDegree = IntArray(n)
    repeat(e) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        adjList[a].add(b)
        inDegree[b]++
    }

    val queue = ArrayDeque<Int>()
    for (i in 0..<n) {
        if (inDegree[i] == 0) {
            queue.addLast(i)
        }
    }

    val topo = ArrayList<Int>()
    while (queue.isNotEmpty()) {
        val par = queue.removeFirst()
        topo.add(par)
        for (child in adjList[par]) {
            inDegree[child]--
            if (inDegree[child] == 0) {
                queue.addLast(child)
            }
        }
    }

    println(if (topo.size != n) "Cycle Detected" else "No Cycle")
}
