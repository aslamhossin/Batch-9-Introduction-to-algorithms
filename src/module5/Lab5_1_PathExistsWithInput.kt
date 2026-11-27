import java.util.Scanner

fun validPath(n: Int, edges: Array<IntArray>, source: Int, destination: Int): Boolean {
    val adj = Array(n) { ArrayList<Int>() }
    for ((a, b) in edges) {
        adj[a].add(b)
        adj[b].add(a)
    }
    val visited = BooleanArray(n)
    fun dfs(node: Int) {
        visited[node] = true
        for (a in adj[node]) {
            if (!visited[a]) {
                dfs(a)
            }
        }
    }
    dfs(source)
    return visited[destination]
}

fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val e = sc.nextInt()
    val edges = Array(e) { intArrayOf(sc.nextInt(), sc.nextInt()) }
    val source = sc.nextInt()
    val destination = sc.nextInt()
    println(validPath(n, edges, source, destination))
}
