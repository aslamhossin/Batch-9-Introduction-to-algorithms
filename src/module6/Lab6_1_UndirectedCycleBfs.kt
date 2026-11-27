import java.util.Scanner

/**
 * Module 6 — Undirected Graph Cycle Detection using BFS
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
    repeat(e) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        adjList[a].add(b)
        adjList[b].add(a)
    }

    val visited = BooleanArray(n)
    val parent = IntArray(n) { -1 }
    var cycle = false

    fun bfs(src: Int) {
        val queue = ArrayDeque<Int>()
        queue.addLast(src)
        visited[src] = true

        while (queue.isNotEmpty()) {
            val par = queue.removeFirst()
            for (child in adjList[par]) {
                if (visited[child] && parent[par] != child) {
                    cycle = true
                }
                if (!visited[child]) {
                    queue.addLast(child)
                    visited[child] = true
                    parent[child] = par
                }
            }
        }
    }

    for (i in 0..<n) {
        if (!visited[i]) {
            bfs(i)
        }
    }

    println(if (cycle) "Cycle Detected" else "No Cycle")
}
