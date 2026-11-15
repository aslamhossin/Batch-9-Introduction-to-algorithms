import java.util.Scanner

/**
 * Module 3 — Codelab 3.4: Number of Connected Components
 * রেফারেন্স: Module 3/number_of_components.cpp এর সম্পূর্ণ Kotlin রূপান্তর।
 *
 * নমুনা Input:
 * 7 4
 * 0 1
 * 1 2
 * 3 4
 * 5 6
 *
 * আউটপুট:
 * 3
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val e = sc.nextInt()
    val graph = Array(n) { ArrayList<Int>() }
    repeat(e) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        graph[a].add(b)
        graph[b].add(a)
    }

    val visited = BooleanArray(n)

    fun dfs(src: Int) {
        visited[src] = true
        for (child in graph[src]) {
            if (!visited[child]) {
                dfs(child)
            }
        }
    }

    var components = 0
    for (i in graph.indices) {
        if (!visited[i]) {
            components++
            dfs(i)
        }
    }
    println(components)
}
