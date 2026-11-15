import java.util.Scanner

/**
 * Module 5 — Codelab 5.1: Find if Path Exists in Graph (LeetCode #1971)
 * রেফারেন্স: Module 5/find_if_path_exists_in_graph.cpp এর সম্পূর্ণ Kotlin রূপান্তর।
 *
 * নমুনা Input (পথ নেই -> false):
 * 6 4
 * 0 1
 * 0 2
 * 3 5
 * 5 4
 * 0 5
 *
 * আউটপুট:
 * false
 */
fun main() {
    val sc = Scanner(System.`in`)

    // ধাপ ১: n (শহর সংখ্যা) ও e (রাস্তা সংখ্যা) নিয়ে 1D Adjacency List বানানো
    val n = sc.nextInt()
    val e = sc.nextInt()
    val graph = Array(n) { ArrayList<Int>() }

    repeat(e) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        graph[a].add(b)
        graph[b].add(a)
    }

    // ধাপ ২: শুরুর শহর (source) ও গন্তব্য শহর (destination) ইনপুট নেওয়া
    val source = sc.nextInt()
    val destination = sc.nextInt()

    // ধাপ ৩: 1D visited[] অ্যারে নিয়ে শুধু source থেকে DFS চালানো
    val visited = BooleanArray(n)

    fun dfs(src: Int) {
        visited[src] = true
        for (child in graph[src]) {
            if (!visited[child]) {
                dfs(child)
            }
        }
    }

    dfs(source)

    // ধাপ ৪: destination শহরটি visited হয়েছে কি না তা প্রিন্ট করা
    println(visited[destination])
}
