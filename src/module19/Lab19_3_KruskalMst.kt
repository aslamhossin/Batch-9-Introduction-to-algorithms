import java.util.Scanner

/**
 * Module 19 — Kruskal's Minimum Spanning Tree (MST) using DSU
 *
 * Sample Input:
 * 4 5
 * 0 1 10
 * 0 2 6
 * 0 3 5
 * 1 3 15
 * 2 3 4
 *
 * Expected Output:
 * 19
 */
fun main() {
    data class Edge(val a: Int, val b: Int, val c: Int)

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

    val edgeList = ArrayList<Edge>(e)
    repeat(e) {
        edgeList.add(Edge(sc.nextInt(), sc.nextInt(), sc.nextInt()))
    }

    edgeList.sortBy { it.c }

    var totalCost = 0
    for (ed in edgeList) {
        if (find(ed.a) != find(ed.b)) {
            dsuUnion(ed.a, ed.b)
            totalCost += ed.c
        }
    }

    println(totalCost)
}
