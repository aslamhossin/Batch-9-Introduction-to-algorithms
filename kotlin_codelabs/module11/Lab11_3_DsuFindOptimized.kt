/**
 * Module 11 — Disjoint Set Union (DSU) Find with Path Compression
 *
 * Expected Output:
 * 1
 * 0 -> 1
 * 1 -> -1
 * 2 -> 1
 * 3 -> 1
 * 4 -> 1
 * 5 -> 1
 */
fun main() {
    val par = IntArray(1005) { -1 }
    par[0] = 1
    par[1] = -1
    par[2] = 1
    par[3] = 1
    par[4] = 5
    par[5] = 3

    fun find(node: Int): Int {
        if (par[node] == -1) return node
        val leader = find(par[node])
        par[node] = leader
        return leader
    }

    println(find(4))
    for (i in 0..<6) {
        println("$i -> ${par[i]}")
    }
}
