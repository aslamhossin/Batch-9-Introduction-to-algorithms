import java.util.Scanner

/**
 * Module 1 — Adjacency List (Directed Graph)
 *
 * Sample Input:
 * 4 3
 * 0 1
 * 1 2
 * 2 3
 *
 * Expected Output:
 * 0 -> 1
 * 1 -> 2
 * 2 -> 3
 * 3 ->
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

    for (i in 0..<n) {
        if (adjList[i].isEmpty()) {
            println("$i ->")
        } else {
            println("$i -> ${adjList[i].joinToString(" ")}")
        }
    }
}
