import java.util.Scanner

/**
 * Module 11 — Undirected Graph Cycle Detection using DSU
 *
 * Sample Input:
 * 3 3
 * 0 1
 * 1 2
 * 2 0
 *
 * Expected Output:
 * Cycle Detected.
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val e = sc.nextInt()

    val par = IntArray(n) { -1 }
    val groupSize = IntArray(n) { 1 }

    fun find(node: Int): Int {
        if (par[node] == -1) return node
        val leader = find(par[node])
        par[node] = leader
        return leader
    }

    fun dsuUnion(node1: Int, node2: Int) {
        val leader1 = find(node1)
        val leader2 = find(node2)
        if (leader1 == leader2) return

        if (groupSize[leader1] >= groupSize[leader2]) {
            par[leader2] = leader1
            groupSize[leader1] += groupSize[leader2]
        } else {
            par[leader1] = leader2
            groupSize[leader2] += groupSize[leader1]
        }
    }

    var cycle = false
    repeat(e) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        if (find(a) == find(b)) {
            cycle = true
        } else {
            dsuUnion(a, b)
        }
    }

    println(if (cycle) "Cycle Detected." else "No cycle.")
}
