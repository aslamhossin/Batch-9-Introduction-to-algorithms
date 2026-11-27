import java.util.Scanner

/**
 * Module 2 — Check if Destination Node is Reachable via BFS
 *
 * Sample Input:
 * 5 3
 * 0 1
 * 1 2
 * 3 4
 * 0 2
 *
 * Expected Output:
 * YES
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

    fun bfs(start: Int) {
        val queue = ArrayDeque<Int>()
        queue.addLast(start)
        visited[start] = true

        while (queue.isNotEmpty()) {
            val par = queue.removeFirst()
            for (child in adjList[par]) {
                if (!visited[child]) {
                    queue.addLast(child)
                    visited[child] = true
                }
            }
        }
    }

    bfs(src)
    println(if (visited[dst]) "YES" else "NO")
}
