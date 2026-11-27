import java.util.Scanner

/**
 * Module 7 — Adjacency List for Weighted Undirected Graph
 *
 * Sample Input:
 * 4 3
 * 0 1 10
 * 1 2 5
 * 2 3 8
 *
 * Expected Output:
 * 0 -> (1, 10)
 * 1 -> (0, 10), (2, 5)
 * 2 -> (1, 5), (3, 8)
 * 3 -> (2, 8)
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val e = sc.nextInt()

    val adjList = Array(n) { ArrayList<Pair<Int, Int>>() }
    repeat(e) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        val c = sc.nextInt()
        adjList[a].add(b to c)
        adjList[b].add(a to c)
    }

    for (i in 0..<n) {
        val edges = adjList[i].joinToString(", ") { "(${it.first}, ${it.second})" }
        println("$i -> $edges")
    }
}
