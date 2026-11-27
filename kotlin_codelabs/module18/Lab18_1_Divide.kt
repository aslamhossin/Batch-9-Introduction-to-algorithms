import java.util.Scanner

/**
 * Module 18 — Divide Step Visualization (Recursive Array Splitting)
 *
 * Sample Input:
 * 4
 * 10 20 30 40
 *
 * Expected Output:
 * 10 20 30 40
 * 10 20
 * 10
 * 20
 * 30 40
 * 30
 * 40
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val arr = IntArray(n) { sc.nextInt() }

    fun divide(l: Int, r: Int) {
        println((l..r).joinToString(" ") { arr[it].toString() })
        if (l >= r) return
        val mid = (l + r) / 2
        divide(l, mid)
        divide(mid + 1, r)
    }

    if (n > 0) divide(0, n - 1)
}
