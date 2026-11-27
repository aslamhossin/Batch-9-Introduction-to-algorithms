/**
 * Module 11 — Disjoint Set Union (DSU) Find using Loop
 *
 * Expected Output:
 * 1
 */
fun main() {
    val par = IntArray(1005) { -1 }
    par[0] = 1
    par[1] = -1
    par[2] = 1
    par[3] = 1
    par[4] = 5
    par[5] = 3

    fun find(startNode: Int): Int {
        var node = startNode
        while (par[node] != -1) {
            node = par[node]
        }
        return node
    }

    println(find(4))
}
