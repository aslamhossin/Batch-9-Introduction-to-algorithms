import java.util.Scanner

/**
 * Module 1 — Edge List
 *
 * Sample Input:
 * 4 3
 * 0 1
 * 1 2
 * 2 3
 *
 * Expected Output:
 * 0 1
 * 1 2
 * 2 3
 */
fun main() {
    val sc = Scanner(System.`in`)
    sc.nextInt() // n (node count)
    val e = sc.nextInt()

    val edgeList = ArrayList<Pair<Int, Int>>(e)
    repeat(e) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        edgeList.add(a to b)
    }

    for ((u, v) in edgeList) {
        println("$u $v")
    }
}
