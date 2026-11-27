import java.util.Scanner

/**
 * Module 2 — Breadth-First Search (BFS) Traversal
 *
 * Sample Input:
 * 5 4
 * 0 1
 * 0 2
 * 1 3
 * 2 4
 *
 * Expected Output:
 * 0 1 2 3 4
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
    val order = ArrayList<Int>()

    fun bfs(src: Int) {
        val queue = ArrayDeque<Int>()
        queue.addLast(src)
        visited[src] = true

        while (queue.isNotEmpty()) {
            val par = queue.removeFirst()
            order.add(par)

            for (child in adjList[par]) {
                if (!visited[child]) {
                    queue.addLast(child)
                    visited[child] = true
                }
            }
        }
    }

    bfs(0)
    println(order.joinToString(" "))
}
