import java.util.Scanner

/**
 * Module 3 — Codelab 3.1 (Input থেকে Graph তৈরি + DFS Count)
 * রেফারেন্স: Module 3/dfs.cpp এর সম্পূর্ণ Kotlin রূপান্তর।
 *
 * নমুনা Input:
 * 5 3
 * 0 1
 * 1 2
 * 3 4
 *
 * আউটপুট:
 * 3
 */
fun main(args: Array<String>) {
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

    val visited = BooleanArray(n)
    var count = 0

    fun dfs(src: Int) {
        visited[src] = true
        count++

        for (child in adj[src]) {
            if (visited[child].not()) {
                dfs(child)
            }
        }
    }

    dfs(0)
    println(count)
}
