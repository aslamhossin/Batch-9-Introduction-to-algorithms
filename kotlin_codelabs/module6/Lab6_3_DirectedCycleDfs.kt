import java.util.Scanner

/**
 * Module 6 — Directed Graph Cycle Detection using DFS (pathVisited tracking)
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
    }

    val visited = BooleanArray(n)
    val pathVisited = BooleanArray(n)
    var cycle = false

    fun dfs(src: Int) {
        visited[src] = true
        pathVisited[src] = true
        for (child in adjList[src]) {
            if (visited[child] && pathVisited[child]) {
                cycle = true
            }
            if (!visited[child]) {
                dfs(child)
            }
        }
        pathVisited[src] = false
    }

    for (i in 0..<n) {
        if (!visited[i]) {
            dfs(i)
        }
    }

    println(if (cycle) "Cycle Detected" else "No Cycle")
}
