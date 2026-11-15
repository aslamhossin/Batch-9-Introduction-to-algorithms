import java.util.Scanner

fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val e = sc.nextInt()

    val adj = Array(n) { ArrayList<Int>() }

    repeat(e) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        adj[a].add(b)
        adj[b].add(a)
    }

    val source = sc.nextInt()
    val destination = sc.nextInt()

    val visited = BooleanArray(n)

    fun dfs(src: Int) {
        visited[src] = true
        if (src == destination) return
        for (child in adj[src]) {
            if (!visited[child]) {
                dfs(child)
            }
        }
    }
    dfs(source)
    println(visited[destination])
}
