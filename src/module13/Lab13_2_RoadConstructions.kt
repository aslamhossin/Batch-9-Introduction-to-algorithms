import java.util.Scanner

/**
 * Module 13 — Road Construction (DSU Component Count & Max Component Size)
 *
 * Sample Input:
 * 5 3
 * 1 2
 * 1 3
 * 4 5
 *
 * Expected Output:
 * 4 2
 * 3 3
 * 2 3
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val e = sc.nextInt()

    val par = IntArray(n + 1) { -1 }
    val groupSize = IntArray(n + 1) { 1 }
    var components = n
    var maxSize = 1

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
            maxSize = maxOf(maxSize, groupSize[leader1])
        } else {
            par[leader1] = leader2
            groupSize[leader2] += groupSize[leader1]
            maxSize = maxOf(maxSize, groupSize[leader2])
        }
        components--
    }

    repeat(e) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        dsuUnion(a, b)
        println("$components $maxSize")
    }
}
