import java.util.Scanner

/**
 * Module 2 — Shortest Path Printing via BFS Parent Tracking
 *
 * Sample Input:
 * 5 5
 * 0 1
 * 0 2
 * 1 3
 * 2 3
 * 3 4
 * 0 4
 *
 * Expected Output:
 * 0 1 3 4
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

    val src = sc.nextInt()
    val dst = sc.nextInt()
    val visited = BooleanArray(n)
    val level = IntArray(n) { -1 }
    val parent = IntArray(n) { -1 }

    fun bfs(start: Int) {
        val queue = ArrayDeque<Int>()
        queue.addLast(start)
        visited[start] = true
        level[start] = 0

        while (queue.isNotEmpty()) {
            val par = queue.removeFirst()
            for (child in adjList[par]) {
                if (!visited[child]) {
                    queue.addLast(child)
                    visited[child] = true
                    level[child] = level[par] + 1
                    parent[child] = par
                }
            }
        }
    }

    bfs(src)

    val path = ArrayList<Int>()
    var node = dst
    while (node != -1) {
        path.add(node)
        node = parent[node]
    }
    path.reverse()
    println(path.joinToString(" "))
}
