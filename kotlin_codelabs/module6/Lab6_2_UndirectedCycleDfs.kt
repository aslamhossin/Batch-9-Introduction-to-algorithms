import java.util.Scanner

/**
 * Module 6 — Undirected Graph Cycle Detection using DFS
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

    fun dfs(src: Int) {
        visited[src] = true
        for (child in adjList[src]) {
            if (visited[child] && parent[src] != child) {
                cycle = true
            }
            if (!visited[child]) {
                parent[child] = src
                dfs(child)
            }
        }
    }

    for (i in 0..<n) {
        if (!visited[i]) {
            dfs(i)
        }
    }

    println(if (cycle) "Cycle Detected" else "No Cycle")
}
