import java.util.Scanner

/**
 * Module 13 — Roads Not Only in Berland (DSU Redundant Edges & Reconnection)
 *
 * Sample Input:
 * 7
 * 1 2
 * 2 3
 * 3 1
 * 4 5
 * 5 6
 * 6 7
 *
 * Expected Output:
 * 1
 * 3 1 1 4
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()

    val par = IntArray(n + 1) { -1 }
    val groupSize = IntArray(n + 1) { 1 }

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

    val removeEdges = ArrayList<Pair<Int, Int>>()
    val createEdges = ArrayList<Pair<Int, Int>>()

    repeat(n - 1) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        if (find(a) == find(b)) {
            removeEdges.add(a to b)
        } else {
            dsuUnion(a, b)
        }
    }

    for (i in 2..n) {
        if (find(1) != find(i)) {
            createEdges.add(1 to i)
            dsuUnion(1, i)
        }
    }

    println(removeEdges.size)
    for (i in removeEdges.indices) {
        println("${removeEdges[i].first} ${removeEdges[i].second} ${createEdges[i].first} ${createEdges[i].second}")
    }
}
