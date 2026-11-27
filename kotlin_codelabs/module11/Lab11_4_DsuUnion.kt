/**
 * Module 11 — Disjoint Set Union (DSU) Union by Size + Path Compression
 *
 * Expected Output:
 * 0 -> 1
 * 1 -> -1
 * 2 -> 1
 * 3 -> 1
 * 4 -> -1
 * 5 -> -1
 */
fun main() {
    val par = IntArray(1005) { -1 }
    val groupSize = IntArray(1005) { 1 }

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

    dsuUnion(1, 2)
    dsuUnion(2, 0)
    dsuUnion(3, 1)

    for (i in 0..<6) {
        println("$i -> ${par[i]}")
    }
}
